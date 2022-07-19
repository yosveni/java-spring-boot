package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "The marriage regime data of the person.")
@Getter
@Setter
public class CreatePartnerSpouseTO {

    @ApiModelProperty(value = "The person partner cpf.", required = true)
    @NotEmpty
    @Size(max = 11)
    private String cpfPartner;

    @ApiModelProperty(value = "The spouse of person partner cpf.")
    @Size(max = 11)
    private String cpfSpouse;

    @ApiModelProperty(value = "The marriage regime between the person and their spouse.")
    private String marriageRegime;

}
