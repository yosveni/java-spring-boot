package com.linkapital.core.services.person.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The person document.")
@Getter
@Setter
public class PersonDocumentTO {

    @ApiModelProperty(value = "The id.")
    private Long id;

    @ApiModelProperty(value = "The name.")
    private String name;

    @ApiModelProperty(value = "The name.")
    private String spouseName;

    @ApiModelProperty(value = "The irpf documents.")
    private List<DirectoryTO> irpfDocuments;

    @ApiModelProperty(value = "The irpf receipt documents.")
    private List<DirectoryTO> irpfReceiptDocuments;

    @ApiModelProperty(value = "The spouse irpf documents.")
    private List<DirectoryTO> spouseIrpfDocuments;

    @ApiModelProperty(value = "The spouse irpf receipt documents.")
    private List<DirectoryTO> spouseIrpfReceiptDocuments;

    public PersonDocumentTO() {
        this.irpfDocuments = new ArrayList<>();
        this.irpfReceiptDocuments = new ArrayList<>();
        this.spouseIrpfDocuments = new ArrayList<>();
        this.spouseIrpfReceiptDocuments = new ArrayList<>();
    }

    public PersonDocumentTO withId(Long id) {
        setId(id);
        return this;
    }

    public PersonDocumentTO withName(String name) {
        setName(name);
        return this;
    }

    public PersonDocumentTO withSpouseName(String spouseName) {
        setSpouseName(spouseName);
        return this;
    }

    public PersonDocumentTO withIrpfDocuments(List<DirectoryTO> irpfDocuments) {
        setIrpfDocuments(irpfDocuments);
        return this;
    }

    public PersonDocumentTO withIrpfReceiptDocuments(List<DirectoryTO> irpfReceiptDocuments) {
        setIrpfReceiptDocuments(irpfReceiptDocuments);
        return this;
    }

    public PersonDocumentTO withSpouseIrpfDocuments(List<DirectoryTO> spouseIrpfDocuments) {
        setSpouseIrpfDocuments(spouseIrpfDocuments);
        return this;
    }

    public PersonDocumentTO withSpouseIrpfReceiptDocuments(List<DirectoryTO> spouseIrpfReceiptDocuments) {
        setSpouseIrpfReceiptDocuments(spouseIrpfReceiptDocuments);
        return this;
    }

}
