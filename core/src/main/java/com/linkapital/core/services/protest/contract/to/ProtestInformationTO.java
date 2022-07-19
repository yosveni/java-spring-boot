package com.linkapital.core.services.protest.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The protest information data.")
@Getter
@Setter
public class ProtestInformationTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The cpf or cnpj.")
    private String document;

    @ApiModelProperty(value = "The amount of protests.")
    private int total;

    @ApiModelProperty(value = "The amount of protests with errors.")
    private int totalError;

    @ApiModelProperty(value = "The date the search was carried out.")
    private LocalDateTime created;

    @ApiModelProperty(value = "The analysis of the protests.")
    private ProtestAnalysisTO analysis;

    @ApiModelProperty(value = "The protest registries.")
    private List<ProtestRegistryTO> protestRegistries;

    public ProtestInformationTO() {
        this.protestRegistries = new ArrayList<>();
    }

}
