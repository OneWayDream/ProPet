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
import ru.itis.backend.dto.PetInfoDto;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.services.PetInfoService;

import java.util.List;

@RestController
@RequestMapping("/pet-info")
@RequiredArgsConstructor
public class PetInfoController {

    @NonNull
    protected PetInfoService petInfoService;

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
    public ResponseEntity<List<PetInfoDto>> getAllPets() {
        return ResponseEntity.ok(petInfoService.findAll());
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
    public ResponseEntity<PetInfoDto> getPetInfoById(@PathVariable Long id){
        return ResponseEntity.ok(petInfoService.findById(id));
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
    public ResponseEntity<List<PetInfoDto>> getAllPetsByUserId(@PathVariable Long id){
        return ResponseEntity.ok(petInfoService.findAllByUserId(id));
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
    public ResponseEntity<PetInfoDto> addPet(@RequestBody PetInfoDto petInfoDto){
        return ResponseEntity.ok(petInfoService.add(petInfoDto));
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
    public ResponseEntity<PetInfoDto> updatePetInfoById(@RequestBody PetInfoDto petInfoDto){
        return ResponseEntity.ok(petInfoService.update(petInfoDto));
    }

    @Operation(summary = "Deleting pet info by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<?> deletePetInfoById(@PathVariable Long id){
        petInfoService.delete(PetInfoDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
