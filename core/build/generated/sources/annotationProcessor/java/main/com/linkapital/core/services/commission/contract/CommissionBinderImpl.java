package com.linkapital.core.services.commission.contract;

import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignConditionTO;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignLimitTO;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionPaymentPercentTO;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionPercentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignAttributeTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignConditionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignLimitTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionInstallmentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionPaymentPercentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionPercentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionUserTO;
import com.linkapital.core.services.commission.contract.to.update.UpdateCommissionCampaignTO;
import com.linkapital.core.services.commission.datasource.domain.Commission;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaign;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignAttribute;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignCondition;
import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignLimit;
import com.linkapital.core.services.commission.datasource.domain.CommissionInstallment;
import com.linkapital.core.services.commission.datasource.domain.CommissionPaymentPercent;
import com.linkapital.core.services.commission.datasource.domain.CommissionPercent;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class CommissionBinderImpl implements CommissionBinder {

    @Override
    public CommissionCampaign bind(CreateCommissionCampaignTO source) {
        if ( source == null ) {
            return null;
        }

        CommissionCampaign commissionCampaign = new CommissionCampaign();

        commissionCampaign.setTitle( source.getTitle() );
        commissionCampaign.setActive( source.isActive() );
        commissionCampaign.setLimit( createCommissionCampaignLimitTOToCommissionCampaignLimit( source.getLimit() ) );
        commissionCampaign.setPaymentPercent( createCommissionPaymentPercentTOToCommissionPaymentPercent( source.getPaymentPercent() ) );
        commissionCampaign.setPercents( createCommissionPercentTOListToCommissionPercentList( source.getPercents() ) );
        commissionCampaign.setConditions( createCommissionCampaignConditionTOListToCommissionCampaignConditionList( source.getConditions() ) );

        return commissionCampaign;
    }

    @Override
    public CommissionCampaignTO bind(CommissionCampaign source) {
        if ( source == null ) {
            return null;
        }

        CommissionCampaignTO commissionCampaignTO = new CommissionCampaignTO();

        if ( source.getId() != null ) {
            commissionCampaignTO.setId( source.getId() );
        }
        commissionCampaignTO.setTitle( source.getTitle() );
        commissionCampaignTO.setActive( source.isActive() );
        commissionCampaignTO.setLimit( commissionCampaignLimitToCommissionCampaignLimitTO( source.getLimit() ) );
        commissionCampaignTO.setPaymentPercent( commissionPaymentPercentToCommissionPaymentPercentTO( source.getPaymentPercent() ) );
        commissionCampaignTO.setPercents( commissionPercentListToCommissionPercentTOList( source.getPercents() ) );
        commissionCampaignTO.setConditions( commissionCampaignConditionListToCommissionCampaignConditionTOList( source.getConditions() ) );
        commissionCampaignTO.setBase( source.isBase() );

        return commissionCampaignTO;
    }

    @Override
    public CommissionCampaign set(UpdateCommissionCampaignTO source, CommissionCampaign target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setTitle( source.getTitle() );
        target.setActive( source.isActive() );
        if ( source.getLimit() != null ) {
            if ( target.getLimit() == null ) {
                target.setLimit( new CommissionCampaignLimit() );
            }
            commissionCampaignLimitTOToCommissionCampaignLimit( source.getLimit(), target.getLimit() );
        }
        else {
            target.setLimit( null );
        }
        if ( source.getPaymentPercent() != null ) {
            if ( target.getPaymentPercent() == null ) {
                target.setPaymentPercent( new CommissionPaymentPercent() );
            }
            commissionPaymentPercentTOToCommissionPaymentPercent( source.getPaymentPercent(), target.getPaymentPercent() );
        }
        else {
            target.setPaymentPercent( null );
        }
        if ( target.getPercents() != null ) {
            List<CommissionPercent> list = commissionPercentTOListToCommissionPercentList( source.getPercents() );
            if ( list != null ) {
                target.getPercents().clear();
                target.getPercents().addAll( list );
            }
            else {
                target.setPercents( null );
            }
        }
        else {
            List<CommissionPercent> list = commissionPercentTOListToCommissionPercentList( source.getPercents() );
            if ( list != null ) {
                target.setPercents( list );
            }
        }
        if ( target.getConditions() != null ) {
            List<CommissionCampaignCondition> list1 = commissionCampaignConditionTOListToCommissionCampaignConditionList( source.getConditions() );
            if ( list1 != null ) {
                target.getConditions().clear();
                target.getConditions().addAll( list1 );
            }
            else {
                target.setConditions( null );
            }
        }
        else {
            List<CommissionCampaignCondition> list1 = commissionCampaignConditionTOListToCommissionCampaignConditionList( source.getConditions() );
            if ( list1 != null ) {
                target.setConditions( list1 );
            }
        }

        return target;
    }

    @Override
    public List<CommissionCampaignTO> bind(List<CommissionCampaign> source) {
        if ( source == null ) {
            return null;
        }

        List<CommissionCampaignTO> list = new ArrayList<CommissionCampaignTO>( source.size() );
        for ( CommissionCampaign commissionCampaign : source ) {
            list.add( bind( commissionCampaign ) );
        }

        return list;
    }

    @Override
    public List<CommissionCampaignAttributeTO> bindToAttributeList(List<CommissionCampaignAttribute> source) {
        if ( source == null ) {
            return null;
        }

        List<CommissionCampaignAttributeTO> list = new ArrayList<CommissionCampaignAttributeTO>( source.size() );
        for ( CommissionCampaignAttribute commissionCampaignAttribute : source ) {
            list.add( commissionCampaignAttributeToCommissionCampaignAttributeTO( commissionCampaignAttribute ) );
        }

        return list;
    }

    @Override
    public List<CommissionInstallmentTO> bindToCommissionInstallmentList(List<CommissionInstallment> source) {
        if ( source == null ) {
            return null;
        }

        List<CommissionInstallmentTO> list = new ArrayList<CommissionInstallmentTO>( source.size() );
        for ( CommissionInstallment commissionInstallment : source ) {
            list.add( commissionInstallmentToCommissionInstallmentTO( commissionInstallment ) );
        }

        return list;
    }

    @Override
    public CommissionTO bind(Commission source) {
        if ( source == null ) {
            return null;
        }

        CommissionTO commissionTO = new CommissionTO();

        if ( source.getId() != null ) {
            commissionTO.setId( source.getId() );
        }
        commissionTO.setTotal( source.getTotal() );
        commissionTO.setDisbursement( source.getDisbursement() );
        commissionTO.setAmortization( source.getAmortization() );
        commissionTO.setLiquidation( source.getLiquidation() );
        commissionTO.setReleaseDate( source.getReleaseDate() );
        commissionTO.setInstallments( commissionInstallmentSetToCommissionInstallmentTOList( source.getInstallments() ) );

        return commissionTO;
    }

    @Override
    public CommissionUserTO bind(CommissionTO source) {
        if ( source == null ) {
            return null;
        }

        CommissionUserTO commissionUserTO = new CommissionUserTO();

        commissionUserTO.setId( source.getId() );
        commissionUserTO.setTotal( source.getTotal() );
        commissionUserTO.setDisbursement( source.getDisbursement() );
        commissionUserTO.setAmortization( source.getAmortization() );
        commissionUserTO.setLiquidation( source.getLiquidation() );
        commissionUserTO.setReleaseDate( source.getReleaseDate() );
        List<CommissionInstallmentTO> list = source.getInstallments();
        if ( list != null ) {
            commissionUserTO.setInstallments( new ArrayList<CommissionInstallmentTO>( list ) );
        }

        return commissionUserTO;
    }

    protected CommissionCampaignLimit createCommissionCampaignLimitTOToCommissionCampaignLimit(CreateCommissionCampaignLimitTO createCommissionCampaignLimitTO) {
        if ( createCommissionCampaignLimitTO == null ) {
            return null;
        }

        CommissionCampaignLimit commissionCampaignLimit = new CommissionCampaignLimit();

        commissionCampaignLimit.setCampaignLimit( createCommissionCampaignLimitTO.getCampaignLimit() );
        commissionCampaignLimit.setUserLimit( createCommissionCampaignLimitTO.getUserLimit() );

        return commissionCampaignLimit;
    }

    protected CommissionPaymentPercent createCommissionPaymentPercentTOToCommissionPaymentPercent(CreateCommissionPaymentPercentTO createCommissionPaymentPercentTO) {
        if ( createCommissionPaymentPercentTO == null ) {
            return null;
        }

        CommissionPaymentPercent commissionPaymentPercent = new CommissionPaymentPercent();

        commissionPaymentPercent.setDisbursement( createCommissionPaymentPercentTO.getDisbursement() );
        commissionPaymentPercent.setAmortization( createCommissionPaymentPercentTO.getAmortization() );
        commissionPaymentPercent.setLiquidation( createCommissionPaymentPercentTO.getLiquidation() );

        return commissionPaymentPercent;
    }

    protected CommissionPercent createCommissionPercentTOToCommissionPercent(CreateCommissionPercentTO createCommissionPercentTO) {
        if ( createCommissionPercentTO == null ) {
            return null;
        }

        CommissionPercent commissionPercent = new CommissionPercent();

        commissionPercent.setMonthMin( createCommissionPercentTO.getMonthMin() );
        commissionPercent.setMonthMax( createCommissionPercentTO.getMonthMax() );
        commissionPercent.setPercent( createCommissionPercentTO.getPercent() );

        return commissionPercent;
    }

    protected List<CommissionPercent> createCommissionPercentTOListToCommissionPercentList(List<CreateCommissionPercentTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommissionPercent> list1 = new ArrayList<CommissionPercent>( list.size() );
        for ( CreateCommissionPercentTO createCommissionPercentTO : list ) {
            list1.add( createCommissionPercentTOToCommissionPercent( createCommissionPercentTO ) );
        }

        return list1;
    }

    protected CommissionCampaignAttribute commissionCampaignAttributeTOToCommissionCampaignAttribute(CommissionCampaignAttributeTO commissionCampaignAttributeTO) {
        if ( commissionCampaignAttributeTO == null ) {
            return null;
        }

        CommissionCampaignAttribute commissionCampaignAttribute = new CommissionCampaignAttribute();

        commissionCampaignAttribute.setId( commissionCampaignAttributeTO.getId() );
        commissionCampaignAttribute.setName( commissionCampaignAttributeTO.getName() );
        commissionCampaignAttribute.setAttributeType( commissionCampaignAttributeTO.getAttributeType() );

        return commissionCampaignAttribute;
    }

    protected CommissionCampaignCondition createCommissionCampaignConditionTOToCommissionCampaignCondition(CreateCommissionCampaignConditionTO createCommissionCampaignConditionTO) {
        if ( createCommissionCampaignConditionTO == null ) {
            return null;
        }

        CommissionCampaignCondition commissionCampaignCondition = new CommissionCampaignCondition();

        commissionCampaignCondition.setValue( createCommissionCampaignConditionTO.getValue() );
        commissionCampaignCondition.setOperator( createCommissionCampaignConditionTO.getOperator() );
        commissionCampaignCondition.setConnector( createCommissionCampaignConditionTO.getConnector() );
        commissionCampaignCondition.setCampaignAttribute( commissionCampaignAttributeTOToCommissionCampaignAttribute( createCommissionCampaignConditionTO.getCampaignAttribute() ) );

        return commissionCampaignCondition;
    }

    protected List<CommissionCampaignCondition> createCommissionCampaignConditionTOListToCommissionCampaignConditionList(List<CreateCommissionCampaignConditionTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommissionCampaignCondition> list1 = new ArrayList<CommissionCampaignCondition>( list.size() );
        for ( CreateCommissionCampaignConditionTO createCommissionCampaignConditionTO : list ) {
            list1.add( createCommissionCampaignConditionTOToCommissionCampaignCondition( createCommissionCampaignConditionTO ) );
        }

        return list1;
    }

    protected CommissionCampaignLimitTO commissionCampaignLimitToCommissionCampaignLimitTO(CommissionCampaignLimit commissionCampaignLimit) {
        if ( commissionCampaignLimit == null ) {
            return null;
        }

        CommissionCampaignLimitTO commissionCampaignLimitTO = new CommissionCampaignLimitTO();

        commissionCampaignLimitTO.setCampaignLimit( commissionCampaignLimit.getCampaignLimit() );
        commissionCampaignLimitTO.setUserLimit( commissionCampaignLimit.getUserLimit() );
        if ( commissionCampaignLimit.getId() != null ) {
            commissionCampaignLimitTO.setId( commissionCampaignLimit.getId() );
        }

        return commissionCampaignLimitTO;
    }

    protected CommissionPaymentPercentTO commissionPaymentPercentToCommissionPaymentPercentTO(CommissionPaymentPercent commissionPaymentPercent) {
        if ( commissionPaymentPercent == null ) {
            return null;
        }

        CommissionPaymentPercentTO commissionPaymentPercentTO = new CommissionPaymentPercentTO();

        commissionPaymentPercentTO.setDisbursement( commissionPaymentPercent.getDisbursement() );
        commissionPaymentPercentTO.setAmortization( commissionPaymentPercent.getAmortization() );
        commissionPaymentPercentTO.setLiquidation( commissionPaymentPercent.getLiquidation() );
        commissionPaymentPercentTO.setId( commissionPaymentPercent.getId() );

        return commissionPaymentPercentTO;
    }

    protected CommissionPercentTO commissionPercentToCommissionPercentTO(CommissionPercent commissionPercent) {
        if ( commissionPercent == null ) {
            return null;
        }

        CommissionPercentTO commissionPercentTO = new CommissionPercentTO();

        commissionPercentTO.setMonthMin( commissionPercent.getMonthMin() );
        commissionPercentTO.setMonthMax( commissionPercent.getMonthMax() );
        commissionPercentTO.setPercent( commissionPercent.getPercent() );
        commissionPercentTO.setId( commissionPercent.getId() );

        return commissionPercentTO;
    }

    protected List<CommissionPercentTO> commissionPercentListToCommissionPercentTOList(List<CommissionPercent> list) {
        if ( list == null ) {
            return null;
        }

        List<CommissionPercentTO> list1 = new ArrayList<CommissionPercentTO>( list.size() );
        for ( CommissionPercent commissionPercent : list ) {
            list1.add( commissionPercentToCommissionPercentTO( commissionPercent ) );
        }

        return list1;
    }

    protected CommissionCampaignAttributeTO commissionCampaignAttributeToCommissionCampaignAttributeTO(CommissionCampaignAttribute commissionCampaignAttribute) {
        if ( commissionCampaignAttribute == null ) {
            return null;
        }

        CommissionCampaignAttributeTO commissionCampaignAttributeTO = new CommissionCampaignAttributeTO();

        if ( commissionCampaignAttribute.getId() != null ) {
            commissionCampaignAttributeTO.setId( commissionCampaignAttribute.getId() );
        }
        commissionCampaignAttributeTO.setName( commissionCampaignAttribute.getName() );
        commissionCampaignAttributeTO.setAttributeType( commissionCampaignAttribute.getAttributeType() );

        return commissionCampaignAttributeTO;
    }

    protected CommissionCampaignConditionTO commissionCampaignConditionToCommissionCampaignConditionTO(CommissionCampaignCondition commissionCampaignCondition) {
        if ( commissionCampaignCondition == null ) {
            return null;
        }

        CommissionCampaignConditionTO commissionCampaignConditionTO = new CommissionCampaignConditionTO();

        commissionCampaignConditionTO.setValue( commissionCampaignCondition.getValue() );
        commissionCampaignConditionTO.setOperator( commissionCampaignCondition.getOperator() );
        commissionCampaignConditionTO.setConnector( commissionCampaignCondition.getConnector() );
        commissionCampaignConditionTO.setCampaignAttribute( commissionCampaignAttributeToCommissionCampaignAttributeTO( commissionCampaignCondition.getCampaignAttribute() ) );
        commissionCampaignConditionTO.setId( commissionCampaignCondition.getId() );

        return commissionCampaignConditionTO;
    }

    protected List<CommissionCampaignConditionTO> commissionCampaignConditionListToCommissionCampaignConditionTOList(List<CommissionCampaignCondition> list) {
        if ( list == null ) {
            return null;
        }

        List<CommissionCampaignConditionTO> list1 = new ArrayList<CommissionCampaignConditionTO>( list.size() );
        for ( CommissionCampaignCondition commissionCampaignCondition : list ) {
            list1.add( commissionCampaignConditionToCommissionCampaignConditionTO( commissionCampaignCondition ) );
        }

        return list1;
    }

    protected void commissionCampaignLimitTOToCommissionCampaignLimit(CommissionCampaignLimitTO commissionCampaignLimitTO, CommissionCampaignLimit mappingTarget) {
        if ( commissionCampaignLimitTO == null ) {
            return;
        }

        mappingTarget.setId( commissionCampaignLimitTO.getId() );
        mappingTarget.setCampaignLimit( commissionCampaignLimitTO.getCampaignLimit() );
        mappingTarget.setUserLimit( commissionCampaignLimitTO.getUserLimit() );
    }

    protected void commissionPaymentPercentTOToCommissionPaymentPercent(CommissionPaymentPercentTO commissionPaymentPercentTO, CommissionPaymentPercent mappingTarget) {
        if ( commissionPaymentPercentTO == null ) {
            return;
        }

        mappingTarget.setId( commissionPaymentPercentTO.getId() );
        mappingTarget.setDisbursement( commissionPaymentPercentTO.getDisbursement() );
        mappingTarget.setAmortization( commissionPaymentPercentTO.getAmortization() );
        mappingTarget.setLiquidation( commissionPaymentPercentTO.getLiquidation() );
    }

    protected CommissionPercent commissionPercentTOToCommissionPercent(CommissionPercentTO commissionPercentTO) {
        if ( commissionPercentTO == null ) {
            return null;
        }

        CommissionPercent commissionPercent = new CommissionPercent();

        commissionPercent.setId( commissionPercentTO.getId() );
        commissionPercent.setMonthMin( commissionPercentTO.getMonthMin() );
        commissionPercent.setMonthMax( commissionPercentTO.getMonthMax() );
        commissionPercent.setPercent( commissionPercentTO.getPercent() );

        return commissionPercent;
    }

    protected List<CommissionPercent> commissionPercentTOListToCommissionPercentList(List<CommissionPercentTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommissionPercent> list1 = new ArrayList<CommissionPercent>( list.size() );
        for ( CommissionPercentTO commissionPercentTO : list ) {
            list1.add( commissionPercentTOToCommissionPercent( commissionPercentTO ) );
        }

        return list1;
    }

    protected CommissionCampaignCondition commissionCampaignConditionTOToCommissionCampaignCondition(CommissionCampaignConditionTO commissionCampaignConditionTO) {
        if ( commissionCampaignConditionTO == null ) {
            return null;
        }

        CommissionCampaignCondition commissionCampaignCondition = new CommissionCampaignCondition();

        commissionCampaignCondition.setId( commissionCampaignConditionTO.getId() );
        commissionCampaignCondition.setValue( commissionCampaignConditionTO.getValue() );
        commissionCampaignCondition.setOperator( commissionCampaignConditionTO.getOperator() );
        commissionCampaignCondition.setConnector( commissionCampaignConditionTO.getConnector() );
        commissionCampaignCondition.setCampaignAttribute( commissionCampaignAttributeTOToCommissionCampaignAttribute( commissionCampaignConditionTO.getCampaignAttribute() ) );

        return commissionCampaignCondition;
    }

    protected List<CommissionCampaignCondition> commissionCampaignConditionTOListToCommissionCampaignConditionList(List<CommissionCampaignConditionTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommissionCampaignCondition> list1 = new ArrayList<CommissionCampaignCondition>( list.size() );
        for ( CommissionCampaignConditionTO commissionCampaignConditionTO : list ) {
            list1.add( commissionCampaignConditionTOToCommissionCampaignCondition( commissionCampaignConditionTO ) );
        }

        return list1;
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

    protected CommissionInstallmentTO commissionInstallmentToCommissionInstallmentTO(CommissionInstallment commissionInstallment) {
        if ( commissionInstallment == null ) {
            return null;
        }

        CommissionInstallmentTO commissionInstallmentTO = new CommissionInstallmentTO();

        if ( commissionInstallment.getId() != null ) {
            commissionInstallmentTO.setId( commissionInstallment.getId() );
        }
        commissionInstallmentTO.setTotal( commissionInstallment.getTotal() );
        commissionInstallmentTO.setTotalBase( commissionInstallment.getTotalBase() );
        commissionInstallmentTO.setHasPaid( commissionInstallment.isHasPaid() );
        commissionInstallmentTO.setType( commissionInstallment.getType() );
        commissionInstallmentTO.setPaymentDate( commissionInstallment.getPaymentDate() );
        commissionInstallmentTO.setCreated( commissionInstallment.getCreated() );
        commissionInstallmentTO.setDocument( directoryToDirectoryTO( commissionInstallment.getDocument() ) );

        return commissionInstallmentTO;
    }

    protected List<CommissionInstallmentTO> commissionInstallmentSetToCommissionInstallmentTOList(Set<CommissionInstallment> set) {
        if ( set == null ) {
            return null;
        }

        List<CommissionInstallmentTO> list = new ArrayList<CommissionInstallmentTO>( set.size() );
        for ( CommissionInstallment commissionInstallment : set ) {
            list.add( commissionInstallmentToCommissionInstallmentTO( commissionInstallment ) );
        }

        return list;
    }
}
