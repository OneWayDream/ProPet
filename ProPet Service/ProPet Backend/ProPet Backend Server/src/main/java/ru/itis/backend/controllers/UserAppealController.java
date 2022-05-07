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
import ru.itis.backend.dto.UserAppealDto;
import ru.itis.backend.services.UserAppealService;

import java.util.List;

@RestController
@RequestMapping("/user-appeal")
@RequiredArgsConstructor
@CrossOrigin
public class UserAppealController {

    @NonNull
    protected UserAppealService service;

    @Operation(summary = "Getting all user appeals.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserAppealDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<List<UserAppealDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Getting user appeal by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserAppealDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<UserAppealDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Getting all user appeals by user id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserAppealDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-user-id/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "id",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<List<UserAppealDto>> getByUserId(@PathVariable Long id,
                                                           @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.getAllByUserId(id));
    }

    @Operation(summary = "Adding a new appeal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserAppealDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "userAppealDto",
            argField = "accountId",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<UserAppealDto> add(@RequestBody UserAppealDto userAppealDto,
                                                   @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.add(userAppealDto));
    }

    @Operation(summary = "Updating an appeal by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserAppealDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<UserAppealDto> updateById(@RequestBody UserAppealDto userAppealDto){
        return ResponseEntity.ok(service.update(userAppealDto));
    }

    @Operation(summary = "Deleting an appeal by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.delete(UserAppealDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
