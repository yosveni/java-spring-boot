package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.sped.contract.to.AnalysisTO;
import com.linkapital.core.services.sped.contract.to.HorizontalAnalysisTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "The company client data of the learning 4.")
@Getter
@Setter
public class CompanyLearning4TO implements GenericTO {

    @ApiModelProperty(value = "The cnpj of the company.", required = true)
    private String cnpj;

    @ApiModelProperty(value = "The sped balance document.")
    private DirectoryTO spedDocument;

    @ApiModelProperty(value = "The financial and vertical analysis list.")
    private List<AnalysisTO> analysis;

    @ApiModelProperty(value = "The horizontal analysis.")
    private List<HorizontalAnalysisTO> horizontalAnalysis;

    @ApiModelProperty(value = "The comments list.")
    private List<CommentTO> comments;

    public CompanyLearning4TO() {
        this.analysis = new ArrayList<>();
        this.horizontalAnalysis = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public CompanyLearning4TO withCnpj(String cnpj) {
        setCnpj(cnpj);
        return this;
    }

    public CompanyLearning4TO withSpedDocument(DirectoryTO spedDocument) {
        setSpedDocument(spedDocument);
        return this;
    }

    public CompanyLearning4TO withAnalysis(List<AnalysisTO> analysis) {
        setAnalysis(analysis);
        return this;
    }

    public CompanyLearning4TO withHorizontalAnalysis(List<HorizontalAnalysisTO> horizontalAnalysis) {
        setHorizontalAnalysis(horizontalAnalysis);
        return this;
    }

    public CompanyLearning4TO withComments(List<CommentTO> comments) {
        setComments(comments);
        return this;
    }

}
