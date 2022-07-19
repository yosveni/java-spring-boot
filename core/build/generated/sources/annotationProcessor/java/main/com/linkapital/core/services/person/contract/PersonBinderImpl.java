package com.linkapital.core.services.person.contract;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.person.contract.to.CorporateParticipationTO;
import com.linkapital.core.services.person.contract.to.DisabilitiesBacenTO;
import com.linkapital.core.services.person.contract.to.HistoricalCriminalTO;
import com.linkapital.core.services.person.contract.to.HistoricalFunctionalTO;
import com.linkapital.core.services.person.contract.to.IrpfTO;
import com.linkapital.core.services.person.contract.to.JudicialProcessQuantityTO;
import com.linkapital.core.services.person.contract.to.JudicialProcessTO;
import com.linkapital.core.services.person.contract.to.LightPersonSpouseTO;
import com.linkapital.core.services.person.contract.to.PersonPartnerTO;
import com.linkapital.core.services.person.contract.to.RelationshipTO;
import com.linkapital.core.services.person.datasource.domain.CorporateParticipation;
import com.linkapital.core.services.person.datasource.domain.DisabilitiesBacen;
import com.linkapital.core.services.person.datasource.domain.HistoricalCriminal;
import com.linkapital.core.services.person.datasource.domain.HistoricalFunctional;
import com.linkapital.core.services.person.datasource.domain.Irpf;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.person.datasource.domain.Relationship;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.contract.to.DebitMteProcessTO;
import com.linkapital.core.services.shared.contract.to.DebitMteTO;
import com.linkapital.core.services.shared.contract.to.DebitPgfnDauTO;
import com.linkapital.core.services.shared.contract.to.DebitPgfnTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import com.linkapital.core.services.shared.datasource.domain.DebitMte;
import com.linkapital.core.services.shared.datasource.domain.DebitMteProcess;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfn;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfnDau;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcess;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcessQuantity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:18-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class PersonBinderImpl implements PersonBinder {

    @Override
    public List<PersonPartnerTO> bind(List<Person> source) {
        if ( source == null ) {
            return null;
        }

        List<PersonPartnerTO> list = new ArrayList<PersonPartnerTO>( source.size() );
        for ( Person person : source ) {
            list.add( personToPersonPartnerTO( person ) );
        }

        return list;
    }

    @Override
    public LightPersonSpouseTO bindToPersonSpouseTO(Person source) {
        if ( source == null ) {
            return null;
        }

        LightPersonSpouseTO lightPersonSpouseTO = new LightPersonSpouseTO();

        if ( source.getId() != null ) {
            lightPersonSpouseTO.setId( source.getId() );
        }
        lightPersonSpouseTO.setCpf( source.getCpf() );
        lightPersonSpouseTO.setName( source.getName() );
        lightPersonSpouseTO.setSex( source.getSex() );
        lightPersonSpouseTO.setEmail( source.getEmail() );
        lightPersonSpouseTO.setCns( source.getCns() );
        lightPersonSpouseTO.setNis( source.getNis() );
        lightPersonSpouseTO.setMotherCpf( source.getMotherCpf() );
        lightPersonSpouseTO.setMotherName( source.getMotherName() );
        lightPersonSpouseTO.setFatherName( source.getFatherName() );
        lightPersonSpouseTO.setOriginCountry( source.getOriginCountry() );
        lightPersonSpouseTO.setSocialInscription( source.getSocialInscription() );
        lightPersonSpouseTO.setDebitPgfnDauValue( source.getDebitPgfnDauValue() );
        lightPersonSpouseTO.setSituationCpf( source.getSituationCpf() );
        lightPersonSpouseTO.setEducationLevel( source.getEducationLevel() );
        lightPersonSpouseTO.setDeficiencyType( source.getDeficiencyType() );
        lightPersonSpouseTO.setProfessionNeoway( source.getProfessionNeoway() );
        lightPersonSpouseTO.setProfessionCbo( source.getProfessionCbo() );
        lightPersonSpouseTO.setSituation( source.getSituation() );
        lightPersonSpouseTO.setRegisterSituation( source.getRegisterSituation() );
        lightPersonSpouseTO.setMarriageRegime( source.getMarriageRegime() );
        lightPersonSpouseTO.setInscriptionCpfDate( source.getInscriptionCpfDate() );
        lightPersonSpouseTO.setQuantityQsaUnique( source.getQuantityQsaUnique() );
        lightPersonSpouseTO.setAge( source.getAge() );
        lightPersonSpouseTO.setDeadDate( source.getDeadDate() );
        lightPersonSpouseTO.setDeficiency( source.isDeficiency() );
        lightPersonSpouseTO.setDead( source.isDead() );
        lightPersonSpouseTO.setDeadConfirmation( source.isDeadConfirmation() );
        lightPersonSpouseTO.setPublicAgent( source.isPublicAgent() );
        lightPersonSpouseTO.setBirthDate( source.getBirthDate() );
        lightPersonSpouseTO.setMainAddress( addressToAddressTO( source.getMainAddress() ) );
        lightPersonSpouseTO.setDebitPgfnDau( debitPgfnDauToDebitPgfnDauTO( source.getDebitPgfnDau() ) );
        lightPersonSpouseTO.setDebitMte( debitMteToDebitMteTO( source.getDebitMte() ) );
        lightPersonSpouseTO.setJudicialProcess( judicialProcessToJudicialProcessTO( source.getJudicialProcess() ) );
        lightPersonSpouseTO.setHistoricalCriminal( historicalCriminalToHistoricalCriminalTO( source.getHistoricalCriminal() ) );
        Set<String> set = source.getPhones();
        if ( set != null ) {
            lightPersonSpouseTO.setPhones( new ArrayList<String>( set ) );
        }
        Set<String> set1 = source.getCriminalProcess();
        if ( set1 != null ) {
            lightPersonSpouseTO.setCriminalProcess( new ArrayList<String>( set1 ) );
        }
        lightPersonSpouseTO.setIrpfDocuments( directoryListToDirectoryTOList( source.getIrpfDocuments() ) );
        lightPersonSpouseTO.setIrpfReceiptDocuments( directoryListToDirectoryTOList( source.getIrpfReceiptDocuments() ) );
        lightPersonSpouseTO.setIrpf( irpfSetToIrpfTOList( source.getIrpf() ) );
        lightPersonSpouseTO.setHistoricalFunctional( historicalFunctionalSetToHistoricalFunctionalTOList( source.getHistoricalFunctional() ) );
        lightPersonSpouseTO.setRelationships( relationshipSetToRelationshipTOList( source.getRelationships() ) );
        lightPersonSpouseTO.setDisabilitiesBacens( disabilitiesBacenSetToDisabilitiesBacenTOList( source.getDisabilitiesBacens() ) );
        lightPersonSpouseTO.setOtherAddresses( addressSetToAddressTOList( source.getOtherAddresses() ) );
        lightPersonSpouseTO.setCorporatesParticipation( corporateParticipationSetToCorporateParticipationTOList( source.getCorporatesParticipation() ) );

        lightPersonSpouseTO.setSpouse( source.getSpouse() != null ? source.getSpouse().getName() : null );

        return lightPersonSpouseTO;
    }

    protected PersonPartnerTO personToPersonPartnerTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonPartnerTO personPartnerTO = new PersonPartnerTO();

        personPartnerTO.setId( person.getId() );
        personPartnerTO.setCpf( person.getCpf() );
        personPartnerTO.setName( person.getName() );

        return personPartnerTO;
    }

    protected AddressTO addressToAddressTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressTO addressTO = new AddressTO();

        if ( address.getId() != null ) {
            addressTO.setId( address.getId() );
        }
        addressTO.setNeighborhood( address.getNeighborhood() );
        addressTO.setOriginalNeighborhood( address.getOriginalNeighborhood() );
        addressTO.setZip( address.getZip() );
        addressTO.setAddress1( address.getAddress1() );
        addressTO.setAddress2( address.getAddress2() );
        addressTO.setMRegion( address.getMRegion() );
        addressTO.setMicroRegion( address.getMicroRegion() );
        addressTO.setRegion( address.getRegion() );
        addressTO.setCountry( address.getCountry() );
        addressTO.setCodeCountry( address.getCodeCountry() );
        addressTO.setMunicipality( address.getMunicipality() );
        addressTO.setCodeMunicipality( address.getCodeMunicipality() );
        addressTO.setBorderMunicipality( address.getBorderMunicipality() );
        addressTO.setNumber( address.getNumber() );
        addressTO.setPrecision( address.getPrecision() );
        addressTO.setUf( address.getUf() );
        addressTO.setRegistryUf( address.getRegistryUf() );
        addressTO.setBuildingType( address.getBuildingType() );
        addressTO.setFormattedAddress( address.getFormattedAddress() );
        addressTO.setLatitude( address.getLatitude() );
        addressTO.setLongitude( address.getLongitude() );
        addressTO.setDeliveryRestriction( address.isDeliveryRestriction() );
        addressTO.setResidentialAddress( address.isResidentialAddress() );
        addressTO.setLatestAddress( address.isLatestAddress() );
        addressTO.setCollectiveBuilding( address.isCollectiveBuilding() );
        List<String> list = address.getRfPhones();
        if ( list != null ) {
            addressTO.setRfPhones( new ArrayList<String>( list ) );
        }

        return addressTO;
    }

    protected DebitPgfnTO debitPgfnToDebitPgfnTO(DebitPgfn debitPgfn) {
        if ( debitPgfn == null ) {
            return null;
        }

        DebitPgfnTO debitPgfnTO = new DebitPgfnTO();

        if ( debitPgfn.getId() != null ) {
            debitPgfnTO.setId( debitPgfn.getId() );
        }
        debitPgfnTO.setNature( debitPgfn.getNature() );
        debitPgfnTO.setInscriptionNumber( debitPgfn.getInscriptionNumber() );
        debitPgfnTO.setDebit( debitPgfn.getDebit() );

        return debitPgfnTO;
    }

    protected List<DebitPgfnTO> debitPgfnListToDebitPgfnTOList(List<DebitPgfn> list) {
        if ( list == null ) {
            return null;
        }

        List<DebitPgfnTO> list1 = new ArrayList<DebitPgfnTO>( list.size() );
        for ( DebitPgfn debitPgfn : list ) {
            list1.add( debitPgfnToDebitPgfnTO( debitPgfn ) );
        }

        return list1;
    }

    protected DebitPgfnDauTO debitPgfnDauToDebitPgfnDauTO(DebitPgfnDau debitPgfnDau) {
        if ( debitPgfnDau == null ) {
            return null;
        }

        DebitPgfnDauTO debitPgfnDauTO = new DebitPgfnDauTO();

        if ( debitPgfnDau.getId() != null ) {
            debitPgfnDauTO.setId( debitPgfnDau.getId() );
        }
        debitPgfnDauTO.setTotalDebits( debitPgfnDau.getTotalDebits() );
        debitPgfnDauTO.setDebitPgfns( debitPgfnListToDebitPgfnTOList( debitPgfnDau.getDebitPgfns() ) );

        return debitPgfnDauTO;
    }

    protected DebitMteProcessTO debitMteProcessToDebitMteProcessTO(DebitMteProcess debitMteProcess) {
        if ( debitMteProcess == null ) {
            return null;
        }

        DebitMteProcessTO debitMteProcessTO = new DebitMteProcessTO();

        debitMteProcessTO.setId( debitMteProcess.getId() );
        debitMteProcessTO.setNumber( debitMteProcess.getNumber() );
        debitMteProcessTO.setSituation( debitMteProcess.getSituation() );
        debitMteProcessTO.setInfringementCategory( debitMteProcess.getInfringementCategory() );
        debitMteProcessTO.setInfringementCapitulation( debitMteProcess.getInfringementCapitulation() );

        return debitMteProcessTO;
    }

    protected List<DebitMteProcessTO> debitMteProcessListToDebitMteProcessTOList(List<DebitMteProcess> list) {
        if ( list == null ) {
            return null;
        }

        List<DebitMteProcessTO> list1 = new ArrayList<DebitMteProcessTO>( list.size() );
        for ( DebitMteProcess debitMteProcess : list ) {
            list1.add( debitMteProcessToDebitMteProcessTO( debitMteProcess ) );
        }

        return list1;
    }

    protected DebitMteTO debitMteToDebitMteTO(DebitMte debitMte) {
        if ( debitMte == null ) {
            return null;
        }

        DebitMteTO debitMteTO = new DebitMteTO();

        debitMteTO.setId( debitMte.getId() );
        debitMteTO.setCode( debitMte.getCode() );
        debitMteTO.setDebitSituation( debitMte.getDebitSituation() );
        debitMteTO.setCertificateType( debitMte.getCertificateType() );
        debitMteTO.setEmissionDate( debitMte.getEmissionDate() );
        debitMteTO.setProcesses( debitMteProcessListToDebitMteProcessTOList( debitMte.getProcesses() ) );

        return debitMteTO;
    }

    protected JudicialProcessQuantityTO judicialProcessQuantityToJudicialProcessQuantityTO(JudicialProcessQuantity judicialProcessQuantity) {
        if ( judicialProcessQuantity == null ) {
            return null;
        }

        JudicialProcessQuantityTO judicialProcessQuantityTO = new JudicialProcessQuantityTO();

        judicialProcessQuantityTO.setId( judicialProcessQuantity.getId() );
        judicialProcessQuantityTO.setType( judicialProcessQuantity.getType() );
        judicialProcessQuantityTO.setQuantityActive( judicialProcessQuantity.getQuantityActive() );
        judicialProcessQuantityTO.setQuantityActivePart( judicialProcessQuantity.getQuantityActivePart() );
        judicialProcessQuantityTO.setQuantityPassivePart( judicialProcessQuantity.getQuantityPassivePart() );
        judicialProcessQuantityTO.setQuantityOthers( judicialProcessQuantity.getQuantityOthers() );
        judicialProcessQuantityTO.setQuantityTotal( judicialProcessQuantity.getQuantityTotal() );

        return judicialProcessQuantityTO;
    }

    protected List<JudicialProcessQuantityTO> judicialProcessQuantityListToJudicialProcessQuantityTOList(List<JudicialProcessQuantity> list) {
        if ( list == null ) {
            return null;
        }

        List<JudicialProcessQuantityTO> list1 = new ArrayList<JudicialProcessQuantityTO>( list.size() );
        for ( JudicialProcessQuantity judicialProcessQuantity : list ) {
            list1.add( judicialProcessQuantityToJudicialProcessQuantityTO( judicialProcessQuantity ) );
        }

        return list1;
    }

    protected JudicialProcessTO judicialProcessToJudicialProcessTO(JudicialProcess judicialProcess) {
        if ( judicialProcess == null ) {
            return null;
        }

        JudicialProcessTO judicialProcessTO = new JudicialProcessTO();

        judicialProcessTO.setId( judicialProcess.getId() );
        judicialProcessTO.setTotalActiveValue( judicialProcess.getTotalActiveValue() );
        judicialProcessTO.setTotalPassiveValue( judicialProcess.getTotalPassiveValue() );
        judicialProcessTO.setTotalOthersValue( judicialProcess.getTotalOthersValue() );
        judicialProcessTO.setTotalValue( judicialProcess.getTotalValue() );
        judicialProcessTO.setJudicialProcessQuantities( judicialProcessQuantityListToJudicialProcessQuantityTOList( judicialProcess.getJudicialProcessQuantities() ) );

        return judicialProcessTO;
    }

    protected HistoricalCriminalTO historicalCriminalToHistoricalCriminalTO(HistoricalCriminal historicalCriminal) {
        if ( historicalCriminal == null ) {
            return null;
        }

        HistoricalCriminalTO historicalCriminalTO = new HistoricalCriminalTO();

        historicalCriminalTO.setId( historicalCriminal.getId() );
        historicalCriminalTO.setStatus( historicalCriminal.getStatus() );
        historicalCriminalTO.setSituation( historicalCriminal.getSituation() );
        historicalCriminalTO.setProtocol( historicalCriminal.getProtocol() );
        historicalCriminalTO.setConsultationDate( historicalCriminal.getConsultationDate() );

        return historicalCriminalTO;
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

    protected IrpfTO irpfToIrpfTO(Irpf irpf) {
        if ( irpf == null ) {
            return null;
        }

        IrpfTO irpfTO = new IrpfTO();

        irpfTO.setId( irpf.getId() );
        irpfTO.setBank( irpf.getBank() );
        irpfTO.setAgency( irpf.getAgency() );
        irpfTO.setLot( irpf.getLot() );
        irpfTO.setStatementStatus( irpf.getStatementStatus() );
        irpfTO.setYearExercise( irpf.getYearExercise() );
        irpfTO.setAvailabilityDate( irpf.getAvailabilityDate() );

        return irpfTO;
    }

    protected List<IrpfTO> irpfSetToIrpfTOList(Set<Irpf> set) {
        if ( set == null ) {
            return null;
        }

        List<IrpfTO> list = new ArrayList<IrpfTO>( set.size() );
        for ( Irpf irpf : set ) {
            list.add( irpfToIrpfTO( irpf ) );
        }

        return list;
    }

    protected HistoricalFunctionalTO historicalFunctionalToHistoricalFunctionalTO(HistoricalFunctional historicalFunctional) {
        if ( historicalFunctional == null ) {
            return null;
        }

        HistoricalFunctionalTO historicalFunctionalTO = new HistoricalFunctionalTO();

        historicalFunctionalTO.setId( historicalFunctional.getId() );
        historicalFunctionalTO.setCnpj( historicalFunctional.getCnpj() );
        historicalFunctionalTO.setSocialReason( historicalFunctional.getSocialReason() );
        historicalFunctionalTO.setMonths( historicalFunctional.getMonths() );
        historicalFunctionalTO.setAdmissionDate( historicalFunctional.getAdmissionDate() );
        historicalFunctionalTO.setDismissedDate( historicalFunctional.getDismissedDate() );

        return historicalFunctionalTO;
    }

    protected List<HistoricalFunctionalTO> historicalFunctionalSetToHistoricalFunctionalTOList(Set<HistoricalFunctional> set) {
        if ( set == null ) {
            return null;
        }

        List<HistoricalFunctionalTO> list = new ArrayList<HistoricalFunctionalTO>( set.size() );
        for ( HistoricalFunctional historicalFunctional : set ) {
            list.add( historicalFunctionalToHistoricalFunctionalTO( historicalFunctional ) );
        }

        return list;
    }

    protected RelationshipTO relationshipToRelationshipTO(Relationship relationship) {
        if ( relationship == null ) {
            return null;
        }

        RelationshipTO relationshipTO = new RelationshipTO();

        if ( relationship.getId() != null ) {
            relationshipTO.setId( relationship.getId() );
        }
        relationshipTO.setCpf( relationship.getCpf() );
        relationshipTO.setName( relationship.getName() );
        relationshipTO.setDescription( relationship.getDescription() );

        return relationshipTO;
    }

    protected List<RelationshipTO> relationshipSetToRelationshipTOList(Set<Relationship> set) {
        if ( set == null ) {
            return null;
        }

        List<RelationshipTO> list = new ArrayList<RelationshipTO>( set.size() );
        for ( Relationship relationship : set ) {
            list.add( relationshipToRelationshipTO( relationship ) );
        }

        return list;
    }

    protected DisabilitiesBacenTO disabilitiesBacenToDisabilitiesBacenTO(DisabilitiesBacen disabilitiesBacen) {
        if ( disabilitiesBacen == null ) {
            return null;
        }

        DisabilitiesBacenTO disabilitiesBacenTO = new DisabilitiesBacenTO();

        disabilitiesBacenTO.setId( disabilitiesBacen.getId() );
        disabilitiesBacenTO.setPenalty( disabilitiesBacen.getPenalty() );
        disabilitiesBacenTO.setDuration( disabilitiesBacen.getDuration() );
        disabilitiesBacenTO.setPenaltyPeriodDate( disabilitiesBacen.getPenaltyPeriodDate() );
        disabilitiesBacenTO.setPublicationDate( disabilitiesBacen.getPublicationDate() );

        return disabilitiesBacenTO;
    }

    protected List<DisabilitiesBacenTO> disabilitiesBacenSetToDisabilitiesBacenTOList(Set<DisabilitiesBacen> set) {
        if ( set == null ) {
            return null;
        }

        List<DisabilitiesBacenTO> list = new ArrayList<DisabilitiesBacenTO>( set.size() );
        for ( DisabilitiesBacen disabilitiesBacen : set ) {
            list.add( disabilitiesBacenToDisabilitiesBacenTO( disabilitiesBacen ) );
        }

        return list;
    }

    protected List<AddressTO> addressSetToAddressTOList(Set<Address> set) {
        if ( set == null ) {
            return null;
        }

        List<AddressTO> list = new ArrayList<AddressTO>( set.size() );
        for ( Address address : set ) {
            list.add( addressToAddressTO( address ) );
        }

        return list;
    }

    protected CorporateParticipationTO corporateParticipationToCorporateParticipationTO(CorporateParticipation corporateParticipation) {
        if ( corporateParticipation == null ) {
            return null;
        }

        CorporateParticipationTO corporateParticipationTO = new CorporateParticipationTO();

        corporateParticipationTO.setId( corporateParticipation.getId() );
        corporateParticipationTO.setCnpj( corporateParticipation.getCnpj() );
        corporateParticipationTO.setSocialReason( corporateParticipation.getSocialReason() );
        corporateParticipationTO.setDescriptionCnae( corporateParticipation.getDescriptionCnae() );
        corporateParticipationTO.setBusinessActivityCnae( corporateParticipation.getBusinessActivityCnae() );
        corporateParticipationTO.setSocialCapital( corporateParticipation.getSocialCapital() );
        corporateParticipationTO.setSituation( corporateParticipation.getSituation() );
        corporateParticipationTO.setEstimatedBilling( corporateParticipation.getEstimatedBilling() );
        corporateParticipationTO.setEstimatedBillingGroup( corporateParticipation.getEstimatedBillingGroup() );
        corporateParticipationTO.setMunicipality( corporateParticipation.getMunicipality() );
        corporateParticipationTO.setUf( corporateParticipation.getUf() );
        corporateParticipationTO.setQualification( corporateParticipation.getQualification() );
        corporateParticipationTO.setQualificationRF( corporateParticipation.getQualificationRF() );
        corporateParticipationTO.setLevelPreparation( corporateParticipation.getLevelPreparation() );
        corporateParticipationTO.setLevelPreparationRF( corporateParticipation.getLevelPreparationRF() );
        corporateParticipationTO.setParticipation( corporateParticipation.getParticipation() );
        corporateParticipationTO.setParticipationRF( corporateParticipation.getParticipationRF() );
        corporateParticipationTO.setParticipationSocialCapital( corporateParticipation.getParticipationSocialCapital() );
        corporateParticipationTO.setParticipationSocialCapitalRF( corporateParticipation.getParticipationSocialCapitalRF() );
        corporateParticipationTO.setOpeningDate( corporateParticipation.getOpeningDate() );

        return corporateParticipationTO;
    }

    protected List<CorporateParticipationTO> corporateParticipationSetToCorporateParticipationTOList(Set<CorporateParticipation> set) {
        if ( set == null ) {
            return null;
        }

        List<CorporateParticipationTO> list = new ArrayList<CorporateParticipationTO>( set.size() );
        for ( CorporateParticipation corporateParticipation : set ) {
            list.add( corporateParticipationToCorporateParticipationTO( corporateParticipation ) );
        }

        return list;
    }
}
