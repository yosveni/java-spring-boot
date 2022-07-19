package com.linkapital.core.services.identification.datasource.domain;

import com.linkapital.core.services.identification.contract.enums.IdentificationState;
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
@Entity(name = "TAB_IDENTIFICATION")
@SequenceGenerator(name = "gen_identification", sequenceName = "seq_identification", allocationSize = 1)
public class Identification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_identification")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Date valid;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(nullable = false)
    private IdentificationState state;

    @Column(nullable = false)
    private byte[] document;

    @Column(name = "reverse_document")
    private byte[] reverseDocument;

    @Column(name = "selfie_capture")
    private byte[] selfieCapture;

    @PrePersist
    private void preSave() {
        this.created = new Date();
        this.modified = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }

    public Identification withType(String type) {
        setType(type);
        return this;
    }

    public Identification withValid(Date valid) {
        setValid(valid);
        return this;
    }

    public Identification withState(IdentificationState state) {
        setState(state);
        return this;
    }

    public Identification withDocument(byte[] document) {
        setDocument(document);
        return this;
    }

    public Identification withReverseDocument(byte[] reverseDocument) {
        setReverseDocument(reverseDocument);
        return this;
    }

}
