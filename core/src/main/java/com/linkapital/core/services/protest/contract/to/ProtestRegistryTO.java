package com.linkapital.core.services.protest.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The protest registry data.")
@Getter
@Setter
public class ProtestRegistryTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The phone.")
    private String phone;

    @ApiModelProperty(value = "The address.")
    private String address;

    @ApiModelProperty(value = "The city code.")
    private String cityCode;

    @ApiModelProperty(value = "The city Ibge code.")
    private String cityCodeIbge;

    @ApiModelProperty(value = "The municipality.")
    private String municipality;

    @ApiModelProperty(value = "The city.")
    private String city;

    @ApiModelProperty(value = "The neighborhood.")
    private String neighborhood;

    @ApiModelProperty(value = "The federal unit.")
    private String uf;

    @ApiModelProperty(value = "The code.")
    private int code;

    @ApiModelProperty(value = "The amount.")
    private int amount;

    @ApiModelProperty(value = "The search period.")
    private String searchPeriod;

    @ApiModelProperty(value = "The update date.")
    private String updateDate;

    @ApiModelProperty(value = "The protests.")
    private List<ProtestTO> protests;

    public ProtestRegistryTO() {
        this.protests = new ArrayList<>();
    }

}
