package com.linkapital.core.resources.offer;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.company.contract.to.SelectIndicativeOfferTO;
import com.linkapital.core.services.company_user.contract.to.CompanyClientTO;
import com.linkapital.core.services.company_user.contract.to.RequestOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.get.IndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.contract.to.update.UpdateIndicativeOfferTO;
import com.linkapital.core.services.indicative_offer.impl.FacadeIndicativeOfferServiceImpl;
import com.linkapital.core.services.offer.OfferService;
import com.linkapital.core.services.offer.contract.to.AcceptOfferTO;
import com.linkapital.core.services.offer.contract.to.AcceptPartnerOfferTO;
import com.linkapital.core.services.offer.contract.to.CreateOfferLogTO;
import com.linkapital.core.services.offer.contract.to.UpdateOfferStateTO;
import com.linkapital.core.services.offer.contract.to.create.CreateOfferTO;
import com.linkapital.core.services.offer.contract.to.get.OfferTO;
import com.linkapital.core.services.offer.contract.to.update.UpdateOfferTO;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.linkapital.core.services.offer.contract.OfferBinder.OFFER_BINDER;

@Api(value = "Offer Operations", description = "Offer operations")
@RestController
@RequestMapping("/offer")
public class OfferResource {

    private final OfferService offerService;
    private final FacadeIndicativeOfferServiceImpl facadeIndicativeOfferService;

    public OfferResource(OfferService offerService,
                         FacadeIndicativeOfferServiceImpl facadeIndicativeOfferService) {
        this.offerService = offerService;
        this.facadeIndicativeOfferService = facadeIndicativeOfferService;
    }

    @ApiOperation(value = "Update indicative offer.")
    @PutMapping(value = "/indicative", consumes = "application/json", produces = "application/json")
    public ResponseEntity<IndicativeOfferTO> updateLearningOffer(@ApiParam(value = "The data for update the learning offer.", required = true)
                                                                 @RequestBody @Valid @NotNull UpdateIndicativeOfferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(facadeIndicativeOfferService.updateIndicativeOffer(to));
    }

    @ApiOperation(value = "Select indicative offer.")
    @PostMapping(value = "/client/indicative/select", produces = "application/json")
    public ResponseEntity<CompanyClientTO> selectIndicativeOffer(@ApiParam(value = "The data for select the indicative offer.", required = true)
                                                                 @RequestBody @Valid @NotNull SelectIndicativeOfferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.selectIndicativeOffer(to));
    }

    @ApiOperation(value = "Request indicative proposal.")
    @PostMapping(value = "/client/request", produces = "application/json")
    public ResponseEntity<CompanyClientTO> requestOffer(@ApiParam(value = "The data for the offer request.", required = true)
                                                        @RequestBody @Valid @NotNull RequestOfferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.requestOffer(to));
    }

    @ApiOperation(value = "Accept offer.")
    @PutMapping(value = "/client/accept", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CompanyClientTO> acceptOffer(@ApiParam(value = "The data for the offer accept.", required = true)
                                                       @RequestBody @Valid @NotNull AcceptOfferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.acceptOffer(to));
    }

    @ApiOperation(value = "Accept the partner bank offer.")
    @PutMapping(value = "/client/partner_bank/accept", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferTO> acceptOffer(@ApiParam(value = "The data to accept the partner bank offer.", required = true)
                                               @RequestBody @Valid @NotNull AcceptPartnerOfferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.acceptPartnerOffer(to));
    }

    @ApiOperation(value = "Update offer state.")
    @PutMapping(value = "/client/state", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferTO> updateState(@ApiParam(value = "The data to change the status of the offer.", required = true)
                                               @RequestBody @Valid @NotNull UpdateOfferStateTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.updateState(to));
    }

    @ApiOperation(value = "Create offer.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<OfferTO>> create(@ApiParam(value = "The offer list to create.", required = true)
                                                @RequestBody @Valid @NotNull List<CreateOfferTO> to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.create(to));
    }

    @ApiOperation(value = "Create notification.")
    @PostMapping(value = "/notification", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferTO> createNotification(@ApiParam(value = "The notification to create.", required = true)
                                                      @RequestBody @Valid @NotNull CreateOfferLogTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.createNotification(to));
    }

    @ApiOperation(value = "Update offer.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<OfferTO> update(@ApiParam(value = "The offer data to update.", required = true)
                                          @RequestBody @Valid @NotNull UpdateOfferTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(offerService.update(to));
    }

    @ApiOperation(value = "Get offer by id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<OfferTO> getById(@ApiParam(value = "The offer id.", required = true)
                                           @PathVariable Long id) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(OFFER_BINDER.bind(offerService.getById(id)));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all offers.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OfferTO>> getAll() {
        return ResponseEntity.ok(offerService.getAll());
    }

    @ApiOperation(value = "Get all offers by cnpj.")
    @GetMapping(value = "/all/by_cnpj/{cnpj}", produces = "application/json")
    public ResponseEntity<List<OfferTO>> getAllByCnpj(@ApiParam(value = "The company cnpj.", required = true)
                                                      @PathVariable String cnpj) {
        return ResponseEntity.ok(offerService.getAllByCnpj(cnpj));
    }

    @ApiOperation(value = "Delete offer by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The offer id.", required = true)
                                       @PathVariable Long id) throws UnprocessableEntityException {
        offerService.delete(id);
        return ResponseEntity.ok().build();
    }

}
