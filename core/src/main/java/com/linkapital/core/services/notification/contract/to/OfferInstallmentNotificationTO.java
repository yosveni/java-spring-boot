package com.linkapital.core.services.notification.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.offer_installment.contract.to.BaseOfferInstallmentTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The offer installment data.")
@Getter
@Setter
public class OfferInstallmentNotificationTO extends BaseOfferInstallmentTO {

    @ApiModelProperty(value = "The offer contract installment id.")
    private long id;

    @ApiModelProperty(value = "The offer id.")
    private long offerId;

    @ApiModelProperty(value = "The offer contract type.")
    private long offerType;

    @ApiModelProperty(value = "the date of created.")
    private Date created;

    @ApiModelProperty(value = "the payment ticket.")
    private DirectoryTO document;

    @ApiModelProperty(value = "the Company cnpj.")
    private String cnpj;

}
