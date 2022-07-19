package com.linkapital.core.services.security.datasource;

import com.linkapital.core.services.security.datasource.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByCode(String code);

}
