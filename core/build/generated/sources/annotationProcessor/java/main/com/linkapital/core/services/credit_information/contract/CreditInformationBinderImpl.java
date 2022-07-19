package com.linkapital.core.services.credit_information.contract;

import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import com.linkapital.core.services.credit_information.contract.to.EarningTO;
import com.linkapital.core.services.credit_information.contract.to.ResumeOperationTO;
import com.linkapital.core.services.credit_information.datasource.domain.CreditInformation;
import com.linkapital.core.services.credit_information.datasource.domain.Earning;
import com.linkapital.core.services.credit_information.datasource.domain.ResumeOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class CreditInformationBinderImpl implements CreditInformationBinder {

    @Override
    public List<CreditInformationTO> bind(List<CreditInformation> source) {
        if ( source == null ) {
            return null;
        }

        List<CreditInformationTO> list = new ArrayList<CreditInformationTO>( source.size() );
        for ( CreditInformation creditInformation : source ) {
            list.add( creditInformationToCreditInformationTO( creditInformation ) );
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
}
