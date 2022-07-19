package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "All financial activity data.")
@Getter
@Setter
public class FinancialActivityTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The segment that issues the license.")
    private String segment;

    @ApiModelProperty(value = "The unique number of the person's qualification in COAF (Financial Activities Control Council).")
    private String enablementNumber;

    @ApiModelProperty(value = "The enablement situation.")
    private String enablementSituation;

    @ApiModelProperty(value = "The query date.")
    private Date queryDate;

    @ApiModelProperty(value = "The enablement date.")
    private Date enablementDate;

}
