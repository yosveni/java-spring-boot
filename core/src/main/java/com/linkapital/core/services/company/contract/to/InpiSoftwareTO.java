package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "All INPI software data.")
@Getter
@Setter
public class InpiSoftwareTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The process number of software registration at INPI.")
    private String processNumber;

    @ApiModelProperty(value = "The depositor's legal representative.")
    private String procurator;

    @ApiModelProperty(value = "The title and / or name of registered software.")
    private String title;

    @ApiModelProperty(value = "The date of registration of the software at the INPI.")
    private Date depositDate;

    @ApiModelProperty(value = "The name of the authors who developed the software in question.")
    private List<String> authors;

    public InpiSoftwareTO() {
        this.authors = new ArrayList<>();
    }

}
