package com.linkapital.core.services.whatsapp.contract;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.configuration.datasource.domain.SysConfiguration;
import com.linkapital.core.services.whatsapp.contract.to.CreateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.DefaultMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateMessageWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UpdateUserWhatsAppTO;
import com.linkapital.core.services.whatsapp.contract.to.UserWhatsAppTO;
import com.linkapital.core.services.whatsapp.datasource.domain.UserWhatsApp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.linkapital.core.util.json.JsonSerdes.jsonfy;
import static com.linkapital.core.util.json.JsonSerdes.parse;

@Mapper
public interface WhatsAppBinder {

    WhatsAppBinder WHATS_APP_BINDER = Mappers.getMapper(WhatsAppBinder.class);

    Function<SysConfiguration, DefaultMessageWhatsAppTO> parse = source ->
            parse(jsonfy(source.getConfiguration()), DefaultMessageWhatsAppTO.class);

    BiFunction<SysConfiguration, UpdateMessageWhatsAppTO, SysConfiguration> bind = (target, source) -> {
        var defaultMessage = parse.apply(target);
        defaultMessage.setMessage(source.getMessage());
        target.setConfiguration(jsonfy(defaultMessage));

        return target;
    };

    UnaryOperator<List<UserWhatsApp>> filterFewerCalls = source -> source
            .stream()
            .filter(userWhatsApp -> userWhatsApp.getTimesCalled() < source.get(0).getTimesCalled())
            .toList();

    static UserWhatsApp random(List<UserWhatsApp> userWhatsAppEntities) throws UnprocessableEntityException {
        var num = 0;

        try {
            num = SecureRandom.getInstanceStrong().nextInt(userWhatsAppEntities.size());
        } catch (NoSuchAlgorithmException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return userWhatsAppEntities.get(num);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "timesCalled", ignore = true)
    UserWhatsApp bind(CreateUserWhatsAppTO createUserWhatsAppTO);

    UserWhatsAppTO bind(UserWhatsApp userWhatsApp);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "timesCalled", ignore = true)
    UserWhatsApp set(@MappingTarget UserWhatsApp target, UpdateUserWhatsAppTO source);

    List<UserWhatsAppTO> bind(List<UserWhatsApp> source);

}
