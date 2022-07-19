package com.linkapital.core.services.company.contract.to;

import com.linkapital.core.services.company_user.contract.to.CompanyLearning2TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning3TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearning4TO;
import com.linkapital.core.services.company_user.contract.to.CompanyLearningTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "All company data.")
@Getter
@Setter
public class CompanyTO {

    @ApiModelProperty(value = "The data of the first learning of the company.")
    private CompanyLearningTO companyLearning1;

    @ApiModelProperty(value = "The data of the second learning of the company.")
    private CompanyLearning2TO companyLearning2;

    @ApiModelProperty(value = "The data of the third learning of the company.")
    private CompanyLearning3TO companyLearning3;

    @ApiModelProperty(value = "The data of the four learning of the company.")
    private CompanyLearning4TO companyLearning4;

    public CompanyTO withCompanyLearning1(CompanyLearningTO companyLearning1) {
        setCompanyLearning1(companyLearning1);
        return this;
    }

    public CompanyTO withCompanyLearning2(CompanyLearning2TO companyLearning2) {
        setCompanyLearning2(companyLearning2);
        return this;
    }

    public CompanyTO withCompanyLearning3(CompanyLearning3TO companyLearning3) {
        setCompanyLearning3(companyLearning3);
        return this;
    }

    public CompanyTO withCompanyLearning4(CompanyLearning4TO companyLearning4) {
        setCompanyLearning4(companyLearning4);
        return this;
    }

}
