package com.linkapital.core.services.company.datasource.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity(name = "TAB_LEARNING_ANALYSIS")
@SequenceGenerator(name = "gen_learning_analysis", sequenceName = "seq_learning_analysis", allocationSize = 1)
public class LearningAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_learning_analysis")
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Object learningTwo;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Object learningThree;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Object learningFour;

}
