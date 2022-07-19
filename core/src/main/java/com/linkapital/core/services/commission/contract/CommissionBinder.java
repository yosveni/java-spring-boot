package com.linkapital.core.services.commission.contract;

import com.linkapital.core.services.commission.contract.enums.InstallmentType;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignAttributeTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionInstallmentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionUserTO;
import com.linkapital.core.services.commission.contract.to.update.UpdateCommissionCampaignTO;
import com.linkapital.core.services.commission.datasource.domain.Commission;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaign;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignAttribute;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignCondition;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignUser;
import com.linkapital.core.services.commission.datasource.domain.CommissionInstallment;
import com.linkapital.core.services.commission.datasource.domain.CommissionPercent;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.util.functions.TriFunction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.IntStream;

import static com.linkapital.core.services.commission.contract.enums.CampaignConnector.OR;
import static com.linkapital.core.services.commission.contract.enums.InstallmentType.AMORTIZATION;
import static com.linkapital.core.services.commission.contract.enums.InstallmentType.DISBURSEMENT;
import static com.linkapital.core.services.commission.contract.enums.InstallmentType.LIQUIDATION;
import static com.linkapital.core.util.DateUtil.addMonth;
import static com.linkapital.core.util.json.JsonSerdes.convert;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;

@Mapper
public interface CommissionBinder {

    CommissionBinder COMMISSION_BINDER = Mappers.getMapper(CommissionBinder.class);

    ToDoubleBiFunction<Integer, List<CommissionPercent>> calculatePercent = (payments, percents) -> percents
            .stream()
            .filter(commissionPercent -> payments >= commissionPercent.getMonthMin() &&
                    payments <= commissionPercent.getMonthMax())
            .map(CommissionPercent::getPercent)
            .findFirst()
            .orElse(0D);

    Consumer<CommissionCampaign> setCommissionCampaignLimit = campaign -> {
        var limitCount = campaign.getLimit();
        if (limitCount != null)
            limitCount.setLimitCount(limitCount.getLimitCount() + 1);
    };

    BiConsumer<CommissionCampaign, User> setCommissionCampaignUser = (campaign, user) -> {
        var campaignUsers = campaign.getCampaignUsers();
        campaignUsers
                .stream()
                .filter(commissionCampaignUser -> commissionCampaignUser.getUser().getId().equals(user.getId()))
                .findFirst()
                .ifPresentOrElse(commissionCampaignUser ->
                                commissionCampaignUser.withCount(commissionCampaignUser.getCount() + 1),
                        () -> campaignUsers.add(new CommissionCampaignUser()
                                .withCampaign(campaign)
                                .withCount(1)
                                .withUser(user)));
    };

    Function<CommissionCampaign, List<List<CommissionCampaignCondition>>> buildConditions = source -> {
        var allConditions = new ArrayList<List<CommissionCampaignCondition>>();
        var andConditions = new ArrayList<CommissionCampaignCondition>();

        source.getConditions().forEach(condition -> {
            var connector = condition.getConnector();

            if (connector == null || connector == OR) {
                if (!andConditions.isEmpty()) {
                    andConditions.add(condition);
                    allConditions.add(andConditions);
                    andConditions.clear();
                } else
                    allConditions.add(singletonList(condition));
            } else
                andConditions.add(condition);
        });

        return allConditions;
    };

    Function<List<Offer>, List<CommissionUserTO>> buildListCommissionUserTO = source -> source
            .stream()
            .map(COMMISSION_BINDER::bindCommissionUserTO)
            .toList();

    BiPredicate<Offer, CommissionCampaignCondition> resolvePredicate = (offer, condition) -> {
        var value = condition.getValue();
        var compare = switch (condition.getCampaignAttribute().getAttributeType()) {
            case PRODUCT -> String.valueOf(offer.getType());
            case CONTRACT_DATE -> String.valueOf(offer.getContractDate());
            case OPERATION_VALE -> String.valueOf(offer.getTotal());
        };

        return switch (condition.getOperator()) {
            case EQUAL -> compare.equals(value);
            case GREATER_OR_EQUAL -> compare.compareTo(value) >= 0;
            case LESSER_OR_EQUAL -> compare.compareTo(value) <= 0;
            case LESSER -> compare.compareTo(value) < 0;
            case GREATER -> compare.compareTo(value) > 0;
        };
    };

    BiPredicate<Long, CommissionCampaign> validateLimit = (userId, campaign) -> {
        var commissionCampaignLimit = campaign.getLimit();
        if (commissionCampaignLimit == null)
            return true;

        if (commissionCampaignLimit.getLimitCount() >= commissionCampaignLimit.getCampaignLimit())
            return false;

        var commissionCampaignUser = campaign.getCampaignUsers()
                .stream()
                .filter(campaignUser -> campaignUser.getId().equals(userId))
                .findFirst()
                .orElse(null);

        return commissionCampaignUser == null ||
                commissionCampaignUser.getCount() < commissionCampaignLimit.getUserLimit();
    };

    BiPredicate<Offer, List<List<CommissionCampaignCondition>>> toPredicate = (offer, conditions) -> {
        var orConditions = new ArrayList<Boolean>();

        for (var campaignConditions : conditions) {
            var result = true;
            var cont = 0;

            while (result && cont < campaignConditions.size()) {
                result = resolvePredicate.test(offer, campaignConditions.get(0));
                cont++;
            }

            orConditions.add(result);
        }

        return orConditions
                .stream()
                .anyMatch(aBoolean -> aBoolean);
    };

    BiFunction<Offer, List<CommissionCampaign>, List<CommissionCampaign>> filterMatch = (offer, source) -> {
        if (source.isEmpty())
            return emptyList();

        var filter = source
                .stream()
                .filter(campaign -> {
                    if (!validateLimit.test(offer.getUser().getId(), campaign))
                        return false;

                    var groupConditions = buildConditions.apply(campaign);
                    return toPredicate.test(offer, groupConditions);
                })
                .toList();

        return filter.isEmpty()
                ? singletonList(source.get(0))
                : filter;
    };

    TriFunction<Integer, Double, Commission, Set<CommissionInstallment>> buildInstallments =
            (paymentSize, amortizationBase, commission) -> {
                var totalInstallment = paymentSize + 2;
                return IntStream
                        .range(0, totalInstallment)
                        .mapToObj(index -> {
                            if (index == 0)
                                return buildInstallment(paymentSize, 0, commission.getDisbursement(),
                                        amortizationBase, commission.getReleaseDate(), commission, DISBURSEMENT);
                            else if (index == totalInstallment - 1)
                                return buildInstallment(paymentSize, index - 1, commission.getLiquidation(),
                                        amortizationBase, commission.getReleaseDate(), commission, LIQUIDATION);
                            return buildInstallment(paymentSize, index, commission.getAmortization(), amortizationBase,
                                    commission.getReleaseDate(), commission, AMORTIZATION);
                        })
                        .collect(toSet());
            };

    static CommissionInstallment buildInstallment(int paymentSize,
                                                  int monthToAdd,
                                                  double payment,
                                                  double amortizationBase,
                                                  Date releaseDate,
                                                  Commission commission,
                                                  InstallmentType installmentType) {
        return new CommissionInstallment()
                .withTotal(payment / paymentSize)
                .withHasPaid(false)
                .withTotalBase(amortizationBase / paymentSize)
                .withPaymentDate(addMonth(releaseDate, monthToAdd))
                .withCommission(commission)
                .withInstallmentType(installmentType);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "base", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "campaignUsers", ignore = true)
    CommissionCampaign bind(CreateCommissionCampaignTO source);

    CommissionCampaignTO bind(CommissionCampaign source);

    @Mapping(target = "base", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "campaignUsers", ignore = true)
    @Mapping(target = "limit.limitCount", ignore = true)
    CommissionCampaign set(UpdateCommissionCampaignTO source, @MappingTarget CommissionCampaign target);

    List<CommissionCampaignTO> bind(List<CommissionCampaign> source);

    List<CommissionCampaignAttributeTO> bindToAttributeList(List<CommissionCampaignAttribute> source);

    List<CommissionInstallmentTO> bindToCommissionInstallmentList(List<CommissionInstallment> source);

    @Mapping(target = "campaign", ignore = true)
    CommissionTO bind(Commission source);

    @Mapping(target = "campaign", ignore = true)
    CommissionUserTO bind(CommissionTO source);

    default CommissionUserTO bindCommissionUserTO(Offer source) {
        var cnpj = source.getCnpj();
        var user = source.getUser();
        var socialReason = user.getCompanies()
                .stream()
                .filter(companyUser -> companyUser.getUser().getId().equals(user.getId()) &&
                        companyUser.getCompany().getMainInfo().getCnpj().equals(cnpj))
                .findFirst()
                .map(companyUser -> companyUser.getCompany().getMainInfo().getSocialReason())
                .orElse(null);
        var to = bindToCommissionTO(source.getCommission());

        return bind(to)
                .withCnpj(cnpj)
                .withOfferType(source.getType())
                .withSocialReason(socialReason);
    }

    default CommissionTO bindToCommissionTO(Commission source) {
        if (source == null)
            return null;
        var campaign = source.getCampaign();

        return bind(source)
                .withCampaign(campaign == null
                        ? null
                        : convert(campaign, CommissionCampaignTO.class));
    }

}
