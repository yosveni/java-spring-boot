package com.linkapital.core.services.security.contract;

import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.contract.to.create.CreateRoleTO;
import com.linkapital.core.services.security.contract.to.update.UpdateRoleTO;
import com.linkapital.core.services.security.datasource.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper
public interface RoleBinder {

    RoleBinder ROLE_BINDER = Mappers.getMapper(RoleBinder.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role bind(CreateRoleTO source);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role bind(UpdateRoleTO source);

    RoleTO bind(Role source);

    List<RoleTO> bind(List<Role> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    Role set(@MappingTarget Role target, Role source);

    default Role update(Role target, Role source) {
        var set = set(target, source);
        set.setCreated(target.getCreated());
        set.setModified(new Date());

        return set;
    }

}
