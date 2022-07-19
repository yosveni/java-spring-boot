package com.linkapital.core.services.configuration.datasource.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity(name = "TAB_SYS_CONFIGURATION")
@SequenceGenerator(name = "gen_configuration", sequenceName = "seq_sys_configuration", allocationSize = 1)
public class SysConfiguration {

    @Type(type = "jsonb")
    @Column(name = "configuration", columnDefinition = "jsonb", nullable = false)
    public Object configuration;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_configuration")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

}
