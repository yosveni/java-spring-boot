package com.linkapital.core.services.company_user.contract.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(description = "The data to update learning offer three.")
@Getter
@Setter
public class UpdateLearningThreeTO extends InitLearningThreeTO {

    @ApiModelProperty(value = "Indicates if invoices are replaced.", required = true)
    private boolean clean;

    @Override
    public UpdateLearningThreeTO withCnpj(String cnpj) {
        super.withCnpj(cnpj);
        return this;
    }

    @Override
    public UpdateLearningThreeTO withUserId(Long userId) {
        super.withUserId(userId);
        return this;
    }

    @Override
    public UpdateLearningThreeTO withAvgReceiptTerm(Integer avgReceiptTerm) {
        super.withAvgReceiptTerm(avgReceiptTerm);
        return this;
    }

    @Override
    public UpdateLearningThreeTO withInvoiceFiles(MultipartFile invoiceFiles) {
        super.withInvoiceFiles(invoiceFiles);
        return this;
    }

    public UpdateLearningThreeTO withClean(boolean clean) {
        setClean(clean);
        return this;
    }

    @Override
    public UpdateLearningThreeTO withSpedBalanceFile(MultipartFile spedBalanceFile) {
        super.withSpedBalanceFile(spedBalanceFile);
        return this;
    }

    @Override
    public UpdateLearningThreeTO withSpedFiles(MultipartFile[] spedFiles) {
        super.withSpedFiles(spedFiles);
        return this;
    }

    @Override
    public UpdateLearningThreeTO withNfeDuplicatesFiles(MultipartFile[] nfeDuplicatesFiles) {
        super.withNfeDuplicatesFiles(nfeDuplicatesFiles);
        return this;
    }

}
