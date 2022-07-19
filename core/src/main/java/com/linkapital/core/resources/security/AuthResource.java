package com.linkapital.core.resources.security;

import com.linkapital.core.exceptions.InvalidRefreshTokenException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.contract.to.AuthenticationRequestTO;
import com.linkapital.core.services.security.contract.to.RefreshTokenTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Api(value = "Auth Operations.", description = "Operations pertaining to the authentication in the system.")
@RestController
@RequestMapping("/auth")
public class AuthResource {

    private final UserService userService;

    public AuthResource(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Login by email and password.", notes = "Login with email.")
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserAuthenticatedTO> login(@ApiParam(value = "The user data to authenticate.", required = true)
                                                     @RequestBody @Valid @NotNull AuthenticationRequestTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.login(to));
    }

    @ApiOperation(value = "Logout.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Refresh user token.")
    @PostMapping(value = "/refresh_token", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RefreshTokenTO> refreshToken(@ApiParam(value = "The data to update the token.", required = true)
                                                       @RequestBody @Valid @NotNull RefreshTokenTO to) throws InvalidRefreshTokenException {
        return ResponseEntity.ok(userService.refreshToken(to));
    }

}