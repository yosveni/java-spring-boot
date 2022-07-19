package com.linkapital.core.services.offer_installment.contract;

import com.linkapital.core.services.notification.contract.to.OfferInstallmentNotificationTO;
import com.linkapital.core.services.offer_installment.contract.to.BaseOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.UpdateOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.datasource.domain.OfferInstallment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface OfferInstallmentBinder {

    OfferInstallmentBinder OFFER_INSTALLMENT_BINDER = Mappers.getMapper(OfferInstallmentBinder.class);

    OfferInstallmentTO bind(OfferInstallment source);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "document", ignore = true)
    @Mapping(target = "offer", ignore = true)
    OfferInstallment set(UpdateOfferInstallmentTO source, @MappingTarget OfferInstallment target);

    Set<OfferInstallment> bind(List<BaseOfferInstallmentTO> source);

    @Mapping(target = "offerId", expression = "java(source.getOffer().getId())")
    @Mapping(target = "offerType", expression = "java(source.getOffer().getType())")
    @Mapping(target = "cnpj", expression = "java(source.getOffer().getCnpj())")
    OfferInstallmentNotificationTO bindToNotification(OfferInstallment source);

    @Mapping(target = "offerId", expression = "java(source.getOffer().getId())")
    @Mapping(target = "offerType", expression = "java(source.getOffer().getType())")
    @Mapping(target = "cnpj", expression = "java(source.getOffer().getCnpj())")
    List<OfferInstallmentNotificationTO> bindToNotificationList(List<OfferInstallment> source);

    List<OfferInstallmentTO> bindToList(List<OfferInstallment> source);

}
