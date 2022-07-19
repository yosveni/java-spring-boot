package com.linkapital.core.services.property_guarantee.datasource.domain;

import com.linkapital.core.services.company_user.datasource.domain.CompanyUser;
import com.linkapital.core.services.directory.datasource.domain.Directory;
import com.linkapital.core.services.property_guarantee.contract.enums.OwnerType;
import com.linkapital.core.services.property_guarantee.contract.enums.PropertyType;
import com.linkapital.core.services.shared.datasource.domain.Address;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_PROPERTY_GUARANTEE")
@SequenceGenerator(name = "gen_property_guarantee", sequenceName = "seq_property_guarantee", allocationSize = 1)
public class PropertyGuarantee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_property_guarantee")
    private Long id;

    @Column(name = "registry_number")
    private String registryNumber;

    @Column(name = "reference_property")
    private long referenceProperty;

    @Column(name = "evaluation_value", nullable = false)
    private double evaluationValue;

    @Column(name = "built_area")
    private double builtArea;

    @Column(name = "ground_area")
    private double groundArea;

    @Column(nullable = false)
    private PropertyType type;

    @Column(nullable = false)
    private OwnerType ownerType;

    @OneToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "address")
    private Address address;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "document_id")
    private Directory document;

    @ManyToOne
    @JoinColumn(name = "company_user_id", nullable = false)
    private CompanyUser companyUser;

    public PropertyGuarantee withDocument(Directory document) {
        setDocument(document);
        return this;
    }

    public PropertyGuarantee withCompanyUser(CompanyUser companyUser) {
        setCompanyUser(companyUser);
        return this;
    }

}
