package com.linkapital.core.services.offer_installment.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The offer installment data.")
@Getter
@Setter
public class OfferInstallmentTO extends BaseOfferInstallmentTO {

    @ApiModelProperty(value = "The offer contract installment id.")
    private long id;

    @ApiModelProperty(value = "the date of created.")
    private Date created;

    @ApiModelProperty(value = "the payment ticket.")
    private DirectoryTO document;

}
