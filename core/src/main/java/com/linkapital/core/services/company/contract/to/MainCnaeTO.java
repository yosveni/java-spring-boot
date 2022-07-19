package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The main cnae data.")
@Getter
@Setter
public class MainCnaeTO extends CnaeTO {

    @ApiModelProperty(value = "The physical productions.")
    private List<PhysicalProductionTO> physicalProductions;

    public MainCnaeTO() {
        this.physicalProductions = new ArrayList<>();
    }

}
