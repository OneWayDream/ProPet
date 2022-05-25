package ru.itis.backend.controllers.rest;

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
import ru.itis.backend.dto.rest.AppealAnswerDto;
import ru.itis.backend.services.AppealAnswerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/appeal-answer")
@RequiredArgsConstructor
@CrossOrigin
public class AppealAnswerController {

    @NonNull
    protected AppealAnswerService service;

    @Operation(summary = "Getting all answers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @GetMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<List<AppealAnswerDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Getting an answer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<AppealAnswerDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Adding a new answer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<AppealAnswerDto> add(@Valid @RequestBody AppealAnswerDto appealAnswerDto){
        return ResponseEntity.ok(service.add(appealAnswerDto));
    }

    @Operation(summary = "Updating an answer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AppealAnswerDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<AppealAnswerDto> updateById(@Valid @RequestBody AppealAnswerDto appealAnswerDto){
        return ResponseEntity.ok(service.update(appealAnswerDto));
    }

    @Operation(summary = "Deleting an answer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting"),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity does not exists"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.delete(AppealAnswerDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
