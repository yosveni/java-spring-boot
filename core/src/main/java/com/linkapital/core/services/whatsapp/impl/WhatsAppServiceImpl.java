package com.linkapital.core.services.whatsapp.impl;

import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.configuration.ConfigurationService;
import com.linkapital.core.services.whatsapp.WhatsAppService;
import com.linkapital.core.services.whatsapp.contract.to.CreateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.DefaultMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UserWhatsAppTO;
import com.linkapital.core.services.whatsapp.datasource.UserWhatsAppRepository;
import com.linkapital.core.services.whatsapp.datasource.domain.UserWhatsApp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.whatsapp.contract.WhatsAppBinder.WHATS_APP_BINDER;
import static com.linkapital.core.services.whatsapp.contract.WhatsAppBinder.bind;
import static com.linkapital.core.services.whatsapp.contract.WhatsAppBinder.filterFewerCalls;
import static com.linkapital.core.services.whatsapp.contract.WhatsAppBinder.parse;
import static com.linkapital.core.services.whatsapp.contract.WhatsAppBinder.random;

@Service
@Transactional
public class WhatsAppServiceImpl implements WhatsAppService {

    private static final String KEY_MESSAGE_CONFIGURATION = "WhatsApp";
    private final UserWhatsAppRepository userWhatsAppRepository;
    private final ConfigurationService configurationService;

    public WhatsAppServiceImpl(UserWhatsAppRepository userWhatsAppRepository, ConfigurationService configurationService) {
        this.userWhatsAppRepository = userWhatsAppRepository;
        this.configurationService = configurationService;
    }

    @Override
    public UserWhatsAppTO save(CreateUserWhatsAppTO to) throws UnprocessableEntityException {
        if (userWhatsAppRepository.existsByNumber(to.getNumber()))
            throw new UnprocessableEntityException(msg("whatsapp.user.number.found"));

        var userWhatsApp = save(WHATS_APP_BINDER.bind(to));

        return WHATS_APP_BINDER.bind(userWhatsApp);
    }

    @Override
    public UserWhatsAppTO update(UpdateUserWhatsAppTO to) throws ResourceNotFoundException, UnprocessableEntityException {
        var optional = userWhatsAppRepository.findByNumber(to.getNumber());

        if (optional.isPresent() && !optional.get().getId().equals(to.getId()))
            throw new UnprocessableEntityException(msg("whatsapp.user.with.number.found", to.getNumber()));

        return WHATS_APP_BINDER.bind(save(WHATS_APP_BINDER.set(getById(to.getId()), to)));
    }

    @Override
    public DefaultMessageWhatsAppTO updateDefaultMessage(UpdateMessageWhatsAppTO to) throws NotFoundSystemConfigurationException {
        var sysConfiguration = configurationService.getByName(KEY_MESSAGE_CONFIGURATION);
        configurationService.save(bind.apply(sysConfiguration, to));

        return new DefaultMessageWhatsAppTO()
                .withMessage(to.getMessage());
    }

    @Override
    public List<UserWhatsAppTO> getAll() {
        return WHATS_APP_BINDER.bind(userWhatsAppRepository.findAll());
    }

    @Override
    public UserWhatsAppTO getRandomUser() throws UnprocessableEntityException {
        var contacts = userWhatsAppRepository.findAllByEnabledIsTrueOrderByTimesCalledDesc();

        if (contacts.isEmpty())
            return null;

        var filter = filterFewerCalls.apply(contacts);
        var userWhatsApp = random(filter.isEmpty() ? contacts : filter);
        userWhatsApp.setTimesCalled(userWhatsApp.getTimesCalled() + 1);

        return WHATS_APP_BINDER.bind(save(userWhatsApp));
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional
                .of(getById(id))
                .ifPresent(userWhatsAppRepository::delete);
    }

    @Override
    public UserWhatsApp getById(Long id) throws ResourceNotFoundException {
        return userWhatsAppRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(msg("whatsapp.user.id.not.found", id)));
    }

    @Override
    public DefaultMessageWhatsAppTO getDefaultMessage() throws NotFoundSystemConfigurationException {
        return parse.apply(configurationService.getByName(KEY_MESSAGE_CONFIGURATION));
    }

    //region Save a whatsapp user
    private UserWhatsApp save(UserWhatsApp userWhatsApp) {
        return userWhatsAppRepository.save(userWhatsApp);
    }
    //endregion

}
