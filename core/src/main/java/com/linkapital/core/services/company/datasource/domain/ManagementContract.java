package com.linkapital.core.services.company.datasource.domain;

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
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "TAB_MANAGEMENT_CONTRACT")
@SequenceGenerator(name = "gen_management_contract", sequenceName = "seq_management_contract", allocationSize = 1)
public class ManagementContract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_management_contract")
    private Long id;

    private int quantity;

    @Column(name = "total_value")
    private double totalValue;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "management_contract_id")
    private List<Contract> contracts;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "management_contract_id")
    private List<ModalityContract> modalityContracts;

    public ManagementContract() {
        this.contracts = new ArrayList<>();
        this.modalityContracts = new ArrayList<>();
    }

}
