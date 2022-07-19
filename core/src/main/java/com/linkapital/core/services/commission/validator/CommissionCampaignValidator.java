package com.linkapital.core.services.commission.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.commission.datasource.CommissionCampaignRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Aspect
@Component
public class CommissionCampaignValidator {

    private static final String DEFAULT_ARGS_NAMES = "id";

    private final CommissionCampaignRepository commissionCampaignRepository;

    public CommissionCampaignValidator(CommissionCampaignRepository commissionCampaignRepository) {
        this.commissionCampaignRepository = commissionCampaignRepository;
    }

    @Before(value = "execution(* com.linkapital.core.services.commission.CommissionCampaignService+.delete(..)) && args(id)",
            argNames = DEFAULT_ARGS_NAMES)
    public void updateEmail(long id) throws UnprocessableEntityException {
        if (commissionCampaignRepository.existsByIdAndBaseIsTrue(id))
            throw new UnprocessableEntityException(msg("campaing.base.cannot.be.eliminated"));
    }

}
