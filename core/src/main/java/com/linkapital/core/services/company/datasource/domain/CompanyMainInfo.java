package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.company.contract.enums.RegistrationSituation;
import com.linkapital.core.services.shared.datasource.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMPANY_MAIN_INFO")
@SequenceGenerator(name = "gen_company_main_info", sequenceName = "seq_company_main_info", allocationSize = 1)
public class CompanyMainInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_main_info")
    private Long id;

    @Column(length = 18, unique = true, nullable = false)
    private String cnpj;

    @Column(name = "fantasy_name", columnDefinition = "TEXT", nullable = false)
    private String socialReason;

    @Column(name = "opening_date")
    private LocalDateTime openingDate;

    @Column(name = "registration_situation")
    private RegistrationSituation registrationSituation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Address address;

}
