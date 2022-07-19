package com.linkapital.core.services.commission.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CampaignValue {

    private int product;
    private double operationValue;
    private Date contractDate;

    public CampaignValue withProduct(int product) {
        setProduct(product);
        return this;
    }

    public CampaignValue withOperationValue(double operationValue) {
        setOperationValue(operationValue);
        return this;
    }

    public CampaignValue withContractDate(Date contractDate) {
        setContractDate(contractDate);
        return this;
    }

}
