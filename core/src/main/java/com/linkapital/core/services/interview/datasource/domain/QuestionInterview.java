package com.linkapital.core.services.interview.datasource.domain;

import com.linkapital.core.services.interview.contract.enums.InterviewArea;
import com.linkapital.core.services.interview.contract.enums.InterviewField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_QUESTION_INTERVIEW")
@SequenceGenerator(name = "gen_question_interview", sequenceName = "seq_question_interview", allocationSize = 1)
public class QuestionInterview {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_question_interview")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(name = "possible_value", nullable = false)
    private String possibleValue;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @Column(nullable = false)
    private InterviewArea area;

    @Column(nullable = false)
    private InterviewField field;

    @PrePersist
    private void preSave() {
        this.created = LocalDateTime.now();
        this.modified = this.created;
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = LocalDateTime.now();
    }

}
