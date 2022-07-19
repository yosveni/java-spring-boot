package com.linkapital.core.services.company.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company_user.contract.to.CompanyConfirm1TO;
import com.linkapital.core.services.company_user.contract.to.InitLearningFourTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningThreeTO;
import com.linkapital.core.services.company_user.contract.to.InitLearningTwoTO;
import com.linkapital.core.services.company_user.contract.to.LearningConfirmTO;
import com.linkapital.core.services.security.datasource.UserRepository;
import com.linkapital.core.services.security.datasource.domain.User;
import lombok.NonNull;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.security.contract.enums.Authority.BACKOFFICE;
import static com.linkapital.core.services.security.contract.enums.Authority.SECURITY;

@Aspect
@Component
public class CompanyValidator {

    private static final String DEFAULT_ARGS_NAMES = "to";

    private final UserRepository userRepository;

    public CompanyValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void validateElementNull(Object obj, String msg) throws UnprocessableEntityException {
        if (obj == null)
            throw new UnprocessableEntityException(msg);
    }

    public static void validateListEmpty(@NonNull Collection<?> list, String msg) throws UnprocessableEntityException {
        if (list.isEmpty())
            throw new UnprocessableEntityException(msg);
    }

    public static void validateListNotEmpty(@NonNull Collection<?> list,
                                            String msg) throws UnprocessableEntityException {
        if (!list.isEmpty())
            throw new UnprocessableEntityException(msg);
    }

    public static void validateListContaints(@NonNull Collection<?> list,
                                             Object element,
                                             String msg) throws UnprocessableEntityException {
        if (list.contains(element))
            throw new UnprocessableEntityException(msg);
    }

    public static void validateScr(boolean scr) throws UnprocessableEntityException {
        if (!scr)
            throw new UnprocessableEntityException(msg("company.scr.search.authorization"));
    }

    @Before(value = "execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.initLearningTwo(..)) && args(to)",
            argNames = DEFAULT_ARGS_NAMES)
    public void validateInitLearningOne(@NonNull InitLearningTwoTO to) throws UnprocessableEntityException {
        validateUserContainsCnpjOrIsAdminOrBackoffice(to.getCnpj(), to.getUserId());
    }

    @Before(value = "execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.initLearningThree(..)) && args(to)",
            argNames = DEFAULT_ARGS_NAMES)
    public void validateInitLearningThree(@NonNull InitLearningThreeTO to) throws UnprocessableEntityException {
        validateUserContainsCnpjOrIsAdminOrBackoffice(to.getCnpj(), to.getUserId());
    }

    @Before(value = "execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.initLearningFour(..)) && args(to)",
            argNames = DEFAULT_ARGS_NAMES)
    public void validateInitLearningFourTO(@NonNull InitLearningFourTO to) throws UnprocessableEntityException {
        validateUserContainsCnpjOrIsAdminOrBackoffice(to.getCnpj(), to.getUserId());
    }

    @Before(value = "execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.confirmLearningOne(..)) && args(to)",
            argNames = DEFAULT_ARGS_NAMES)
    public void validateConfirmLearningOne(@NonNull CompanyConfirm1TO to) throws UnprocessableEntityException {
        validateUserContainsCnpjOrIsAdminOrBackoffice(to.getCnpj(), to.getUserId());
    }

    @Before(value = "execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.confirmLearningTwo(..)) && args(to)" +
            "|| execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.confirmLearningThree(..)) && args(to)" +
            "|| execution(* com.linkapital.core.services.company_user.CompanyUserIndicativeOfferService+.confirmLearningFour(..)) && args(to)",
            argNames = DEFAULT_ARGS_NAMES)
    public void validateConfirmLearningTwoThreeAndFour(@NonNull LearningConfirmTO to)
            throws UnprocessableEntityException {
        validateUserContainsCnpjOrIsAdminOrBackoffice(to.getCnpj(), to.getUserId());
    }

    private void validateUserContainsCnpjOrIsAdminOrBackoffice(String cnpj,
                                                               Long userId) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userId != null)
            user = userRepository
                    .findById(userId)
                    .orElseThrow(() -> new UnprocessableEntityException(msg("security.user.id.not.found")));

        var cnpjList = getAllCnpjOfCumpanyUser(user);
        var authority = user.getRole().getAuthority();
        if (!cnpjList.contains(cnpj) && !authority.equals(SECURITY) && !authority.equals(BACKOFFICE))
            throw new UnprocessableEntityException(msg("security.user.company.not.found.on.client.list"));
    }

    private List<String> getAllCnpjOfCumpanyUser(@NonNull User user) {
        return user.getCompanies().stream()
                .map(companyUser -> companyUser.getCompany().getMainInfo().getCnpj())
                .toList();
    }

}
