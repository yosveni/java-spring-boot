package com.linkapital.core.services.protest.contract.to;

import com.linkapital.core.services.protest.contract.emuns.ProtestArea;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@ApiModel(description = "The protest data.")
@Getter
@Setter
public class ProtestTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The key.")
    private String key;

    @ApiModelProperty(value = "The presenter name.")
    private String presenterName;

    @ApiModelProperty(value = "The assignor name.")
    private String assignorName;

    @ApiModelProperty(value = "The value.")
    private double value;

    @ApiModelProperty(value = "If it has consent.")
    private boolean hasConsent;

    @ApiModelProperty(value = "The consult date.")
    private LocalDate consultDate;

    @ApiModelProperty(value = "The due date.")
    private LocalDate dueDate;

    @ApiModelProperty(value = "The area to which it belongs. (FINANCIAL_SEGMENT, TAXES_PUBLIC, OTHER)")
    private ProtestArea area;

}
