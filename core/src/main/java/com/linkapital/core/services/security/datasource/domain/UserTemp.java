package com.linkapital.core.services.security.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import java.util.Date;

import static java.util.Locale.ROOT;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_USER_TEMP")
@SequenceGenerator(name = "gen_tab_user_temp", sequenceName = "seq_tab_user_temp", allocationSize = 1)
public class UserTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_tab_user_temp")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "code_country_phone")
    private String codeCountryPhone;

    private String phone;

    @Column(name = "complete_phone")
    private String completePhone;

    @Column(length = 6, nullable = false)
    private String codeConfirmation;

    @Column(name = "linkeding_contact")
    private String linkedingContact;

    @Column(length = 11)
    private String cpf;

    @Column(nullable = false)
    private boolean partner;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public String getEmail() {
        return this.email != null ? this.email.toLowerCase(ROOT) : null;
    }

    public UserTemp withCodeConfirmation(String codeConfirmation) {
        setCodeConfirmation(codeConfirmation);
        return this;
    }

}
