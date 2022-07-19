package com.linkapital.core.services.whatsapp.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Getter
@Setter
@Entity(name = "TAB_USER_WHATSAPP")
@SequenceGenerator(name = "gen_user_whatsapp", sequenceName = "seq_user_whatsapp", allocationSize = 1)
public class UserWhatsApp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_user_whatsapp")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String number;

    @Column(name = "work_position")
    private String workPosition;

    @Column(name = "times_called")
    private int timesCalled;

    private boolean enabled;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
        this.enabled = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

}
