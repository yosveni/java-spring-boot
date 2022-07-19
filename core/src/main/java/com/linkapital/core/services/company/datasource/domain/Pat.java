package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.company.contract.enums.RegistrationSituation;
import com.linkapital.core.services.person.datasource.domain.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_PAT")
@SequenceGenerator(name = "gen_pat", sequenceName = "seq_pat", allocationSize = 1)
public class Pat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_pat")
    private Long id;

    private String inscription;

    @Column(name = "benefited_employees")
    private int benefitedEmployees;

    @Column(name = "exercise_date")
    private Date exerciseDate;

    @Column(name = "registration_situation")
    private RegistrationSituation registrationSituation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id")
    private Person responsible;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "pat_id")
    private List<MealProvided> mealProvides;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "pat_id", nullable = false)
    private List<PatModality> modalities;

    public Pat() {
        this.mealProvides = new ArrayList<>();
        this.modalities = new ArrayList<>();
    }

}
