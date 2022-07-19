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
@Entity(name = "TAB_AUTHORIZATION_QUESTION")
@SequenceGenerator(name = "gen_authorization_question", sequenceName = "seq_authorization_question", allocationSize = 1)
public class AuthorizationQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_authorization_question")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(name = "detail_title", columnDefinition = "TEXT")
    private String detailTitle;

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
