package com.linkapital.core.services.company.datasource.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_OPEN_CAPITAL")
@SequenceGenerator(name = "gen_open_capital", sequenceName = "seq_open_capital", allocationSize = 1)
public class OpenCapital {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_open_capital")
    private Long id;

    private String site;

    @Column(name = "main_activity")
    private String mainActivity;

    @Column(name = "negotiation_code")
    private String negotiationCode;

    @Column(name = "name_business")
    private String nameBusiness;

    @Column(name = "sectorial_classification")
    private String sectorialClassification;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "person")
    private Person person;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "open_capital_id")
    private List<PatrimonialBalance> patrimonialBalances;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "open_capital_id")
    private List<ResultDemonstration> resultDemonstrations;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "open_capital_id")
    private List<Penalty> penalties;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "open_capital_id")
    private List<ActionPosition> actionPositions;

    public OpenCapital() {
        this.patrimonialBalances = new ArrayList<>();
        this.resultDemonstrations = new ArrayList<>();
        this.penalties = new ArrayList<>();
        this.actionPositions = new ArrayList<>();
    }

}
