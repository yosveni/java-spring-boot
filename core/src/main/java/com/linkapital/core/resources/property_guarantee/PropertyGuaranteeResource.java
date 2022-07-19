package com.linkapital.core.resources.property_guarantee;

import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.property_guarantee.PropertyGuaranteeService;
import com.linkapital.core.services.property_guarantee.contract.to.CreatePropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.PropertyGuaranteeTO;
import com.linkapital.core.services.property_guarantee.contract.to.UpdatePropertyGuaranteeTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.dao.DataIntegrityViolationException;
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

@Api(value = "Property Guarantee Operations.", description = "Operations pertaining to the property guarantee.")
@RestController
@RequestMapping("/property_guarantee")
public class PropertyGuaranteeResource {

    private final PropertyGuaranteeService propertyGuaranteeService;

    public PropertyGuaranteeResource(PropertyGuaranteeService propertyGuaranteeService) {
        this.propertyGuaranteeService = propertyGuaranteeService;
    }

    @ApiOperation(value = "Create property guarantee.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PropertyGuaranteeTO> create(@ApiParam(value = "The data of the property in guarantee.", required = true)
                                                      @RequestBody @NotNull @Valid CreatePropertyGuaranteeTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(propertyGuaranteeService.create(to));
    }

    @ApiOperation(value = "Update property guarantee.")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PropertyGuaranteeTO> update(@ApiParam(value = "The data of the property in guarantee.", required = true)
                                                      @RequestBody @NotNull @Valid UpdatePropertyGuaranteeTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(propertyGuaranteeService.update(to));
    }

    @ApiOperation(value = "Delete property guarantee by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The property guarantee id.", required = true)
                                       @PathVariable Long id) throws UnprocessableEntityException {
        try {
            propertyGuaranteeService.delete(id);
        } catch (DataIntegrityViolationException ignored) {
        }

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete property guarantee document.")
    @DeleteMapping(value = "/document/{id}")
    public ResponseEntity<Void> deleteDocument(@ApiParam(value = "The property guarantee id.", required = true)
                                               @PathVariable long id) throws UnprocessableEntityException {
        propertyGuaranteeService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

}
