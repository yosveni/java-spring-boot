package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_COMPANY_EMPLOYEE")
@SequenceGenerator(name = "gen_company_employee", sequenceName = "seq_company_employee", allocationSize = 1)
public class CompanyEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_company_employee")
    private Long id;

    private String cpf;

    private String name;

    @Column(name = "resignation_year")
    private String resignationYear;

    @Column(name = "admission_date")
    private LocalDateTime admissionDate;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    public CompanyEmployee withCpf(String cpf) {
        setCpf(cpf);
        return this;
    }

    public CompanyEmployee withName(String name) {
        setName(name);
        return this;
    }

    public CompanyEmployee withResignationYear(String resignationYear) {
        setResignationYear(resignationYear);
        return this;
    }

    public CompanyEmployee withAdmissionDate(LocalDateTime admissionDate) {
        setAdmissionDate(admissionDate);
        return this;
    }

    public CompanyEmployee withBirthDate(LocalDateTime birthDate) {
        setBirthDate(birthDate);
        return this;
    }

}
