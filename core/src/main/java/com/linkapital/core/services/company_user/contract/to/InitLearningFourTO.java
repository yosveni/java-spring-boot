package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "The data to start learning two.")
@Getter
@Setter
public class InitLearningFourTO {

    @ApiModelProperty(value = "The cnpj.", required = true)
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The id of the user who owns the company.")
    @Min(1)
    private Long userId;

    @ApiModelProperty(value = "The average receipt term.")
    private int avgReceiptTerm;

    @ApiModelProperty(value = "The invoices file.")
    private MultipartFile invoicesFile;

    @ApiModelProperty(value = "The sped balance file.")
    private MultipartFile spedBalanceFile;

    @ApiModelProperty(value = "The sped files.")
    private MultipartFile[] spedFiles;

    @ApiModelProperty(value = "The nfe duplicates files.")
    private MultipartFile[] nfeDuplicatesFiles;

    public InitLearningFourTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public InitLearningFourTO withUserId(Long userId) {
        setUserId(userId);
        return this;
    }

    public InitLearningFourTO withAvgReceiptTerm(Integer avgReceiptTerm) {
        setAvgReceiptTerm(avgReceiptTerm);
        return this;
    }

    public InitLearningFourTO withInvoicesFile(MultipartFile invoicesFile) {
        setInvoicesFile(invoicesFile);
        return this;
    }

    public InitLearningFourTO withSpedBalanceFile(MultipartFile spedBalanceFile) {
        setSpedBalanceFile(spedBalanceFile);
        return this;
    }

    public InitLearningFourTO withSpedFiles(MultipartFile[] spedFiles) {
        setSpedFiles(spedFiles);
        return this;
    }

    public InitLearningFourTO withNfeDuplicatesFiles(MultipartFile[] nfeDuplicatesFiles) {
        setNfeDuplicatesFiles(nfeDuplicatesFiles);
        return this;
    }

}
