package com.linkapital.core.services.invoice.datasource.domain;

import com.linkapital.core.services.shared.datasource.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_SENDER_RECIPIENT")
@SequenceGenerator(name = "gen_sender_recipient", sequenceName = "seq_sender_recipient", allocationSize = 1)
public class SenderRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_sender_recipient")
    private Long id;

    private String cnpj;

    private String cnae;

    private String name;

    @Column(name = "fantasy_name")
    private String fantasyName;

    private String email;

    private String ie;

    private String im;

    private String crt;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Address address;

}
