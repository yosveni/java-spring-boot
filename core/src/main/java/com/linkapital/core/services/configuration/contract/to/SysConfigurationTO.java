package com.linkapital.core.services.configuration.contract.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import javax.validation.constraints.NotNull;

@With
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysConfigurationTO {

    @NotNull
    public Object configuration;
    private Long id;
    @NotNull
    private String name;
    private String description;

}
