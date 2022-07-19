package com.linkapital.core.services.security.datasource;

import com.linkapital.core.services.security.datasource.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    Optional<User> findFirstByEmailOrCpf(String email, String cpf);

    List<User> findAllByEnabledTrue();

    @Query(value = "select * from tab_user tu join tab_identification ti on ti.id = tu.identification_id where ti.state = 1", nativeQuery = true)
    List<User> findAllByIdentificationFailed();

}
