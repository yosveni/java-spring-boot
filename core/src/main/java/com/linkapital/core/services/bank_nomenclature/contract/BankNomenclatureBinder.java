package com.linkapital.core.services.bank_nomenclature.contract;

import com.linkapital.core.services.bank_nomenclature.contract.to.create.CreateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.CompanyBankDocument;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.notification.contract.to.CompanyBankDocumentNotificationTO;
import com.linkapital.core.util.functions.TriFunction;
import org.apache.commons.lang3.SerializationUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.linkapital.core.services.indicative_offer.contract.IndicativeOfferBinder.validateState;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.OFFER_REQUEST;
import static com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState.SELECTED;

@Mapper
public interface BankNomenclatureBinder {

    BankNomenclatureBinder BANK_NOMENCLATURE_BINDER = Mappers.getMapper(BankNomenclatureBinder.class);

    UnaryOperator<List<CompanyBankNomenclatureTO>> cloneList = source -> source
            .stream()
            .map(SerializationUtils::clone)
            .toList();

    Function<CompanyUser, List<Integer>> getAreas = source -> {
        var areas = new ArrayList<Integer>();

        if (validateState.test(source.getIndicativeOfferOne(), SELECTED) ||
                validateState.test(source.getIndicativeOfferOne(), OFFER_REQUEST))
            areas.add(1);

        if (validateState.test(source.getIndicativeOfferTwo(), SELECTED) ||
                validateState.test(source.getIndicativeOfferTwo(), OFFER_REQUEST))
            areas.add(2);

        if (validateState.test(source.getIndicativeOfferThree(), SELECTED) ||
                validateState.test(source.getIndicativeOfferThree(), OFFER_REQUEST))
            areas.add(3);

        if (validateState.test(source.getIndicativeOfferFour(), SELECTED) ||
                validateState.test(source.getIndicativeOfferFour(), OFFER_REQUEST))
            areas.add(4);

        return areas;
    };

    BiFunction<String, CompanyBankDocumentTO, CompanyBankDocumentNotificationTO> buildCompanyBankDocumentNotification =
            (cnpj, to) -> new CompanyBankDocumentNotificationTO()
                    .withCnpj(cnpj)
                    .withCompanyBankDocument(to);

    TriFunction<List<Integer>,
            List<CompanyBankNomenclatureTO>,
            Set<CompanyBankDocument>,
            List<CompanyBankNomenclatureTO>> filterBankNomenclature = (areas, bankNomenclatures, documents) ->
            bankNomenclatures
                    .stream()
                    .map(nomenclature -> nomenclature.getNomenclatureUrgencies()
                            .stream()
                            .filter(nomenclatureUrgency -> areas.contains(nomenclatureUrgency.getArea()))
                            .findFirst()
                            .flatMap(to -> {
                                documents
                                        .stream()
                                        .filter(bankDocument ->
                                                nomenclature.getId() == bankDocument.getBankNomenclature().getId())
                                        .findFirst()
                                        .ifPresent(companyBankDocument -> {
                                            nomenclature.setHasDocument(true);
                                            nomenclature.setDescriptionState(companyBankDocument.getDescriptionState());
                                            nomenclature.setState(companyBankDocument.getState());
                                        });

                                return Optional
                                        .of(nomenclature);
                            })
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .toList();

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "partnersBank", ignore = true)
    BankNomenclature bind(CreateBankNomenclatureTO source);

    BankNomenclatureTO bind(BankNomenclature source);

    CompanyBankDocumentTO bind(CompanyBankDocument source);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "partnersBank", ignore = true)
    BankNomenclature set(UpdateBankNomenclatureTO source, @MappingTarget BankNomenclature target);

    List<BankNomenclatureTO> bind(List<BankNomenclature> source);

    @Mapping(target = "hasDocument", ignore = true)
    List<CompanyBankNomenclatureTO> bindCompanyBankNomenclatureTO(Collection<BankNomenclature> source);

}
