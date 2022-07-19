package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.authorization.contract.to.authorization.CompanyClientAuthorizationTO;
import com.linkapital.core.services.bank_nomenclature.contract.to.get.CompanyBankNomenclatureTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company client data.")
@Getter
@Setter
public class CompanyClientTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The fantasy name.")
    private String fantasyName;

    @ApiModelProperty(value = "The social reason.")
    private String socialReason;

    @ApiModelProperty(value = "The url of the company image.")
    private String image;

    @ApiModelProperty(value = "The average reception period in days")
    private int avgReceiptTermInvoices;

    @ApiModelProperty(value = "The credit requested.")
    private double creditRequested;

    @ApiModelProperty(value = "The invoicing informed.")
    private double invoicingInformed;

    @ApiModelProperty(value = "Indicates whether to search for SCR documents.")
    private boolean scr;

    @ApiModelProperty(value = "Indicates if learning 1 was started.")
    private boolean initIndicativeOfferOne;

    @ApiModelProperty(value = "Indicates if learning 2 was started.")
    private boolean initIndicativeOfferTwo;

    @ApiModelProperty(value = "Indicates if learning 3 was started.")
    private boolean initIndicativeOfferThree;

    @ApiModelProperty(value = "Indicates if learning 4 was started.")
    private boolean initIndicativeOfferFour;

    @ApiModelProperty(value = "Indicates if the customer is the owner of the company.")
    private boolean owner;

    @ApiModelProperty(value = "Indicates if the company has an owner.")
    private boolean hasAnOwner;

    @ApiModelProperty(value = "Indicates if have SPED balance document.")
    private boolean hasSpedBalanceDocument;

    @ApiModelProperty(value = "Indicates if have duplicate NFE documents.")
    private boolean hasNfeDuplicity;

    @ApiModelProperty(value = "Indicates if have SPED documents.")
    private boolean hasSpedDocuments;

    @ApiModelProperty(value = "Indicates if have invoice documents.")
    private boolean hasInvoiceDocuments;

    @ApiModelProperty(value = "The authorization data.")
    private CompanyClientAuthorizationTO ownerAuthorization;

    @ApiModelProperty(value = "The indicative offer one.")
    private IndicativeOfferTO indicativeOfferOne;

    @ApiModelProperty(value = "The indicative offer two.")
    private IndicativeOfferTO indicativeOfferTwo;

    @ApiModelProperty(value = "The indicative offer three.")
    private IndicativeOfferTO indicativeOfferThree;

    @ApiModelProperty(value = "The indicative offer four.")
    private IndicativeOfferTO indicativeOfferFour;

    @ApiModelProperty(value = "The bank nomenclatures.")
    private List<CompanyBankNomenclatureTO> bankNomenclatures;

    public CompanyClientTO() {
        this.bankNomenclatures = new ArrayList<>();
    }

    public CompanyClientTO withIndicativeOfferOne(IndicativeOfferTO indicativeOfferOne) {
        setIndicativeOfferOne(indicativeOfferOne);
        return this;
    }

    public CompanyClientTO withIndicativeOfferTwo(IndicativeOfferTO indicativeOfferTwo) {
        setIndicativeOfferTwo(indicativeOfferTwo);
        return this;
    }

    public CompanyClientTO withIndicativeOfferThree(IndicativeOfferTO indicativeOfferThree) {
        setIndicativeOfferThree(indicativeOfferThree);
        return this;
    }

    public CompanyClientTO withIndicativeOfferFour(IndicativeOfferTO indicativeOfferFour) {
        setIndicativeOfferFour(indicativeOfferFour);
        return this;
    }

    public CompanyClientTO withBankNomenclature(List<CompanyBankNomenclatureTO> bankNomenclatures) {
        setBankNomenclatures(bankNomenclatures);
        return this;
    }

}
