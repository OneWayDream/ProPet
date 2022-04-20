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
import ru.itis.backend.dto.SitterInfoDto;
import ru.itis.backend.services.SitterInfoService;

import java.util.List;

@RestController
@RequestMapping("/sitter-info")
@RequiredArgsConstructor
public class SitterInfoController {

    @NonNull
    protected SitterInfoService sitterInfoService;

    @Operation(summary = "Getting all sitter's cards.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = SitterInfoDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<List<SitterInfoDto>> getAllPets() {
        return ResponseEntity.ok(sitterInfoService.findAll());
    }

    @Operation(summary = "Getting sitter info by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = SitterInfoDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<SitterInfoDto> getPetInfoById(@PathVariable Long id){
        return ResponseEntity.ok(sitterInfoService.findById(id));
    }

    @Operation(summary = "Getting sitter info by user's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = SitterInfoDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-user-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<SitterInfoDto> getAllPetsByUserId(@PathVariable Long id){
        return ResponseEntity.ok(sitterInfoService.findByUserId(id));
    }

    @Operation(summary = "Adding a new sitter info.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = SitterInfoDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<SitterInfoDto> addPet(@RequestBody SitterInfoDto sitterInfoDto){
        return ResponseEntity.ok(sitterInfoService.add(sitterInfoDto));
    }

    @Operation(summary = "Updating a sitter card by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = SitterInfoDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<SitterInfoDto> updatePetInfoById(@RequestBody SitterInfoDto sitterInfoDto){
        return ResponseEntity.ok(sitterInfoService.update(sitterInfoDto));
    }

    @Operation(summary = "Deleting sitter info by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<?> deletePetInfoById(@PathVariable Long id){
        sitterInfoService.delete(SitterInfoDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
