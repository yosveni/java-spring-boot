package com.linkapital.core.services.partner_bank.contract;

import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureLightTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureUrgencyTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclatureUrgency;
import com.linkapital.core.services.partner_bank.contract.to.create.CreatePartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankTO;
import com.linkapital.core.services.partner_bank.contract.to.update.UpdatePartnerBankTO;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
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
public class PartnerBankBinderImpl implements PartnerBankBinder {

    @Override
    public PartnerBankTO bind(PartnerBank source) {
        if ( source == null ) {
            return null;
        }

        PartnerBankTO partnerBankTO = new PartnerBankTO();

        partnerBankTO.setName( source.getName() );
        partnerBankTO.setDays( source.getDays() );
        Set<Integer> set = source.getAreas();
        if ( set != null ) {
            partnerBankTO.setAreas( new ArrayList<Integer>( set ) );
        }
        if ( source.getId() != null ) {
            partnerBankTO.setId( source.getId() );
        }
        partnerBankTO.setBankNomenclatures( bankNomenclatureSetToBankNomenclatureLightTOList( source.getBankNomenclatures() ) );

        return partnerBankTO;
    }

    @Override
    public PartnerBank bind(CreatePartnerBankTO source) {
        if ( source == null ) {
            return null;
        }

        PartnerBank partnerBank = new PartnerBank();

        partnerBank.setName( source.getName() );
        partnerBank.setDays( source.getDays() );
        List<Integer> list = source.getAreas();
        if ( list != null ) {
            partnerBank.setAreas( new HashSet<Integer>( list ) );
        }

        return partnerBank;
    }

    @Override
    public PartnerBank bind(PartnerBank target, UpdatePartnerBankTO source) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setName( source.getName() );
        target.setDays( source.getDays() );
        if ( target.getAreas() != null ) {
            List<Integer> list = source.getAreas();
            if ( list != null ) {
                target.getAreas().clear();
                target.getAreas().addAll( list );
            }
            else {
                target.setAreas( null );
            }
        }
        else {
            List<Integer> list = source.getAreas();
            if ( list != null ) {
                target.setAreas( new HashSet<Integer>( list ) );
            }
        }

        return target;
    }

    @Override
    public List<PartnerBankTO> bind(List<PartnerBank> source) {
        if ( source == null ) {
            return null;
        }

        List<PartnerBankTO> list = new ArrayList<PartnerBankTO>( source.size() );
        for ( PartnerBank partnerBank : source ) {
            list.add( bind( partnerBank ) );
        }

        return list;
    }

    protected BankNomenclatureUrgencyTO bankNomenclatureUrgencyToBankNomenclatureUrgencyTO(BankNomenclatureUrgency bankNomenclatureUrgency) {
        if ( bankNomenclatureUrgency == null ) {
            return null;
        }

        BankNomenclatureUrgencyTO bankNomenclatureUrgencyTO = new BankNomenclatureUrgencyTO();

        bankNomenclatureUrgencyTO.setId( bankNomenclatureUrgency.getId() );
        bankNomenclatureUrgencyTO.setArea( bankNomenclatureUrgency.getArea() );
        bankNomenclatureUrgencyTO.setUrgency( bankNomenclatureUrgency.getUrgency() );

        return bankNomenclatureUrgencyTO;
    }

    protected List<BankNomenclatureUrgencyTO> bankNomenclatureUrgencySetToBankNomenclatureUrgencyTOList(Set<BankNomenclatureUrgency> set) {
        if ( set == null ) {
            return null;
        }

        List<BankNomenclatureUrgencyTO> list = new ArrayList<BankNomenclatureUrgencyTO>( set.size() );
        for ( BankNomenclatureUrgency bankNomenclatureUrgency : set ) {
            list.add( bankNomenclatureUrgencyToBankNomenclatureUrgencyTO( bankNomenclatureUrgency ) );
        }

        return list;
    }

    protected BankNomenclatureLightTO bankNomenclatureToBankNomenclatureLightTO(BankNomenclature bankNomenclature) {
        if ( bankNomenclature == null ) {
            return null;
        }

        BankNomenclatureLightTO bankNomenclatureLightTO = new BankNomenclatureLightTO();

        bankNomenclatureLightTO.setDescription( bankNomenclature.getDescription() );
        bankNomenclatureLightTO.setStage( bankNomenclature.getStage() );
        Set<String> set = bankNomenclature.getExtensions();
        if ( set != null ) {
            bankNomenclatureLightTO.setExtensions( new ArrayList<String>( set ) );
        }
        if ( bankNomenclature.getId() != null ) {
            bankNomenclatureLightTO.setId( bankNomenclature.getId() );
        }
        bankNomenclatureLightTO.setNomenclatureUrgencies( bankNomenclatureUrgencySetToBankNomenclatureUrgencyTOList( bankNomenclature.getNomenclatureUrgencies() ) );

        return bankNomenclatureLightTO;
    }

    protected List<BankNomenclatureLightTO> bankNomenclatureSetToBankNomenclatureLightTOList(Set<BankNomenclature> set) {
        if ( set == null ) {
            return null;
        }

        List<BankNomenclatureLightTO> list = new ArrayList<BankNomenclatureLightTO>( set.size() );
        for ( BankNomenclature bankNomenclature : set ) {
            list.add( bankNomenclatureToBankNomenclatureLightTO( bankNomenclature ) );
        }

        return list;
    }
}
