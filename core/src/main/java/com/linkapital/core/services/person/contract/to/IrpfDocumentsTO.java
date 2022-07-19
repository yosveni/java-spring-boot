package com.linkapital.core.services.person.contract.to;

import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The list of IRPF documents and IRPF Receipt documents.")
@Getter
@Setter
public class IrpfDocumentsTO {

    @ApiModelProperty(value = "The IRPF documents.")
    private List<DirectoryTO> irpfDocuments;

    @ApiModelProperty(value = "The IRPF RECEIPT documents.")
    private List<DirectoryTO> irpfReceiptDocuments;

    public IrpfDocumentsTO() {
        this.irpfDocuments = new ArrayList<>();
        this.irpfReceiptDocuments = new ArrayList<>();
    }

    public IrpfDocumentsTO withIrpfDocuments(List<DirectoryTO> irpfDocuments) {
        setIrpfDocuments(irpfDocuments);
        return this;
    }

    public IrpfDocumentsTO withIrpfReceiptDocuments(List<DirectoryTO> irpfReceiptDocuments) {
        setIrpfReceiptDocuments(irpfReceiptDocuments);
        return this;
    }

}
