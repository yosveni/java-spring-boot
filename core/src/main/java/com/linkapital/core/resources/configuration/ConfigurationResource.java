package com.linkapital.core.resources.configuration;

import com.linkapital.core.exceptions.InvalidSystemConfigurationException;
import com.linkapital.core.exceptions.NotFoundSystemConfigurationException;
import com.linkapital.core.services.configuration.ConfigurationService;
import com.linkapital.core.services.configuration.contract.to.SysConfigurationTO;
import com.linkapital.core.util.documentation.NoContentResponse;
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

@Api(value = "System Configurations.", description = "Operations over the system configuration.")
@RestController
@RequestMapping("/configuration")
public class ConfigurationResource {

    private final ConfigurationService service;

    public ConfigurationResource(ConfigurationService service) {
        this.service = service;
    }

    @ApiOperation(value = "Create a system configuration.")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SysConfigurationTO> create(@ApiParam(value = "The data to create the system configuration.", required = true)
                                                     @RequestBody @Valid @NotNull SysConfigurationTO configuration) throws InvalidSystemConfigurationException {
        return ResponseEntity.ok(service.create(configuration));
    }

    @ApiOperation(value = "Update a system configuration.")
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SysConfigurationTO> update(@ApiParam(value = "The id of configuration to be update", required = true)
                                                     @PathVariable String id,
                                                     @ApiParam(value = "The data to create the monthly invoicing.", required = true)
                                                     @RequestBody @Valid @NotNull SysConfigurationTO configuration) throws InvalidSystemConfigurationException, NotFoundSystemConfigurationException {
        return ResponseEntity.ok(service.update(configuration.withId(Long.parseLong(id))));
    }

    @ApiOperation(value = "Load a system configuration.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SysConfigurationTO> load(@ApiParam(value = "The id of configuration to be load", required = true)
                                                   @PathVariable String id) throws NotFoundSystemConfigurationException {
        return ResponseEntity.ok(service.load(Long.parseLong(id)));
    }

    @ApiOperation(value = "Load all system configurations.", produces = "application/json")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SysConfigurationTO>> loadAll() {
        return ResponseEntity.ok(service.loadAll().getElements());
    }

    @ApiOperation(value = "Delete a system configuration.", response = NoContentResponse.class)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@ApiParam(value = "The id of configuration to be delete", required = true)
                                       @PathVariable Long id) throws NotFoundSystemConfigurationException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
