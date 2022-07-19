package com.linkapital.core.services.authorization.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_AUTHORIZATION_ANSWER")
@SequenceGenerator(name = "gen_authorization_answer", sequenceName = "seq_authorization_answer", allocationSize = 1)
public class AuthorizationAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_authorization_answer")
    private Long id;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "yes_answer")
    private boolean yesAnswer;

    @ManyToOne
    @JoinColumn(name = "authorization_question_id", nullable = false)
    private AuthorizationQuestion question;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public AuthorizationAnswer withQuestion(AuthorizationQuestion question) {
        this.question = question;
        return this;
    }

    public AuthorizationAnswer withYesAnswer(boolean yesAnswer) {
        this.yesAnswer = yesAnswer;
        return this;
    }

}
