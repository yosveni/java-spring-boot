package com.linkapital.core.services.security.datasource.domain;

import com.linkapital.core.services.company.contract.enums.CreditApplicationFlow;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@Entity(name = "TAB_USER_GUIDE")
@SequenceGenerator(name = "gen_user_guide", sequenceName = "seq_user_guide", allocationSize = 1)
public class UserGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_user_guide")
    private Long id;

    @Column(name = "add_company_audio")
    private boolean addCompanyAudio;

    @Column(name = "aval_audio")
    private boolean avalAudio;

    @Column(name = "discount_audio")
    private boolean discountAudio;

    @Column(name = "re_audio")
    private boolean reAudio;

    @Column(name = "im_audio")
    private boolean imAudio;

    @Column(name = "general_company ")
    private boolean generalCompany;

    private boolean completed;

    @Column(name = "credit_application_flow")
    private CreditApplicationFlow creditApplicationFlow;

    public UserGuide() {
        this.creditApplicationFlow = CreditApplicationFlow.DEFAULT;
    }

}
