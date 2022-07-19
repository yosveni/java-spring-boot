package com.linkapital.core.services.company_user.contract.to;

import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel(description = "The company client data of the learning 1.")
@Getter
@Setter
public class CompanyLearning1TO extends CompanyLearningPatternTO {

    @Override
    public CompanyLearning1TO withProtestInformation(ProtestInformationTO protestInformation) {
        setProtestInformation(protestInformation);
        return this;
    }

    @Override
    public CompanyLearning1TO withInvoicingInformed(double invoicingInformed) {
        setInvoicingInformed(invoicingInformed);
        return this;
    }

    @Override
    public CompanyLearning1TO withIndicativeOfferOne(IndicativeOfferTO indicativeOfferOne) {
        setIndicativeOfferOne(indicativeOfferOne);
        return this;
    }

    @Override
    public CompanyLearning1TO withIndicativeOfferTwo(IndicativeOfferTO indicativeOfferTwo) {
        setIndicativeOfferTwo(indicativeOfferTwo);
        return this;
    }

    @Override
    public CompanyLearning1TO withIndicativeOfferThree(IndicativeOfferTO indicativeOfferThree) {
        setIndicativeOfferThree(indicativeOfferThree);
        return this;
    }

    @Override
    public CompanyLearning1TO withIndicativeOfferFour(IndicativeOfferTO indicativeOfferFour) {
        setIndicativeOfferFour(indicativeOfferFour);
        return this;
    }

    @Override
    public CompanyLearning1TO withComments(List<CommentTO> comments) {
        setComments(comments);
        return this;
    }

    @Override
    public CompanyLearning1TO withDebtDocuments(List<DirectoryTO> debtDocuments) {
        setDebtDocuments(debtDocuments);
        return this;
    }

}
