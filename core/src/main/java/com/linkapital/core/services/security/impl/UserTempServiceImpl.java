package com.linkapital.core.services.security.impl;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.notification.EmailService;
import com.linkapital.core.services.notification.NotificationService;
import com.linkapital.core.services.security.RoleService;
import com.linkapital.core.services.security.UserTempService;
import com.linkapital.core.services.security.contract.to.CodeNotificationTO;
import com.linkapital.core.services.security.contract.to.ConfirmationCodeTO;
import com.linkapital.core.services.security.contract.to.RegisterUserTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import com.linkapital.core.services.security.contract.to.UserTempTO;
import com.linkapital.core.services.security.datasource.UserRepository;
import com.linkapital.core.services.security.datasource.UserTempRepository;
import com.linkapital.core.services.security.datasource.domain.UserTemp;
import com.linkapital.core.services.security.jwt.TokenProvider;
import com.linkapital.core.services.security.validator.UserAspectValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.notification.contract.enums.EmailType.NORMAL;
import static com.linkapital.core.services.security.contract.UserBinder.USER_BINDER;
import static com.linkapital.core.services.security.contract.UserTempBinder.USER_TEMP_BINDER;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_AGENT;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_CLIENT;
import static com.linkapital.core.util.enums.FieldErrorResponse.ERROR;
import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Service
@Transactional
public class UserTempServiceImpl implements UserTempService {

    private final UserTempRepository userTempRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final TokenProvider jwtTokenProvider;
    private final UserAspectValidator userAspectValidator;

    public UserTempServiceImpl(UserTempRepository userTempRepository,
                               UserRepository userRepository, RoleService roleService,
                               EmailService emailService,
                               NotificationService notificationService,
                               TokenProvider jwtTokenProvider,
                               UserAspectValidator userAspectValidator) {
        this.userTempRepository = userTempRepository;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.notificationService = notificationService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userAspectValidator = userAspectValidator;
    }

    @Override
    public UserTempTO register(RegisterUserTO to) throws UnprocessableEntityException {
        var userTempNew = USER_BINDER.bind(to);

        var userTempDatabase = userTempRepository
                .findByEmail(userTempNew.getEmail())
                .orElse(null);
        userTempNew.setPassword(createDelegatingPasswordEncoder().encode(userTempNew.getPassword()));
        userTempNew.setCodeConfirmation(format("%06d", new Random().nextInt(1000000)));

        var userTemp = userTempDatabase != null
                ? userTempRepository.save(USER_BINDER.set(userTempDatabase, userTempNew))
                : userTempRepository.save(userTempNew);

        emailService.send(NORMAL, userTemp.getEmail(),
                msg("notification.email.registration.code.subject"),
                msg("notification.email.registration.code.body", userTemp.getCodeConfirmation()));

        return USER_TEMP_BINDER.bind(userTemp);
    }

    @Override
    public UserAuthenticatedTO registerConfirmationCode(ConfirmationCodeTO to) throws UnprocessableEntityException {
        var userTemp = getByEmail(to.getEmail());
        userAspectValidator.validateCode(to.getCode(), userTemp.getCodeConfirmation(), userTemp.getModified());

        return confirmSave(userTemp);
    }

    @Override
    public List<UserTempTO> getAll() {
        return USER_TEMP_BINDER.bind(userTempRepository.findAll());
    }

    @Override
    public void delete(long id) throws UnprocessableEntityException {
        userTempRepository.delete(getById(id));
    }

    @Override
    public UserTempTO sendCode(CodeNotificationTO to) throws UnprocessableEntityException {
        var userTemp = getByEmail(to.getEmail());
        return USER_TEMP_BINDER.bind(userTempRepository.save(userTemp
                .withCodeConfirmation(notificationService.sendAndGetCode(userTemp.getEmail(), userTemp.getPhone(),
                        to.getType()))));
    }

    //region Confirm user and remove it from the user temp
    private UserAuthenticatedTO confirmSave(UserTemp userTemp) {
        var user = USER_BINDER.bindUser(userTemp);
        user.setRole(userTemp.isPartner()
                ? roleService.getByCode(LKP_AGENT.toString())
                : roleService.getByCode(LKP_CLIENT.toString()));

        userTempRepository.delete(userTemp);

        var token = jwtTokenProvider.generateToken(randomUUID().toString(), user.getEmail(),
                user.getAuthoritiesValues(), false);
        var refreshToken = jwtTokenProvider.generateToken(randomUUID().toString(), user.getEmail(),
                user.getAuthoritiesValues(), true);

        return USER_BINDER.bindAuthenticatedUser(userRepository.save(user))
                .withToken(token)
                .withRefreshToken(refreshToken);
    }
    //endregion

    //region Get user temp by id
    private UserTemp getById(long id) throws UnprocessableEntityException {
        return userTempRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("security.user.id.not.found", id)));
    }
    //endregion

    //region Get user temp by email
    private UserTemp getByEmail(String email) throws UnprocessableEntityException {
        return userTempRepository
                .findByEmail(email)
                .orElseThrow(() -> new UnprocessableEntityException(msg("security.user.email.not.found", email),
                        ERROR));
    }
    //endregion

}
