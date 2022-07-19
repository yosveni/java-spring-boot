package com.linkapital.core.services.company.datasource;

import com.linkapital.core.services.company.datasource.domain.Cnae;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyCnaeRepository extends JpaRepository<Cnae, Long> {

    Optional<Cnae> findByCode(String code);

    List<Cnae> findAllByDescription(String description);

}
