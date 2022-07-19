package com.linkapital.core.services.indicative_offer.contract.to.get;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.linkapital.core.services.indicative_offer.contract.enums.IndicativeOfferState;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "All data of the learning offer.")
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IndicativeOfferOneTO.class, name = "1"),
        @JsonSubTypes.Type(value = IndicativeOfferTwoTO.class, name = "2"),
        @JsonSubTypes.Type(value = IndicativeOfferThreeTO.class, name = "3"),
        @JsonSubTypes.Type(value = IndicativeOfferTwoTO.class, name = "4")
})
public class IndicativeOfferTO implements Serializable {

    private static final long serialVersionUID = 8407671711309567100L;

    @ApiModelProperty(value = "The id.")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The expiration period.")
    private String deadline;

    @ApiModelProperty(value = "The type.")
    private int type;

    @ApiModelProperty(notes = "The approved volume.")
    private double volume;

    @ApiModelProperty(value = "The taxes to collect.")
    private double tax;

    @ApiModelProperty(notes = "The percentage of precision.")
    private double precision;

    @ApiModelProperty(value = "The state.")
    private IndicativeOfferState state;

    @ApiModelProperty(value = "The offers list.")
    private List<OfferTO> offers;

    public IndicativeOfferTO() {
        this.offers = new ArrayList<>();
    }

    public IndicativeOfferTO withType(int type) {
        setType(type);
        return this;
    }

    public IndicativeOfferTO withId(Long id) {
        setId(id);
        return this;
    }

    public IndicativeOfferTO withDeadline(String deadline) {
        setDeadline(deadline);
        return this;
    }

    public IndicativeOfferTO withVolume(double volume) {
        setVolume(volume);
        return this;
    }

    public IndicativeOfferTO withPrecision(double precision) {
        setPrecision(precision);
        return this;
    }

    public IndicativeOfferTO withTax(double tax) {
        setTax(tax);
        return this;
    }

    public IndicativeOfferTO withState(IndicativeOfferState state) {
        setState(state);
        return this;
    }

    public IndicativeOfferTO withOffers(List<OfferTO> offers) {
        setOffers(offers);
        return this;
    }

}
