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
import ru.itis.backend.dto.app.PetInfoDto;
import ru.itis.backend.dto.app.PetTransferApplyDto;
import ru.itis.backend.dto.forms.PetTransferApplyForm;
import ru.itis.backend.models.PetTransferApplyStatus;
import ru.itis.backend.services.PetInfoService;
import ru.itis.backend.services.PetTransferApplyService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/pet-transfer-apply")
@RequiredArgsConstructor
@CrossOrigin
public class PetTransferApplyController {

    protected final PetTransferApplyService service;
    protected final PetInfoService petInfoService;

    @Operation(summary = "Getting pet transfer apply by customer id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetTransferApplyDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @GetMapping(
            value = "/by-customer-id/{id}",
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "id"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetTransferApplyDto>> getByCustomerId(@PathVariable Long id,
                                                                     @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.findAllByCustomerId(id));
    }

    @Operation(summary = "Getting pet transfer apply by performer id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetTransferApplyDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @GetMapping(
            value = "/by-performer-id/{id}",
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "id"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetTransferApplyDto>> getByPerformerId(@PathVariable Long id,
                                                               @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.findAllByPerformerId(id));
    }

    @Operation(summary = "Adding a new pet transfer apply.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetTransferApplyDto.class)
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
            argName = "petTransferApplyForm",
            argField = "customerId"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetTransferApplyDto> add(@RequestBody PetTransferApplyForm petTransferApplyForm,
                                                   @RequestHeader("JWT") String token) throws AccessDeniedException {
        if (!petInfoService.findById(petTransferApplyForm.getPetId()).getAccountId()
                .equals(petTransferApplyForm.getCustomerId())){
            throw new AccessDeniedException("Access denied");
        }
        return ResponseEntity.ok(service.add(
                PetTransferApplyDto.builder()
                        .customerId(petTransferApplyForm.getCustomerId())
                        .performerId(petTransferApplyForm.getPerformerId())
                        .petId(petTransferApplyForm.getPetId())
                        .customerAgreement(false)
                        .performerAgreement(false)
                        .status(PetTransferApplyStatus.UNCONFIRMED)
                        .city(petTransferApplyForm.getCity())
                        .build()
        ));
    }

    @Operation(summary = "Confirm a pet transfer apply by customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success confirming", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetTransferApplyDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PatchMapping(
            value = "/customer-confirm",
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "customerId"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetTransferApplyDto> confirmByCustomer(@RequestParam Long applyId,
                                                                 @RequestParam Long customerId,
                                                                 @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.confirmByCustomer(applyId, customerId));
    }

    @Operation(summary = "Confirm a pet transfer apply by performer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success confirming", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = PetTransferApplyDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PatchMapping(
            value = "/performer-confirm",
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "performerId"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetTransferApplyDto> confirmByPerformer(@RequestParam Long applyId,
                                                                 @RequestParam Long performerId,
                                                                 @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.confirmByPerformer(applyId, performerId));
    }



}
