package com.linkapital.core.services.bank_nomenclature.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company bank document data.")
@Getter
@Setter
public class CompanyBankDocumentLightTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "The description.")
    private String description;

    @ApiModelProperty(value = "The directories.")
    private List<DirectoryTO> directories;

    public CompanyBankDocumentLightTO() {
        this.directories = new ArrayList<>();
    }

    public CompanyBankDocumentLightTO withId(long id) {
        setId(id);
        return this;
    }

    public CompanyBankDocumentLightTO withDescription(String description) {
        setDescription(description);
        return this;
    }

    public CompanyBankDocumentLightTO withDirectories(List<DirectoryTO> directories) {
        setDirectories(directories);
        return this;
    }

}
