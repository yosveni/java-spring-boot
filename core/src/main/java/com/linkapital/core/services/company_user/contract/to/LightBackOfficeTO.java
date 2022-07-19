package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.company.contract.enums.CompanyState;
import com.linkapital.core.services.company.datasource.domain.SimpleNational;
import com.linkapital.core.services.security.contract.to.LightUserTO;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "Light company back office data.")
@Getter
@Setter
public class LightBackOfficeTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The business name under which a company becomes known to the public.")
    private String fantasyName;

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The social reason company.")
    private String socialReason;

    @ApiModelProperty(value = "The opening date.")
    private LocalDateTime openingDate;

    @ApiModelProperty(value = "The estimated billing.")
    private String estimatedBilling;

    @ApiModelProperty(value = "The credit requested.")
    private double creditRequested;

    @ApiModelProperty(value = "The invoicing informed.")
    private double invoicingInformed;

    @ApiModelProperty(value = "Indicates if learning 1 was started.")
    private boolean initIndicativeOfferOne;

    @ApiModelProperty(value = "Indicates if learning 2 was started.")
    private boolean initIndicativeOfferTwo;

    @ApiModelProperty(value = "Indicates if learning 3 was started.")
    private boolean initIndicativeOfferThree;

    @ApiModelProperty(value = "Indicates if learning 4 was started.")
    private boolean initIndicativeOfferFour;

    @ApiModelProperty(value = "Indicates if have SPED balance document.")
    private boolean hasSpedBalanceDocument;

    @ApiModelProperty(value = "Indicates if have duplicate NFE documents.")
    private boolean hasNfeDuplicity;

    @ApiModelProperty(value = "Indicates if have SPED documents.")
    private boolean hasSpedDocuments;

    @ApiModelProperty(value = "Indicates if have invoice documents.")
    private boolean hasInvoiceDocuments;

    @ApiModelProperty(value = "The registration date.")
    private Date created;

    @ApiModelProperty(value = "The company state.")
    private CompanyState companyState;

    @ApiModelProperty(value = "The simple national.")
    private SimpleNational simpleNational;

    @ApiModelProperty(value = "The address data.")
    private AddressTO address;

    @ApiModelProperty(value = "The user data.")
    private LightUserTO user;

    @ApiModelProperty(value = "A list containing the numbers of the indicative offers with status OFFER_REQUESTED")
    private List<Integer> offerTypeNumbers;

    public LightBackOfficeTO() {
        this.offerTypeNumbers = new ArrayList<>();
    }

    public LightBackOfficeTO withId(Long id) {
        setId(id);
        return this;
    }

    public LightBackOfficeTO withFantasyName(String fantasyName) {
        setFantasyName(fantasyName);
        return this;
    }

    public LightBackOfficeTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public LightBackOfficeTO withSocialReason(String socialReason) {
        setSocialReason(socialReason);
        return this;
    }

    public LightBackOfficeTO withOpeningDate(LocalDateTime openingDate) {
        setOpeningDate(openingDate);
        return this;
    }

    public LightBackOfficeTO withEstimatedBilling(String estimatedBilling) {
        setEstimatedBilling(estimatedBilling);
        return this;
    }

    public LightBackOfficeTO withInitIndicativeOfferOne(boolean initIndicativeOfferOne) {
        setInitIndicativeOfferOne(initIndicativeOfferOne);
        return this;
    }

    public LightBackOfficeTO withInitIndicativeOfferTwo(boolean initIndicativeOfferTwo) {
        setInitIndicativeOfferTwo(initIndicativeOfferTwo);
        return this;
    }

    public LightBackOfficeTO withInitIndicativeOfferThree(boolean initIndicativeOfferThree) {
        setInitIndicativeOfferThree(initIndicativeOfferThree);
        return this;
    }

    public LightBackOfficeTO withInitIndicativeOfferFour(boolean initIndicativeOfferFour) {
        setInitIndicativeOfferFour(initIndicativeOfferFour);
        return this;
    }

    public LightBackOfficeTO withHasSpedBalanceDocument(boolean spedBalanceDocument) {
        setHasSpedBalanceDocument(spedBalanceDocument);
        return this;
    }

    public LightBackOfficeTO withHasNfeDuplicity(boolean nfeDuplicity) {
        setHasNfeDuplicity(nfeDuplicity);
        return this;
    }

    public LightBackOfficeTO withHasSpedDocuments(boolean spedDocuments) {
        setHasSpedDocuments(spedDocuments);
        return this;
    }

    public LightBackOfficeTO withHasInvoiceDocuments(boolean invoiceDocuments) {
        setHasInvoiceDocuments(invoiceDocuments);
        return this;
    }

    public LightBackOfficeTO withCreditRequested(double creditRequested) {
        setCreditRequested(creditRequested);
        return this;
    }

    public LightBackOfficeTO withInvoicingInformed(double invoicingInformed) {
        setInvoicingInformed(invoicingInformed);
        return this;
    }

    public LightBackOfficeTO withCreated(Date created) {
        setCreated(created);
        return this;
    }

    public LightBackOfficeTO withCompanyState(CompanyState companyState) {
        setCompanyState(companyState);
        return this;
    }

    public LightBackOfficeTO withSimpleNational(SimpleNational simpleNational) {
        setSimpleNational(simpleNational);
        return this;
    }

    public LightBackOfficeTO withAddress(AddressTO address) {
        setAddress(address);
        return this;
    }

    public LightBackOfficeTO withUser(LightUserTO user) {
        setUser(user);
        return this;
    }

    public LightBackOfficeTO withOfferTypeNumbers(List<Integer> offerTypeNumbers) {
        setOfferTypeNumbers(offerTypeNumbers);
        return this;
    }

}
