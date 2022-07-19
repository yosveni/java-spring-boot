package com.linkapital.core.services.offer.contract.to.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.linkapital.core.services.offer.contract.enums.OfferState;
import com.linkapital.core.services.offer.contract.to.BaseOfferTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "The indicative proposal data to create.")
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateOfferOneTO.class, name = "1"),
        @JsonSubTypes.Type(value = UpdateOfferTwoTO.class, name = "2"),
        @JsonSubTypes.Type(value = UpdateOfferThreeTO.class, name = "3"),
        @JsonSubTypes.Type(value = UpdateOfferFourTO.class, name = "4")
})
public class UpdateOfferTO extends BaseOfferTO {

    @ApiModelProperty(value = "The id.", required = true)
    @Min(1)
    private long id;

    @ApiModelProperty(value = "The partner bank id.", required = true)
    @NotNull
    @Min(1)
    private long partnerBank;

    @ApiModelProperty(value = "The state.", required = true)
    @NotNull
    private OfferState state;

}
