package com.linkapital.core.services.partner_bank.contract.to.create;

import com.linkapital.core.services.partner_bank.contract.to.BasePartnerBankTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The partner bank data to create.")
@Getter
@Setter
public class CreatePartnerBankTO extends BasePartnerBankTO {

    @ApiModelProperty(value = "The bank nomenclatures list.")
    private List<Long> bankNomenclatures;

    public CreatePartnerBankTO() {
        this.bankNomenclatures = new ArrayList<>();
    }

    @Override
    public String getName() {
        return super.getName().toUpperCase();
    }

}
