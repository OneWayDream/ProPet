package ru.itis.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.backend.annotations.JwtAccessConstraint;
import ru.itis.backend.dto.AccountDto;
import ru.itis.backend.services.AccountService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {

    @NonNull
    protected AccountService service;

    @Operation(summary = "Getting all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Getting user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Getting user by login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-login/{login}",
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountDto> getByLogin(@PathVariable String login){
        return ResponseEntity.ok(service.findUserByLogin(login));
    }

    @Operation(summary = "Getting user by mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-mail/{mail}",
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountDto> getByMail(@PathVariable String mail){
        return ResponseEntity.ok(service.findUserByMail(mail));
    }

    @Operation(summary = "Adding a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<AccountDto> add(@RequestBody AccountDto userDto){
        return ResponseEntity.ok(service.add(userDto));
    }

    @Operation(summary = "Updating user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "id",
            argName = "accountDto",
            argField = "id",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<AccountDto> updateById(@RequestBody AccountDto accountDto,
                                                 @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.update(accountDto));
    }

    @Operation(summary = "Deleting user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "id",
            argName = "id",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.delete(AccountDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate user's account by the link id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success activation", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            })
    })
    @PostMapping(
            value = "/activate/{linkValue}",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<AccountDto> activateAccount(@PathVariable String linkValue){
        return ResponseEntity.ok(service.activateUser(linkValue));
    }


}
