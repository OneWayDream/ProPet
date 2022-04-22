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
import ru.itis.backend.dto.UserAppealDto;
import ru.itis.backend.services.UserAppealService;

import java.util.List;

@RestController
@RequestMapping("/user-appeal")
@RequiredArgsConstructor
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
    public ResponseEntity<List<UserAppealDto>> getAllAppeals() {
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
    public ResponseEntity<UserAppealDto> getActivationLinkById(@PathVariable Long id){
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
    public ResponseEntity<List<UserAppealDto>> getAppealsByUserId(@PathVariable Long id){
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
    public ResponseEntity<UserAppealDto> addActivationLink(@RequestBody UserAppealDto userAppealDto){
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
    public ResponseEntity<UserAppealDto> updateActivationLinkById(@RequestBody UserAppealDto userAppealDto){
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
    public ResponseEntity<?> deleteActivationLinkById(@PathVariable Long id){
        service.delete(UserAppealDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
