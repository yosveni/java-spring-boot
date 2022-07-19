package com.linkapital.core.services.protest.datasource.domain;

import com.linkapital.core.services.protest.contract.emuns.ProtestArea;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_PROTEST")
@SequenceGenerator(name = "gen_protest", sequenceName = "seq_protest", allocationSize = 1)
public class Protest {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_protest")
    private Long id;

    private String key;

    @Column(name = "presenter_name")
    private String presenterName;

    @Column(name = "assignor_name")
    private String assignorName;

    private double value;

    @Column(name = "has_consent")
    private boolean hasConsent;

    @Column(name = "consult_date")
    private LocalDate consultDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(nullable = false)
    private ProtestArea area;

    public Protest withKey(String key) {
        setKey(key);
        return this;
    }

    public Protest withPresenterName(String presenterName) {
        setPresenterName(presenterName);
        return this;
    }

    public Protest withAssignorName(String assignorName) {
        setAssignorName(assignorName);
        return this;
    }

    public Protest withValue(double value) {
        setValue(value);
        return this;
    }

    public Protest withHasConsent(boolean hasConsent) {
        setHasConsent(hasConsent);
        return this;
    }

    public Protest withConsultDate(LocalDate consultDate) {
        setConsultDate(consultDate);
        return this;
    }

    public Protest withDueDate(LocalDate dueDate) {
        setDueDate(dueDate);
        return this;
    }

    public Protest withArea(ProtestArea protestArea) {
        setArea(protestArea);
        return this;
    }

}
