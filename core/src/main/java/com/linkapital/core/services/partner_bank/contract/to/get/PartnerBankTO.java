package com.linkapital.core.services.partner_bank.contract.to.get;

import com.linkapital.core.services.bank_nomenclature.contract.to.get.BankNomenclatureLightTO;
import com.linkapital.core.services.partner_bank.contract.to.BasePartnerBankTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel(description = "The partner bank data.")
@Getter
@Setter
public class PartnerBankTO extends BasePartnerBankTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The partner bank list.")
    private List<BankNomenclatureLightTO> bankNomenclatures;

}
