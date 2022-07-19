package com.linkapital.core.services.indicative_offer.contract.to.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(description = "All data of the learning offer.")
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateIndicativeOfferOneTO.class, name = "1"),
        @JsonSubTypes.Type(value = UpdateIndicativeOfferTwoTO.class, name = "2"),
        @JsonSubTypes.Type(value = UpdateIndicativeOfferThreeTO.class, name = "3"),
        @JsonSubTypes.Type(value = UpdateIndicativeOfferFourTO.class, name = "4")
})
public class UpdateIndicativeOfferTO implements Serializable {

    private static final long serialVersionUID = 8407671711309567100L;

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The type.", required = true)
    @NotNull
    @Min(1)
    private int type;

    @ApiModelProperty(value = "The expiration period.", required = true)
    @NotEmpty
    private String deadline;

    @ApiModelProperty(value = "The approved volume.", required = true)
    @NotNull
    private double volume;

    @ApiModelProperty(value = "The taxes to collect.", required = true)
    @NotNull
    private double tax;

}
