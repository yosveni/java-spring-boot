package com.linkapital.core.services.shared.datasource;

import com.linkapital.core.services.shared.datasource.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
