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
import ru.itis.backend.dto.AppealAnswerDto;
import ru.itis.backend.services.AppealAnswerService;

import java.util.List;

@RestController
@RequestMapping("/appeal-answer")
@RequiredArgsConstructor
public class AppealAnswerController {

    @NonNull
    protected AppealAnswerService service;

    @Operation(summary = "Getting all answers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<List<AppealAnswerDto>> getAllAnswers() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Getting an answer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<AppealAnswerDto> getAnswerById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Adding a new answer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<AppealAnswerDto> addAnswer(@RequestBody AppealAnswerDto appealAnswerDto){
        return ResponseEntity.ok(service.add(appealAnswerDto));
    }

    @Operation(summary = "Updating an answer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<AppealAnswerDto> updateActivationLinkById(@RequestBody AppealAnswerDto appealAnswerDto){
        return ResponseEntity.ok(service.update(appealAnswerDto));
    }

    @Operation(summary = "Deleting an answer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<?> deleteAnswerById(@PathVariable Long id){
        service.delete(AppealAnswerDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
