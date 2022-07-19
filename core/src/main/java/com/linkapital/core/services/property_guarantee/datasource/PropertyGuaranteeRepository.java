package com.linkapital.core.services.property_guarantee.datasource;

import com.linkapital.core.services.property_guarantee.datasource.domain.PropertyGuarantee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyGuaranteeRepository extends JpaRepository<PropertyGuarantee, Long> {

}
