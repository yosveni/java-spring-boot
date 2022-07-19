package com.linkapital.core.services.reputation.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.reputation.ReputationService;
import com.linkapital.core.services.reputation.contract.to.CreateRatingTO;
import com.linkapital.core.services.reputation.contract.to.ReputationTO;
import com.linkapital.core.services.reputation.datasource.ReputationRepository;
import com.linkapital.core.services.reputation.datasource.domain.Reputation;
import com.linkapital.core.services.security.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.linkapital.core.services.notification.contract.enums.EmailType.RATE;
import static com.linkapital.core.services.reputation.contract.ReputationBinder.REPUTATION_BINDER;
import static com.linkapital.core.services.reputation.contract.ReputationBinder.setReputation;
import static com.linkapital.core.services.reputation.validator.ReputationValidator.validateRated;

@Service
@Transactional
public class ReputationServiceImpl implements ReputationService {

    private final ReputationRepository reputationRepository;
    private final UserService userService;
    private final EmailService emailService;

    public ReputationServiceImpl(ReputationRepository reputationRepository,
                                 UserService userService,
                                 EmailService emailService) {
        this.reputationRepository = reputationRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public ReputationTO createRate(CreateRatingTO to) throws UnprocessableEntityException {
        var user = userService.getByEmail(to.getEmail());
        var reputation = getReputation();
        validateRated(user.isHasRating());

        reputation = reputationRepository.save(setReputation.apply(user.getEmail(), to, reputation));
        user.setHasRating(true);
        userService.save(user);
        emailService.send(RATE, user.getEmail(), to.getComment(), to.getRatingValue());

        return REPUTATION_BINDER.bind(reputation);
    }

    @Override
    public ReputationTO get() {
        return REPUTATION_BINDER.bind(getReputation());
    }

    private Reputation getReputation() {
        return Optional
                .of(reputationRepository.findAll())
                .flatMap(reputations -> !reputations.isEmpty()
                        ? Optional.of(reputations.get(0))
                        : Optional.of(new Reputation()))
                .orElse(new Reputation());
    }

}
