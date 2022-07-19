package com.linkapital.core.services.configuration.contract;

import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.services.configuration.datasource.domain.SysConfiguration;
import com.linkapital.core.util.generic.GenericFilterTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.linkapital.core.util.json.JsonSerdes.convert;

@Mapper
public interface SysConfigurationBinder {

    SysConfigurationBinder CONFIGURATION_BINDER = Mappers.getMapper(SysConfigurationBinder.class);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    SysConfiguration bind(SysConfigurationTO source);

    SysConfigurationTO bind(SysConfiguration source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    SysConfiguration set(@MappingTarget SysConfiguration target, SysConfigurationTO source);

    default GenericFilterTO<SysConfigurationTO> bind(List<SysConfigurationTO> elements) {
        GenericFilterTO<SysConfigurationTO> filter = new GenericFilterTO<>();
        filter.setElements(elements);
        filter.setTotal(elements.size());
        return filter;
    }

    default Object bindTransform(SysConfiguration source, Class target) {
        return convert(source.getConfiguration(), target);
    }

}
