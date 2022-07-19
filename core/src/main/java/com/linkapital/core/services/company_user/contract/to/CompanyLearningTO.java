package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.company.contract.to.CafirTO;
import com.linkapital.core.services.company.contract.to.CeisTO;
import com.linkapital.core.services.company.contract.to.CnaeTO;
import com.linkapital.core.services.company.contract.to.CnepTO;
import com.linkapital.core.services.company.contract.to.CnjCniaTO;
import com.linkapital.core.services.company.contract.to.CompanyExportTO;
import com.linkapital.core.services.company.contract.to.CompanyRelatedTO;
import com.linkapital.core.services.company.contract.to.CrsfnTO;
import com.linkapital.core.services.company.contract.to.FinancialActivityTO;
import com.linkapital.core.services.company.contract.to.HeavyVehicleInfoTO;
import com.linkapital.core.services.company.contract.to.InternationalListTO;
import com.linkapital.core.services.company.contract.to.JudicialProcessTO;
import com.linkapital.core.services.company.contract.to.ProconTO;
import com.linkapital.core.services.company.contract.to.SimpleNationalTO;
import com.linkapital.core.services.company.contract.to.SintegraInscriptionTO;
import com.linkapital.core.services.company.contract.to.SuframaTO;
import com.linkapital.core.services.company.contract.to.WorkMteTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import com.linkapital.core.services.shared.contract.to.PropertyTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company client data")
@Getter
@Setter
public class CompanyLearningTO extends CompanyLearningPatternTO {

    @ApiModelProperty(value = "Indicate whether the company is obliged to pay the service tax.")
    private boolean passiveIISS;

    @ApiModelProperty(value = "The judicial process.")
    private JudicialProcessTO judicialProcess;

    @ApiModelProperty(value = "The procon.")
    private ProconTO procon;

    @ApiModelProperty(value = "The heavy vehicle info.")
    private HeavyVehicleInfoTO heavyVehicleInfo;

    @ApiModelProperty(value = "The financial activity.")
    private FinancialActivityTO financialActivity;

    @ApiModelProperty(value = "The financial activity.")
    private CafirTO cafir;

    @ApiModelProperty(value = "The data of simple national.")
    private SimpleNationalTO simpleNational;

    @ApiModelProperty(value = "The data of suframa.")
    private SuframaTO suframa;

    @ApiModelProperty(value = "The cnae lists.")
    private List<CnaeTO> cnaes;

    @ApiModelProperty(value = "The exports lists.")
    private List<CompanyExportTO> exports;

    @ApiModelProperty(value = "The imports list.")
    private List<CompanyExportTO> imports;

    @ApiModelProperty(value = "The cnj cnias list.")
    private List<CnjCniaTO> cnjCnias;

    @ApiModelProperty(value = "The workMtes list.")
    private List<WorkMteTO> workMtes;

    @ApiModelProperty(value = "The crsfns list.")
    private List<CrsfnTO> crsfns;

    @ApiModelProperty(value = "The ceis list.")
    private List<CeisTO> ceis;

    @ApiModelProperty(value = "The cneps list.")
    private List<CnepTO> cneps;

    @ApiModelProperty(value = "The international list.")
    private List<InternationalListTO> internationalLists;

    @ApiModelProperty(value = "The companies related list.")
    private List<CompanyRelatedTO> companiesRelated;

    @ApiModelProperty(value = "The properties list.")
    private List<PropertyTO> properties;

    @ApiModelProperty(value = "The inscriptions sintegras list.")
    private List<SintegraInscriptionTO> sintegraInscriptions;

    public CompanyLearningTO() {
        this.cnaes = new ArrayList<>();
        this.exports = new ArrayList<>();
        this.imports = new ArrayList<>();
        this.cnjCnias = new ArrayList<>();
        this.workMtes = new ArrayList<>();
        this.crsfns = new ArrayList<>();
        this.ceis = new ArrayList<>();
        this.cneps = new ArrayList<>();
        this.internationalLists = new ArrayList<>();
        this.companiesRelated = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.sintegraInscriptions = new ArrayList<>();
    }

    @Override
    public CompanyLearningTO withInvoicingInformed(double invoicingInformed) {
        setInvoicingInformed(invoicingInformed);
        return this;
    }

    @Override
    public CompanyLearningTO withProtestInformation(ProtestInformationTO protestInformation) {
        setProtestInformation(protestInformation);
        return this;
    }

    public CompanyLearningTO withIndicativeOfferOne(IndicativeOfferTO indicativeOfferOne) {
        setIndicativeOfferOne(indicativeOfferOne);
        return this;
    }

    public CompanyLearningTO withIndicativeOfferTwo(IndicativeOfferTO indicativeOfferTwo) {
        setIndicativeOfferTwo(indicativeOfferTwo);
        return this;
    }

    public CompanyLearningTO withIndicativeOfferThree(IndicativeOfferTO indicativeOfferThree) {
        setIndicativeOfferThree(indicativeOfferThree);
        return this;
    }

    public CompanyLearningTO withIndicativeOfferFour(IndicativeOfferTO indicativeOfferFour) {
        setIndicativeOfferFour(indicativeOfferFour);
        return this;
    }

    @Override
    public CompanyLearningTO withComments(List<CommentTO> comments) {
        setComments(comments);
        return this;
    }

    @Override
    public CompanyLearningTO withDebtDocuments(List<DirectoryTO> debtDocuments) {
        setDebtDocuments(debtDocuments);
        return this;
    }

}
