package com.linkapital.core.services.authorization.datasource.domain;

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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_AUTHORIZATION_TRACE")
@SequenceGenerator(name = "gen_authorization_trace", sequenceName = "seq_authorization_trace", allocationSize = 1)
public class AuthorizationTrace {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_authorization_trace")
    private Long id;

    @Column(nullable = false)
    private String ip;

    private String city;

    @Column(name = "country_name")
    private String countryName;

    private String cep;

    private String navigator;

    private String device;

    private Double latitude;

    private Double longitude;

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

}
