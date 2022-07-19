package com.linkapital.core.services.sped.datasource.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_SPED")
@SequenceGenerator(name = "gen_sped", sequenceName = "seq_sped", allocationSize = 1)
public class Sped implements Serializable, Comparable<Sped> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_sped")
    private Long id;

    @Column(name = "demonstrative_init_date")
    private LocalDate demonstrativeInitDate;

    @Column(name = "demonstrative_end_date")
    private LocalDate demonstrativeEndDate;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "sped_id")
    private List<SpedBalance> spedBalances;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "sped_id")
    private List<SpedDemonstration> spedDemonstrations;

    public Sped() {
        this.spedBalances = new ArrayList<>();
        this.spedDemonstrations = new ArrayList<>();
    }

    public Sped withId(Long id) {
        setId(id);
        return this;
    }

    public Sped withDemonstrativeInitDate(LocalDate demonstrativeInitDate) {
        setDemonstrativeInitDate(demonstrativeInitDate);
        return this;
    }

    public Sped withDemonstrativeEndDate(LocalDate demonstrativeEndDate) {
        setDemonstrativeEndDate(demonstrativeEndDate);
        return this;
    }

    public Sped withSpedBalances(List<SpedBalance> spedBalances) {
        setSpedBalances(spedBalances);
        return this;
    }

    public Sped withSpedDemonstrations(List<SpedDemonstration> spedDemonstrations) {
        setSpedDemonstrations(spedDemonstrations);
        return this;
    }

    @Override
    public int compareTo(@NonNull Sped sped) {
        return this.demonstrativeEndDate.compareTo(sped.demonstrativeEndDate) * -1;
    }

}
