package com.linkapital.core.resources.reputation;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.reputation.ReputationService;
import com.linkapital.core.services.reputation.contract.to.CreateRatingTO;
import com.linkapital.core.services.reputation.contract.to.ReputationTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(value = "Reputation Operations.", description = "Operations pertaining to the reputations.")
@RestController
@RequestMapping("/reputation")
public class ReputationResource {

    private final ReputationService reputationService;

    public ReputationResource(ReputationService reputationService) {
        this.reputationService = reputationService;
    }

    @ApiOperation(value = "Rate the system.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReputationTO> doRate(@ApiParam(value = "The data to create the rating.", required = true)
                                               @RequestBody @NotNull @Valid CreateRatingTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(reputationService.createRate(to));
    }

    @ApiOperation(value = "Get system reputation.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<ReputationTO> get() {
        return ResponseEntity.ok(reputationService.get());
    }

}
