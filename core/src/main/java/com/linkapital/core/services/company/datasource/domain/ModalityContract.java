package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_MODALITY_CONTRACT")
@SequenceGenerator(name = "gen_modality_contract", sequenceName = "seq_modality_contract", allocationSize = 1)
public class ModalityContract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_modality_contract")
    private Long id;

    private String type;

    private int quantity;

}
