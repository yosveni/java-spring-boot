package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(description = "The data to update learning offer four.")
@Getter
@Setter
public class UpdateLearningFourTO extends InitLearningFourTO {

    @ApiModelProperty(value = "Indicates if invoices are replaced.", required = true)
    private boolean clean;

    public UpdateLearningFourTO withClean(boolean clean) {
        setClean(clean);
        return this;
    }

    @Override
    public UpdateLearningFourTO withCnpj(String cnpj) {
        super.withCnpj(cnpj);
        return this;
    }

    @Override
    public UpdateLearningFourTO withUserId(Long userId) {
        super.withUserId(userId);
        return this;
    }

    @Override
    public UpdateLearningFourTO withAvgReceiptTerm(Integer avgReceiptTerm) {
        super.withAvgReceiptTerm(avgReceiptTerm);
        return this;
    }

    @Override
    public UpdateLearningFourTO withInvoicesFile(MultipartFile invoicesFile) {
        super.withInvoicesFile(invoicesFile);
        return this;
    }

    @Override
    public UpdateLearningFourTO withSpedBalanceFile(MultipartFile spedBalanceFile) {
        super.withSpedBalanceFile(spedBalanceFile);
        return this;
    }

    @Override
    public UpdateLearningFourTO withSpedFiles(MultipartFile[] spedFiles) {
        super.withSpedFiles(spedFiles);
        return this;
    }

    @Override
    public UpdateLearningFourTO withNfeDuplicatesFiles(MultipartFile[] nfeDuplicatesFiles) {
        super.withNfeDuplicatesFiles(nfeDuplicatesFiles);
        return this;
    }

}
