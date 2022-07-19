package com.linkapital.core.services.security;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.contract.to.create.CreateRoleTO;
import com.linkapital.core.services.security.contract.to.update.UpdateRoleTO;
import com.linkapital.core.services.security.datasource.domain.Role;

import java.util.List;

/**
 * Default interface for {@link RoleService}
 * Has the responsibility to make operations over the {@link Role} entity
 *
 * @since 0.0.1
 */
public interface RoleService {

    /**
     * Save the given role entity in the database
     *
     * @param to {@link CreateRoleTO} the given role to be register
     * @return {@link RoleTO}
     */
    RoleTO create(CreateRoleTO to);

    /**
     * Update the role
     *
     * @param to {@link UpdateRoleTO} the given role to be update
     * @return {@link RoleTO}
     * @throws UnprocessableEntityException if the role is not found
     */
    RoleTO update(UpdateRoleTO to) throws UnprocessableEntityException;

    /**
     * Get role by code
     *
     * @param code {@link String} the code name
     * @return {@link Role}
     */
    Role getByCode(String code);

    /**
     * Get role by id
     *
     * @param id {@link Long} the role id
     * @return {@link Role}
     * @throws UnprocessableEntityException if the role is not found
     */
    Role getById(Long id) throws UnprocessableEntityException;

    /**
     * Get all roles
     *
     * @return {@link List}<{@link RoleTO}>
     */
    List<RoleTO> getAll();

    /**
     * Delete a role entity by the given id
     *
     * @param id {@link Long} the id of the role to be delete
     * @throws UnprocessableEntityException if the role is not found
     */
    void delete(Long id) throws UnprocessableEntityException;

}
