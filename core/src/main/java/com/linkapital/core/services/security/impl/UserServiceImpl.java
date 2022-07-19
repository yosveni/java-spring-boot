package com.linkapital.core.services.security.impl;


import com.linkapital.core.exceptions.InvalidRefreshTokenException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.identification.contract.to.IdentificationTO;
import com.linkapital.core.services.notification.NotificationService;
import com.linkapital.core.services.notification.WebsocketService;
import com.linkapital.core.services.security.RoleService;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.UserTempService;
import com.linkapital.core.services.security.contract.enums.Authority;
import com.linkapital.core.services.security.contract.enums.Intensity;
import com.linkapital.core.services.security.contract.to.AuthenticationRequestTO;
import com.linkapital.core.services.security.contract.to.CodeNotificationTO;
import com.linkapital.core.services.security.contract.to.ConfirmationCodeTO;
import com.linkapital.core.services.security.contract.to.RefreshTokenTO;
import com.linkapital.core.services.security.contract.to.ResetPasswordTO;
import com.linkapital.core.services.security.contract.to.UserActiveTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import com.linkapital.core.services.security.contract.to.UserIdentificationTO;
import com.linkapital.core.services.security.contract.to.UserTO;
import com.linkapital.core.services.security.contract.to.create.CreateUserTO;
import com.linkapital.core.services.security.contract.to.update.UpdatePasswordTO;
import com.linkapital.core.services.security.contract.to.update.UpdateUserTO;
import com.linkapital.core.services.security.datasource.UserRepository;
import com.linkapital.core.services.security.datasource.domain.User;
import com.linkapital.core.services.security.jwt.TokenProvider;
import com.linkapital.core.services.security.validator.UserAspectValidator;
import lombok.NonNull;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.identification.contract.IdentificationBinder.IDENTIFICATION_BINDER;
import static com.linkapital.core.services.identification.contract.enums.IdentificationState.FACE;
import static com.linkapital.core.services.notification.WebsocketService.AUTHORIZATION_STATUS_CHANGED;
import static com.linkapital.core.services.notification.contract.enums.WebsocketBroker.TOPIC;
import static com.linkapital.core.services.security.contract.UserBinder.USER_BINDER;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_AGENT;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_CLIENT;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_EMP;
import static com.linkapital.core.services.security.contract.enums.Intensity.ENTREPRENEUR;
import static com.linkapital.core.services.security.contract.enums.Intensity.PARTNER;
import static com.linkapital.core.services.security.contract.enums.NotificationProcessType.CHANGE_PASSWORD;
import static com.linkapital.core.services.shared.contract.AddressBinder.ADDRESS_BINDER;
import static com.linkapital.core.util.enums.FieldErrorResponse.EMAIL;
import static java.util.UUID.randomUUID;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenProvider;
    private final UserAspectValidator userAspectValidator;
    private final UserTempService userTempService;
    private final NotificationService notificationService;
    private final WebsocketService webSocketService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           AuthenticationManager authenticationManager,
                           TokenProvider jwtTokenProvider,
                           UserAspectValidator userAspectValidator,
                           UserTempService userTempService,
                           NotificationService notificationService,
                           WebsocketService webSocketService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userAspectValidator = userAspectValidator;
        this.userTempService = userTempService;
        this.notificationService = notificationService;
        this.webSocketService = webSocketService;
    }

    @Override
    public UserAuthenticatedTO login(@NonNull AuthenticationRequestTO to) throws UnprocessableEntityException {
        User user;
        try {
            user = (User) authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(to.getEmail(), to.getPassword()))
                    .getPrincipal();
        } catch (AuthenticationException e) {
            throw new UnprocessableEntityException(
                    e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException
                            ? msg("security.user.authentication.failed")
                            : e.getMessage());
        }

        var tokenData = generateTokenAndRefreshToken(user);

        return USER_BINDER.bindAuthenticatedUser(user)
                .withToken(tokenData.getToken())
                .withRefreshToken(tokenData.getRefreshToken());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserTO create(CreateUserTO to) throws UnprocessableEntityException {
        var user = USER_BINDER.bind(to);

        if (user.getRole() == null)
            user.setRole(roleService.getByCode(LKP_CLIENT.toString()));
        else
            user.setRole(roleService.getById(user.getRole().getId()));

        user.setPassword(createDelegatingPasswordEncoder().encode(new RandomString(10).nextString()));

        return USER_BINDER.bind(save(user));
    }

    @Override
    public UserTO update(@NonNull UpdateUserTO to) throws UnprocessableEntityException {
        var user = getById(to.getId());
        userAspectValidator.validateUserAdmin(user.getEmail());
        user = USER_BINDER.set(to, user);
        user.setAddress(ADDRESS_BINDER.bind(to.getAddress()));

        if (user.getRole() == null)
            user.setRole(roleService.getByCode(LKP_CLIENT.toString()));
        else
            user.setRole(roleService.getById(to.getRole().getId()));

        return USER_BINDER.bind(save(user));
    }

    @Override
    public UserAuthenticatedTO updateEmail(@NonNull String email) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userRepository.saveAndFlush(user.withEmail(email.toLowerCase()));
        var tokenData = generateTokenAndRefreshToken(user);

        return USER_BINDER.bindAuthenticatedUser(user)
                .withToken(tokenData.getToken())
                .withRefreshToken(tokenData.getRefreshToken());
    }

    @Override
    public UserTO enable(long id) throws UnprocessableEntityException {
        var user = getById(id);
        userAspectValidator.validateUserAdmin(user.getEmail());
        user.setEnabled(!user.isEnabled());

        return USER_BINDER.bind(save(user));
    }

    @Override
    public UserTO sendCode(@NonNull CodeNotificationTO to) throws UnprocessableEntityException {
        var processType = to.getProcessType();
        var user = processType != null && processType.equals(CHANGE_PASSWORD)
                ? getByEmail(to.getEmail())
                : getUserAuthenticated();
        var code = notificationService.sendAndGetCode(to.getEmail(), to.getPhone(), to.getType());
        user = save(user.withCodeConfirmation(code));

        return USER_BINDER.bind(user);
    }

    @Override
    public UserAuthenticatedTO confirmationCode(@NonNull ConfirmationCodeTO to) throws UnprocessableEntityException {
        User user;
        switch (to.getType()) {
            case REGISTER:
                return userTempService.registerConfirmationCode(to);
            case CHANGE_PASSWORD:
                user = getByEmail(to.getEmail());
                userAspectValidator.validateCode(to.getCode(), user.getCodeConfirmation(), user.getModified());
                user.setInitChangePassword(new Date());
                break;
            default:
                user = getUserAuthenticated();
                userAspectValidator.validateCode(to.getCode(), user.getCodeConfirmation(), user.getModified());
                break;
        }

        return USER_BINDER.bindAuthenticatedUser(save(user));
    }

    @Override
    public void resetPassword(@NonNull ResetPasswordTO to) throws UnprocessableEntityException {
        var user = getByEmail(to.getEmail());
        userAspectValidator.validateChangePass(user.getInitChangePassword());
        user.setPassword(createDelegatingPasswordEncoder().encode(to.getPassword()));
        save(user);
    }

    @Override
    public void resetPasswordFromAdmin(@NonNull ResetPasswordTO to) throws UnprocessableEntityException {
        Optional
                .of(getByEmail(to.getEmail()))
                .ifPresent(user -> {
                    user.setPassword(createDelegatingPasswordEncoder().encode(to.getPassword()));
                    save(user);
                });
    }

    @Override
    public UserTO updatePassword(@NonNull UpdatePasswordTO to) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(createDelegatingPasswordEncoder().encode(to.getNewPassword()));

        return USER_BINDER.bind(save(user));
    }

    @Override
    public IdentificationTO getIdentificationByUser(long id) throws UnprocessableEntityException {
        return Optional
                .of(getById(id))
                .map(user -> IDENTIFICATION_BINDER.bind(user.getIdentification()))
                .orElse(null);
    }

    @Override
    public IdentificationTO acceptIdentification(long id) throws UnprocessableEntityException {
        var user = getById(id);
        userAspectValidator.validateElementNull(user.getIdentification(), msg("identification.not.found"));
        user = updateUserIdentificationByIntensity(user);

        webSocketService.dispatch(
                user.getEmail(),
                TOPIC,
                AUTHORIZATION_STATUS_CHANGED,
                msg("identification.completed"),
                null
        );

        return IDENTIFICATION_BINDER.bind(user.getIdentification());
    }

    @Override
    public User getByEmail(String email) throws UnprocessableEntityException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UnprocessableEntityException(msg("security.user.email.not.found", email),
                        EMAIL));
    }

    @Override
    public User getById(long id) throws UnprocessableEntityException {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("security.user.id.not.found", id)));
    }

    @Override
    public UserTO getProfile() throws UnprocessableEntityException {
        return Optional
                .of(getById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()))
                .map(USER_BINDER::bind)
                .orElse(null);
    }

    @Override
    public List<UserTO> getAll() {
        return USER_BINDER.bindToListTO(userRepository.findAll());
    }

    @Override
    public List<UserActiveTO> getAllActive() {
        return USER_BINDER.bindToUserActiveListTO(userRepository.findAllByEnabledTrue());
    }

    @Override
    public List<UserIdentificationTO> getAllWithIdentificationFailed() {
        return USER_BINDER.bindToIdentificationListTO(userRepository.findAllByIdentificationFailed());
    }

    @Override
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public void delete(long id) throws UnprocessableEntityException {
        var user = getById(id);
        userAspectValidator.validateUserAdmin(user.getEmail());
        userRepository.delete(user);
    }

    @Override
    public void deleteCompaniesRelatedToUser(String email) throws UnprocessableEntityException {
        Optional
                .ofNullable(getByEmail(email))
                .ifPresent(user -> {
                    var companiesUser = user.getCompanies();
                    companiesUser.forEach(companyUser -> {
                        if (companyUser.getOwnerAuthorization() != null)
                            companyUser.setOwnerAuthorization(null);
                    });
                    user.getCompanies().removeAll(companiesUser);
                    save(user);
                });
    }

    @Override
    public RefreshTokenTO refreshToken(@NonNull RefreshTokenTO to) throws InvalidRefreshTokenException {
        var token = jwtTokenProvider.refreshToken(to.getRefreshToken());

        return new RefreshTokenTO()
                .withToken(token.get("token"))
                .withRefreshToken(token.get("refreshToken"));
    }

    @Override
    public void enableRateForAll() {
        var users = userRepository.findAll();
        users.forEach(user -> user.setHasRating(false));
        userRepository.saveAll(users);
    }

    @Override
    public UserTO updateIntensity(Intensity intensity) throws UnprocessableEntityException {
        return Optional
                .of(getById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()))
                .map(user -> USER_BINDER.bind(save(user.withIntensity(intensity))))
                .orElse(null);
    }

    @Override
    public User updateUserIdentificationByIntensity(@NonNull User user) {
        var intensity = user.getIntensity();
        user.getIdentification().setState(FACE);

        if (intensity == null)
            return user;

        if (intensity.equals(PARTNER))
            user.setRole(roleService.getByCode(LKP_AGENT.toString()));
        else if (intensity.equals(ENTREPRENEUR))
            user.setRole(roleService.getByCode(LKP_EMP.toString()));

        return save(user);
    }

    @Override
    public Authority getAuthority() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getRole().getAuthority();
    }

    @Override
    public UserTO uploadImage(@NonNull MultipartFile file) throws UnprocessableEntityException {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        byte[] image;

        try {
            image = file.getBytes();
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return USER_BINDER.bind(save(user.withImage(image)));
    }

    private RefreshTokenTO generateTokenAndRefreshToken(@NonNull User user) {
        var email = user.getEmail();
        var token = jwtTokenProvider.generateToken(randomUUID().toString(), email,
                user.getAuthoritiesValues(), false);
        var refreshToken = jwtTokenProvider.generateToken(randomUUID().toString(), email,
                user.getAuthoritiesValues(), true);

        return new RefreshTokenTO()
                .withToken(token)
                .withRefreshToken(refreshToken);
    }

    private User getUserAuthenticated() throws UnprocessableEntityException {
        var authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authentication.equals("anonymousUser"))
            return getById(((User) authentication).getId());
        else
            throw new UnprocessableEntityException(msg("security.user.not.authenticated"));
    }

}
