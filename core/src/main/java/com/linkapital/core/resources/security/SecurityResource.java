package com.linkapital.core.resources.security;

import com.linkapital.core.exceptions.ResourceNotFoundException;
import com.linkapital.core.exceptions.UnprocessableEntityException;
import com.linkapital.core.services.identification.contract.to.IdentificationTO;
import com.linkapital.core.services.security.RoleService;
import com.linkapital.core.services.security.UserService;
import com.linkapital.core.services.security.UserTempService;
import com.linkapital.core.services.security.contract.enums.Intensity;
import com.linkapital.core.services.security.contract.to.CodeNotificationTO;
import com.linkapital.core.services.security.contract.to.ConfirmationCodeTO;
import com.linkapital.core.services.security.contract.to.RegisterUserTO;
import com.linkapital.core.services.security.contract.to.ResetPasswordTO;
import com.linkapital.core.services.security.contract.to.RoleTO;
import com.linkapital.core.services.security.contract.to.UserActiveTO;
import com.linkapital.core.services.security.contract.to.UserAuthenticatedTO;
import com.linkapital.core.services.security.contract.to.UserIdentificationTO;
import com.linkapital.core.services.security.contract.to.UserTO;
import com.linkapital.core.services.security.contract.to.UserTempTO;
import com.linkapital.core.services.security.contract.to.create.CreateRoleTO;
import com.linkapital.core.services.security.contract.to.create.CreateUserTO;
import com.linkapital.core.services.security.contract.to.update.UpdatePasswordTO;
import com.linkapital.core.services.security.contract.to.update.UpdateRoleTO;
import com.linkapital.core.services.security.contract.to.update.UpdateUserTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.dao.DataIntegrityViolationException;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.linkapital.core.configuration.context.MessageContextHolder.msg;

@Api(value = "Security Operations.", description = "Roles, Users and Authorities operations.")
@RestController
@RequestMapping("/security")
public class SecurityResource {

    private final UserService userService;
    private final UserTempService userTempService;
    private final RoleService roleService;

    public SecurityResource(UserService userService, UserTempService userTempService, RoleService roleService) {
        this.userService = userService;
        this.userTempService = userTempService;
        this.roleService = roleService;
    }

    //USERS

    @ApiOperation(value = "Get profile.")
    @GetMapping(value = "/user/profile", produces = "application/json")
    public ResponseEntity<UserTO> getProfile() throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(userService.getProfile());
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get the user identification data.")
    @GetMapping(value = "/user/identification/{id}", produces = "application/json")
    public ResponseEntity<IdentificationTO> getIdentificationByUser(@ApiParam(value = "The user id.", required = true)
                                                                    @PathVariable Long id) throws ResourceNotFoundException {
        try {
            return ResponseEntity.ok(userService.getIdentificationByUser(id));
        } catch (UnprocessableEntityException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all users.")
    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<List<UserTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @ApiOperation(value = "Get all active users.")
    @GetMapping(value = "/user/active", produces = "application/json")
    public ResponseEntity<List<UserActiveTO>> getAllUsersByEnabled() {
        return ResponseEntity.ok(userService.getAllActive());
    }

    @ApiOperation(value = "Get all users that did not complete the identification or that the process failed.")
    @GetMapping(value = "/user/identification/failed", produces = "application/json")
    public ResponseEntity<List<UserIdentificationTO>> getAllWithIdentificationFailed() {
        return ResponseEntity.ok(userService.getAllWithIdentificationFailed());
    }

    @ApiOperation(value = "Get all temporal users.")
    @GetMapping(value = "/user_temp", produces = "application/json")
    public ResponseEntity<List<UserTempTO>> getAllUsersTemp() {
        return ResponseEntity.ok(userTempService.getAll());
    }

    @ApiOperation(value = "Create a user.")
    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserTO> create(@ApiParam(value = "The user data to create.", required = true)
                                         @RequestBody @NotNull @Valid CreateUserTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.create(to));
    }

    @ApiOperation(value = "Register a client user.")
    @PostMapping(value = "/user/register_client", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserTempTO> register(@ApiParam(value = "The user data to register.", required = true)
                                               @RequestBody @NotNull @Valid RegisterUserTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userTempService.register(to));
    }

    @ApiOperation(value = "Confirm code sent to user.")
    @PostMapping(value = "/user/confirm_code", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserAuthenticatedTO> confirmationCode(@ApiParam(value = "The data to confirm the code send to user.", required = true)
                                                                @RequestBody @NotNull @Valid ConfirmationCodeTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.confirmationCode(to));
    }

    @ApiOperation(value = "Send code in registration process.")
    @PostMapping(value = "/user/register/send_code", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserTempTO> registerSendCode(@ApiParam(value = "The data to send the notification code.", required = true)
                                                       @RequestBody @NotNull @Valid CodeNotificationTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userTempService.sendCode(to));
    }

    @ApiOperation(value = "Send registration code.")
    @PostMapping(value = "/user/send_code", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserTO> sendCode(@ApiParam(value = "The user email.", required = true)
                                           @RequestBody @NotNull @Valid CodeNotificationTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.sendCode(to));
    }

    @ApiOperation(value = "Update a user.")
    @PutMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserTO> updateUser(@ApiParam(value = "The user object to update in database table.", required = true)
                                             @RequestBody @NotNull @Valid UpdateUserTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.update(to));
    }

    @ApiOperation(value = "Update a user email.")
    @PutMapping(value = "/user/update_email/{email}", produces = "application/json")
    public ResponseEntity<UserAuthenticatedTO> updateEmail(@ApiParam(value = "The new user email.", required = true)
                                                           @PathVariable @NotEmpty @Email String email) {
        return ResponseEntity.ok(userService.updateEmail(email));
    }

    @ApiOperation(value = "Update password.")
    @PutMapping(value = "/user/update_password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserTO> updatePassword(@ApiParam(value = "The data to update password.", required = true)
                                                 @RequestBody @NotNull @Valid UpdatePasswordTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.updatePassword(to));
    }

    @ApiOperation(value = "Enable or disable a user.")
    @PutMapping(value = "/user/enable/{id}", produces = "application/json")
    public ResponseEntity<UserTO> enable(@ApiParam(value = "The user id.", required = true)
                                         @PathVariable Long id) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.enable(id));
    }

    @ApiOperation(value = "Update a user intensity.")
    @PutMapping(value = "/user/intensity/{intensity}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserTO> updateIntensity(@ApiParam(value = "The intensity of the user.", required = true)
                                                  @PathVariable Intensity intensity) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.updateIntensity(intensity));
    }

    @ApiOperation(value = "Reset password for client.")
    @PutMapping(value = "/user/client/reset_password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> resetPassword(@ApiParam(value = "The data to reset password.", required = true)
                                              @RequestBody @NotNull @Valid ResetPasswordTO to) throws UnprocessableEntityException {
        userService.resetPassword(to);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Reset password for admin.")
    @PutMapping(value = "/user/reset_password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> resetPasswordFromAdmin(@ApiParam(value = "The data to reset password.", required = true)
                                                       @RequestBody @NotNull @Valid ResetPasswordTO to) throws UnprocessableEntityException {
        userService.resetPasswordFromAdmin(to);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Change the status of the identification to accepted.")
    @PutMapping(value = "/user/identification/accept/{id}", produces = "application/json")
    public ResponseEntity<IdentificationTO> acceptIdentification(@ApiParam(value = "The user id.", required = true)
                                                                 @PathVariable Long id) throws UnprocessableEntityException {
        return ResponseEntity.ok(userService.acceptIdentification(id));
    }

    @ApiOperation(value = "Enable all users who made a rate to do it again.")
    @PutMapping(value = "/enable_rate")
    public ResponseEntity<Void> enableRateForAll() {
        userService.enableRateForAll();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete a user.")
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Void> deleteUser(@ApiParam(value = "The user id.", required = true)
                                           @PathVariable Long id) throws UnprocessableEntityException {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException(msg("security.user.not.deleted"));
        }
    }

    @ApiOperation(value = "Delete a temporary user.")
    @DeleteMapping(value = "/user_temp/{id}")
    public ResponseEntity<Void> deleteUserTemp(@ApiParam(value = "The temporary user id.", required = true)
                                               @PathVariable Long id) throws UnprocessableEntityException {
        userTempService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //ROLES

    @ApiOperation(value = "Create a role.")
    @PostMapping(value = "/role", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleTO> createRole(@ApiParam(value = "The role object to store in database table.", required = true)
                                             @RequestBody @NotNull @Valid CreateRoleTO to) {
        return ResponseEntity.ok(roleService.create(to));
    }

    @ApiOperation(value = "Update a role.")
    @PutMapping(value = "/role", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleTO> updateRole(@ApiParam(value = "The role object to update in database table.", required = true)
                                             @RequestBody @NotNull @Valid UpdateRoleTO to) throws UnprocessableEntityException {
        return ResponseEntity.ok(roleService.update(to));
    }

    @ApiOperation(value = "Delete a role.")
    @DeleteMapping(value = "/role/{id}")
    public ResponseEntity<Void> deleteRole(@ApiParam(value = "The role id.", required = true)
                                           @PathVariable Long id) throws UnprocessableEntityException {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get all roles.")
    @GetMapping(value = "/role", produces = "application/json")
    public ResponseEntity<List<RoleTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @ApiOperation(value = "Delete the relationship between the user and the companies.")
    @DeleteMapping(value = "/user/companies/{email}")
    public ResponseEntity<Void> deleteByEmail(@ApiParam(value = "The user email.", required = true)
                                              @PathVariable String email) throws UnprocessableEntityException {
        userService.deleteCompaniesRelatedToUser(email);
        return ResponseEntity.noContent().build();
    }

}
