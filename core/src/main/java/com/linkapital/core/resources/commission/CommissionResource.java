package com.linkapital.core.resources.commission;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.commission.CommissionCampaignService;
import com.linkapital.core.services.commission.CommissionService;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.create.CreateCommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignAttributeTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionCampaignTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionTO;
import com.linkapital.core.services.commission.contract.to.get.CommissionUserTO;
import com.linkapital.core.services.commission.contract.to.update.UpdateCommissionCampaignTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.linkapital.core.services.commission.contract.CommissionBinder.COMMISSION_BINDER;

@Api(value = "Commission Operations", description = "Operations pertaining to the commission")
@RestController
@RequestMapping("/commission")
public class CommissionResource {

    private final CommissionService commissionService;
    private final CommissionCampaignService commissionCampaignService;

    public CommissionResource(CommissionService commissionService,
                              CommissionCampaignService commissionCampaignService) {
        this.commissionService = commissionService;
        this.commissionCampaignService = commissionCampaignService;
    }

    @ApiOperation(value = "Create commission.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommissionTO> create(@ApiParam(value = "The commission data.", required = true)
                                               @RequestBody @NotNull @Valid CreateCommissionTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(commissionService.create(to));
    }

    @ApiOperation(value = "Create commission campaign.")
    @PostMapping(value = "/campaign", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommissionCampaignTO> create(@ApiParam(value = "The commission campaign data.", required = true)
                                                       @RequestBody @NotNull @Valid CreateCommissionCampaignTO to) {
        return ResponseEntity.ok(commissionCampaignService.create(to));
    }

    @ApiOperation(value = "Update commission campaign.")
    @PutMapping(value = "/campaign", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommissionCampaignTO> update(@ApiParam(value = "The commission campaign data.", required = true)
                                                       @RequestBody @NotNull @Valid UpdateCommissionCampaignTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(commissionCampaignService.update(to));
    }

    @ApiOperation(value = "Get commission by user.")
    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<List<CommissionUserTO>> getAllCommissionByUserId(@ApiParam(value = "The user id.")
                                                                           @RequestParam(required = false) @Min(1) Long userId) {
        return ResponseEntity.ok(commissionService.getAllCommissionByUserId(userId));
    }

    @ApiOperation(value = "Get commission campaign by id.")
    @GetMapping(value = "/campaign/{id}", produces = "application/json")
    public ResponseEntity<CommissionCampaignTO> getById(@ApiParam(value = "The commission campaign id.", required = true)
                                                        @PathVariable @Min(1) long id) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(COMMISSION_BINDER.bind(commissionCampaignService.getById(id)));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get the commissions that meet the conditions of the campaigns according to the values of the offer.")
    @GetMapping(value = "/campaign/match/{offerId}", produces = "application/json")
    public ResponseEntity<List<CommissionCampaignTO>> getAllMatchByOffer(@ApiParam(value = "The offer id.", required = true)
                                                                         @PathVariable @Min(1) long offerId) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(commissionCampaignService.getAllMatchByOffer(offerId));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all commission campaign.")
    @GetMapping(value = "/campaign", produces = "application/json")
    public ResponseEntity<List<CommissionCampaignTO>> getAll() {
        return ResponseEntity.ok(commissionCampaignService.getAll());
    }

    @ApiOperation(value = "Get all commission campaign attributes.")
    @GetMapping(value = "/campaign/attributes", produces = "application/json")
    public ResponseEntity<List<CommissionCampaignAttributeTO>> getAllAttributes() {
        return ResponseEntity.ok(commissionCampaignService.getAllAttributes());
    }

    @ApiOperation(value = "Delete commission campaign by id.")
    @DeleteMapping(value = "/campaign/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The commission campaign id.", required = true)
                                       @PathVariable @Min(1) long id) throws UnprocessableEntityException {
        commissionCampaignService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
