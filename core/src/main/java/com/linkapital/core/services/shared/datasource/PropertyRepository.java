package com.linkapital.core.services.shared.datasource;

import com.linkapital.core.services.shared.datasource.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    boolean existsByAddress_Id(long id);

}
