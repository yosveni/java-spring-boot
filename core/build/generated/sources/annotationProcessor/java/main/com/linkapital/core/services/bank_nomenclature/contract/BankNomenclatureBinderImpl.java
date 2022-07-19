package com.linkapital.core.services.bank_nomenclature.contract;

import com.linkapital.core.services.bank_nomenclature.contract.to.create.CreateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.create.CreateBankNomenclatureUrgencyTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureUrgencyTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.update.UpdateBankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclatureUrgency;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.CompanyBankDocument;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankLightTO;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class BankNomenclatureBinderImpl implements BankNomenclatureBinder {

    @Override
    public BankNomenclature bind(CreateBankNomenclatureTO source) {
        if ( source == null ) {
            return null;
        }

        BankNomenclature bankNomenclature = new BankNomenclature();

        bankNomenclature.setDescription( source.getDescription() );
        bankNomenclature.setStage( source.getStage() );
        List<String> list = source.getExtensions();
        if ( list != null ) {
            bankNomenclature.setExtensions( new HashSet<String>( list ) );
        }
        bankNomenclature.setNomenclatureUrgencies( createBankNomenclatureUrgencyTOListToBankNomenclatureUrgencySet( source.getNomenclatureUrgencies() ) );

        return bankNomenclature;
    }

    @Override
    public BankNomenclatureTO bind(BankNomenclature source) {
        if ( source == null ) {
            return null;
        }

        BankNomenclatureTO bankNomenclatureTO = new BankNomenclatureTO();

        bankNomenclatureTO.setDescription( source.getDescription() );
        bankNomenclatureTO.setStage( source.getStage() );
        Set<String> set = source.getExtensions();
        if ( set != null ) {
            bankNomenclatureTO.setExtensions( new ArrayList<String>( set ) );
        }
        if ( source.getId() != null ) {
            bankNomenclatureTO.setId( source.getId() );
        }
        bankNomenclatureTO.setPartnersBank( partnerBankSetToPartnerBankLightTOList( source.getPartnersBank() ) );
        bankNomenclatureTO.setNomenclatureUrgencies( bankNomenclatureUrgencySetToBankNomenclatureUrgencyTOList( source.getNomenclatureUrgencies() ) );

        return bankNomenclatureTO;
    }

    @Override
    public CompanyBankDocumentTO bind(CompanyBankDocument source) {
        if ( source == null ) {
            return null;
        }

        CompanyBankDocumentTO companyBankDocumentTO = new CompanyBankDocumentTO();

        if ( source.getId() != null ) {
            companyBankDocumentTO.setId( source.getId() );
        }
        companyBankDocumentTO.setDescriptionState( source.getDescriptionState() );
        companyBankDocumentTO.setState( source.getState() );
        companyBankDocumentTO.setBankNomenclature( bind( source.getBankNomenclature() ) );
        companyBankDocumentTO.setDirectories( directoryListToDirectoryTOList( source.getDirectories() ) );

        return companyBankDocumentTO;
    }

    @Override
    public BankNomenclature set(UpdateBankNomenclatureTO source, BankNomenclature target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setDescription( source.getDescription() );
        target.setStage( source.getStage() );
        if ( target.getExtensions() != null ) {
            List<String> list = source.getExtensions();
            if ( list != null ) {
                target.getExtensions().clear();
                target.getExtensions().addAll( list );
            }
            else {
                target.setExtensions( null );
            }
        }
        else {
            List<String> list = source.getExtensions();
            if ( list != null ) {
                target.setExtensions( new HashSet<String>( list ) );
            }
        }
        if ( target.getNomenclatureUrgencies() != null ) {
            Set<BankNomenclatureUrgency> set = bankNomenclatureUrgencyTOListToBankNomenclatureUrgencySet( source.getNomenclatureUrgencies() );
            if ( set != null ) {
                target.getNomenclatureUrgencies().clear();
                target.getNomenclatureUrgencies().addAll( set );
            }
            else {
                target.setNomenclatureUrgencies( null );
            }
        }
        else {
            Set<BankNomenclatureUrgency> set = bankNomenclatureUrgencyTOListToBankNomenclatureUrgencySet( source.getNomenclatureUrgencies() );
            if ( set != null ) {
                target.setNomenclatureUrgencies( set );
            }
        }

        return target;
    }

    @Override
    public List<BankNomenclatureTO> bind(List<BankNomenclature> source) {
        if ( source == null ) {
            return null;
        }

        List<BankNomenclatureTO> list = new ArrayList<BankNomenclatureTO>( source.size() );
        for ( BankNomenclature bankNomenclature : source ) {
            list.add( bind( bankNomenclature ) );
        }

        return list;
    }

    @Override
    public List<CompanyBankNomenclatureTO> bindCompanyBankNomenclatureTO(Collection<BankNomenclature> source) {
        if ( source == null ) {
            return null;
        }

        List<CompanyBankNomenclatureTO> list = new ArrayList<CompanyBankNomenclatureTO>( source.size() );
        for ( BankNomenclature bankNomenclature : source ) {
            list.add( bankNomenclatureToCompanyBankNomenclatureTO( bankNomenclature ) );
        }

        return list;
    }

    protected BankNomenclatureUrgency createBankNomenclatureUrgencyTOToBankNomenclatureUrgency(CreateBankNomenclatureUrgencyTO createBankNomenclatureUrgencyTO) {
        if ( createBankNomenclatureUrgencyTO == null ) {
            return null;
        }

        BankNomenclatureUrgency bankNomenclatureUrgency = new BankNomenclatureUrgency();

        bankNomenclatureUrgency.setArea( createBankNomenclatureUrgencyTO.getArea() );
        bankNomenclatureUrgency.setUrgency( createBankNomenclatureUrgencyTO.getUrgency() );

        return bankNomenclatureUrgency;
    }

    protected Set<BankNomenclatureUrgency> createBankNomenclatureUrgencyTOListToBankNomenclatureUrgencySet(List<CreateBankNomenclatureUrgencyTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<BankNomenclatureUrgency> set = new HashSet<BankNomenclatureUrgency>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( CreateBankNomenclatureUrgencyTO createBankNomenclatureUrgencyTO : list ) {
            set.add( createBankNomenclatureUrgencyTOToBankNomenclatureUrgency( createBankNomenclatureUrgencyTO ) );
        }

        return set;
    }

    protected PartnerBankLightTO partnerBankToPartnerBankLightTO(PartnerBank partnerBank) {
        if ( partnerBank == null ) {
            return null;
        }

        PartnerBankLightTO partnerBankLightTO = new PartnerBankLightTO();

        partnerBankLightTO.setName( partnerBank.getName() );
        partnerBankLightTO.setDays( partnerBank.getDays() );
        Set<Integer> set = partnerBank.getAreas();
        if ( set != null ) {
            partnerBankLightTO.setAreas( new ArrayList<Integer>( set ) );
        }
        if ( partnerBank.getId() != null ) {
            partnerBankLightTO.setId( partnerBank.getId() );
        }

        return partnerBankLightTO;
    }

    protected List<PartnerBankLightTO> partnerBankSetToPartnerBankLightTOList(Set<PartnerBank> set) {
        if ( set == null ) {
            return null;
        }

        List<PartnerBankLightTO> list = new ArrayList<PartnerBankLightTO>( set.size() );
        for ( PartnerBank partnerBank : set ) {
            list.add( partnerBankToPartnerBankLightTO( partnerBank ) );
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

    protected List<DirectoryTO> directoryListToDirectoryTOList(List<Directory> list) {
        if ( list == null ) {
            return null;
        }

        List<DirectoryTO> list1 = new ArrayList<DirectoryTO>( list.size() );
        for ( Directory directory : list ) {
            list1.add( directoryToDirectoryTO( directory ) );
        }

        return list1;
    }

    protected BankNomenclatureUrgency bankNomenclatureUrgencyTOToBankNomenclatureUrgency(BankNomenclatureUrgencyTO bankNomenclatureUrgencyTO) {
        if ( bankNomenclatureUrgencyTO == null ) {
            return null;
        }

        BankNomenclatureUrgency bankNomenclatureUrgency = new BankNomenclatureUrgency();

        bankNomenclatureUrgency.setId( bankNomenclatureUrgencyTO.getId() );
        bankNomenclatureUrgency.setArea( bankNomenclatureUrgencyTO.getArea() );
        bankNomenclatureUrgency.setUrgency( bankNomenclatureUrgencyTO.getUrgency() );

        return bankNomenclatureUrgency;
    }

    protected Set<BankNomenclatureUrgency> bankNomenclatureUrgencyTOListToBankNomenclatureUrgencySet(List<BankNomenclatureUrgencyTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<BankNomenclatureUrgency> set = new HashSet<BankNomenclatureUrgency>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( BankNomenclatureUrgencyTO bankNomenclatureUrgencyTO : list ) {
            set.add( bankNomenclatureUrgencyTOToBankNomenclatureUrgency( bankNomenclatureUrgencyTO ) );
        }

        return set;
    }

    protected CompanyBankNomenclatureTO bankNomenclatureToCompanyBankNomenclatureTO(BankNomenclature bankNomenclature) {
        if ( bankNomenclature == null ) {
            return null;
        }

        CompanyBankNomenclatureTO companyBankNomenclatureTO = new CompanyBankNomenclatureTO();

        companyBankNomenclatureTO.setDescription( bankNomenclature.getDescription() );
        companyBankNomenclatureTO.setStage( bankNomenclature.getStage() );
        Set<String> set = bankNomenclature.getExtensions();
        if ( set != null ) {
            companyBankNomenclatureTO.setExtensions( new ArrayList<String>( set ) );
        }
        if ( bankNomenclature.getId() != null ) {
            companyBankNomenclatureTO.setId( bankNomenclature.getId() );
        }
        companyBankNomenclatureTO.setNomenclatureUrgencies( bankNomenclatureUrgencySetToBankNomenclatureUrgencyTOList( bankNomenclature.getNomenclatureUrgencies() ) );

        return companyBankNomenclatureTO;
    }
}
