package ru.itis.backend.controllers.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.backend.annotations.JwtAccessConstraint;
import ru.itis.backend.dto.app.TreatyInfoDto;
import ru.itis.backend.services.TreatyInfoService;

@RestController
@RequestMapping("/treaty-info")
@RequiredArgsConstructor
@CrossOrigin
public class TreatyInfoController {

    protected final TreatyInfoService service;

    @Operation(summary = "Getting treaty info by account id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = TreatyInfoDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @GetMapping(
            value = "/by-user-id/{id}",
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "id"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TreatyInfoDto> getByUserId(@PathVariable Long id, @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.getByAccountId(id));
    }

    @Operation(summary = "Adding a new treaty info.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = TreatyInfoDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "treatyInfoDto",
            argField = "accountId"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TreatyInfoDto> add(@RequestBody TreatyInfoDto treatyInfoDto,
                                             @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.add(treatyInfoDto));
    }

    @Operation(summary = "Updating treaty info by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = TreatyInfoDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity does not exists"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "treatyInfoDto",
            argField = "accountId"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TreatyInfoDto> updateById(@RequestBody TreatyInfoDto treatyInfoDto,
                                                     @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.update(treatyInfoDto));
    }

    @Operation(summary = "Deleting treaty info by account id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting"),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity does not exists"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "502", description = "Something wrong with jwt server connection")
    })
    @DeleteMapping(
            value = "/by-account-id/{id}",
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "id"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteByAccountId(@PathVariable Long id, @RequestHeader("JWT") String token){
        service.deleteByAccountId(id);
        return ResponseEntity.ok().build();
    }


}
