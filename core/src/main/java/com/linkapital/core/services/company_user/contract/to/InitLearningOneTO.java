package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "The data to start learning offer one.")
@Getter
@Setter
public class InitLearningOneTO {

    @ApiModelProperty(value = "The cnpj.", required = true)
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The id of the user who owns the company.")
    private Long userId;

    @ApiModelProperty(value = "The average receipt term.")
    private int avgReceiptTerm;

    @ApiModelProperty(value = "The credit requested.", required = true)
    @NotNull
    private double creditRequested;

    @ApiModelProperty(value = "The invoicing informed.", required = true)
    @NotNull
    private double invoicingInformed;

    @ApiModelProperty(value = "The invoices file.")
    private MultipartFile invoicesFile;

    @ApiModelProperty(value = "The sped balance file.")
    private MultipartFile spedBalanceFile;

    @ApiModelProperty(value = "The sped files.")
    private MultipartFile[] spedFiles;

    @ApiModelProperty(value = "The nfe duplicates files.")
    private MultipartFile[] nfeDuplicatesFiles;

    public InitLearningOneTO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public InitLearningOneTO withUserId(Long userId) {
        setUserId(userId);
        return this;
    }

    public InitLearningOneTO withCreditRequested(double creditRequested) {
        setCreditRequested(creditRequested);
        return this;
    }

    public InitLearningOneTO withInvoicingInformed(double invoicingInformed) {
        setInvoicingInformed(invoicingInformed);
        return this;
    }

    public InitLearningOneTO withAvgReceiptTerm(Integer avgReceiptTerm) {
        setAvgReceiptTerm(avgReceiptTerm);
        return this;
    }

    public InitLearningOneTO withInvoicesFile(MultipartFile invoicesFile) {
        setInvoicesFile(invoicesFile);
        return this;
    }

    public InitLearningOneTO withSpedBalanceFile(MultipartFile spedBalanceFile) {
        setSpedBalanceFile(spedBalanceFile);
        return this;
    }

    public InitLearningOneTO withSpedFiles(MultipartFile[] spedFiles) {
        setSpedFiles(spedFiles);
        return this;
    }

    public InitLearningOneTO withNfeDuplicatesFiles(MultipartFile[] nfeDuplicatesFiles) {
        setNfeDuplicatesFiles(nfeDuplicatesFiles);
        return this;
    }

}
