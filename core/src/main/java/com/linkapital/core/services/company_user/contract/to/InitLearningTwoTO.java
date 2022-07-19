package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.person.contract.to.CreatePartnerSpouseTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The data to start learning two.")
@Getter
@Setter
public class InitLearningTwoTO {

    @ApiModelProperty(value = "The cnpj of the company.", required = true)
    @NotEmpty
    @Size(max = 18)
    private String cnpj;

    @ApiModelProperty(value = "The id of the user who owns the company.")
    @Min(1)
    private Long userId;

    @ApiModelProperty(value = "The avg receipt term.")
    @Min(1)
    private Integer avgReceiptTerm;

    @ApiModelProperty(value = "The invoices file.")
    private MultipartFile invoicesFile;

    @ApiModelProperty(value = "The sped files.")
    private MultipartFile[] spedFiles;

    @ApiModelProperty(value = "The list of cpf of partner and spouse.")
    @NotEmpty
    private List<CreatePartnerSpouseTO> partnersSpouse;

    public InitLearningTwoTO() {
        this.partnersSpouse = new ArrayList<>();
    }

    public InitLearningTwoTO withInvoicesFile(MultipartFile invoicesFile) {
        setInvoicesFile(invoicesFile);
        return this;
    }

    public InitLearningTwoTO withSpedFiles(MultipartFile[] spedFiles) {
        setSpedFiles(spedFiles);
        return this;
    }

}
