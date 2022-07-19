package com.linkapital.core.resources.offer_installment;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.offer_installment.OfferInstallmentService;
import com.linkapital.core.services.offer_installment.contract.to.CreateOfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.OfferInstallmentTO;
import com.linkapital.core.services.offer_installment.contract.to.UpdateOfferInstallmentTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "Offer Installment Operations", description = "Offer installment Operations")
@RestController
@RequestMapping("/offer/installment")
public class OfferInstallmentResource {

    private final OfferInstallmentService offerInstallmentService;

    public OfferInstallmentResource(OfferInstallmentService offerInstallmentService) {
        this.offerInstallmentService = offerInstallmentService;
    }

    @ApiOperation(value = "Create offer installment.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<OfferInstallmentTO>> create(@ApiParam(value = "The data to create the installment of the offer.", required = true)
                                                           @RequestBody @Valid @NotNull CreateOfferInstallmentTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerInstallmentService.create(to));
    }

    @ApiOperation(value = "Update offer installment.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferInstallmentTO> update(@ApiParam(value = "The data to update the installment of the offer.", required = true)
                                                     @RequestBody @Valid @NotNull UpdateOfferInstallmentTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerInstallmentService.update(to));
    }

    @ApiOperation(value = "Delete offer installment by id.")
    @DeleteMapping(value = "/{offerId}/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The offer id.", required = true)
                                       @PathVariable long offerId,
                                       @ApiParam(value = "The offer installment id.", required = true)
                                       @PathVariable long id) throws UnprocessableEntityException {
        offerInstallmentService.delete(offerId, id);
        return ResponseEntity.noContent().build();
    }

}
