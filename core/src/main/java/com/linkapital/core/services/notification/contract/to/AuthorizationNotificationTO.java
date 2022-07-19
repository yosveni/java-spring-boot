package com.linkapital.core.services.notification.contract.to;

import com.linkapital.core.services.authorization.contract.enums.AuthorizationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class AuthorizationNotificationTO {

    private String cnpj;
    private long authorizationId;
    private AuthorizationState authorizationState;

}
