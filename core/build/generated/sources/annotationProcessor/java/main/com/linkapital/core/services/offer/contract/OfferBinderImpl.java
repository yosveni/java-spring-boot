package com.linkapital.core.services.offer.contract;

import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureLightTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureUrgencyTO;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclature;
import com.linkapital.core.services.bank_nomenclature.datasource.domain.BankNomenclatureUrgency;
import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.comment.datasource.domain.Comment;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.offer.contract.to.OfferStateLogsTO;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.offer.datasource.domain.OfferStateLogs;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.offer_installment.datasource.domain.OfferInstallment;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankTO;
import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import com.linkapital.core.services.security.contract.to.LightUserTO;
import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.datasource.domain.Role;
import com.linkapital.core.services.security.datasource.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T22:29:28-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class OfferBinderImpl implements OfferBinder {

    @Override
    public OfferTO bind(Offer source) {
        if ( source == null ) {
            return null;
        }

        OfferTO offerTO = new OfferTO();

        offerTO.setCnpj( source.getCnpj() );
        offerTO.setDescription( source.getDescription() );
        offerTO.setNextStepDescription( source.getNextStepDescription() );
        offerTO.setRejectedReason( source.getRejectedReason() );
        offerTO.setMonthCet( source.getMonthCet() );
        offerTO.setYearCet( source.getYearCet() );
        offerTO.setType( source.getType() );
        offerTO.setVolume( source.getVolume() );
        offerTO.setInstallments( source.getInstallments() );
        offerTO.setTaxPercent( source.getTaxPercent() );
        offerTO.setTaxValue( source.getTaxValue() );
        offerTO.setDiscount( source.getDiscount() );
        offerTO.setIof( source.getIof() );
        offerTO.setRegistrationFee( source.getRegistrationFee() );
        offerTO.setTotal( source.getTotal() );
        offerTO.setPayByInstallment( source.getPayByInstallment() );
        offerTO.setResponseTime( source.getResponseTime() );
        if ( source.getId() != null ) {
            offerTO.setId( source.getId() );
        }
        offerTO.setAccepted( source.isAccepted() );
        offerTO.setCreated( source.getCreated() );
        offerTO.setContractDate( source.getContractDate() );
        offerTO.setState( source.getState() );
        offerTO.setPartnerBank( partnerBankToPartnerBankTO( source.getPartnerBank() ) );
        offerTO.setPayments( offerInstallmentSetToOfferInstallmentTOList( source.getPayments() ) );
        offerTO.setOfferStateLogs( offerStateLogsSetToOfferStateLogsTOList( source.getOfferStateLogs() ) );
        offerTO.setComments( commentSetToCommentTOList( source.getComments() ) );
        offerTO.setDocuments( directorySetToDirectoryTOList( source.getDocuments() ) );

        return offerTO;
    }

    @Override
    public DirectoryTO bind(Directory directory) {
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

    protected PartnerBankTO partnerBankToPartnerBankTO(PartnerBank partnerBank) {
        if ( partnerBank == null ) {
            return null;
        }

        PartnerBankTO partnerBankTO = new PartnerBankTO();

        partnerBankTO.setName( partnerBank.getName() );
        partnerBankTO.setDays( partnerBank.getDays() );
        Set<Integer> set = partnerBank.getAreas();
        if ( set != null ) {
            partnerBankTO.setAreas( new ArrayList<Integer>( set ) );
        }
        if ( partnerBank.getId() != null ) {
            partnerBankTO.setId( partnerBank.getId() );
        }
        partnerBankTO.setBankNomenclatures( bankNomenclatureSetToBankNomenclatureLightTOList( partnerBank.getBankNomenclatures() ) );

        return partnerBankTO;
    }

    protected OfferInstallmentTO offerInstallmentToOfferInstallmentTO(OfferInstallment offerInstallment) {
        if ( offerInstallment == null ) {
            return null;
        }

        OfferInstallmentTO offerInstallmentTO = new OfferInstallmentTO();

        offerInstallmentTO.setTotal( offerInstallment.getTotal() );
        offerInstallmentTO.setHasPaid( offerInstallment.isHasPaid() );
        offerInstallmentTO.setExpiration( offerInstallment.getExpiration() );
        if ( offerInstallment.getId() != null ) {
            offerInstallmentTO.setId( offerInstallment.getId() );
        }
        offerInstallmentTO.setCreated( offerInstallment.getCreated() );
        offerInstallmentTO.setDocument( bind( offerInstallment.getDocument() ) );

        return offerInstallmentTO;
    }

    protected List<OfferInstallmentTO> offerInstallmentSetToOfferInstallmentTOList(Set<OfferInstallment> set) {
        if ( set == null ) {
            return null;
        }

        List<OfferInstallmentTO> list = new ArrayList<OfferInstallmentTO>( set.size() );
        for ( OfferInstallment offerInstallment : set ) {
            list.add( offerInstallmentToOfferInstallmentTO( offerInstallment ) );
        }

        return list;
    }

    protected OfferStateLogsTO offerStateLogsToOfferStateLogsTO(OfferStateLogs offerStateLogs) {
        if ( offerStateLogs == null ) {
            return null;
        }

        OfferStateLogsTO offerStateLogsTO = new OfferStateLogsTO();

        offerStateLogsTO.setId( offerStateLogs.getId() );
        offerStateLogsTO.setNotification( offerStateLogs.getNotification() );
        offerStateLogsTO.setOfferState( offerStateLogs.getOfferState() );
        offerStateLogsTO.setCreated( offerStateLogs.getCreated() );

        return offerStateLogsTO;
    }

    protected List<OfferStateLogsTO> offerStateLogsSetToOfferStateLogsTOList(Set<OfferStateLogs> set) {
        if ( set == null ) {
            return null;
        }

        List<OfferStateLogsTO> list = new ArrayList<OfferStateLogsTO>( set.size() );
        for ( OfferStateLogs offerStateLogs : set ) {
            list.add( offerStateLogsToOfferStateLogsTO( offerStateLogs ) );
        }

        return list;
    }

    protected RoleTO roleToRoleTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleTO roleTO = new RoleTO();

        roleTO.setId( role.getId() );
        roleTO.setName( role.getName() );
        roleTO.setCode( role.getCode() );
        roleTO.setDescription( role.getDescription() );
        roleTO.setAuthority( role.getAuthority() );

        return roleTO;
    }

    protected LightUserTO userToLightUserTO(User user) {
        if ( user == null ) {
            return null;
        }

        LightUserTO lightUserTO = new LightUserTO();

        lightUserTO.setId( user.getId() );
        lightUserTO.setEmail( user.getEmail() );
        lightUserTO.setName( user.getName() );
        lightUserTO.setCodeCountryPhone( user.getCodeCountryPhone() );
        lightUserTO.setPhone( user.getPhone() );
        lightUserTO.setRole( roleToRoleTO( user.getRole() ) );

        return lightUserTO;
    }

    protected List<DirectoryTO> directorySetToDirectoryTOList(Set<Directory> set) {
        if ( set == null ) {
            return null;
        }

        List<DirectoryTO> list = new ArrayList<DirectoryTO>( set.size() );
        for ( Directory directory : set ) {
            list.add( bind( directory ) );
        }

        return list;
    }

    protected CommentTO commentToCommentTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentTO commentTO = new CommentTO();

        commentTO.setId( comment.getId() );
        commentTO.setComment( comment.getComment() );
        commentTO.setLearningNumber( comment.getLearningNumber() );
        commentTO.setCreated( comment.getCreated() );
        commentTO.setLearningSession( comment.getLearningSession() );
        commentTO.setCommentArea( comment.getCommentArea() );
        commentTO.setUser( userToLightUserTO( comment.getUser() ) );
        Set<Long> set = comment.getUsersViews();
        if ( set != null ) {
            commentTO.setUsersViews( new ArrayList<Long>( set ) );
        }
        commentTO.setAttachments( directorySetToDirectoryTOList( comment.getAttachments() ) );

        return commentTO;
    }

    protected List<CommentTO> commentSetToCommentTOList(Set<Comment> set) {
        if ( set == null ) {
            return null;
        }

        List<CommentTO> list = new ArrayList<CommentTO>( set.size() );
        for ( Comment comment : set ) {
            list.add( commentToCommentTO( comment ) );
        }

        return list;
    }
}
