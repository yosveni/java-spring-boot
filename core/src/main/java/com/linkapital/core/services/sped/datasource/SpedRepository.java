package com.linkapital.core.services.sped.datasource;

import com.linkapital.core.services.sped.datasource.domain.Sped;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpedRepository extends JpaRepository<Sped, Long> {

}
