package com.linkapital.core.services.security.validator;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.security.contract.to.update.UpdateRoleTO;
import com.linkapital.core.services.security.datasource.RoleRepository;
import com.linkapital.core.services.security.datasource.domain.Role;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_SEC;

@Aspect
@Component
public class RoleAspectValidator {

    private static final String ARGS_UPDATE = "joinPoint, to";
    private static final String ARGS_DELETE = "joinPoint, id";
    private final RoleRepository roleRepository;

    public RoleAspectValidator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Before(value = "execution(* com.linkapital.core.services.security.RoleService+.update(..)) && args(to)",
            argNames = ARGS_UPDATE)
    public void beforeUpdate(JoinPoint joinPoint, UpdateRoleTO to) throws UnprocessableEntityException {
        var code = to.getCode();
        var name = to.getName();

        if (code.equals(LKP_SEC.name()))
            throw new UnprocessableEntityException(msg("security.role.not.modifiable"));

        var target = roleRepository.findAll();

        for (Role role : target) {
            if (role.getId().equals(to.getId()))
                continue;

            var lowerCode = role.getCode().toLowerCase();
            var lowerName = role.getName().toLowerCase();

            if (code.toLowerCase().equals(lowerCode))
                throw new UnprocessableEntityException(msg("security.role.code.exists", code));
            else if (name.toLowerCase().equals(lowerName))
                throw new UnprocessableEntityException(msg("security.role.name.exists", name));
        }

    }

    @Before(value = "execution(* com.linkapital.core.services.security.RoleService+.delete(..)) && args(id)",
            argNames = ARGS_DELETE)
    public void beforeDelete(JoinPoint joinPoint, long id) throws UnprocessableEntityException {
        var role = roleRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("security.role.id.not.found", id)));

        if (role.getCode().equals(LKP_SEC.name()))
            throw new UnprocessableEntityException(msg("security.role.not.modifiable"));
    }

}
