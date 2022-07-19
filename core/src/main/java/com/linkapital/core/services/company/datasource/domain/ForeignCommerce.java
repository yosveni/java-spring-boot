package com.linkapital.core.services.company.datasource.domain;

import com.linkapital.core.services.company.contract.enums.EnabledSituation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_FOREIGN_COMMERCE")
@SequenceGenerator(name = "gen_foreign_commerce", sequenceName = "seq_foreign_commerce", allocationSize = 1)
public class ForeignCommerce {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_foreign_commerce")
    private Long id;

    private String modality;

    @Column(name = "sub_modality")
    private String submodality;

    @Column(name = "authorized_operations")
    private String authorizedOperations;

    private boolean enabled;

    @Column(name = "situation_date")
    private Date situationDate;

    @Column(name = "enabled_situation")
    private EnabledSituation enabledSituation;

}
