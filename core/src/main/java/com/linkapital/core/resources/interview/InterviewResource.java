package com.linkapital.core.resources.interview;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.interview.InterviewService;
import com.linkapital.core.services.interview.contract.to.AnswerQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.CreateQuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.QuestionInterviewTO;
import com.linkapital.core.services.interview.contract.to.UpdateQuestionInterviewTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Api(value = "Interview Operations", description = "Interview Operations")
@RestController
@RequestMapping("/interview")
@Validated
public class InterviewResource {

    private final InterviewService interviewService;

    public InterviewResource(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @ApiOperation(value = "Create question interview.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<QuestionInterviewTO> save(@ApiParam(value = "The data to save question interview.", required = true)
                                                    @RequestBody @NotNull @Valid CreateQuestionInterviewTO to) {
        return ResponseEntity.ok(interviewService.save(to));
    }

    @ApiOperation(value = "Update question intervew.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<QuestionInterviewTO> update(@ApiParam(value = "The data to update question interview.", required = true)
                                                      @RequestBody @NotNull @Valid UpdateQuestionInterviewTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(interviewService.update(to));
    }

    @ApiOperation(value = "Answer questions.")
    @PutMapping(value = "/answer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> answerQuestions(@ApiParam(value = "The answer question interview.", required = true)
                                                @RequestBody @NotNull @Valid AnswerQuestionInterviewTO to) throws UnprocessableEntityException {
        interviewService.answerQuestions(to);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get question interview by id")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<QuestionInterviewTO> getQuestionInterviewById(@ApiParam(value = "The question interview id.", required = true)
                                                                        @PathVariable @Min(1) long id) throws UnprocessableEntityException {
        return ResponseEntity.ok(interviewService.getQuestionInterviewById(id));
    }

    @ApiOperation(value = "Get all question interview.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<QuestionInterviewTO>> getAll() {
        return ResponseEntity.ok(interviewService.geAll());
    }

    @ApiOperation(value = "Delete question interview by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The question interview id.", required = true)
                                       @PathVariable @Min(1) long id) throws UnprocessableEntityException {
        try {
            interviewService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException(msg("question.interview.not.deleted"));
        }

        return ResponseEntity.noContent().build();
    }

}
