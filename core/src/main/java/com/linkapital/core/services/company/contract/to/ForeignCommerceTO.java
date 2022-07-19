package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.company.contract.enums.EnabledSituation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "The foreign commerce data.")
@Getter
@Setter
public class ForeignCommerceTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The modality.")
    private String modality;

    @ApiModelProperty(value = "The sub-modality.")
    private String submodality;

    @ApiModelProperty(value = "The authorized operations type.")
    private String authorizedOperations;

    @ApiModelProperty(value = "If is enabled.")
    private boolean enabled;

    @ApiModelProperty(value = "The date situation.")
    private Date situationDate;

    @ApiModelProperty(value = "The enabled situation type.")
    private EnabledSituation enabledSituation;

}
