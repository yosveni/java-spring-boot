package com.linkapital.core.services.offer.contract.to.create;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.linkapital.core.services.offer.contract.to.BaseOfferTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "The offer data to create.")
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOfferOneTO.class, name = "1"),
        @JsonSubTypes.Type(value = CreateOfferTwoTO.class, name = "2"),
        @JsonSubTypes.Type(value = CreateOfferThreeTO.class, name = "3"),
        @JsonSubTypes.Type(value = CreateOfferFourTO.class, name = "4")
})
public class CreateOfferTO extends BaseOfferTO {

    @ApiModelProperty(value = "The user id.", required = true)
    @NotNull
    private long user;

    @ApiModelProperty(value = "The partner bank id.", required = true)
    @NotNull
    private long partnerBank;

}
