package com.linkapital.core.services.security.contract.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "All data of a authority.")
public enum Authority {
    SECURITY,
    BACKOFFICE,
    PARTNER,
    CLIENT,
    ENTREPRENEUR,
    ANONYMOUS
}
