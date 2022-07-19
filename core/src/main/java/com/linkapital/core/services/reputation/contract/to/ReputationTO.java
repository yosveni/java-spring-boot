package com.linkapital.core.services.reputation.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "All reputation data.")
@Getter
@Setter
public class ReputationTO {

    @ApiModelProperty(value = "The id.", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "The total of rating.")
    private int totalRating;

    @ApiModelProperty(value = "The count of ratings with value 1.")
    private int count1;

    @ApiModelProperty(value = "The count of ratings with value 2.")
    private int count2;

    @ApiModelProperty(value = "The count of ratings with value 3.")
    private int count3;

    @ApiModelProperty(value = "The count of ratings with value 4.")
    private int count4;

    @ApiModelProperty(value = "The count of ratings with value 5.")
    private int count5;

    @ApiModelProperty(value = "The ratings average.")
    private double avg;

    @ApiModelProperty(value = "The percentage that the ratings with value 1 represent of the total.")
    private double avg1;

    @ApiModelProperty(value = "The percentage that the ratings with value 2 represent of the total.")
    private double avg2;

    @ApiModelProperty(value = "The percentage that the ratings with value 3 represent of the total.")
    private double avg3;

    @ApiModelProperty(value = "The percentage that the ratings with value 4 represent of the total.")
    private double avg4;

    @ApiModelProperty(value = "The percentage that the ratings with value 5 represent of the total.")
    private double avg5;

    @ApiModelProperty(value = "The list of the ratings")
    private List<RatingTO> ratings;

    public ReputationTO() {
        this.ratings = new ArrayList<>();
    }

}
