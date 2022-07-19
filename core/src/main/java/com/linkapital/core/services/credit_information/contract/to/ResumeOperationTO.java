package com.linkapital.core.services.credit_information.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The resume of the operation data.")
@Getter
@Setter
public class ResumeOperationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The modality.")
    private String modality;

    @ApiModelProperty(value = "The exchange variation.")
    private String exchangeVariation;

    @ApiModelProperty(value = "The earnings list.")
    private List<EarningTO> earnings;

    public ResumeOperationTO() {
        this.earnings = new ArrayList<>();
    }

}
