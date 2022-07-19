package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.company.contract.enums.RegistrationSituation;
import com.linkapital.core.services.shared.contract.to.AddressTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel(description = "The company main info.")
@Getter
@Setter
public class CompanyMainInfoTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The social reason.")
    private String socialReason;

    @ApiModelProperty(value = "The opening date.")
    private LocalDateTime openingDate;

    @ApiModelProperty(value = "The registration situation.")
    private RegistrationSituation registrationSituation;

    @ApiModelProperty(value = "The address.")
    private AddressTO address;

}
