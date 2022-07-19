package com.linkapital.core.services.offer_installment.contract;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.notification.contract.to.OfferInstallmentNotificationTO;
import com.linkapital.core.services.offer_installment.contract.to.BaseOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.UpdateOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.datasource.domain.OfferInstallment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class OfferInstallmentBinderImpl implements OfferInstallmentBinder {

    @Override
    public OfferInstallmentTO bind(OfferInstallment source) {
        if ( source == null ) {
            return null;
        }

        OfferInstallmentTO offerInstallmentTO = new OfferInstallmentTO();

        offerInstallmentTO.setTotal( source.getTotal() );
        offerInstallmentTO.setHasPaid( source.isHasPaid() );
        offerInstallmentTO.setExpiration( source.getExpiration() );
        if ( source.getId() != null ) {
            offerInstallmentTO.setId( source.getId() );
        }
        offerInstallmentTO.setCreated( source.getCreated() );
        offerInstallmentTO.setDocument( directoryToDirectoryTO( source.getDocument() ) );

        return offerInstallmentTO;
    }

    @Override
    public OfferInstallment set(UpdateOfferInstallmentTO source, OfferInstallment target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setTotal( source.getTotal() );
        target.setHasPaid( source.isHasPaid() );
        target.setExpiration( source.getExpiration() );

        return target;
    }

    @Override
    public Set<OfferInstallment> bind(List<BaseOfferInstallmentTO> source) {
        if ( source == null ) {
            return null;
        }

        Set<OfferInstallment> set = new HashSet<OfferInstallment>( Math.max( (int) ( source.size() / .75f ) + 1, 16 ) );
        for ( BaseOfferInstallmentTO baseOfferInstallmentTO : source ) {
            set.add( baseOfferInstallmentTOToOfferInstallment( baseOfferInstallmentTO ) );
        }

        return set;
    }

    @Override
    public OfferInstallmentNotificationTO bindToNotification(OfferInstallment source) {
        if ( source == null ) {
            return null;
        }

        OfferInstallmentNotificationTO offerInstallmentNotificationTO = new OfferInstallmentNotificationTO();

        offerInstallmentNotificationTO.setTotal( source.getTotal() );
        offerInstallmentNotificationTO.setHasPaid( source.isHasPaid() );
        offerInstallmentNotificationTO.setExpiration( source.getExpiration() );
        if ( source.getId() != null ) {
            offerInstallmentNotificationTO.setId( source.getId() );
        }
        offerInstallmentNotificationTO.setCreated( source.getCreated() );
        offerInstallmentNotificationTO.setDocument( directoryToDirectoryTO( source.getDocument() ) );

        offerInstallmentNotificationTO.setOfferId( source.getOffer().getId() );
        offerInstallmentNotificationTO.setOfferType( source.getOffer().getType() );
        offerInstallmentNotificationTO.setCnpj( source.getOffer().getCnpj() );

        return offerInstallmentNotificationTO;
    }

    @Override
    public List<OfferInstallmentNotificationTO> bindToNotificationList(List<OfferInstallment> source) {
        if ( source == null ) {
            return null;
        }

        List<OfferInstallmentNotificationTO> list = new ArrayList<OfferInstallmentNotificationTO>( source.size() );
        for ( OfferInstallment offerInstallment : source ) {
            list.add( bindToNotification( offerInstallment ) );
        }

        return list;
    }

    @Override
    public List<OfferInstallmentTO> bindToList(List<OfferInstallment> source) {
        if ( source == null ) {
            return null;
        }

        List<OfferInstallmentTO> list = new ArrayList<OfferInstallmentTO>( source.size() );
        for ( OfferInstallment offerInstallment : source ) {
            list.add( bind( offerInstallment ) );
        }

        return list;
    }

    protected DirectoryTO directoryToDirectoryTO(Directory directory) {
        if ( directory == null ) {
            return null;
        }

        DirectoryTO directoryTO = new DirectoryTO();

        directoryTO.setId( directory.getId() );
        directoryTO.setName( directory.getName() );
        directoryTO.setExt( directory.getExt() );
        directoryTO.setUrl( directory.getUrl() );
        directoryTO.setType( directory.getType() );

        return directoryTO;
    }

    protected OfferInstallment baseOfferInstallmentTOToOfferInstallment(BaseOfferInstallmentTO baseOfferInstallmentTO) {
        if ( baseOfferInstallmentTO == null ) {
            return null;
        }

        OfferInstallment offerInstallment = new OfferInstallment();

        offerInstallment.setTotal( baseOfferInstallmentTO.getTotal() );
        offerInstallment.setHasPaid( baseOfferInstallmentTO.isHasPaid() );
        offerInstallment.setExpiration( baseOfferInstallmentTO.getExpiration() );

        return offerInstallment;
    }
}
