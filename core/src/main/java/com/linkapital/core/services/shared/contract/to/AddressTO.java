package com.linkapital.core.services.shared.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company address data.")
@Getter
@Setter
public class AddressTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The neighborhood.")
    private String neighborhood;

    @ApiModelProperty(value = "The original neighborhood.")
    private String originalNeighborhood;

    @ApiModelProperty(value = "The zip.")
    private String zip;

    @ApiModelProperty(value = "The address1.")
    private String address1;

    @ApiModelProperty(value = "The address2.")
    private String address2;

    @ApiModelProperty(value = "The mRegion.")
    private String mRegion;

    @ApiModelProperty(value = "The micro region.")
    private String microRegion;

    @ApiModelProperty(value = "The region.")
    private String region;

    @ApiModelProperty(value = "The country.")
    private String country;

    @ApiModelProperty(value = "The code of the country.")
    private String codeCountry;

    @ApiModelProperty(value = "The municipality.")
    private String municipality;

    @ApiModelProperty(value = "The code of the municipality.")
    private String codeMunicipality;

    @ApiModelProperty(value = "The border municipality.")
    private String borderMunicipality;

    @ApiModelProperty(value = "The number.")
    private String number;

    @ApiModelProperty(value = "The precision.")
    private String precision;

    @ApiModelProperty(value = "The record state in question.")
    private String uf;

    @ApiModelProperty(value = "The registry of record state in question.")
    private String registryUf;

    @ApiModelProperty(value = "The building type.")
    private String buildingType;

    @ApiModelProperty(value = "The formatted address.")
    private String formattedAddress;

    @ApiModelProperty(value = "The latitude value.")
    private Double latitude;

    @ApiModelProperty(value = "The longitude value.")
    private Double longitude;

    @ApiModelProperty(value = "If have delivery restriction.")
    private boolean deliveryRestriction;

    @ApiModelProperty(value = "If is a residential address.")
    private boolean residentialAddress;

    @ApiModelProperty(value = "If is the latest address.")
    private boolean latestAddress;

    @ApiModelProperty(value = "If is a collective Building.")
    private boolean collectiveBuilding;

    @ApiModelProperty(value = "The phones list.")
    private List<String> rfPhones;

    public AddressTO() {
        this.rfPhones = new ArrayList<>();
    }

}
