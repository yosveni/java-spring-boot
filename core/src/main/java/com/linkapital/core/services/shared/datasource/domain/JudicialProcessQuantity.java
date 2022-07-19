package com.linkapital.core.services.shared.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_JUDICIAL_PROCESS_QUANTITY")
@SequenceGenerator(name = "gen_judicial_process_quantity", sequenceName = "seq_judicial_process_quantity", allocationSize = 1)
public class JudicialProcessQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_judicial_process_quantity")
    private Long id;

    private String type;

    @Column(name = "quantity_active")
    private int quantityActive;

    @Column(name = "quantity_active_part")
    private int quantityActivePart;

    @Column(name = "quantity_passive_part")
    private int quantityPassivePart;

    @Column(name = "quantity_others")
    private int quantityOthers;

    @Column(name = "quantity_total")
    private int quantityTotal;

}
