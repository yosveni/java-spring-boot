package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "The data to start learning offer three.")
@Getter
@Setter
public class InitLearningThreeTO {

    @ApiModelProperty(value = "The cnpj.", required = true)
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The id of the user who owns the company.")
    private Long userId;

    @ApiModelProperty(value = "The average receipt term.")
    private int avgReceiptTerm;

    @ApiModelProperty(value = "The compressed file of invoices to upload.")
    private MultipartFile invoiceFiles;

    @ApiModelProperty(value = "The sped balance file.")
    private MultipartFile spedBalanceFile;

    @ApiModelProperty(value = "The sped files.")
    private MultipartFile[] spedFiles;

    @ApiModelProperty(value = "The nfe duplicates files.")
    private MultipartFile[] nfeDuplicatesFiles;

    public InitLearningThreeTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public InitLearningThreeTO withUserId(Long userId) {
        setUserId(userId);
        return this;
    }

    public InitLearningThreeTO withAvgReceiptTerm(Integer avgReceiptTerm) {
        setAvgReceiptTerm(avgReceiptTerm);
        return this;
    }

    public InitLearningThreeTO withInvoiceFiles(MultipartFile invoiceFiles) {
        setInvoiceFiles(invoiceFiles);
        return this;
    }

    public InitLearningThreeTO withSpedBalanceFile(MultipartFile spedBalanceFile) {
        setSpedBalanceFile(spedBalanceFile);
        return this;
    }

    public InitLearningThreeTO withSpedFiles(MultipartFile[] spedFiles) {
        setSpedFiles(spedFiles);
        return this;
    }

    public InitLearningThreeTO withNfeDuplicatesFiles(MultipartFile[] nfeDuplicatesFiles) {
        setNfeDuplicatesFiles(nfeDuplicatesFiles);
        return this;
    }

}
