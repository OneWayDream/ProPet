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
import ru.itis.backend.dto.PetInfoDto;
import ru.itis.backend.services.PetInfoService;

import java.util.List;

@RestController
@RequestMapping("/pet-info")
@RequiredArgsConstructor
@CrossOrigin
public class PetInfoController {

    @NonNull
    protected PetInfoService service;

    @Operation(summary = "Getting all pet cards.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetInfoDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<List<PetInfoDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Getting pet info by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetInfoDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<PetInfoDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Getting all pet cards by user's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetInfoDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-user-id/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetInfoDto>> getAllByUserId(@PathVariable Long id){
        return ResponseEntity.ok(service.findAllByUserId(id));
    }

    @Operation(summary = "Adding a new pet info.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetInfoDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "petInfoDto",
            argField = "accountId",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<PetInfoDto> add(@RequestBody PetInfoDto petInfoDto,
                                          @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.add(petInfoDto));
    }

    @Operation(summary = "Updating a pet card by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetInfoDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "petInfoDto",
            argField = "accountId",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<PetInfoDto> updateById(@RequestBody PetInfoDto petInfoDto,
                                                     @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.update(petInfoDto));
    }

    @Operation(summary = "Deleting pet info by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
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
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestHeader("JWT") String token){
        service.delete(PetInfoDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
