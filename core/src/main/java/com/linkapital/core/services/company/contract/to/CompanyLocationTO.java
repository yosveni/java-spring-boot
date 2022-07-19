package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.company.contract.enums.ActivityLevel;
import com.linkapital.core.services.company.contract.enums.CompanySize;
import com.linkapital.core.services.shared.contract.to.DebitPgfnDauTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company location data")
@Getter
@Setter
public class CompanyLocationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The fantasy name.")
    private String fantasyName;

    @ApiModelProperty(value = "The registration situation reason.")
    private String registrationSituationReason;

    @ApiModelProperty(value = "The rf email.")
    private String rfEmail;

    @ApiModelProperty(value = "The registration situation date.")
    private String dateRegistrationSituation;

    @ApiModelProperty(value = "The age.")
    private int age;

    @ApiModelProperty(value = "The gross billing.")
    private double grossBilling;

    @ApiModelProperty(value = "The company size.")
    private CompanySize companySize;

    @ApiModelProperty(value = "The activity level.")
    private ActivityLevel activityLevel;

    @ApiModelProperty(value = "The main info.")
    private CompanyMainInfoTO mainInfo;

    @ApiModelProperty(value = "The main cnae.")
    private MainCnaeTO mainCnae;

    @ApiModelProperty(value = "The tax health.")
    private TaxHealthTO taxHealth;

    @ApiModelProperty(value = "The simple national.")
    private SimpleNationalTO simpleNational;

    @ApiModelProperty(value = "The judicial process.")
    private JudicialProcessTO judicialProcess;

    @ApiModelProperty(value = "The debit PGFN.")
    private DebitPgfnDauTO debitPgfnDau;

    @ApiModelProperty(value = "The phone list.")
    private List<String> phones;

    @ApiModelProperty(value = "The employee growths list.")
    private List<EmployeeGrowthTO> employeeGrowths;

    public CompanyLocationTO() {
        this.phones = new ArrayList<>();
        this.employeeGrowths = new ArrayList<>();
    }

}
