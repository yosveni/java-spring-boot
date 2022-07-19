package com.linkapital.core.services.interview.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_ANSWER_INTERVIEW")
@SequenceGenerator(name = "gen_answer_interview", sequenceName = "seq_answer_interview", allocationSize = 1)
public class AnswerInterview {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "gen_answer_interview")
    private Long id;

    @Column(nullable = false)
    private int answer;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @ManyToOne
    @JoinColumn(name = "question_interview_id", nullable = false)
    private QuestionInterview questionInterview;

    @PrePersist
    private void preSave() {
        this.created = LocalDateTime.now();
        this.modified = this.created;
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = LocalDateTime.now();
    }


    public AnswerInterview withAnswer(int answer) {
        setAnswer(answer);
        return this;
    }

    public AnswerInterview withQuestionInterview(QuestionInterview questionInterview) {
        setQuestionInterview(questionInterview);
        return this;
    }

}
