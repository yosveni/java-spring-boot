package com.linkapital.core.services.partner_bank.datasource;

import com.linkapital.core.services.partner_bank.datasource.domain.PartnerBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerBankRepository extends JpaRepository<PartnerBank, Long> {

    boolean existsById(Long id);

    boolean existsByIdIsNotAndName(Long id, String name);

    boolean existsByName(String name);

    List<PartnerBank> findAllByIdIn(List<Long> ids);

}
