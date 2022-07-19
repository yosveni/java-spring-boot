package com.linkapital.core.services.security.impl;

import com.linkapital.core.events.user.UserEventsPublisher;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.security.RoleService;
import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.contract.to.create.CreateRoleTO;
import com.linkapital.core.services.security.contract.to.update.UpdateRoleTO;
import com.linkapital.core.services.security.datasource.RoleRepository;
import com.linkapital.core.services.security.datasource.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;
import static com.linkapital.core.services.security.contract.RoleBinder.ROLE_BINDER;
import static com.linkapital.core.services.security.contract.enums.Code.LKP_CLIENT;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserEventsPublisher userEventsPublisher;

    public RoleServiceImpl(RoleRepository roleRepository,
                           UserEventsPublisher userEventsPublisher) {
        this.roleRepository = roleRepository;
        this.userEventsPublisher = userEventsPublisher;
    }

    @Override
    public RoleTO create(CreateRoleTO to) {
        var role = ROLE_BINDER.bind(to);
        return ROLE_BINDER.bind(roleRepository.save(role));
    }

    @Override
    public RoleTO update(UpdateRoleTO to) throws UnprocessableEntityException {
        return Optional
                .of(getById(to.getId()))
                .map(role -> {
                    role = ROLE_BINDER.update(role, ROLE_BINDER.bind(to));
                    return ROLE_BINDER.bind(roleRepository.save(role));
                })
                .orElse(null);
    }

    @Override
    public Role getByCode(String code) {
        return roleRepository.findByCode(code);
    }

    @Override
    public Role getById(Long id) throws UnprocessableEntityException {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new UnprocessableEntityException(msg("security.role.id.not.found", id)));
    }

    @Override
    public List<RoleTO> getAll() {
        return ROLE_BINDER.bind(roleRepository.findAll());
    }

    @Override
    public void delete(Long id) throws UnprocessableEntityException {
        Optional
                .of(getById(id))
                .ifPresent(role -> {
                    role.getUsers().forEach(user -> user.setRole(getByCode(LKP_CLIENT.name())));
                    userEventsPublisher.saveAll(role.getUsers());
                    roleRepository.deleteById(id);
                });
    }

}