package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.bank_operation.contract.to.BankOperationTO;
import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.company.contract.enums.CompanyClosingPropensity;
import com.linkapital.core.services.company.contract.enums.CompanySize;
import com.linkapital.core.services.company.contract.to.CompanyBeneficiaryTO;
import com.linkapital.core.services.company.contract.to.CompanyMainInfoTO;
import com.linkapital.core.services.company.contract.to.CompanyPartnersTO;
import com.linkapital.core.services.company.contract.to.EmployeeGrowthTO;
import com.linkapital.core.services.company.contract.to.MainCnaeTO;
import com.linkapital.core.services.company.contract.to.TaxHealthTO;
import com.linkapital.core.services.credit_information.contract.to.CreditInformationTO;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CriCraDebentureTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.ibge.contract.to.IbgeTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import com.linkapital.core.services.shared.contract.to.DebitPgfnDauTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class CompanyLearningPatternTO implements GenericTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The business name under which a company becomes known to the public.")
    private String fantasyName;

    @ApiModelProperty(value = "The the code of the legal regime in which the company fits.")
    private String legalNatureCode;

    @ApiModelProperty(value = "The legal regime in which the company fits.")
    private String legalNatureDescription;

    @ApiModelProperty(value = "The date when the company changed its situation to the current registration situation.")
    private String dateRegistrationSituation;

    @ApiModelProperty(value = "The explanation of why the company has the current registration situation.")
    private String registrationSituationReason;

    @ApiModelProperty(value = "The date when the company changed its situation to the current special situation.")
    private String dateSpecialSituation;

    @ApiModelProperty(value = "The special situation of the company.")
    private String specialSituation;

    @ApiModelProperty(value = "The potential for a company to have a food delivery service, whether that service is" +
            " its own or through applications.")
    private String deliveryPropensity;

    @ApiModelProperty(value = "The potential of a company to market its products in an online format, be it selling" +
            " through its own e-commerce site or through Marketplaces platforms.")
    private String eCommercePropensity;

    @ApiModelProperty(value = "The years of the company.")
    private int age;

    @ApiModelProperty(value = "The number of debits gross billing.")
    private double grossBilling;

    @ApiModelProperty(value = "The number of social capital.")
    private double socialCapital;

    @ApiModelProperty(value = "The invoicing informed.")
    private double invoicingInformed;

    @ApiModelProperty(value = "The size.")
    private CompanySize companySize;

    @ApiModelProperty(value = "The Closure Propensity Model of the Company.")
    private CompanyClosingPropensity companyClosingPropensity;

    @ApiModelProperty(value = "The main information of the company.")
    private CompanyMainInfoTO mainInfo;

    @ApiModelProperty(value = "The main cnae.")
    private MainCnaeTO mainCnae;

    @ApiModelProperty(value = "The PGFN DAU debits.")
    private DebitPgfnDauTO debitPgfnDau;

    @ApiModelProperty(value = "The tax health.")
    private TaxHealthTO taxHealth;

    @ApiModelProperty(value = "The IBGE data.")
    private IbgeTO ibge;

    @ApiModelProperty(value = "The protest information data.")
    private ProtestInformationTO protestInformation;

    @ApiModelProperty(value = "The indicative offer one.")
    private IndicativeOfferTO indicativeOfferOne;

    @ApiModelProperty(value = "The indicative offer two.")
    private IndicativeOfferTO indicativeOfferTwo;

    @ApiModelProperty(value = "The indicative offer three.")
    private IndicativeOfferTO indicativeOfferThree;

    @ApiModelProperty(value = "The indicative offer four.")
    private IndicativeOfferTO indicativeOfferFour;

    @ApiModelProperty(value = "The list of DEBIT documents.")
    private List<DirectoryTO> debtDocuments;

    @ApiModelProperty(value = "The list of employees.")
    private List<CompanyEmployeeTO> employees;

    @ApiModelProperty(value = "The list of ex-employees.")
    private List<CompanyEmployeeTO> exEmployees;

    @ApiModelProperty(value = "The list of beneficiary companies.")
    private List<CompanyBeneficiaryTO> beneficiaries;

    @ApiModelProperty(value = "The list of company partners.")
    private List<CompanyPartnersTO> partners;

    @ApiModelProperty(value = "The list of employees growth.")
    private List<EmployeeGrowthTO> employeeGrowths;

    @ApiModelProperty(value = "The list of bank operations.")
    private List<BankOperationTO> bankOperations;

    @ApiModelProperty(value = "The credit information.")
    private List<CreditInformationTO> creditsInformation;

    @ApiModelProperty(value = "The CRI CRA and DEBENTURES.")
    private List<CriCraDebentureTO> criCraDebentures;

    @ApiModelProperty(value = "The list of comments.")
    private List<CommentTO> comments;

    @ApiModelProperty(value = "The list of JUCESP documents.")
    private List<DirectoryTO> jucespDocuments;

    CompanyLearningPatternTO() {
        this.debtDocuments = new ArrayList<>();
        this.partners = new ArrayList<>();
        this.beneficiaries = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.exEmployees = new ArrayList<>();
        this.employeeGrowths = new ArrayList<>();
        this.bankOperations = new ArrayList<>();
        this.creditsInformation = new ArrayList<>();
        this.criCraDebentures = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.jucespDocuments = new ArrayList<>();
    }

    public CompanyLearningPatternTO withInvoicingInformed(double invoicingInformed) {
        setInvoicingInformed(invoicingInformed);
        return this;
    }

    public CompanyLearningPatternTO withProtestInformation(ProtestInformationTO protestInformation) {
        setProtestInformation(protestInformation);
        return this;
    }

    public CompanyLearningPatternTO withIndicativeOfferOne(IndicativeOfferTO indicativeOfferOne) {
        setIndicativeOfferOne(indicativeOfferOne);
        return this;
    }

    public CompanyLearningPatternTO withIndicativeOfferTwo(IndicativeOfferTO indicativeOfferTwo) {
        setIndicativeOfferTwo(indicativeOfferTwo);
        return this;
    }

    public CompanyLearningPatternTO withIndicativeOfferThree(IndicativeOfferTO indicativeOfferThree) {
        setIndicativeOfferThree(indicativeOfferThree);
        return this;
    }

    public CompanyLearningPatternTO withIndicativeOfferFour(IndicativeOfferTO indicativeOfferFour) {
        setIndicativeOfferFour(indicativeOfferFour);
        return this;
    }

    public CompanyLearningPatternTO withDebtDocuments(List<DirectoryTO> debtDocuments) {
        setDebtDocuments(debtDocuments);
        return this;
    }

    public CompanyLearningPatternTO withComments(List<CommentTO> comments) {
        setComments(comments);
        return this;
    }

}
