package com.linkapital.core.services.commission.contract.to.get;

import com.linkapital.core.services.commission.contract.enums.InstallmentType;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel(description = "The commission installment data.")
@Getter
@Setter
public class CommissionInstallmentTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The total.")
    private double total;

    @ApiModelProperty(value = "The total base.")
    private double totalBase;

    @ApiModelProperty(value = "Indicates if the commission was paid.")
    private boolean hasPaid;

    @ApiModelProperty(value = "The installment type.")
    private InstallmentType type;

    @ApiModelProperty(value = "The payment date.")
    private Date paymentDate;

    @ApiModelProperty(value = "The created date.")
    private Date created;

    @ApiModelProperty(value = "The document.")
    private DirectoryTO document;

}
