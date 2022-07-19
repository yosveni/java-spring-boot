package com.linkapital.core.services.whatsapp.datasource;

import com.linkapital.core.services.whatsapp.datasource.domain.UserWhatsApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserWhatsAppRepository extends JpaRepository<UserWhatsApp, Long> {

    boolean existsByNumber(String number);

    Optional<UserWhatsApp> findByNumber(String number);

    List<UserWhatsApp> findAllByEnabledIsTrueOrderByTimesCalledDesc();

}
