package com.linkapital.core.services.security.datasource.domain;

import com.linkapital.core.services.commission.datasource.domain.CommissionCampaignUser;
import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.identification.datasource.domain.Identification;
import com.linkapital.core.services.security.contract.enums.Intensity;
import com.linkapital.core.services.shared.datasource.domain.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Locale.ROOT;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity(name = "TAB_USER")
@SequenceGenerator(name = "gen_tab_user", sequenceName = "seq_tab_user", allocationSize = 1)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_tab_user")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "confirm_token")
    private String confirmToken;

    @Column(name = "code_country_phone", length = 6)
    private String codeCountryPhone;

    private String phone;

    @Column(name = "complete_phone")
    private String completePhone;

    @Column(name = "linkeding_contact")
    private String linkedingContact;

    @Column(length = 11, unique = true)
    private String cpf;

    @Column(length = 6)
    private String codeConfirmation;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "has_rating", nullable = false)
    private boolean hasRating;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(name = "init_change_password")
    private Date initChangePassword;

    private Intensity intensity;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "identification_id")
    private Identification identification;

    @ManyToOne(optional = false, cascade = MERGE)
    private Role role;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "user_guide_id")
    private UserGuide userGuide;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private byte[] image;

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = ALL)
    private Set<CompanyUser> companies;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Set<CommissionCampaignUser> campaignUsers;

    public User() {
        this.enabled = false;
        this.intensity = Intensity.NONE;
        this.userGuide = new UserGuide();
        this.companies = new HashSet<>();
        this.campaignUsers = new HashSet<>();
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

    public List<String> getAuthoritiesValues() {
        return new ArrayList<>(Collections.singleton(String.format("ROLE_%s", role.getAuthority().toString())));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream
                .of(String.format("ROLE_%s", role.getAuthority().toString()))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public String getEmail() {
        return this.email != null ? this.email.toLowerCase(ROOT) : null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public User withEmail(String email) {
        setEmail(email);
        return this;
    }

    public User withCodeConfirmation(String codeConfirmation) {
        setCodeConfirmation(codeConfirmation);
        return this;
    }

    public User withIntensity(Intensity intensity) {
        setIntensity(intensity);
        return this;
    }

    public User withIdentification(Identification identification) {
        setIdentification(identification);
        return this;
    }

    public User withImage(byte[] image) {
        setImage(image);
        return this;
    }

}
