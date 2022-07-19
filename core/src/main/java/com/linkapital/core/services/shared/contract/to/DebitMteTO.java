package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The debit Mte data.")
@Getter
@Setter
public class DebitMteTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The code.")
    private String code;

    @ApiModelProperty(value = "The debit situation.")
    private String debitSituation;

    @ApiModelProperty(value = "The certificate type.")
    private String certificateType;

    @ApiModelProperty(value = "The emission date.")
    private Date emissionDate;

    @ApiModelProperty(value = "The processes list.")
    private List<DebitMteProcessTO> processes;

    public DebitMteTO() {
        this.processes = new ArrayList<>();
    }

}
