package com.linkapital.core.services.company.contract;

import com.linkapital.core.services.authorization.contract.to.authorization.CompanyClientAuthorizationTO;
import com.linkapital.core.services.authorization.datasource.domain.OwnerAuthorization;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureUrgencyTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankDocumentTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclatureUrgency;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.CompanyBankDocument;
import com.linkapital.core.services.bank_operation.contract.to.BankOperationTO;
import com.linkapital.core.services.bank_operation.datasource.domain.BankOperation;
import com.linkapital.core.services.company.contract.to.CeisTO;
import com.linkapital.core.services.company.contract.to.CnaeTO;
import com.linkapital.core.services.company.contract.to.CndsTO;
import com.linkapital.core.services.company.contract.to.CnepTO;
import com.linkapital.core.services.company.contract.to.CnjCniaTO;
import com.linkapital.core.services.company.contract.to.CompanyBeneficiaryTO;
import com.linkapital.core.services.company.contract.to.CompanyExportTO;
import com.linkapital.core.services.company.contract.to.CompanyLocationTO;
import com.linkapital.core.services.company.contract.to.CompanyMainInfoTO;
import com.linkapital.core.services.company.contract.to.CompanyPartnersTO;
import com.linkapital.core.services.company.contract.to.CompanyRelatedTO;
import com.linkapital.core.services.company.contract.to.CrsfnTO;
import com.linkapital.core.services.company.contract.to.EmployeeGrowthTO;
import com.linkapital.core.services.company.contract.to.FinancialActivityTO;
import com.linkapital.core.services.company.contract.to.HeavyVehicleInfoTO;
import com.linkapital.core.services.company.contract.to.HeavyVehicleTO;
import com.linkapital.core.services.company.contract.to.InternationalListTO;
import com.linkapital.core.services.company.contract.to.LightCompanyTO;
import com.linkapital.core.services.company.contract.to.MainCnaeTO;
import com.linkapital.core.services.company.contract.to.ProconGroupTO;
import com.linkapital.core.services.company.contract.to.ProconProcessesTO;
import com.linkapital.core.services.company.contract.to.ProconTO;
import com.linkapital.core.services.company.contract.to.SimpleNationalTO;
import com.linkapital.core.services.company.contract.to.SintegraInscriptionTO;
import com.linkapital.core.services.company.contract.to.SuframaTO;
import com.linkapital.core.services.company.contract.to.TaxHealthTO;
import com.linkapital.core.services.company.contract.to.WorkMteTO;
import com.linkapital.core.services.company.datasource.domain.Ceis;
import com.linkapital.core.services.company.datasource.domain.Cnae;
import com.linkapital.core.services.company.datasource.domain.Cnds;
import com.linkapital.core.services.company.datasource.domain.Cnep;
import com.linkapital.core.services.company.datasource.domain.CnjCnia;
import com.linkapital.core.services.company.datasource.domain.Company;
import com.linkapital.core.services.company.datasource.domain.CompanyBeneficiary;
import com.linkapital.core.services.company.datasource.domain.CompanyEmployee;
import com.linkapital.core.services.company.datasource.domain.CompanyExport;
import com.linkapital.core.services.company.datasource.domain.CompanyMainInfo;
import com.linkapital.core.services.company.datasource.domain.CompanyPartners;
import com.linkapital.core.services.company.datasource.domain.CompanyRelated;
import com.linkapital.core.services.company.datasource.domain.Crsfn;
import com.linkapital.core.services.company.datasource.domain.EmployeeGrowth;
import com.linkapital.core.services.company.datasource.domain.FinancialActivity;
import com.linkapital.core.services.company.datasource.domain.HeavyVehicle;
import com.linkapital.core.services.company.datasource.domain.HeavyVehicleInfo;
import com.linkapital.core.services.company.datasource.domain.InternationalList;
import com.linkapital.core.services.company.datasource.domain.Procon;
import com.linkapital.core.services.company.datasource.domain.ProconGroup;
import com.linkapital.core.services.company.datasource.domain.ProconProcesses;
import com.linkapital.core.services.company.datasource.domain.SimpleNational;
import com.linkapital.core.services.company.datasource.domain.SintegraInscription;
import com.linkapital.core.services.company.datasource.domain.Suframa;
import com.linkapital.core.services.company.datasource.domain.TaxHealth;
import com.linkapital.core.services.company.datasource.domain.WorkMte;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.CompanyEmployeeTO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning1TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearningTO;
import com.linkapital.core.services.company_user.contract.to.LightBackOfficeTO;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import com.linkapital.core.services.credit_information.contract.to.EarningTO;
import com.linkapital.core.services.credit_information.contract.to.ResumeOperationTO;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.credit_information.datasource.domain.Earning;
import com.linkapital.core.services.credit_information.datasource.domain.ResumeOperation;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CriCraDebentureTO;
import com.linkapital.core.services.cri_cra_debenture.datasource.domain.CriCraDebenture;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.ibge.contract.to.EconomicStatisticsTO;
import com.linkapital.core.services.ibge.contract.to.GeographicStatisticsTO;
import com.linkapital.core.services.ibge.contract.to.IbgeTO;
import com.linkapital.core.services.ibge.contract.to.WorkPerformanceStatisticsTO;
import com.linkapital.core.services.ibge.datasource.domain.EconomicStatistics;
import com.linkapital.core.services.ibge.datasource.domain.GeographicStatistics;
import com.linkapital.core.services.ibge.datasource.domain.Ibge;
import com.linkapital.core.services.ibge.datasource.domain.WorkPerformanceStatistics;
import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionTO;
import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionVariableTO;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProduction;
import com.linkapital.core.services.industrial_production_index.datasource.domain.PhysicalProductionVariable;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankLightTO;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import com.linkapital.core.services.person.contract.to.CorporateParticipationTO;
import com.linkapital.core.services.person.contract.to.DisabilitiesBacenTO;
import com.linkapital.core.services.person.contract.to.HistoricalCriminalTO;
import com.linkapital.core.services.person.contract.to.HistoricalFunctionalTO;
import com.linkapital.core.services.person.contract.to.IrpfTO;
import com.linkapital.core.services.person.contract.to.JudicialProcessQuantityTO;
import com.linkapital.core.services.person.contract.to.JudicialProcessTO;
import com.linkapital.core.services.person.contract.to.LightPersonTO;
import com.linkapital.core.services.person.contract.to.LightSpouseTO;
import com.linkapital.core.services.person.contract.to.RelationshipTO;
import com.linkapital.core.services.person.datasource.domain.CorporateParticipation;
import com.linkapital.core.services.person.datasource.domain.DisabilitiesBacen;
import com.linkapital.core.services.person.datasource.domain.HistoricalCriminal;
import com.linkapital.core.services.person.datasource.domain.HistoricalFunctional;
import com.linkapital.core.services.person.datasource.domain.Irpf;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.person.datasource.domain.Relationship;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import com.linkapital.core.services.shared.contract.to.CafirTO;
import com.linkapital.core.services.shared.contract.to.DebitMteProcessTO;
import com.linkapital.core.services.shared.contract.to.DebitMteTO;
import com.linkapital.core.services.shared.contract.to.DebitPgfnDauTO;
import com.linkapital.core.services.shared.contract.to.DebitPgfnTO;
import com.linkapital.core.services.shared.contract.to.PropertyRuralTO;
import com.linkapital.core.services.shared.contract.to.PropertyTO;
import com.linkapital.core.services.shared.datasource.domain.Address;
import com.linkapital.core.services.shared.datasource.domain.Cafir;
import com.linkapital.core.services.shared.datasource.domain.DebitMte;
import com.linkapital.core.services.shared.datasource.domain.DebitMteProcess;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfn;
import com.linkapital.core.services.shared.datasource.domain.DebitPgfnDau;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcess;
import com.linkapital.core.services.shared.datasource.domain.JudicialProcessQuantity;
import com.linkapital.core.services.shared.datasource.domain.Property;
import com.linkapital.core.services.shared.datasource.domain.PropertyRural;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class CompanyBinderImpl implements CompanyBinder {

    @Override
    public CompanyLearning1TO bindToLearning1TO(Company source) {
        if ( source == null ) {
            return null;
        }

        CompanyLearning1TO companyLearning1TO = new CompanyLearning1TO();

        companyLearning1TO.setId( source.getId() );
        companyLearning1TO.setLegalNatureCode( source.getLegalNatureCode() );
        companyLearning1TO.setLegalNatureDescription( source.getLegalNatureDescription() );
        companyLearning1TO.setDateRegistrationSituation( source.getDateRegistrationSituation() );
        companyLearning1TO.setRegistrationSituationReason( source.getRegistrationSituationReason() );
        companyLearning1TO.setDateSpecialSituation( source.getDateSpecialSituation() );
        companyLearning1TO.setSpecialSituation( source.getSpecialSituation() );
        companyLearning1TO.setDeliveryPropensity( source.getDeliveryPropensity() );
        companyLearning1TO.setECommercePropensity( source.getECommercePropensity() );
        companyLearning1TO.setAge( source.getAge() );
        companyLearning1TO.setGrossBilling( source.getGrossBilling() );
        companyLearning1TO.setSocialCapital( source.getSocialCapital() );
        companyLearning1TO.setCompanySize( source.getCompanySize() );
        companyLearning1TO.setCompanyClosingPropensity( source.getCompanyClosingPropensity() );
        companyLearning1TO.setMainInfo( companyMainInfoToCompanyMainInfoTO( source.getMainInfo() ) );
        companyLearning1TO.setMainCnae( cnaeToMainCnaeTO( source.getMainCnae() ) );
        companyLearning1TO.setDebitPgfnDau( debitPgfnDauToDebitPgfnDauTO( source.getDebitPgfnDau() ) );
        companyLearning1TO.setTaxHealth( taxHealthToTaxHealthTO( source.getTaxHealth() ) );
        companyLearning1TO.setIbge( ibgeToIbgeTO( source.getIbge() ) );
        companyLearning1TO.setEmployees( companyEmployeeSetToCompanyEmployeeTOList( source.getEmployees() ) );
        companyLearning1TO.setExEmployees( companyEmployeeSetToCompanyEmployeeTOList( source.getExEmployees() ) );
        companyLearning1TO.setBeneficiaries( companyBeneficiarySetToCompanyBeneficiaryTOList( source.getBeneficiaries() ) );
        companyLearning1TO.setPartners( companyPartnersSetToCompanyPartnersTOList( source.getPartners() ) );
        companyLearning1TO.setEmployeeGrowths( employeeGrowthSetToEmployeeGrowthTOList( source.getEmployeeGrowths() ) );
        companyLearning1TO.setBankOperations( bankOperationSetToBankOperationTOList( source.getBankOperations() ) );
        companyLearning1TO.setCreditsInformation( creditInformationSetToCreditInformationTOList( source.getCreditsInformation() ) );
        companyLearning1TO.setCriCraDebentures( criCraDebentureSetToCriCraDebentureTOList( source.getCriCraDebentures() ) );
        companyLearning1TO.setJucespDocuments( directorySetToDirectoryTOList( source.getJucespDocuments() ) );

        companyLearning1TO.setFantasyName( getFantasyName.apply(source) );

        return companyLearning1TO;
    }

    @Override
    public CompanyClientTO bindClientTO(CompanyUser source) {
        if ( source == null ) {
            return null;
        }

        CompanyClientTO companyClientTO = new CompanyClientTO();

        companyClientTO.setAvgReceiptTermInvoices( source.getAvgReceiptTermInvoices() );
        companyClientTO.setCreditRequested( source.getCreditRequested() );
        companyClientTO.setInvoicingInformed( source.getInvoicingInformed() );
        companyClientTO.setInitIndicativeOfferOne( source.isInitIndicativeOfferOne() );
        companyClientTO.setInitIndicativeOfferTwo( source.isInitIndicativeOfferTwo() );
        companyClientTO.setInitIndicativeOfferThree( source.isInitIndicativeOfferThree() );
        companyClientTO.setInitIndicativeOfferFour( source.isInitIndicativeOfferFour() );
        companyClientTO.setOwner( source.isOwner() );
        companyClientTO.setOwnerAuthorization( ownerAuthorizationToCompanyClientAuthorizationTO( source.getOwnerAuthorization() ) );

        companyClientTO.setId( source.getCompany().getId() );
        companyClientTO.setCnpj( source.getCompany().getMainInfo().getCnpj() );
        companyClientTO.setFantasyName( getFantasyName2.apply(source) );
        companyClientTO.setSocialReason( source.getCompany().getMainInfo().getSocialReason() );
        companyClientTO.setScr( source.getCompany().isScr() );
        companyClientTO.setHasAnOwner( source.getCompany().getUserOwnerId() != null );
        companyClientTO.setHasSpedBalanceDocument( source.getSpedDocument() != null );
        companyClientTO.setHasNfeDuplicity( !source.getNfeDuplicity().isEmpty() );
        companyClientTO.setHasSpedDocuments( !source.getSpeds().isEmpty() );
        companyClientTO.setHasInvoiceDocuments( !source.getIssuedInvoices().isEmpty() );

        return companyClientTO;
    }

    @Override
    public LightBackOfficeTO bindToLightBackOfficeWitchUserTO(Company source, User user) {
        if ( source == null && user == null ) {
            return null;
        }

        LightBackOfficeTO lightBackOfficeTO = new LightBackOfficeTO();

        if ( source != null ) {
            lightBackOfficeTO.setEstimatedBilling( source.getEstimatedBilling() );
            lightBackOfficeTO.setSimpleNational( source.getSimpleNational() );
        }
        if ( user != null ) {
            lightBackOfficeTO.setAddress( addressToAddressTO( user.getAddress() ) );
        }
        lightBackOfficeTO.setId( source.getId() );
        lightBackOfficeTO.setCreated( source.getCreated() );
        lightBackOfficeTO.setCnpj( source.getMainInfo().getCnpj() );
        lightBackOfficeTO.setFantasyName( getFantasyName.apply(source) );
        lightBackOfficeTO.setSocialReason( source.getMainInfo().getSocialReason() );
        lightBackOfficeTO.setOpeningDate( source.getMainInfo().getOpeningDate() );

        return lightBackOfficeTO;
    }

    @Override
    public CompanyLearningTO bindToLearningTO(Company source) {
        if ( source == null ) {
            return null;
        }

        CompanyLearningTO companyLearningTO = new CompanyLearningTO();

        companyLearningTO.setId( source.getId() );
        companyLearningTO.setLegalNatureCode( source.getLegalNatureCode() );
        companyLearningTO.setLegalNatureDescription( source.getLegalNatureDescription() );
        companyLearningTO.setDateRegistrationSituation( source.getDateRegistrationSituation() );
        companyLearningTO.setRegistrationSituationReason( source.getRegistrationSituationReason() );
        companyLearningTO.setDateSpecialSituation( source.getDateSpecialSituation() );
        companyLearningTO.setSpecialSituation( source.getSpecialSituation() );
        companyLearningTO.setDeliveryPropensity( source.getDeliveryPropensity() );
        companyLearningTO.setECommercePropensity( source.getECommercePropensity() );
        companyLearningTO.setAge( source.getAge() );
        companyLearningTO.setGrossBilling( source.getGrossBilling() );
        companyLearningTO.setSocialCapital( source.getSocialCapital() );
        companyLearningTO.setCompanySize( source.getCompanySize() );
        companyLearningTO.setCompanyClosingPropensity( source.getCompanyClosingPropensity() );
        companyLearningTO.setMainInfo( companyMainInfoToCompanyMainInfoTO( source.getMainInfo() ) );
        companyLearningTO.setMainCnae( cnaeToMainCnaeTO( source.getMainCnae() ) );
        companyLearningTO.setDebitPgfnDau( debitPgfnDauToDebitPgfnDauTO( source.getDebitPgfnDau() ) );
        companyLearningTO.setTaxHealth( taxHealthToTaxHealthTO( source.getTaxHealth() ) );
        companyLearningTO.setIbge( ibgeToIbgeTO( source.getIbge() ) );
        companyLearningTO.setEmployees( companyEmployeeSetToCompanyEmployeeTOList( source.getEmployees() ) );
        companyLearningTO.setExEmployees( companyEmployeeSetToCompanyEmployeeTOList( source.getExEmployees() ) );
        companyLearningTO.setBeneficiaries( companyBeneficiarySetToCompanyBeneficiaryTOList( source.getBeneficiaries() ) );
        companyLearningTO.setPartners( companyPartnersSetToCompanyPartnersTOList( source.getPartners() ) );
        companyLearningTO.setEmployeeGrowths( employeeGrowthSetToEmployeeGrowthTOList( source.getEmployeeGrowths() ) );
        companyLearningTO.setBankOperations( bankOperationSetToBankOperationTOList( source.getBankOperations() ) );
        companyLearningTO.setCreditsInformation( creditInformationSetToCreditInformationTOList( source.getCreditsInformation() ) );
        companyLearningTO.setCriCraDebentures( criCraDebentureSetToCriCraDebentureTOList( source.getCriCraDebentures() ) );
        companyLearningTO.setJucespDocuments( directorySetToDirectoryTOList( source.getJucespDocuments() ) );
        companyLearningTO.setPassiveIISS( source.isPassiveIISS() );
        companyLearningTO.setJudicialProcess( judicialProcessToJudicialProcessTO1( source.getJudicialProcess() ) );
        companyLearningTO.setProcon( proconToProconTO( source.getProcon() ) );
        companyLearningTO.setHeavyVehicleInfo( heavyVehicleInfoToHeavyVehicleInfoTO( source.getHeavyVehicleInfo() ) );
        companyLearningTO.setFinancialActivity( financialActivityToFinancialActivityTO( source.getFinancialActivity() ) );
        companyLearningTO.setCafir( cafirToCafirTO1( source.getCafir() ) );
        companyLearningTO.setSimpleNational( simpleNationalToSimpleNationalTO( source.getSimpleNational() ) );
        companyLearningTO.setSuframa( suframaToSuframaTO( source.getSuframa() ) );
        companyLearningTO.setCnaes( cnaeSetToCnaeTOList( source.getCnaes() ) );
        companyLearningTO.setExports( companyExportSetToCompanyExportTOList( source.getExports() ) );
        companyLearningTO.setImports( companyExportSetToCompanyExportTOList( source.getImports() ) );
        companyLearningTO.setCnjCnias( cnjCniaSetToCnjCniaTOList( source.getCnjCnias() ) );
        companyLearningTO.setWorkMtes( workMteSetToWorkMteTOList( source.getWorkMtes() ) );
        companyLearningTO.setCrsfns( crsfnSetToCrsfnTOList( source.getCrsfns() ) );
        companyLearningTO.setCeis( ceisSetToCeisTOList( source.getCeis() ) );
        companyLearningTO.setCneps( cnepSetToCnepTOList( source.getCneps() ) );
        companyLearningTO.setInternationalLists( internationalListSetToInternationalListTOList( source.getInternationalLists() ) );
        companyLearningTO.setCompaniesRelated( companyRelatedSetToCompanyRelatedTOList( source.getCompaniesRelated() ) );
        companyLearningTO.setProperties( propertySetToPropertyTOList( source.getProperties() ) );
        companyLearningTO.setSintegraInscriptions( sintegraInscriptionSetToSintegraInscriptionTOList( source.getSintegraInscriptions() ) );

        companyLearningTO.setFantasyName( getFantasyName.apply(source) );

        return companyLearningTO;
    }

    @Override
    public List<CompanyLocationTO> bind(List<Company> source) {
        if ( source == null ) {
            return null;
        }

        List<CompanyLocationTO> list = new ArrayList<CompanyLocationTO>( source.size() );
        for ( Company company : source ) {
            list.add( companyToCompanyLocationTO( company ) );
        }

        return list;
    }

    @Override
    public List<CompanyBankDocumentTO> bindToCompanyBankDocumentListTO(Set<CompanyBankDocument> source) {
        if ( source == null ) {
            return null;
        }

        List<CompanyBankDocumentTO> list = new ArrayList<CompanyBankDocumentTO>( source.size() );
        for ( CompanyBankDocument companyBankDocument : source ) {
            list.add( companyBankDocumentToCompanyBankDocumentTO( companyBankDocument ) );
        }

        return list;
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

    protected CompanyMainInfoTO companyMainInfoToCompanyMainInfoTO(CompanyMainInfo companyMainInfo) {
        if ( companyMainInfo == null ) {
            return null;
        }

        CompanyMainInfoTO companyMainInfoTO = new CompanyMainInfoTO();

        if ( companyMainInfo.getId() != null ) {
            companyMainInfoTO.setId( companyMainInfo.getId() );
        }
        companyMainInfoTO.setCnpj( companyMainInfo.getCnpj() );
        companyMainInfoTO.setSocialReason( companyMainInfo.getSocialReason() );
        companyMainInfoTO.setOpeningDate( companyMainInfo.getOpeningDate() );
        companyMainInfoTO.setRegistrationSituation( companyMainInfo.getRegistrationSituation() );
        companyMainInfoTO.setAddress( addressToAddressTO( companyMainInfo.getAddress() ) );

        return companyMainInfoTO;
    }

    protected PhysicalProductionVariableTO physicalProductionVariableToPhysicalProductionVariableTO(PhysicalProductionVariable physicalProductionVariable) {
        if ( physicalProductionVariable == null ) {
            return null;
        }

        PhysicalProductionVariableTO physicalProductionVariableTO = new PhysicalProductionVariableTO();

        if ( physicalProductionVariable.getId() != null ) {
            physicalProductionVariableTO.setId( physicalProductionVariable.getId() );
        }
        physicalProductionVariableTO.setMeasureUnit( physicalProductionVariable.getMeasureUnit() );
        physicalProductionVariableTO.setName( physicalProductionVariable.getName() );
        physicalProductionVariableTO.setValue( physicalProductionVariable.getValue() );

        return physicalProductionVariableTO;
    }

    protected PhysicalProductionTO physicalProductionToPhysicalProductionTO(PhysicalProduction physicalProduction) {
        if ( physicalProduction == null ) {
            return null;
        }

        PhysicalProductionTO physicalProductionTO = new PhysicalProductionTO();

        if ( physicalProduction.getId() != null ) {
            physicalProductionTO.setId( physicalProduction.getId() );
        }
        physicalProductionTO.setTerritorialLevel( physicalProduction.getTerritorialLevel() );
        physicalProductionTO.setCodeDescription( physicalProduction.getCodeDescription() );
        physicalProductionTO.setDate( physicalProduction.getDate() );
        physicalProductionTO.setMonthlyIndex( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getMonthlyIndex() ) );
        physicalProductionTO.setFixedBaseIndexWithoutSeasonalAdjustment( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getFixedBaseIndexWithoutSeasonalAdjustment() ) );
        physicalProductionTO.setIndexAccumulatedLast12Months( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getIndexAccumulatedLast12Months() ) );
        physicalProductionTO.setMonthlyPercentageChange( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getMonthlyPercentageChange() ) );
        physicalProductionTO.setPercentageChangeAccumulatedYear( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getPercentageChangeAccumulatedYear() ) );
        physicalProductionTO.setYearToDateIndex( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getYearToDateIndex() ) );
        physicalProductionTO.setPercentageChangeAccumulatedLast12Months( physicalProductionVariableToPhysicalProductionVariableTO( physicalProduction.getPercentageChangeAccumulatedLast12Months() ) );

        return physicalProductionTO;
    }

    protected List<PhysicalProductionTO> physicalProductionListToPhysicalProductionTOList(List<PhysicalProduction> list) {
        if ( list == null ) {
            return null;
        }

        List<PhysicalProductionTO> list1 = new ArrayList<PhysicalProductionTO>( list.size() );
        for ( PhysicalProduction physicalProduction : list ) {
            list1.add( physicalProductionToPhysicalProductionTO( physicalProduction ) );
        }

        return list1;
    }

    protected MainCnaeTO cnaeToMainCnaeTO(Cnae cnae) {
        if ( cnae == null ) {
            return null;
        }

        MainCnaeTO mainCnaeTO = new MainCnaeTO();

        if ( cnae.getId() != null ) {
            mainCnaeTO.setId( cnae.getId() );
        }
        mainCnaeTO.setCode( cnae.getCode() );
        mainCnaeTO.setDescription( cnae.getDescription() );
        mainCnaeTO.setBusinessActivity( cnae.getBusinessActivity() );
        mainCnaeTO.setSector( cnae.getSector() );
        mainCnaeTO.setPhysicalProductions( physicalProductionListToPhysicalProductionTOList( cnae.getPhysicalProductions() ) );

        return mainCnaeTO;
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

    protected CndsTO cndsToCndsTO(Cnds cnds) {
        if ( cnds == null ) {
            return null;
        }

        CndsTO cndsTO = new CndsTO();

        if ( cnds.getId() != null ) {
            cndsTO.setId( cnds.getId() );
        }
        if ( cnds.getEmitterName() != null ) {
            cndsTO.setEmitterName( cnds.getEmitterName().name() );
        }
        cndsTO.setSituation( cnds.getSituation() );
        cndsTO.setCertificateNumber( cnds.getCertificateNumber() );
        cndsTO.setEmissionDate( cnds.getEmissionDate() );
        cndsTO.setExpirationDate( cnds.getExpirationDate() );
        cndsTO.setDocument( directoryToDirectoryTO( cnds.getDocument() ) );

        return cndsTO;
    }

    protected List<CndsTO> cndsListToCndsTOList(List<Cnds> list) {
        if ( list == null ) {
            return null;
        }

        List<CndsTO> list1 = new ArrayList<CndsTO>( list.size() );
        for ( Cnds cnds : list ) {
            list1.add( cndsToCndsTO( cnds ) );
        }

        return list1;
    }

    protected TaxHealthTO taxHealthToTaxHealthTO(TaxHealth taxHealth) {
        if ( taxHealth == null ) {
            return null;
        }

        TaxHealthTO taxHealthTO = new TaxHealthTO();

        if ( taxHealth.getId() != null ) {
            taxHealthTO.setId( taxHealth.getId() );
        }
        taxHealthTO.setTaxHealth( taxHealth.getTaxHealth() );
        taxHealthTO.setCnds( cndsListToCndsTOList( taxHealth.getCnds() ) );

        return taxHealthTO;
    }

    protected GeographicStatisticsTO geographicStatisticsToGeographicStatisticsTO(GeographicStatistics geographicStatistics) {
        if ( geographicStatistics == null ) {
            return null;
        }

        GeographicStatisticsTO geographicStatisticsTO = new GeographicStatisticsTO();

        if ( geographicStatistics.getId() != null ) {
            geographicStatisticsTO.setId( geographicStatistics.getId() );
        }
        geographicStatisticsTO.setEstimatedPopulationYear( geographicStatistics.getEstimatedPopulationYear() );
        geographicStatisticsTO.setEstimatedPopulationLastCensusYear( geographicStatistics.getEstimatedPopulationLastCensusYear() );
        geographicStatisticsTO.setDemographicDensityYear( geographicStatistics.getDemographicDensityYear() );
        geographicStatisticsTO.setEstimatedPopulation( geographicStatistics.getEstimatedPopulation() );
        geographicStatisticsTO.setEstimatedPopulationLastCensus( geographicStatistics.getEstimatedPopulationLastCensus() );
        geographicStatisticsTO.setDemographicDensity( geographicStatistics.getDemographicDensity() );

        return geographicStatisticsTO;
    }

    protected EconomicStatisticsTO economicStatisticsToEconomicStatisticsTO(EconomicStatistics economicStatistics) {
        if ( economicStatistics == null ) {
            return null;
        }

        EconomicStatisticsTO economicStatisticsTO = new EconomicStatisticsTO();

        if ( economicStatistics.getId() != null ) {
            economicStatisticsTO.setId( economicStatistics.getId() );
        }
        economicStatisticsTO.setPibYear( economicStatistics.getPibYear() );
        economicStatisticsTO.setIdhmYear( economicStatistics.getIdhmYear() );
        economicStatisticsTO.setTotalRevenueYear( economicStatistics.getTotalRevenueYear() );
        economicStatisticsTO.setTotalExpensesYear( economicStatistics.getTotalExpensesYear() );
        economicStatisticsTO.setPercentageRevenueSourcesYear( economicStatistics.getPercentageRevenueSourcesYear() );
        economicStatisticsTO.setPib( economicStatistics.getPib() );
        economicStatisticsTO.setIdhm( economicStatistics.getIdhm() );
        economicStatisticsTO.setTotalRevenue( economicStatistics.getTotalRevenue() );
        economicStatisticsTO.setTotalExpenses( economicStatistics.getTotalExpenses() );
        economicStatisticsTO.setPercentageRevenueSources( economicStatistics.getPercentageRevenueSources() );

        return economicStatisticsTO;
    }

    protected WorkPerformanceStatisticsTO workPerformanceStatisticsToWorkPerformanceStatisticsTO(WorkPerformanceStatistics workPerformanceStatistics) {
        if ( workPerformanceStatistics == null ) {
            return null;
        }

        WorkPerformanceStatisticsTO workPerformanceStatisticsTO = new WorkPerformanceStatisticsTO();

        if ( workPerformanceStatistics.getId() != null ) {
            workPerformanceStatisticsTO.setId( workPerformanceStatistics.getId() );
        }
        workPerformanceStatisticsTO.setAverageSalaryYear( workPerformanceStatistics.getAverageSalaryYear() );
        workPerformanceStatisticsTO.setBusyPeopleYear( workPerformanceStatistics.getBusyPeopleYear() );
        workPerformanceStatisticsTO.setOccupiedPopulationYear( workPerformanceStatistics.getOccupiedPopulationYear() );
        workPerformanceStatisticsTO.setPopulationIncomeMonthlyNominalYear( workPerformanceStatistics.getPopulationIncomeMonthlyNominalYear() );
        workPerformanceStatisticsTO.setAverageSalary( workPerformanceStatistics.getAverageSalary() );
        workPerformanceStatisticsTO.setBusyPeople( workPerformanceStatistics.getBusyPeople() );
        workPerformanceStatisticsTO.setOccupiedPopulation( workPerformanceStatistics.getOccupiedPopulation() );
        workPerformanceStatisticsTO.setPopulationIncomeMonthlyNominal( workPerformanceStatistics.getPopulationIncomeMonthlyNominal() );

        return workPerformanceStatisticsTO;
    }

    protected IbgeTO ibgeToIbgeTO(Ibge ibge) {
        if ( ibge == null ) {
            return null;
        }

        IbgeTO ibgeTO = new IbgeTO();

        if ( ibge.getId() != null ) {
            ibgeTO.setId( ibge.getId() );
        }
        ibgeTO.setGeographicStatistics( geographicStatisticsToGeographicStatisticsTO( ibge.getGeographicStatistics() ) );
        ibgeTO.setEconomicStatistics( economicStatisticsToEconomicStatisticsTO( ibge.getEconomicStatistics() ) );
        ibgeTO.setWorkPerformanceStatistics( workPerformanceStatisticsToWorkPerformanceStatisticsTO( ibge.getWorkPerformanceStatistics() ) );

        return ibgeTO;
    }

    protected CompanyEmployeeTO companyEmployeeToCompanyEmployeeTO(CompanyEmployee companyEmployee) {
        if ( companyEmployee == null ) {
            return null;
        }

        CompanyEmployeeTO companyEmployeeTO = new CompanyEmployeeTO();

        if ( companyEmployee.getId() != null ) {
            companyEmployeeTO.setId( companyEmployee.getId() );
        }
        companyEmployeeTO.setCpf( companyEmployee.getCpf() );
        companyEmployeeTO.setName( companyEmployee.getName() );
        companyEmployeeTO.setResignationYear( companyEmployee.getResignationYear() );
        companyEmployeeTO.setAdmissionDate( companyEmployee.getAdmissionDate() );
        companyEmployeeTO.setBirthDate( companyEmployee.getBirthDate() );

        return companyEmployeeTO;
    }

    protected List<CompanyEmployeeTO> companyEmployeeSetToCompanyEmployeeTOList(Set<CompanyEmployee> set) {
        if ( set == null ) {
            return null;
        }

        List<CompanyEmployeeTO> list = new ArrayList<CompanyEmployeeTO>( set.size() );
        for ( CompanyEmployee companyEmployee : set ) {
            list.add( companyEmployeeToCompanyEmployeeTO( companyEmployee ) );
        }

        return list;
    }

    protected CompanyBeneficiaryTO companyBeneficiaryToCompanyBeneficiaryTO(CompanyBeneficiary companyBeneficiary) {
        if ( companyBeneficiary == null ) {
            return null;
        }

        CompanyBeneficiaryTO companyBeneficiaryTO = new CompanyBeneficiaryTO();

        if ( companyBeneficiary.getId() != null ) {
            companyBeneficiaryTO.setId( companyBeneficiary.getId() );
        }
        companyBeneficiaryTO.setDocument( companyBeneficiary.getDocument() );
        companyBeneficiaryTO.setName( companyBeneficiary.getName() );
        companyBeneficiaryTO.setGrade( companyBeneficiary.getGrade() );
        companyBeneficiaryTO.setGradeQsa( companyBeneficiary.getGradeQsa() );
        companyBeneficiaryTO.setParticipation( companyBeneficiary.getParticipation() );
        companyBeneficiaryTO.setParticipationQsa( companyBeneficiary.getParticipationQsa() );
        companyBeneficiaryTO.setDead( companyBeneficiary.isDead() );

        return companyBeneficiaryTO;
    }

    protected List<CompanyBeneficiaryTO> companyBeneficiarySetToCompanyBeneficiaryTOList(Set<CompanyBeneficiary> set) {
        if ( set == null ) {
            return null;
        }

        List<CompanyBeneficiaryTO> list = new ArrayList<CompanyBeneficiaryTO>( set.size() );
        for ( CompanyBeneficiary companyBeneficiary : set ) {
            list.add( companyBeneficiaryToCompanyBeneficiaryTO( companyBeneficiary ) );
        }

        return list;
    }

    protected PropertyRuralTO propertyRuralToPropertyRuralTO(PropertyRural propertyRural) {
        if ( propertyRural == null ) {
            return null;
        }

        PropertyRuralTO propertyRuralTO = new PropertyRuralTO();

        propertyRuralTO.setId( propertyRural.getId() );
        propertyRuralTO.setNirf( propertyRural.getNirf() );
        propertyRuralTO.setName( propertyRural.getName() );
        propertyRuralTO.setCondominium( propertyRural.getCondominium() );
        propertyRuralTO.setMunicipality( propertyRural.getMunicipality() );
        propertyRuralTO.setType( propertyRural.getType() );
        propertyRuralTO.setUf( propertyRural.getUf() );
        propertyRuralTO.setArea( propertyRural.getArea() );

        return propertyRuralTO;
    }

    protected List<PropertyRuralTO> propertyRuralListToPropertyRuralTOList(List<PropertyRural> list) {
        if ( list == null ) {
            return null;
        }

        List<PropertyRuralTO> list1 = new ArrayList<PropertyRuralTO>( list.size() );
        for ( PropertyRural propertyRural : list ) {
            list1.add( propertyRuralToPropertyRuralTO( propertyRural ) );
        }

        return list1;
    }

    protected CafirTO cafirToCafirTO(Cafir cafir) {
        if ( cafir == null ) {
            return null;
        }

        CafirTO cafirTO = new CafirTO();

        cafirTO.setId( cafir.getId() );
        cafirTO.setQuantityCondominiums( cafir.getQuantityCondominiums() );
        cafirTO.setQuantityHolder( cafir.getQuantityHolder() );
        cafirTO.setTotalArea( cafir.getTotalArea() );
        cafirTO.setPropertiesRural( propertyRuralListToPropertyRuralTOList( cafir.getPropertiesRural() ) );

        return cafirTO;
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

    protected PropertyTO propertyToPropertyTO(Property property) {
        if ( property == null ) {
            return null;
        }

        PropertyTO propertyTO = new PropertyTO();

        if ( property.getId() != null ) {
            propertyTO.setId( property.getId() );
        }
        propertyTO.setRegistryNumber( property.getRegistryNumber() );
        propertyTO.setBuildingData( property.getBuildingData() );
        propertyTO.setEvaluationValue( property.getEvaluationValue() );
        propertyTO.setBuiltArea( property.getBuiltArea() );
        propertyTO.setGroundArea( property.getGroundArea() );
        propertyTO.setAddress( addressToAddressTO( property.getAddress() ) );

        return propertyTO;
    }

    protected List<PropertyTO> propertySetToPropertyTOList(Set<Property> set) {
        if ( set == null ) {
            return null;
        }

        List<PropertyTO> list = new ArrayList<PropertyTO>( set.size() );
        for ( Property property : set ) {
            list.add( propertyToPropertyTO( property ) );
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

    protected LightSpouseTO personToLightSpouseTO(Person person) {
        if ( person == null ) {
            return null;
        }

        LightSpouseTO lightSpouseTO = new LightSpouseTO();

        lightSpouseTO.setId( person.getId() );
        lightSpouseTO.setCpf( person.getCpf() );
        lightSpouseTO.setName( person.getName() );
        lightSpouseTO.setSex( person.getSex() );
        lightSpouseTO.setEmail( person.getEmail() );
        lightSpouseTO.setCns( person.getCns() );
        lightSpouseTO.setNis( person.getNis() );
        lightSpouseTO.setMotherCpf( person.getMotherCpf() );
        lightSpouseTO.setMotherName( person.getMotherName() );
        lightSpouseTO.setFatherName( person.getFatherName() );
        lightSpouseTO.setOriginCountry( person.getOriginCountry() );
        lightSpouseTO.setSocialInscription( person.getSocialInscription() );
        lightSpouseTO.setDebitPgfnDauValue( person.getDebitPgfnDauValue() );
        lightSpouseTO.setSituationCpf( person.getSituationCpf() );
        lightSpouseTO.setEducationLevel( person.getEducationLevel() );
        lightSpouseTO.setDeficiencyType( person.getDeficiencyType() );
        lightSpouseTO.setProfessionNeoway( person.getProfessionNeoway() );
        lightSpouseTO.setProfessionCbo( person.getProfessionCbo() );
        lightSpouseTO.setSituation( person.getSituation() );
        lightSpouseTO.setRegisterSituation( person.getRegisterSituation() );
        lightSpouseTO.setMarriageRegime( person.getMarriageRegime() );
        lightSpouseTO.setInscriptionCpfDate( person.getInscriptionCpfDate() );
        lightSpouseTO.setQuantityQsaUnique( person.getQuantityQsaUnique() );
        lightSpouseTO.setAge( person.getAge() );
        lightSpouseTO.setDeadDate( person.getDeadDate() );
        lightSpouseTO.setDeficiency( person.isDeficiency() );
        lightSpouseTO.setDead( person.isDead() );
        lightSpouseTO.setDeadConfirmation( person.isDeadConfirmation() );
        lightSpouseTO.setPublicAgent( person.isPublicAgent() );
        lightSpouseTO.setBirthDate( person.getBirthDate() );
        lightSpouseTO.setMainAddress( addressToAddressTO( person.getMainAddress() ) );
        lightSpouseTO.setCafir( cafirToCafirTO( person.getCafir() ) );
        lightSpouseTO.setDebitPgfnDau( debitPgfnDauToDebitPgfnDauTO( person.getDebitPgfnDau() ) );
        lightSpouseTO.setDebitMte( debitMteToDebitMteTO( person.getDebitMte() ) );
        lightSpouseTO.setJudicialProcess( judicialProcessToJudicialProcessTO( person.getJudicialProcess() ) );
        lightSpouseTO.setHistoricalCriminal( historicalCriminalToHistoricalCriminalTO( person.getHistoricalCriminal() ) );
        Set<String> set = person.getPhones();
        if ( set != null ) {
            lightSpouseTO.setPhones( new ArrayList<String>( set ) );
        }
        Set<String> set1 = person.getCriminalProcess();
        if ( set1 != null ) {
            lightSpouseTO.setCriminalProcess( new ArrayList<String>( set1 ) );
        }
        lightSpouseTO.setIrpfDocuments( directoryListToDirectoryTOList( person.getIrpfDocuments() ) );
        lightSpouseTO.setIrpfReceiptDocuments( directoryListToDirectoryTOList( person.getIrpfReceiptDocuments() ) );
        lightSpouseTO.setIrpf( irpfSetToIrpfTOList( person.getIrpf() ) );
        lightSpouseTO.setHistoricalFunctional( historicalFunctionalSetToHistoricalFunctionalTOList( person.getHistoricalFunctional() ) );
        lightSpouseTO.setProperties( propertySetToPropertyTOList( person.getProperties() ) );
        lightSpouseTO.setRelationships( relationshipSetToRelationshipTOList( person.getRelationships() ) );
        lightSpouseTO.setDisabilitiesBacens( disabilitiesBacenSetToDisabilitiesBacenTOList( person.getDisabilitiesBacens() ) );
        lightSpouseTO.setOtherAddresses( addressSetToAddressTOList( person.getOtherAddresses() ) );
        lightSpouseTO.setCorporatesParticipation( corporateParticipationSetToCorporateParticipationTOList( person.getCorporatesParticipation() ) );

        return lightSpouseTO;
    }

    protected LightPersonTO personToLightPersonTO(Person person) {
        if ( person == null ) {
            return null;
        }

        LightPersonTO lightPersonTO = new LightPersonTO();

        lightPersonTO.setId( person.getId() );
        lightPersonTO.setCpf( person.getCpf() );
        lightPersonTO.setName( person.getName() );
        lightPersonTO.setSex( person.getSex() );
        lightPersonTO.setEmail( person.getEmail() );
        lightPersonTO.setCns( person.getCns() );
        lightPersonTO.setNis( person.getNis() );
        lightPersonTO.setMotherCpf( person.getMotherCpf() );
        lightPersonTO.setMotherName( person.getMotherName() );
        lightPersonTO.setFatherName( person.getFatherName() );
        lightPersonTO.setOriginCountry( person.getOriginCountry() );
        lightPersonTO.setSocialInscription( person.getSocialInscription() );
        lightPersonTO.setDebitPgfnDauValue( person.getDebitPgfnDauValue() );
        lightPersonTO.setSituationCpf( person.getSituationCpf() );
        lightPersonTO.setEducationLevel( person.getEducationLevel() );
        lightPersonTO.setDeficiencyType( person.getDeficiencyType() );
        lightPersonTO.setProfessionNeoway( person.getProfessionNeoway() );
        lightPersonTO.setProfessionCbo( person.getProfessionCbo() );
        lightPersonTO.setSituation( person.getSituation() );
        lightPersonTO.setRegisterSituation( person.getRegisterSituation() );
        lightPersonTO.setMarriageRegime( person.getMarriageRegime() );
        lightPersonTO.setInscriptionCpfDate( person.getInscriptionCpfDate() );
        lightPersonTO.setQuantityQsaUnique( person.getQuantityQsaUnique() );
        lightPersonTO.setAge( person.getAge() );
        lightPersonTO.setDeadDate( person.getDeadDate() );
        lightPersonTO.setDeficiency( person.isDeficiency() );
        lightPersonTO.setDead( person.isDead() );
        lightPersonTO.setDeadConfirmation( person.isDeadConfirmation() );
        lightPersonTO.setPublicAgent( person.isPublicAgent() );
        lightPersonTO.setBirthDate( person.getBirthDate() );
        lightPersonTO.setMainAddress( addressToAddressTO( person.getMainAddress() ) );
        lightPersonTO.setSpouse( personToLightSpouseTO( person.getSpouse() ) );
        lightPersonTO.setCafir( cafirToCafirTO( person.getCafir() ) );
        lightPersonTO.setDebitPgfnDau( debitPgfnDauToDebitPgfnDauTO( person.getDebitPgfnDau() ) );
        lightPersonTO.setDebitMte( debitMteToDebitMteTO( person.getDebitMte() ) );
        lightPersonTO.setJudicialProcess( judicialProcessToJudicialProcessTO( person.getJudicialProcess() ) );
        lightPersonTO.setHistoricalCriminal( historicalCriminalToHistoricalCriminalTO( person.getHistoricalCriminal() ) );
        Set<String> set = person.getPhones();
        if ( set != null ) {
            lightPersonTO.setPhones( new ArrayList<String>( set ) );
        }
        Set<String> set1 = person.getCriminalProcess();
        if ( set1 != null ) {
            lightPersonTO.setCriminalProcess( new ArrayList<String>( set1 ) );
        }
        lightPersonTO.setIrpfDocuments( directoryListToDirectoryTOList( person.getIrpfDocuments() ) );
        lightPersonTO.setIrpfReceiptDocuments( directoryListToDirectoryTOList( person.getIrpfReceiptDocuments() ) );
        lightPersonTO.setIrpf( irpfSetToIrpfTOList( person.getIrpf() ) );
        lightPersonTO.setHistoricalFunctional( historicalFunctionalSetToHistoricalFunctionalTOList( person.getHistoricalFunctional() ) );
        lightPersonTO.setProperties( propertySetToPropertyTOList( person.getProperties() ) );
        lightPersonTO.setRelationships( relationshipSetToRelationshipTOList( person.getRelationships() ) );
        lightPersonTO.setDisabilitiesBacens( disabilitiesBacenSetToDisabilitiesBacenTOList( person.getDisabilitiesBacens() ) );
        lightPersonTO.setOtherAddresses( addressSetToAddressTOList( person.getOtherAddresses() ) );
        lightPersonTO.setCorporatesParticipation( corporateParticipationSetToCorporateParticipationTOList( person.getCorporatesParticipation() ) );

        return lightPersonTO;
    }

    protected LightCompanyTO companyToLightCompanyTO(Company company) {
        if ( company == null ) {
            return null;
        }

        LightCompanyTO lightCompanyTO = new LightCompanyTO();

        lightCompanyTO.setId( company.getId() );
        lightCompanyTO.setMainInfo( companyMainInfoToCompanyMainInfoTO( company.getMainInfo() ) );

        return lightCompanyTO;
    }

    protected CompanyPartnersTO companyPartnersToCompanyPartnersTO(CompanyPartners companyPartners) {
        if ( companyPartners == null ) {
            return null;
        }

        CompanyPartnersTO companyPartnersTO = new CompanyPartnersTO();

        companyPartnersTO.setId( companyPartners.getId() );
        companyPartnersTO.setQualification( companyPartners.getQualification() );
        companyPartnersTO.setQualificationRF( companyPartners.getQualificationRF() );
        companyPartnersTO.setLevelPreparation( companyPartners.getLevelPreparation() );
        companyPartnersTO.setLevelPreparationRF( companyPartners.getLevelPreparationRF() );
        companyPartnersTO.setParticipation( companyPartners.getParticipation() );
        companyPartnersTO.setParticipationRF( companyPartners.getParticipationRF() );
        companyPartnersTO.setParticipationSocialCapital( companyPartners.getParticipationSocialCapital() );
        companyPartnersTO.setParticipationSocialCapitalRF( companyPartners.getParticipationSocialCapitalRF() );
        companyPartnersTO.setEntryDate( companyPartners.getEntryDate() );
        companyPartnersTO.setPerson( personToLightPersonTO( companyPartners.getPerson() ) );
        companyPartnersTO.setCompany( companyToLightCompanyTO( companyPartners.getCompany() ) );
        companyPartnersTO.setCompanyPartner( companyToLightCompanyTO( companyPartners.getCompanyPartner() ) );

        return companyPartnersTO;
    }

    protected List<CompanyPartnersTO> companyPartnersSetToCompanyPartnersTOList(Set<CompanyPartners> set) {
        if ( set == null ) {
            return null;
        }

        List<CompanyPartnersTO> list = new ArrayList<CompanyPartnersTO>( set.size() );
        for ( CompanyPartners companyPartners : set ) {
            list.add( companyPartnersToCompanyPartnersTO( companyPartners ) );
        }

        return list;
    }

    protected EmployeeGrowthTO employeeGrowthToEmployeeGrowthTO(EmployeeGrowth employeeGrowth) {
        if ( employeeGrowth == null ) {
            return null;
        }

        EmployeeGrowthTO employeeGrowthTO = new EmployeeGrowthTO();

        if ( employeeGrowth.getId() != null ) {
            employeeGrowthTO.setId( employeeGrowth.getId() );
        }
        employeeGrowthTO.setYear( employeeGrowth.getYear() );
        employeeGrowthTO.setEmployeeGrowth( employeeGrowth.getEmployeeGrowth() );
        employeeGrowthTO.setGrowth( employeeGrowth.getGrowth() );

        return employeeGrowthTO;
    }

    protected List<EmployeeGrowthTO> employeeGrowthSetToEmployeeGrowthTOList(Set<EmployeeGrowth> set) {
        if ( set == null ) {
            return null;
        }

        List<EmployeeGrowthTO> list = new ArrayList<EmployeeGrowthTO>( set.size() );
        for ( EmployeeGrowth employeeGrowth : set ) {
            list.add( employeeGrowthToEmployeeGrowthTO( employeeGrowth ) );
        }

        return list;
    }

    protected BankOperationTO bankOperationToBankOperationTO(BankOperation bankOperation) {
        if ( bankOperation == null ) {
            return null;
        }

        BankOperationTO bankOperationTO = new BankOperationTO();

        if ( bankOperation.getId() != null ) {
            bankOperationTO.setId( bankOperation.getId() );
        }
        bankOperationTO.setUf( bankOperation.getUf() );
        bankOperationTO.setProduct( bankOperation.getProduct() );
        bankOperationTO.setType( bankOperation.getType() );
        bankOperationTO.setFinancialCost( bankOperation.getFinancialCost() );
        bankOperationTO.setFinancialAgent( bankOperation.getFinancialAgent() );
        bankOperationTO.setGracePeriod( bankOperation.getGracePeriod() );
        bankOperationTO.setAmortizationPeriod( bankOperation.getAmortizationPeriod() );
        bankOperationTO.setTax( bankOperation.getTax() );
        bankOperationTO.setContractedValue( bankOperation.getContractedValue() );
        bankOperationTO.setContractedDate( bankOperation.getContractedDate() );
        bankOperationTO.setModified( bankOperation.getModified() );
        bankOperationTO.setStatus( bankOperation.getStatus() );

        return bankOperationTO;
    }

    protected List<BankOperationTO> bankOperationSetToBankOperationTOList(Set<BankOperation> set) {
        if ( set == null ) {
            return null;
        }

        List<BankOperationTO> list = new ArrayList<BankOperationTO>( set.size() );
        for ( BankOperation bankOperation : set ) {
            list.add( bankOperationToBankOperationTO( bankOperation ) );
        }

        return list;
    }

    protected EarningTO earningToEarningTO(Earning earning) {
        if ( earning == null ) {
            return null;
        }

        EarningTO earningTO = new EarningTO();

        if ( earning.getId() != null ) {
            earningTO.setId( earning.getId() );
        }
        earningTO.setCode( earning.getCode() );
        earningTO.setValue( earning.getValue() );

        return earningTO;
    }

    protected List<EarningTO> earningSetToEarningTOList(Set<Earning> set) {
        if ( set == null ) {
            return null;
        }

        List<EarningTO> list = new ArrayList<EarningTO>( set.size() );
        for ( Earning earning : set ) {
            list.add( earningToEarningTO( earning ) );
        }

        return list;
    }

    protected ResumeOperationTO resumeOperationToResumeOperationTO(ResumeOperation resumeOperation) {
        if ( resumeOperation == null ) {
            return null;
        }

        ResumeOperationTO resumeOperationTO = new ResumeOperationTO();

        if ( resumeOperation.getId() != null ) {
            resumeOperationTO.setId( resumeOperation.getId() );
        }
        resumeOperationTO.setModality( resumeOperation.getModality() );
        resumeOperationTO.setExchangeVariation( resumeOperation.getExchangeVariation() );
        resumeOperationTO.setEarnings( earningSetToEarningTOList( resumeOperation.getEarnings() ) );

        return resumeOperationTO;
    }

    protected List<ResumeOperationTO> resumeOperationSetToResumeOperationTOList(Set<ResumeOperation> set) {
        if ( set == null ) {
            return null;
        }

        List<ResumeOperationTO> list = new ArrayList<ResumeOperationTO>( set.size() );
        for ( ResumeOperation resumeOperation : set ) {
            list.add( resumeOperationToResumeOperationTO( resumeOperation ) );
        }

        return list;
    }

    protected CreditInformationTO creditInformationToCreditInformationTO(CreditInformation creditInformation) {
        if ( creditInformation == null ) {
            return null;
        }

        CreditInformationTO creditInformationTO = new CreditInformationTO();

        if ( creditInformation.getId() != null ) {
            creditInformationTO.setId( creditInformation.getId() );
        }
        creditInformationTO.setCnpjIfRequester( creditInformation.getCnpjIfRequester() );
        creditInformationTO.setConsultDate( creditInformation.getConsultDate() );
        creditInformationTO.setCountOperation( creditInformation.getCountOperation() );
        creditInformationTO.setCountInstitution( creditInformation.getCountInstitution() );
        creditInformationTO.setCountOperationSubJudice( creditInformation.getCountOperationSubJudice() );
        creditInformationTO.setResponsibilityTotalSubJudice( creditInformation.getResponsibilityTotalSubJudice() );
        creditInformationTO.setCountOperationDisagreement( creditInformation.getCountOperationDisagreement() );
        creditInformationTO.setResponsibilityTotalDisagreement( creditInformation.getResponsibilityTotalDisagreement() );
        creditInformationTO.setAssumedObligation( creditInformation.getAssumedObligation() );
        creditInformationTO.setVendorIndirectRisk( creditInformation.getVendorIndirectRisk() );
        creditInformationTO.setPercentDocumentProcessed( creditInformation.getPercentDocumentProcessed() );
        creditInformationTO.setPercentVolumeProcessed( creditInformation.getPercentVolumeProcessed() );
        creditInformationTO.setFind( creditInformation.isFind() );
        creditInformationTO.setStartRelationshipDate( creditInformation.getStartRelationshipDate() );
        creditInformationTO.setCreated( creditInformation.getCreated() );
        creditInformationTO.setOperations( resumeOperationSetToResumeOperationTOList( creditInformation.getOperations() ) );

        return creditInformationTO;
    }

    protected List<CreditInformationTO> creditInformationSetToCreditInformationTOList(Set<CreditInformation> set) {
        if ( set == null ) {
            return null;
        }

        List<CreditInformationTO> list = new ArrayList<CreditInformationTO>( set.size() );
        for ( CreditInformation creditInformation : set ) {
            list.add( creditInformationToCreditInformationTO( creditInformation ) );
        }

        return list;
    }

    protected CriCraDebentureTO criCraDebentureToCriCraDebentureTO(CriCraDebenture criCraDebenture) {
        if ( criCraDebenture == null ) {
            return null;
        }

        CriCraDebentureTO criCraDebentureTO = new CriCraDebentureTO();

        criCraDebentureTO.setCode( criCraDebenture.getCode() );
        criCraDebentureTO.setDebtorIssuer( criCraDebenture.getDebtorIssuer() );
        criCraDebentureTO.setInsuranceSector( criCraDebenture.getInsuranceSector() );
        criCraDebentureTO.setSeriesIssue( criCraDebenture.getSeriesIssue() );
        criCraDebentureTO.setRemuneration( criCraDebenture.getRemuneration() );
        criCraDebentureTO.setIndicativeValue( criCraDebenture.getIndicativeValue() );
        criCraDebentureTO.setSeriesVolumeOnIssueDate( criCraDebenture.getSeriesVolumeOnIssueDate() );
        criCraDebentureTO.setPuParDebenture( criCraDebenture.getPuParDebenture() );
        criCraDebentureTO.setIssueDate( criCraDebenture.getIssueDate() );
        criCraDebentureTO.setDueDate( criCraDebenture.getDueDate() );
        criCraDebentureTO.setType( criCraDebenture.getType() );
        if ( criCraDebenture.getId() != null ) {
            criCraDebentureTO.setId( criCraDebenture.getId() );
        }

        return criCraDebentureTO;
    }

    protected List<CriCraDebentureTO> criCraDebentureSetToCriCraDebentureTOList(Set<CriCraDebenture> set) {
        if ( set == null ) {
            return null;
        }

        List<CriCraDebentureTO> list = new ArrayList<CriCraDebentureTO>( set.size() );
        for ( CriCraDebenture criCraDebenture : set ) {
            list.add( criCraDebentureToCriCraDebentureTO( criCraDebenture ) );
        }

        return list;
    }

    protected List<DirectoryTO> directorySetToDirectoryTOList(Set<Directory> set) {
        if ( set == null ) {
            return null;
        }

        List<DirectoryTO> list = new ArrayList<DirectoryTO>( set.size() );
        for ( Directory directory : set ) {
            list.add( directoryToDirectoryTO( directory ) );
        }

        return list;
    }

    protected CompanyClientAuthorizationTO ownerAuthorizationToCompanyClientAuthorizationTO(OwnerAuthorization ownerAuthorization) {
        if ( ownerAuthorization == null ) {
            return null;
        }

        CompanyClientAuthorizationTO companyClientAuthorizationTO = new CompanyClientAuthorizationTO();

        if ( ownerAuthorization.getId() != null ) {
            companyClientAuthorizationTO.setId( ownerAuthorization.getId() );
        }
        companyClientAuthorizationTO.setState( ownerAuthorization.getState() );

        return companyClientAuthorizationTO;
    }

    protected com.linkapital.core.services.company.contract.to.JudicialProcessQuantityTO judicialProcessQuantityToJudicialProcessQuantityTO1(JudicialProcessQuantity judicialProcessQuantity) {
        if ( judicialProcessQuantity == null ) {
            return null;
        }

        com.linkapital.core.services.company.contract.to.JudicialProcessQuantityTO judicialProcessQuantityTO = new com.linkapital.core.services.company.contract.to.JudicialProcessQuantityTO();

        if ( judicialProcessQuantity.getId() != null ) {
            judicialProcessQuantityTO.setId( judicialProcessQuantity.getId() );
        }
        judicialProcessQuantityTO.setType( judicialProcessQuantity.getType() );
        judicialProcessQuantityTO.setQuantityTotal( judicialProcessQuantity.getQuantityTotal() );
        judicialProcessQuantityTO.setQuantityActive( judicialProcessQuantity.getQuantityActive() );
        judicialProcessQuantityTO.setQuantityOthers( judicialProcessQuantity.getQuantityOthers() );
        judicialProcessQuantityTO.setQuantityActivePart( judicialProcessQuantity.getQuantityActivePart() );
        judicialProcessQuantityTO.setQuantityPassivePart( judicialProcessQuantity.getQuantityPassivePart() );

        return judicialProcessQuantityTO;
    }

    protected List<com.linkapital.core.services.company.contract.to.JudicialProcessQuantityTO> judicialProcessQuantityListToJudicialProcessQuantityTOList1(List<JudicialProcessQuantity> list) {
        if ( list == null ) {
            return null;
        }

        List<com.linkapital.core.services.company.contract.to.JudicialProcessQuantityTO> list1 = new ArrayList<com.linkapital.core.services.company.contract.to.JudicialProcessQuantityTO>( list.size() );
        for ( JudicialProcessQuantity judicialProcessQuantity : list ) {
            list1.add( judicialProcessQuantityToJudicialProcessQuantityTO1( judicialProcessQuantity ) );
        }

        return list1;
    }

    protected com.linkapital.core.services.company.contract.to.JudicialProcessTO judicialProcessToJudicialProcessTO1(JudicialProcess judicialProcess) {
        if ( judicialProcess == null ) {
            return null;
        }

        com.linkapital.core.services.company.contract.to.JudicialProcessTO judicialProcessTO = new com.linkapital.core.services.company.contract.to.JudicialProcessTO();

        if ( judicialProcess.getId() != null ) {
            judicialProcessTO.setId( judicialProcess.getId() );
        }
        judicialProcessTO.setTotalOthersValue( judicialProcess.getTotalOthersValue() );
        judicialProcessTO.setTotalActiveValue( judicialProcess.getTotalActiveValue() );
        judicialProcessTO.setTotalPassiveValue( judicialProcess.getTotalPassiveValue() );
        judicialProcessTO.setTotalValue( judicialProcess.getTotalValue() );
        judicialProcessTO.setJudicialProcessQuantities( judicialProcessQuantityListToJudicialProcessQuantityTOList1( judicialProcess.getJudicialProcessQuantities() ) );

        return judicialProcessTO;
    }

    protected ProconGroupTO proconGroupToProconGroupTO(ProconGroup proconGroup) {
        if ( proconGroup == null ) {
            return null;
        }

        ProconGroupTO proconGroupTO = new ProconGroupTO();

        proconGroupTO.setCnpj( proconGroup.getCnpj() );
        proconGroupTO.setTotalPenaltyValue( proconGroup.getTotalPenaltyValue() );

        return proconGroupTO;
    }

    protected List<ProconGroupTO> proconGroupListToProconGroupTOList(List<ProconGroup> list) {
        if ( list == null ) {
            return null;
        }

        List<ProconGroupTO> list1 = new ArrayList<ProconGroupTO>( list.size() );
        for ( ProconGroup proconGroup : list ) {
            list1.add( proconGroupToProconGroupTO( proconGroup ) );
        }

        return list1;
    }

    protected ProconProcessesTO proconProcessesToProconProcessesTO(ProconProcesses proconProcesses) {
        if ( proconProcesses == null ) {
            return null;
        }

        ProconProcessesTO proconProcessesTO = new ProconProcessesTO();

        proconProcessesTO.setProcessNumber( proconProcesses.getProcessNumber() );
        proconProcessesTO.setStatus( proconProcesses.getStatus() );
        proconProcessesTO.setPenaltyValue( proconProcesses.getPenaltyValue() );

        return proconProcessesTO;
    }

    protected List<ProconProcessesTO> proconProcessesListToProconProcessesTOList(List<ProconProcesses> list) {
        if ( list == null ) {
            return null;
        }

        List<ProconProcessesTO> list1 = new ArrayList<ProconProcessesTO>( list.size() );
        for ( ProconProcesses proconProcesses : list ) {
            list1.add( proconProcessesToProconProcessesTO( proconProcesses ) );
        }

        return list1;
    }

    protected ProconTO proconToProconTO(Procon procon) {
        if ( procon == null ) {
            return null;
        }

        ProconTO proconTO = new ProconTO();

        proconTO.setName( procon.getName() );
        proconTO.setTotalPenaltyValue( procon.getTotalPenaltyValue() );
        proconTO.setGroupPenaltyValue( procon.getGroupPenaltyValue() );
        proconTO.setUpdateDate( procon.getUpdateDate() );
        proconTO.setProconGroups( proconGroupListToProconGroupTOList( procon.getProconGroups() ) );
        proconTO.setProconProcesses( proconProcessesListToProconProcessesTOList( procon.getProconProcesses() ) );

        return proconTO;
    }

    protected HeavyVehicleTO heavyVehicleToHeavyVehicleTO(HeavyVehicle heavyVehicle) {
        if ( heavyVehicle == null ) {
            return null;
        }

        HeavyVehicleTO heavyVehicleTO = new HeavyVehicleTO();

        heavyVehicleTO.setId( heavyVehicle.getId() );
        heavyVehicleTO.setFuel( heavyVehicle.getFuel() );
        heavyVehicleTO.setModel( heavyVehicle.getModel() );
        heavyVehicleTO.setCarPlate( heavyVehicle.getCarPlate() );
        heavyVehicleTO.setRenavam( heavyVehicle.getRenavam() );
        heavyVehicleTO.setType( heavyVehicle.getType() );
        heavyVehicleTO.setUf( heavyVehicle.getUf() );
        heavyVehicleTO.setAntt( heavyVehicle.isAntt() );
        heavyVehicleTO.setProductionYear( heavyVehicle.getProductionYear() );

        return heavyVehicleTO;
    }

    protected List<HeavyVehicleTO> heavyVehicleListToHeavyVehicleTOList(List<HeavyVehicle> list) {
        if ( list == null ) {
            return null;
        }

        List<HeavyVehicleTO> list1 = new ArrayList<HeavyVehicleTO>( list.size() );
        for ( HeavyVehicle heavyVehicle : list ) {
            list1.add( heavyVehicleToHeavyVehicleTO( heavyVehicle ) );
        }

        return list1;
    }

    protected HeavyVehicleInfoTO heavyVehicleInfoToHeavyVehicleInfoTO(HeavyVehicleInfo heavyVehicleInfo) {
        if ( heavyVehicleInfo == null ) {
            return null;
        }

        HeavyVehicleInfoTO heavyVehicleInfoTO = new HeavyVehicleInfoTO();

        heavyVehicleInfoTO.setId( heavyVehicleInfo.getId() );
        heavyVehicleInfoTO.setUpto1( heavyVehicleInfo.getUpto1() );
        heavyVehicleInfoTO.setOver10( heavyVehicleInfo.getOver10() );
        heavyVehicleInfoTO.setBetween2And5( heavyVehicleInfo.getBetween2And5() );
        heavyVehicleInfoTO.setBetween5And10( heavyVehicleInfo.getBetween5And10() );
        heavyVehicleInfoTO.setGroupUpTo1( heavyVehicleInfo.getGroupUpTo1() );
        heavyVehicleInfoTO.setGroupBetween2And5( heavyVehicleInfo.getGroupBetween2And5() );
        heavyVehicleInfoTO.setGroupBetween5And10( heavyVehicleInfo.getGroupBetween5And10() );
        heavyVehicleInfoTO.setGroupOver10( heavyVehicleInfo.getGroupOver10() );
        heavyVehicleInfoTO.setHeavyVehicles( heavyVehicleInfo.getHeavyVehicles() );
        heavyVehicleInfoTO.setHeavyVehiclesGroup( heavyVehicleInfo.getHeavyVehiclesGroup() );
        heavyVehicleInfoTO.setVehicles( heavyVehicleListToHeavyVehicleTOList( heavyVehicleInfo.getVehicles() ) );

        return heavyVehicleInfoTO;
    }

    protected FinancialActivityTO financialActivityToFinancialActivityTO(FinancialActivity financialActivity) {
        if ( financialActivity == null ) {
            return null;
        }

        FinancialActivityTO financialActivityTO = new FinancialActivityTO();

        financialActivityTO.setId( financialActivity.getId() );
        financialActivityTO.setSegment( financialActivity.getSegment() );
        financialActivityTO.setEnablementNumber( financialActivity.getEnablementNumber() );
        financialActivityTO.setEnablementSituation( financialActivity.getEnablementSituation() );
        financialActivityTO.setQueryDate( financialActivity.getQueryDate() );
        financialActivityTO.setEnablementDate( financialActivity.getEnablementDate() );

        return financialActivityTO;
    }

    protected com.linkapital.core.services.company.contract.to.PropertyRuralTO propertyRuralToPropertyRuralTO1(PropertyRural propertyRural) {
        if ( propertyRural == null ) {
            return null;
        }

        com.linkapital.core.services.company.contract.to.PropertyRuralTO propertyRuralTO = new com.linkapital.core.services.company.contract.to.PropertyRuralTO();

        propertyRuralTO.setCondominium( propertyRural.getCondominium() );
        propertyRuralTO.setMunicipality( propertyRural.getMunicipality() );
        propertyRuralTO.setNirf( propertyRural.getNirf() );
        propertyRuralTO.setName( propertyRural.getName() );
        propertyRuralTO.setType( propertyRural.getType() );
        propertyRuralTO.setUf( propertyRural.getUf() );

        return propertyRuralTO;
    }

    protected List<com.linkapital.core.services.company.contract.to.PropertyRuralTO> propertyRuralListToPropertyRuralTOList1(List<PropertyRural> list) {
        if ( list == null ) {
            return null;
        }

        List<com.linkapital.core.services.company.contract.to.PropertyRuralTO> list1 = new ArrayList<com.linkapital.core.services.company.contract.to.PropertyRuralTO>( list.size() );
        for ( PropertyRural propertyRural : list ) {
            list1.add( propertyRuralToPropertyRuralTO1( propertyRural ) );
        }

        return list1;
    }

    protected com.linkapital.core.services.company.contract.to.CafirTO cafirToCafirTO1(Cafir cafir) {
        if ( cafir == null ) {
            return null;
        }

        com.linkapital.core.services.company.contract.to.CafirTO cafirTO = new com.linkapital.core.services.company.contract.to.CafirTO();

        cafirTO.setTotalArea( cafir.getTotalArea() );
        cafirTO.setQuantityCondominiums( cafir.getQuantityCondominiums() );
        cafirTO.setQuantityHolder( cafir.getQuantityHolder() );
        cafirTO.setPropertiesRural( propertyRuralListToPropertyRuralTOList1( cafir.getPropertiesRural() ) );

        return cafirTO;
    }

    protected SimpleNationalTO simpleNationalToSimpleNationalTO(SimpleNational simpleNational) {
        if ( simpleNational == null ) {
            return null;
        }

        SimpleNationalTO simpleNationalTO = new SimpleNationalTO();

        if ( simpleNational.getId() != null ) {
            simpleNationalTO.setId( simpleNational.getId() );
        }
        simpleNationalTO.setSimei( simpleNational.isSimei() );
        simpleNationalTO.setSimple( simpleNational.isSimple() );
        simpleNationalTO.setSimpleIrregular( simpleNational.isSimpleIrregular() );
        simpleNationalTO.setSimeiDate( simpleNational.getSimeiDate() );
        simpleNationalTO.setSimpleDate( simpleNational.getSimpleDate() );

        return simpleNationalTO;
    }

    protected SuframaTO suframaToSuframaTO(Suframa suframa) {
        if ( suframa == null ) {
            return null;
        }

        SuframaTO suframaTO = new SuframaTO();

        suframaTO.setId( suframa.getId() );
        suframaTO.setRegistrationNumber( suframa.getRegistrationNumber() );
        suframaTO.setRegistrationSituation( suframa.getRegistrationSituation() );
        suframaTO.setIcms( suframa.getIcms() );
        suframaTO.setIpi( suframa.getIpi() );
        suframaTO.setPisCofins( suframa.getPisCofins() );
        suframaTO.setExpirationDate( suframa.getExpirationDate() );

        return suframaTO;
    }

    protected CnaeTO cnaeToCnaeTO(Cnae cnae) {
        if ( cnae == null ) {
            return null;
        }

        CnaeTO cnaeTO = new CnaeTO();

        if ( cnae.getId() != null ) {
            cnaeTO.setId( cnae.getId() );
        }
        cnaeTO.setCode( cnae.getCode() );
        cnaeTO.setDescription( cnae.getDescription() );
        cnaeTO.setBusinessActivity( cnae.getBusinessActivity() );
        cnaeTO.setSector( cnae.getSector() );

        return cnaeTO;
    }

    protected List<CnaeTO> cnaeSetToCnaeTOList(Set<Cnae> set) {
        if ( set == null ) {
            return null;
        }

        List<CnaeTO> list = new ArrayList<CnaeTO>( set.size() );
        for ( Cnae cnae : set ) {
            list.add( cnaeToCnaeTO( cnae ) );
        }

        return list;
    }

    protected CompanyExportTO companyExportToCompanyExportTO(CompanyExport companyExport) {
        if ( companyExport == null ) {
            return null;
        }

        CompanyExportTO companyExportTO = new CompanyExportTO();

        companyExportTO.setId( companyExport.getId() );
        companyExportTO.setValue( companyExport.getValue() );
        companyExportTO.setYear( companyExport.getYear() );

        return companyExportTO;
    }

    protected List<CompanyExportTO> companyExportSetToCompanyExportTOList(Set<CompanyExport> set) {
        if ( set == null ) {
            return null;
        }

        List<CompanyExportTO> list = new ArrayList<CompanyExportTO>( set.size() );
        for ( CompanyExport companyExport : set ) {
            list.add( companyExportToCompanyExportTO( companyExport ) );
        }

        return list;
    }

    protected CnjCniaTO cnjCniaToCnjCniaTO(CnjCnia cnjCnia) {
        if ( cnjCnia == null ) {
            return null;
        }

        CnjCniaTO cnjCniaTO = new CnjCniaTO();

        cnjCniaTO.setSphere( cnjCnia.getSphere() );
        cnjCniaTO.setProcessNumber( cnjCnia.getProcessNumber() );
        cnjCniaTO.setDescriptionEntity( cnjCnia.getDescriptionEntity() );
        cnjCniaTO.setUf( cnjCnia.getUf() );
        cnjCniaTO.setValue( cnjCnia.getValue() );
        cnjCniaTO.setRegistrationDate( cnjCnia.getRegistrationDate() );
        List<String> list = cnjCnia.getRelatedIssues();
        if ( list != null ) {
            cnjCniaTO.setRelatedIssues( new ArrayList<String>( list ) );
        }

        return cnjCniaTO;
    }

    protected List<CnjCniaTO> cnjCniaSetToCnjCniaTOList(Set<CnjCnia> set) {
        if ( set == null ) {
            return null;
        }

        List<CnjCniaTO> list = new ArrayList<CnjCniaTO>( set.size() );
        for ( CnjCnia cnjCnia : set ) {
            list.add( cnjCniaToCnjCniaTO( cnjCnia ) );
        }

        return list;
    }

    protected WorkMteTO workMteToWorkMteTO(WorkMte workMte) {
        if ( workMte == null ) {
            return null;
        }

        WorkMteTO workMteTO = new WorkMteTO();

        workMteTO.setFiscalActionYear( workMte.getFiscalActionYear() );
        workMteTO.setQuantityWorkers( workMte.getQuantityWorkers() );
        workMteTO.setProvenanceDecisionDate( workMte.getProvenanceDecisionDate() );
        workMteTO.setAddress( addressToAddressTO( workMte.getAddress() ) );

        return workMteTO;
    }

    protected List<WorkMteTO> workMteSetToWorkMteTOList(Set<WorkMte> set) {
        if ( set == null ) {
            return null;
        }

        List<WorkMteTO> list = new ArrayList<WorkMteTO>( set.size() );
        for ( WorkMte workMte : set ) {
            list.add( workMteToWorkMteTO( workMte ) );
        }

        return list;
    }

    protected CrsfnTO crsfnToCrsfnTO(Crsfn crsfn) {
        if ( crsfn == null ) {
            return null;
        }

        CrsfnTO crsfnTO = new CrsfnTO();

        crsfnTO.setAgreedNumber( crsfn.getAgreedNumber() );
        crsfnTO.setProcessNumber( crsfn.getProcessNumber() );
        crsfnTO.setResourceNumber( crsfn.getResourceNumber() );
        crsfnTO.setPart( crsfn.getPart() );
        crsfnTO.setResourceType( crsfn.getResourceType() );

        return crsfnTO;
    }

    protected List<CrsfnTO> crsfnSetToCrsfnTOList(Set<Crsfn> set) {
        if ( set == null ) {
            return null;
        }

        List<CrsfnTO> list = new ArrayList<CrsfnTO>( set.size() );
        for ( Crsfn crsfn : set ) {
            list.add( crsfnToCrsfnTO( crsfn ) );
        }

        return list;
    }

    protected CeisTO ceisToCeisTO(Ceis ceis) {
        if ( ceis == null ) {
            return null;
        }

        CeisTO ceisTO = new CeisTO();

        ceisTO.setId( ceis.getId() );
        ceisTO.setOrganComplement( ceis.getOrganComplement() );
        ceisTO.setLegalSubstantiation( ceis.getLegalSubstantiation() );
        ceisTO.setProcessNumber( ceis.getProcessNumber() );
        ceisTO.setSanctioningEntity( ceis.getSanctioningEntity() );
        ceisTO.setUfSanctioningEntity( ceis.getUfSanctioningEntity() );
        ceisTO.setInformationEntity( ceis.getInformationEntity() );
        ceisTO.setSanction( ceis.getSanction() );
        ceisTO.setInitSanctionDate( ceis.getInitSanctionDate() );
        ceisTO.setEndSanctionDate( ceis.getEndSanctionDate() );
        ceisTO.setInformationDate( ceis.getInformationDate() );

        return ceisTO;
    }

    protected List<CeisTO> ceisSetToCeisTOList(Set<Ceis> set) {
        if ( set == null ) {
            return null;
        }

        List<CeisTO> list = new ArrayList<CeisTO>( set.size() );
        for ( Ceis ceis : set ) {
            list.add( ceisToCeisTO( ceis ) );
        }

        return list;
    }

    protected CnepTO cnepToCnepTO(Cnep cnep) {
        if ( cnep == null ) {
            return null;
        }

        CnepTO cnepTO = new CnepTO();

        cnepTO.setId( cnep.getId() );
        cnepTO.setProcessNumber( cnep.getProcessNumber() );
        cnepTO.setSanctioningEntity( cnep.getSanctioningEntity() );
        cnepTO.setUfSanctioningEntity( cnep.getUfSanctioningEntity() );
        cnepTO.setSanction( cnep.getSanction() );
        cnepTO.setPenaltyValue( cnep.getPenaltyValue() );
        cnepTO.setInitSanctionDate( cnep.getInitSanctionDate() );
        cnepTO.setEndSanctionDate( cnep.getEndSanctionDate() );

        return cnepTO;
    }

    protected List<CnepTO> cnepSetToCnepTOList(Set<Cnep> set) {
        if ( set == null ) {
            return null;
        }

        List<CnepTO> list = new ArrayList<CnepTO>( set.size() );
        for ( Cnep cnep : set ) {
            list.add( cnepToCnepTO( cnep ) );
        }

        return list;
    }

    protected InternationalListTO internationalListToInternationalListTO(InternationalList internationalList) {
        if ( internationalList == null ) {
            return null;
        }

        InternationalListTO internationalListTO = new InternationalListTO();

        internationalListTO.setId( internationalList.getId() );
        internationalListTO.setName( internationalList.getName() );
        internationalListTO.setQueryDate( internationalList.getQueryDate() );

        return internationalListTO;
    }

    protected List<InternationalListTO> internationalListSetToInternationalListTOList(Set<InternationalList> set) {
        if ( set == null ) {
            return null;
        }

        List<InternationalListTO> list = new ArrayList<InternationalListTO>( set.size() );
        for ( InternationalList internationalList : set ) {
            list.add( internationalListToInternationalListTO( internationalList ) );
        }

        return list;
    }

    protected CompanyRelatedTO companyRelatedToCompanyRelatedTO(CompanyRelated companyRelated) {
        if ( companyRelated == null ) {
            return null;
        }

        CompanyRelatedTO companyRelatedTO = new CompanyRelatedTO();

        if ( companyRelated.getId() != null ) {
            companyRelatedTO.setId( companyRelated.getId() );
        }
        companyRelatedTO.setCnpj( companyRelated.getCnpj() );
        companyRelatedTO.setSocialReason( companyRelated.getSocialReason() );
        companyRelatedTO.setDescriptionCnae( companyRelated.getDescriptionCnae() );
        companyRelatedTO.setMunicipality( companyRelated.getMunicipality() );
        companyRelatedTO.setUf( companyRelated.getUf() );
        companyRelatedTO.setOpeningDate( companyRelated.getOpeningDate() );

        return companyRelatedTO;
    }

    protected List<CompanyRelatedTO> companyRelatedSetToCompanyRelatedTOList(Set<CompanyRelated> set) {
        if ( set == null ) {
            return null;
        }

        List<CompanyRelatedTO> list = new ArrayList<CompanyRelatedTO>( set.size() );
        for ( CompanyRelated companyRelated : set ) {
            list.add( companyRelatedToCompanyRelatedTO( companyRelated ) );
        }

        return list;
    }

    protected SintegraInscriptionTO sintegraInscriptionToSintegraInscriptionTO(SintegraInscription sintegraInscription) {
        if ( sintegraInscription == null ) {
            return null;
        }

        SintegraInscriptionTO sintegraInscriptionTO = new SintegraInscriptionTO();

        sintegraInscriptionTO.setId( sintegraInscription.getId() );
        sintegraInscriptionTO.setRegistrationNumber( sintegraInscription.getRegistrationNumber() );
        sintegraInscriptionTO.setRegistrationSituation( sintegraInscription.getRegistrationSituation() );
        sintegraInscriptionTO.setRegime( sintegraInscription.getRegime() );
        sintegraInscriptionTO.setEmail( sintegraInscription.getEmail() );
        sintegraInscriptionTO.setPhone( sintegraInscription.getPhone() );
        sintegraInscriptionTO.setUf( sintegraInscription.getUf() );
        sintegraInscriptionTO.setRegistrationSituationDate( sintegraInscription.getRegistrationSituationDate() );

        return sintegraInscriptionTO;
    }

    protected List<SintegraInscriptionTO> sintegraInscriptionSetToSintegraInscriptionTOList(Set<SintegraInscription> set) {
        if ( set == null ) {
            return null;
        }

        List<SintegraInscriptionTO> list = new ArrayList<SintegraInscriptionTO>( set.size() );
        for ( SintegraInscription sintegraInscription : set ) {
            list.add( sintegraInscriptionToSintegraInscriptionTO( sintegraInscription ) );
        }

        return list;
    }

    protected CompanyLocationTO companyToCompanyLocationTO(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyLocationTO companyLocationTO = new CompanyLocationTO();

        if ( company.getId() != null ) {
            companyLocationTO.setId( company.getId() );
        }
        companyLocationTO.setFantasyName( company.getFantasyName() );
        companyLocationTO.setRegistrationSituationReason( company.getRegistrationSituationReason() );
        companyLocationTO.setRfEmail( company.getRfEmail() );
        companyLocationTO.setDateRegistrationSituation( company.getDateRegistrationSituation() );
        companyLocationTO.setAge( company.getAge() );
        companyLocationTO.setGrossBilling( company.getGrossBilling() );
        companyLocationTO.setCompanySize( company.getCompanySize() );
        companyLocationTO.setActivityLevel( company.getActivityLevel() );
        companyLocationTO.setMainInfo( companyMainInfoToCompanyMainInfoTO( company.getMainInfo() ) );
        companyLocationTO.setMainCnae( cnaeToMainCnaeTO( company.getMainCnae() ) );
        companyLocationTO.setTaxHealth( taxHealthToTaxHealthTO( company.getTaxHealth() ) );
        companyLocationTO.setSimpleNational( simpleNationalToSimpleNationalTO( company.getSimpleNational() ) );
        companyLocationTO.setJudicialProcess( judicialProcessToJudicialProcessTO1( company.getJudicialProcess() ) );
        companyLocationTO.setDebitPgfnDau( debitPgfnDauToDebitPgfnDauTO( company.getDebitPgfnDau() ) );
        Set<String> set = company.getPhones();
        if ( set != null ) {
            companyLocationTO.setPhones( new ArrayList<String>( set ) );
        }
        companyLocationTO.setEmployeeGrowths( employeeGrowthSetToEmployeeGrowthTOList( company.getEmployeeGrowths() ) );

        return companyLocationTO;
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

    protected BankNomenclatureTO bankNomenclatureToBankNomenclatureTO(BankNomenclature bankNomenclature) {
        if ( bankNomenclature == null ) {
            return null;
        }

        BankNomenclatureTO bankNomenclatureTO = new BankNomenclatureTO();

        bankNomenclatureTO.setDescription( bankNomenclature.getDescription() );
        bankNomenclatureTO.setStage( bankNomenclature.getStage() );
        Set<String> set = bankNomenclature.getExtensions();
        if ( set != null ) {
            bankNomenclatureTO.setExtensions( new ArrayList<String>( set ) );
        }
        if ( bankNomenclature.getId() != null ) {
            bankNomenclatureTO.setId( bankNomenclature.getId() );
        }
        bankNomenclatureTO.setPartnersBank( partnerBankSetToPartnerBankLightTOList( bankNomenclature.getPartnersBank() ) );
        bankNomenclatureTO.setNomenclatureUrgencies( bankNomenclatureUrgencySetToBankNomenclatureUrgencyTOList( bankNomenclature.getNomenclatureUrgencies() ) );

        return bankNomenclatureTO;
    }

    protected CompanyBankDocumentTO companyBankDocumentToCompanyBankDocumentTO(CompanyBankDocument companyBankDocument) {
        if ( companyBankDocument == null ) {
            return null;
        }

        CompanyBankDocumentTO companyBankDocumentTO = new CompanyBankDocumentTO();

        if ( companyBankDocument.getId() != null ) {
            companyBankDocumentTO.setId( companyBankDocument.getId() );
        }
        companyBankDocumentTO.setDescriptionState( companyBankDocument.getDescriptionState() );
        companyBankDocumentTO.setState( companyBankDocument.getState() );
        companyBankDocumentTO.setBankNomenclature( bankNomenclatureToBankNomenclatureTO( companyBankDocument.getBankNomenclature() ) );
        companyBankDocumentTO.setDirectories( directoryListToDirectoryTOList( companyBankDocument.getDirectories() ) );

        return companyBankDocumentTO;
    }
}
