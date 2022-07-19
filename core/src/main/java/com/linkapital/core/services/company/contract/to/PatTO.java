package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.company.contract.enums.RegistrationSituation;
import com.linkapital.core.services.person.contract.to.PersonTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The company's feeding program data.")
@Getter
@Setter
public class PatTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The inscription.")
    private String inscription;

    @ApiModelProperty(value = "The number of benefited employees.")
    private int benefitedEmployees;

    @ApiModelProperty(value = "The exercise date.")
    private Date exerciseDate;

    @ApiModelProperty(value = "The registration situation.")
    private RegistrationSituation registrationSituation;

    @ApiModelProperty(value = "The responsible.")
    private PersonTO responsible;

    @ApiModelProperty(value = "The feeding program modalities.")
    private List<MealProvidedTO> mealProvides;

    @ApiModelProperty(value = "The feeding program modalities.", required = true)
    @NotNull
    private List<PatModalityTO> modalities;

    public PatTO() {
        this.mealProvides = new ArrayList<>();
        this.modalities = new ArrayList<>();
    }

}
