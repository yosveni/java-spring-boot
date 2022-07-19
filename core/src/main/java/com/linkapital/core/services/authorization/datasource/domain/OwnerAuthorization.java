package com.linkapital.core.services.authorization.datasource.domain;

import com.linkapital.core.services.authorization.contract.enums.AuthorizationState;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.linkapital.core.services.authorization.contract.enums.AuthorizationState.PROGRESS;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@Entity(name = "TAB_OWNER_AUTHORIZATION")
@SequenceGenerator(name = "gen_owner_authorization", sequenceName = "seq_owner_authorization", allocationSize = 1)
public class OwnerAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_owner_authorization")
    private Long id;

    private String name;

    @Email
    private String email;

    @Column(length = 11)
    private String cpf;

    @Column(name = "cancelled_Reason", columnDefinition = "TEXT")
    private String cancelledReason;

    private String token;

    @Column(name = "consult_scr", nullable = false)
    private boolean consultScr;

    @Column(name = "has_power", nullable = false)
    private boolean hasPower;

    @Column(name = "has_representative_power", nullable = false)
    private boolean hasRepresentativePower;

    @Column(name = "belongs_company", nullable = false)
    private boolean belongsCompany;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(nullable = false)
    private AuthorizationState state;

    @OneToOne(mappedBy = "ownerAuthorization", cascade = MERGE)
    @JoinColumn(name = "company_user_id", nullable = false)
    private CompanyUser companyUser;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "authorization_trace_id")
    private AuthorizationTrace authorizationTrace;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "owner_authorization_id")
    private List<AuthorizationAnswer> answers;

    public OwnerAuthorization() {
        this.state = PROGRESS;
        this.answers = new ArrayList<>();
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

    public OwnerAuthorization withConsultScr(boolean consultScr) {
        setConsultScr(consultScr);
        return this;
    }

    public OwnerAuthorization withHasPower(boolean hasPower) {
        setHasPower(hasPower);
        return this;
    }

    public OwnerAuthorization withHasRepresentativePower(boolean hasRepresentativePower) {
        setHasRepresentativePower(hasRepresentativePower);
        return this;
    }

    public OwnerAuthorization withBelongsCompany(boolean belongsCompany) {
        setBelongsCompany(belongsCompany);
        return this;
    }

    public OwnerAuthorization withCancelledReason(String cancelledReason) {
        setCancelledReason(cancelledReason);
        return this;
    }

    public OwnerAuthorization withToken(String token) {
        setToken(token);
        return this;
    }

    public OwnerAuthorization withState(AuthorizationState state) {
        setState(state);
        return this;
    }

    public OwnerAuthorization withCompanyUser(CompanyUser companyUser) {
        setCompanyUser(companyUser);
        return this;
    }

    public OwnerAuthorization withTrace(AuthorizationTrace authorizationTrace) {
        setAuthorizationTrace(authorizationTrace);
        return this;
    }

}
