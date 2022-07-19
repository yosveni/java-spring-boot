package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.invoice.contract.to.HorizontalAnalysisTO;
import com.linkapital.core.services.invoice.contract.to.VerticalAnalysisTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company client data of the learning 3.")
@Getter
@Setter
public class CompanyLearning3TO implements GenericTO {

    @ApiModelProperty(value = "The cnpj of the company.")
    private String cnpj;

    @ApiModelProperty(value = "The average receipt term.")
    private int avgReceiptTerm;

    @ApiModelProperty(value = "The Horizontal Analysis Object.")
    private HorizontalAnalysisTO horizontalAnalysisTO;

    @ApiModelProperty(value = "The Vertical Analysis Object.")
    private VerticalAnalysisTO verticalAnalysisTO;

    @ApiModelProperty(value = "The list of NFE duplicity.")
    private List<DirectoryTO> nfeDuplicity;

    @ApiModelProperty(value = "The comments list.")
    private List<CommentTO> comments;

    public CompanyLearning3TO() {
        this.nfeDuplicity = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public CompanyLearning3TO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CompanyLearning3TO withAvgReceiptTerm(int avgReceiptTerm) {
        setAvgReceiptTerm(avgReceiptTerm);
        return this;
    }

    public CompanyLearning3TO withHorizontalAnalysisTO(HorizontalAnalysisTO horizontalAnalysisTO) {
        setHorizontalAnalysisTO(horizontalAnalysisTO);
        return this;
    }

    public CompanyLearning3TO withVerticalAnalysisTO(VerticalAnalysisTO verticalAnalysisTO) {
        setVerticalAnalysisTO(verticalAnalysisTO);
        return this;
    }

    public CompanyLearning3TO withNfeDuplicity(List<DirectoryTO> nfeDuplicity) {
        setNfeDuplicity(nfeDuplicity);
        return this;
    }

    public CompanyLearning3TO withComments(List<CommentTO> comments) {
        setComments(comments);
        return this;
    }

}
