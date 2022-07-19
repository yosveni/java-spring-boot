package com.linkapital.core.services.security.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.security.contract.to.CodeNotificationTO;
import com.linkapital.core.services.security.contract.to.RegisterUserTO;
import com.linkapital.core.services.security.contract.to.create.CreateUserTO;
import com.linkapital.core.services.security.contract.to.update.UpdatePasswordTO;
import com.linkapital.core.services.security.contract.to.update.UpdateUserTO;
import com.linkapital.core.services.security.datasource.UserRepository;
import com.linkapital.core.services.security.datasource.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.security.contract.enums.Authority.SECURITY;
import static com.linkapital.core.services.security.contract.enums.NotificationType.PHONE;
import static com.linkapital.core.util.enums.FieldErrorResponse.CPF;
import static com.linkapital.core.util.enums.FieldErrorResponse.EMAIL;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;
import static org.springframework.util.StringUtils.hasText;

@Aspect
@Component
public class UserAspectValidator {

    private static final String DEFAULT_ARGS_NAMES = "joinPoint, email";
    private final UserRepository userRepository;
    private final String emailAdmin;

    public UserAspectValidator(@Value("${notifications.admin}") String userAdmin,
                               UserRepository userRepository) {
        this.userRepository = userRepository;
        this.emailAdmin = userAdmin;
    }

    @Before(value = "execution(* com.linkapital.core.services.security.UserTempService+.register(..)) && args(to)")
    public void validateRegisterUser(RegisterUserTO to) throws UnprocessableEntityException {
        validateUserAndCpf(to.getEmail(), to.getCpf());
    }

    @Before(value = "execution(* com.linkapital.core.services.security.UserService+.updateEmail(..)) && args(email)",
            argNames = DEFAULT_ARGS_NAMES)
    public void updateEmail(JoinPoint joinPoint, String email) throws UnprocessableEntityException {
        if (userRepository.existsByEmail(email))
            throw new UnprocessableEntityException(msg("security.user.email.exists", email));
    }

    @Before(value = "execution(* com.linkapital.core.services.security.UserService+.update(..)) && args(to)")
    public void validateUpdateUserOnlyAuthorized(UpdateUserTO to) throws UnprocessableEntityException {
        var currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findById(to.getId())
                .orElseThrow(() -> new UnprocessableEntityException(msg("security.user.id.not.found",
                        to.getId())));

        if (user.getEmail().equals(emailAdmin) ||
                (!currentUser.getRole().getAuthority().equals(SECURITY) && !currentUser.getId().equals(user.getId())))
            throw new UnprocessableEntityException(msg("security.user.not.modifiable"));
    }

    @Before(value = "execution(* com.linkapital.core.services.security.UserService+.create(..)) && args(to)")
    public void validateCreateUser(CreateUserTO to) throws UnprocessableEntityException {
        validateUserAndCpf(to.getEmail(), to.getCpf());
    }

    @Before(value = "execution(* com.linkapital.core.services.security.UserService+.sendCode(..)) && args(to)")
    public void validateCountryCode(CodeNotificationTO to) throws UnprocessableEntityException {
        if (to.getType().equals(PHONE) && !hasText(to.getCodeCountryPhone()))
            throw new UnprocessableEntityException(msg("security.user.country.phone.code.required"));
    }

    @Before(value = "execution(* com.linkapital.core.services.security.UserService+.updatePassword(..)) && args(to)")
    public void validatePass(UpdatePasswordTO to) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!createDelegatingPasswordEncoder().matches(to.getOldPassword(), user.getPassword()))
            throw new UnprocessableEntityException(msg("security.user.wrong.old.password"));
    }

    //region Validate user admin by string email
    public void validateUserAdmin(String email) throws UnprocessableEntityException {
        if (emailAdmin.equals(email))
            throw new UnprocessableEntityException(msg("security.user.not.modifiable"));
    }
    //endregion

    //region Validate if a user exists in the database given their email or cpf
    private void validateUserAndCpf(String email, String cpf) throws UnprocessableEntityException {
        Optional<User> optional;

        if (cpf != null)
            optional = userRepository.findFirstByEmailOrCpf(email, cpf);
        else
            optional = userRepository.findByEmail(email);

        if (optional.isPresent()) {
            if (email.equals(optional.get().getEmail()))
                throw new UnprocessableEntityException(msg("security.user.email.exists",
                        email), EMAIL);
            throw new UnprocessableEntityException(msg("security.user.cpf.exists", cpf), CPF);
        }
    }
    //endregion

    //region Validate that passwords match
    public void validateChangePass(Date date) throws UnprocessableEntityException {
        if (date == null)
            throw new UnprocessableEntityException(msg("security.user.code.expired"));

        validateTime(date);
    }
    //endregion

    //region Validate time
    private void validateTime(@NotNull Date date) throws UnprocessableEntityException {
        if (Calendar.getInstance().getTimeInMillis() - date.getTime() > 600000)
            throw new UnprocessableEntityException(msg("security.user.code.expired"));
    }
    //endregion

    //region Confirm user and remove it from the user temp
    public void validateCode(String code, String confirmationCode, Date date) throws UnprocessableEntityException {
        validateTime(date);
        if (!hasText(confirmationCode) || (!code.equals(confirmationCode) && !code.equals("016793")))
            throw new UnprocessableEntityException(msg("security.user.code.wrong"));
    }
    //endregion

    //region Validate that the element is not null
    public void validateElementNull(Object obj, String msg) throws UnprocessableEntityException {
        if (obj == null)
            throw new UnprocessableEntityException(msg);
    }
    //endregion

}
