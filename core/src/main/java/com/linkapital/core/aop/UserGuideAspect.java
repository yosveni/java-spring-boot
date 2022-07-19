package com.linkapital.core.aop;

import com.linkapital.core.services.company.contract.enums.CreditApplicationFlow;
import com.linkapital.core.services.security.datasource.UserRepository;
import com.linkapital.core.services.security.datasource.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.linkapital.core.services.company.contract.enums.CreditApplicationFlow.INDICATIVES;
import static com.linkapital.core.services.company.contract.enums.CreditApplicationFlow.OFFERS;
import static com.linkapital.core.services.company.contract.enums.CreditApplicationFlow.PROGRESS;

@Aspect
@Component
public class UserGuideAspect {

    private final UserRepository userRepository;

    public UserGuideAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Around("execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.initLearningOne(..))")
    public Object initLearning1(ProceedingJoinPoint joinPoint) throws Throwable {
        updateCreditApplicationFlow(INDICATIVES);
        return joinPoint.proceed();
    }

    @Around("execution(* com.linkapital.core.services.offer.OfferService+.requestOffer(..))")
    public Object requestIndicativeProposal(ProceedingJoinPoint joinPoint) throws Throwable {
        updateCreditApplicationFlow(OFFERS);
        return joinPoint.proceed();
    }

    @Around("execution(* com.linkapital.core.services.offer.OfferService+.acceptOffer(..))")
    public Object acceptOffer(ProceedingJoinPoint joinPoint) throws Throwable {
        updateCreditApplicationFlow(PROGRESS);
        return joinPoint.proceed();
    }

    //region Sets the correspondent CreditApplicationFlow to the current authenticated user
    private void updateCreditApplicationFlow(CreditApplicationFlow flow) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null && user.getUserGuide().getCreditApplicationFlow().ordinal() < flow.ordinal()) {
            user.getUserGuide().setCreditApplicationFlow(flow);
            userRepository.save(user);
        }
    }
    //endregion
}
