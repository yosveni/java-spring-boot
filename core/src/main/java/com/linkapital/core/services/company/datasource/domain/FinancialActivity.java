package com.linkapital.core.services.company.datasource.domain;

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
@Entity(name = "TAB_FINANCIAL_ACTIVITY")
@SequenceGenerator(name = "gen_financial_activity", sequenceName = "seq_financial_activity", allocationSize = 1)
public class FinancialActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_financial_activity")
    private Long id;

    @Column(name = "segment", columnDefinition = "TEXT")
    private String segment;

    @Column(name = "enablement_number")
    private String enablementNumber;

    @Column(name = "enablement_situation")
    private String enablementSituation;

    @Column(name = "query_date")
    private Date queryDate;

    @Column(name = "enablement_date")
    private Date enablementDate;

}
