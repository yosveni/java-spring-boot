package com.linkapital.core.services.offer.contract.to.get;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.linkapital.core.services.comment.contract.to.CommentTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionTO;
import com.linkapital.core.services.directory.contract.to.DirectoryTO;
import com.linkapital.core.services.offer.contract.enums.OfferState;
import com.linkapital.core.services.offer.contract.to.BaseOfferTO;
import com.linkapital.core.services.offer.contract.to.OfferStateLogsTO;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.partner_bank.contract.to.get.PartnerBankTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "The indicative proposal data.")
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OfferOneTO.class, name = "1"),
        @JsonSubTypes.Type(value = OfferTwoTO.class, name = "2"),
        @JsonSubTypes.Type(value = OfferThreeTO.class, name = "3"),
        @JsonSubTypes.Type(value = OfferFourTO.class, name = "4")
})
public class OfferTO extends BaseOfferTO {

    @ApiModelProperty(value = "The id.")
    private long id;

    @ApiModelProperty(value = "Indicates if the offer has been accepted or not.")
    private boolean accepted;

    @ApiModelProperty(value = "The date of created.")
    private Date created;

    @ApiModelProperty(value = "The date of the contract.")
    private Date contractDate;

    @ApiModelProperty(value = "The state.", required = true)
    private OfferState state;

    @ApiModelProperty(value = "The partner bank.")
    private PartnerBankTO partnerBank;

    @ApiModelProperty(value = "The commission.")
    private CommissionTO commission;

    @ApiModelProperty(value = "The list of offer payments.")
    private List<OfferInstallmentTO> payments;

    @ApiModelProperty(value = "The list of offer state logs.")
    private List<OfferStateLogsTO> offerStateLogs;

    @ApiModelProperty(value = "The comments.")
    private List<CommentTO> comments;

    @ApiModelProperty(value = "The documents.")
    private List<DirectoryTO> documents;

    public OfferTO() {
        this.payments = new ArrayList<>();
        this.offerStateLogs = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public OfferTO withCommission(CommissionTO commission) {
        setCommission(commission);
        return this;
    }

}
