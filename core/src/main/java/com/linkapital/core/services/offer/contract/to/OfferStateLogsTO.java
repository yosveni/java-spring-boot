package com.linkapital.core.services.offer.contract.to;

import com.linkapital.core.services.offer.contract.enums.OfferState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The offer state logs data.")
@Getter
@Setter
public class OfferStateLogsTO {

    @ApiModelProperty(value = "The log id.")
    private Long id;

    @ApiModelProperty(value = "The notification.")
    private String notification;

    @ApiModelProperty(value = "The state.")
    private OfferState offerState;

    @ApiModelProperty(value = "the date of created.")
    private Date created;

}
