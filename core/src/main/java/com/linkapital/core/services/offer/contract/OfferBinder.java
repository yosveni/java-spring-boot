package com.linkapital.core.services.offer.contract;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.offer.contract.to.BaseOfferTO;
import com.linkapital.core.services.offer.contract.to.create.CreateOfferTO;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import com.linkapital.core.services.offer.contract.to.update.UpdateOfferTO;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.offer.datasource.domain.OfferFour;
import com.linkapital.core.services.offer.datasource.domain.OfferOne;
import com.linkapital.core.services.offer.datasource.domain.OfferThree;
import com.linkapital.core.services.offer.datasource.domain.OfferTwo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.linkapital.core.services.commission.contract.CommissionBinder.COMMISSION_BINDER;

@Mapper
public interface OfferBinder {

    OfferBinder OFFER_BINDER = Mappers.getMapper(OfferBinder.class);

    Function<CreateOfferTO, Offer> buildOfferByType = source -> {
        var type = source.getType();

        return switch (type) {
            case 1 -> buildOffer(new OfferOne(), source)
                    .withType(type);
            case 2 -> buildOffer(new OfferTwo(), source)
                    .withType(type);
            case 3 -> buildOffer(new OfferThree(), source)
                    .withType(type);
            case 4 -> buildOffer(new OfferFour(), source)
                    .withType(type);
            default -> null;
        };
    };

    BiFunction<Offer, UpdateOfferTO, Offer>
            buildUpdatedOffer = OfferBinder::buildOffer;

    private static <I extends Offer, T extends BaseOfferTO> I buildOffer(I offer, T source) {
        offer
                .withCnpj(source.getCnpj())
                .withDescription(source.getDescription())
                .withNextStepDescription(source.getNextStepDescription())
                .withRejectedReason(source.getRejectedReason())
                .withMonthCet(source.getMonthCet())
                .withYearCet(source.getYearCet())
                .withVolume(source.getVolume())
                .withInstallments(source.getInstallments())
                .withTaxPercent(source.getTaxPercent())
                .withTaxValue(source.getTaxValue())
                .withDiscount(source.getDiscount())
                .withIof(source.getIof())
                .withRegistrationFee(source.getRegistrationFee())
                .withTotal(source.getTotal())
                .withPayByInstallment(source.getPayByInstallment())
                .withResponseTime(source.getResponseTime());

        if (source instanceof UpdateOfferTO to)
            offer.withState(to.getState());

        return offer;
    }

    @Mapping(target = "commission", ignore = true)
    OfferTO bind(Offer source);

    default List<OfferTO> bind(Collection<Offer> source) {
        return source
                .stream()
                .map(offer ->
                        bind(offer)
                                .withCommission(COMMISSION_BINDER.bindToCommissionTO(offer.getCommission())))
                .toList();
    }

    DirectoryTO bind(Directory directory);

}
