package com.linkapital.core.services.interview.datasource;

import com.linkapital.core.services.interview.datasource.domain.QuestionInterview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionInterviewRepository extends JpaRepository<QuestionInterview, Long> {

}
