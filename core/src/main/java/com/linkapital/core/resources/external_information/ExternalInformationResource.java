package com.linkapital.core.resources.external_information;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.bank_operation.contract.to.BankOperationTO;
import com.linkapital.core.services.cri_cra_debenture.CriCraDebentureService;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CreateCriCraDebentureListTO;
import com.linkapital.core.services.cri_cra_debenture.contract.to.CriCraDebentureTO;
import com.linkapital.core.services.external_information.impl.ExternalInformationServiceImpl;
import com.linkapital.core.services.ibge.contract.to.IbgeTO;
import com.linkapital.core.services.industrial_production_index.contract.to.PhysicalProductionTO;
import com.linkapital.core.services.protest.contract.to.ProtestInformationTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "Operations pertaining to the Cri Cra Debenture.")
@RestController
@RequestMapping("/information")
public class ExternalInformationResource {

    private final CriCraDebentureService criCraDebentureService;
    private final ExternalInformationServiceImpl externalInformationService;

    public ExternalInformationResource(CriCraDebentureService criCraDebentureService,
                                       ExternalInformationServiceImpl externalInformationService) {
        this.criCraDebentureService = criCraDebentureService;
        this.externalInformationService = externalInformationService;
    }

    @ApiOperation(value = "Create CRI_CRA or Debenture list.")
    @PostMapping(value = "/cri_cra_debentures", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CriCraDebentureTO>> create(@ApiParam(value = "The list of cri_cra or debentures.", required = true)
                                                          @RequestBody @NotNull CreateCriCraDebentureListTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(criCraDebentureService.createCriCraDebentures(to));
    }

    @ApiOperation(value = "Update protest information.")
    @PutMapping(value = "/protest_information/{cnpj}")
    public ResponseEntity<ProtestInformationTO> updateProtestInformation(@ApiParam(value = "The company cnpj.", required = true)
                                                                         @PathVariable String cnpj) throws UnprocessableEntityException {
        return ResponseEntity.ok(externalInformationService.updateProtestInformation(cnpj));
    }

    @ApiOperation(value = "Update IBGEs.")
    @PutMapping(value = "/ibge/{cnpj}", produces = "application/json")
    public ResponseEntity<IbgeTO> updateIbge(@ApiParam(value = "The company cnpj.", required = true)
                                             @PathVariable String cnpj) throws UnprocessableEntityException {
        return ResponseEntity.ok(externalInformationService.updateIbge(cnpj));
    }

    @ApiOperation(value = "Update bank operations.")
    @PutMapping(value = "/bank_operations/{cnpj}", produces = "application/json")
    public ResponseEntity<List<BankOperationTO>> updateBankOperations(@ApiParam(value = "The company cnpj.", required = true)
                                                                      @PathVariable String cnpj) throws UnprocessableEntityException {
        return ResponseEntity.ok(externalInformationService.updateBankOperations(cnpj));
    }

    @ApiOperation(value = "Update physical productions.")
    @PutMapping(value = "/physical_production/{cnpj}", produces = "application/json")
    public ResponseEntity<List<PhysicalProductionTO>> updatePhysicalProduction(@ApiParam(value = "The company cnpj.", required = true)
                                                                               @PathVariable String cnpj) throws UnprocessableEntityException {
        return ResponseEntity.ok(externalInformationService.updatePhysicalProduction(cnpj));
    }

}
