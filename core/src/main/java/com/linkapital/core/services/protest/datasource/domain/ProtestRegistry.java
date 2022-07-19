package com.linkapital.core.services.protest.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity(name = "TAB_PROTEST_REGISTRY")
@SequenceGenerator(name = "gen_protest_registry", sequenceName = "seq_protest_registry", allocationSize = 1)
public class ProtestRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_protest_registry")
    private Long id;

    private String name;

    private String phone;

    private String address;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_code_ibge")
    private String cityCodeIbge;

    private String municipality;

    private String city;

    private String neighborhood;

    private String uf;

    @Column(name = "research_period")
    private String searchPeriod;

    @Column(name = "update_date")
    private String updateDate;

    private int code;

    private int amount;

    @OneToMany(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "protest_registry_id")
    private Set<Protest> protests;

    public ProtestRegistry() {
        protests = new HashSet<>();
    }

    public ProtestRegistry withName(String name) {
        this.name = name;
        return this;
    }

    public ProtestRegistry withPhone(String phone) {
        setPhone(phone);
        return this;
    }

    public ProtestRegistry withAddress(String address) {
        setAddress(address);
        return this;
    }

    public ProtestRegistry withCityCode(String cityCode) {
        setCityCode(cityCode);
        return this;
    }

    public ProtestRegistry withCityCodeIbge(String cityCodeIbge) {
        setCityCodeIbge(cityCodeIbge);
        return this;
    }

    public ProtestRegistry withMunicipality(String municipality) {
        setMunicipality(municipality);
        return this;
    }

    public ProtestRegistry withCity(String city) {
        setCity(city);
        return this;
    }

    public ProtestRegistry withNeighborhood(String neighborhood) {
        setNeighborhood(neighborhood);
        return this;
    }

    public ProtestRegistry withUf(String uf) {
        setUf(uf);
        return this;
    }

    public ProtestRegistry withCode(int code) {
        setCode(code);
        return this;
    }

    public ProtestRegistry withAmount(int amount) {
        setAmount(amount);
        return this;
    }

    public ProtestRegistry withSearchPeriod(String searchPeriod) {
        setSearchPeriod(searchPeriod);
        return this;
    }

    public ProtestRegistry withUpdateDate(String updateDate) {
        setUpdateDate(updateDate);
        return this;
    }

    public ProtestRegistry withProtests(Set<Protest> protests) {
        setProtests(protests);
        return this;
    }

}
