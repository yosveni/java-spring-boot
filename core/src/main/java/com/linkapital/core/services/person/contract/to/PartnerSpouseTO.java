package com.linkapital.core.services.person.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "The marriage regime data of the person.")
@Getter
@Setter
public class PartnerSpouseTO {

    @ApiModelProperty(value = "The person partner cpf.")
    private String cpfPartner;

    @ApiModelProperty(value = "The person partner name.")
    private String name;

    @ApiModelProperty(value = "The spouse of person partner cpf.")
    private String cpfSpouse;

    @ApiModelProperty(value = "The marriage regime between the person and their spouse.")
    private String marriageRegime;

    @ApiModelProperty(value = "Used in the display of learning data 2, indicates if this partner is selected.")
    private boolean selected;

    public PartnerSpouseTO() {
        setSelected(true);
    }

    public PartnerSpouseTO withCpfPartner(String cpfPartner) {
        setCpfPartner(cpfPartner);
        return this;
    }

    public PartnerSpouseTO withName(String name) {
        setName(name);
        return this;
    }

    public PartnerSpouseTO withCpfSpouse(String cpfSpouse) {
        setCpfSpouse(cpfSpouse);
        return this;
    }

    public PartnerSpouseTO withMarriageRegime(String marriageRegime) {
        setMarriageRegime(marriageRegime);
        return this;
    }

    public PartnerSpouseTO withSelected(boolean selected) {
        setSelected(selected);
        return this;
    }

}
