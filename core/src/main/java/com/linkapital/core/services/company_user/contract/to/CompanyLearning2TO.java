package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.person.contract.to.LightPersonSpouseTO;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company client data of the learning 2.")
@Getter
@Setter
public class CompanyLearning2TO implements GenericTO {

    @ApiModelProperty(value = "The cnpj.")
    private String cnpj;

    @ApiModelProperty(value = "The list of properties.")
    private List<PropertyGuaranteeTO> properties;

    @ApiModelProperty(value = "The list of properties rural.")
    private List<PropertyGuaranteeTO> propertiesRural;

    @ApiModelProperty(value = "The list of property in guarantees.")
    private List<PropertyGuaranteeTO> propertyGuarantees;

    @ApiModelProperty(value = "The list of person partners.")
    private List<LightPersonSpouseTO> personPartners;

    @ApiModelProperty(value = "The list of person spouses.")
    private List<LightPersonSpouseTO> personSpouses;

    @ApiModelProperty(value = "The list of comments.")
    private List<CommentTO> comments;

    public CompanyLearning2TO() {
        this.properties = new ArrayList<>();
        this.propertiesRural = new ArrayList<>();
        this.propertyGuarantees = new ArrayList<>();
        this.personPartners = new ArrayList<>();
        this.personSpouses = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public CompanyLearning2TO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CompanyLearning2TO withProperties(List<PropertyGuaranteeTO> properties) {
        setProperties(properties);
        return this;
    }

    public CompanyLearning2TO withPropertiesRural(List<PropertyGuaranteeTO> propertiesRural) {
        setPropertiesRural(propertiesRural);
        return this;
    }

    public CompanyLearning2TO withPropertyGuarantees(List<PropertyGuaranteeTO> propertyGuarantees) {
        setPropertyGuarantees(propertyGuarantees);
        return this;
    }

    public CompanyLearning2TO withPersonPartners(List<LightPersonSpouseTO> personPartners) {
        setPersonPartners(personPartners);
        return this;
    }

    public CompanyLearning2TO withPersonSpouses(List<LightPersonSpouseTO> personSpouses) {
        setPersonSpouses(personSpouses);
        return this;
    }

    public CompanyLearning2TO withComments(List<CommentTO> comments) {
        this.comments = comments;
        return this;
    }

}
