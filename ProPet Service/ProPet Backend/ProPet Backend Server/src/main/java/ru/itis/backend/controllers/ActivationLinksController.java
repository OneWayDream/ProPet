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
import org.springframework.web.bind.annotation.*;
import ru.itis.backend.dto.ActivationLinkDto;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.services.ActivationLinksService;

import java.util.List;

@RestController
@RequestMapping("/activation-link")
@RequiredArgsConstructor
public class ActivationLinksController {

    @NonNull
    protected ActivationLinksService activationLinksService;

    @Operation(summary = "Getting all activation links.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ActivationLinkDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<List<ActivationLinkDto>> getAllActivationLinks() {
        return ResponseEntity.ok(activationLinksService.findAll());
    }

    @Operation(summary = "Getting activation link by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ActivationLinkDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<ActivationLinkDto> getActivationLinkById(@PathVariable Long id){
        return ResponseEntity.ok(activationLinksService.findById(id));
    }

    @Operation(summary = "Getting activation link by account id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ActivationLinkDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-account-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<ActivationLinkDto> getUserByLogin(@PathVariable Long id){
        return ResponseEntity.ok(activationLinksService.findByAccountId(id));
    }

    @Operation(summary = "Adding a new activation link.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ActivationLinkDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<ActivationLinkDto> addUser(@RequestBody ActivationLinkDto activationLinkDto){
        return ResponseEntity.ok(activationLinksService.add(activationLinkDto));
    }

    @Operation(summary = "Updating activation link by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ActivationLinkDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<ActivationLinkDto> updateUserById(@RequestBody ActivationLinkDto activationLinkDto){
        return ResponseEntity.ok(activationLinksService.update(activationLinkDto));
    }

    @Operation(summary = "Deleting activation link by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        activationLinksService.delete(ActivationLinkDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
