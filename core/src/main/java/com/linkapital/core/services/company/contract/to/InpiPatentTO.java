package com.linkapital.core.services.company.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "All INPI patent data.")
@Getter
@Setter
public class InpiPatentTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The refers to the depositor of the trademark / software / patent / industrial design application.")
    private String depositor;

    @ApiModelProperty(value = "The depositor's legal representative.")
    private String procurator;

    @ApiModelProperty(value = "The title or name of the invention.")
    private String title;

    @ApiModelProperty(value = "The process number of patent registration at INPI.")
    private String processNumber;

    @ApiModelProperty(value = "The date on which the INPI (National Institute of Industrial Property) granted the right to a patent requested by the company.")
    private Date concessionDate;

    @ApiModelProperty(value = "The date of publication of the patent at INPI (National Institute of Industrial Property).")
    private Date publicationDate;

    @ApiModelProperty(value = "The patent registration date at the INPI.")
    private Date depositDate;

    @ApiModelProperty(value = "The list of persons responsible for creating the invention patent and utility model.")
    private List<String> inventors;

    public InpiPatentTO() {
        this.inventors = new ArrayList<>();
    }

}
