package com.linkapital.core.services.comment.datasource.domain;

import com.linkapital.core.services.comment.contract.enums.CommentArea;
import com.linkapital.core.services.comment.contract.enums.LearningSession;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.offer.datasource.domain.Offer;
import com.linkapital.core.services.security.datasource.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@Entity(name = "TAB_COMMENT")
@SequenceGenerator(name = "gen_comment", sequenceName = "seq_comment", allocationSize = 1)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_comment")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "learning_number", nullable = false)
    private int learningNumber;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "learning_session", nullable = false)
    private LearningSession learningSession;

    @Column(name = "comment_area", nullable = false)
    private CommentArea commentArea;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "company_user_id")
    private CompanyUser companyUser;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ElementCollection(targetClass = Long.class)
    private Set<Long> usersViews;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "comment_id")
    private Set<Directory> attachments;

    public Comment() {
        this.usersViews = new HashSet<>();
        this.attachments = new HashSet<>();
    }

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }


    public Comment withComment(String comment) {
        setComment(comment);
        return this;
    }

    public Comment withLearningNumber(int learningNumber) {
        setLearningNumber(learningNumber);
        return this;
    }

    public Comment withLearningSession(LearningSession learningSession) {
        setLearningSession(learningSession);
        return this;
    }

    public Comment withCommentArea(CommentArea commentArea) {
        setCommentArea(commentArea);
        return this;
    }

    public Comment withUser(User user) {
        setUser(user);
        return this;
    }

    public Comment withCompanyUser(CompanyUser companyUser) {
        setCompanyUser(companyUser);
        return this;
    }

    public Comment withUsersViews(long userId) {
        this.usersViews.add(userId);
        return this;
    }

}
