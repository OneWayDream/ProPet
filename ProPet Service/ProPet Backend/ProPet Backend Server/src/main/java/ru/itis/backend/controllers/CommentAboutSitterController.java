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
import ru.itis.backend.dto.CommentAboutSitterDto;
import ru.itis.backend.services.CommentAboutSitterService;

import java.util.List;

@RestController
@RequestMapping("/comment-about-sitter")
@RequiredArgsConstructor
public class CommentAboutSitterController {

    @NonNull
    protected CommentAboutSitterService service;

    @Operation(summary = "Getting all comments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = CommentAboutSitterDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<List<CommentAboutSitterDto>> getAllComments() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Getting a comment by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = CommentAboutSitterDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<CommentAboutSitterDto> getCommentById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Getting a comment by account id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = CommentAboutSitterDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-user-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<List<CommentAboutSitterDto>> getCommentsByUserId(@PathVariable Long id){
        return ResponseEntity.ok(service.findAllByUserId(id));
    }

    @Operation(summary = "Adding a new comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = CommentAboutSitterDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<CommentAboutSitterDto> addComment(@RequestBody CommentAboutSitterDto commentAboutSitterDto){
        return ResponseEntity.ok(service.add(commentAboutSitterDto));
    }

    @Operation(summary = "Updating a comment by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = CommentAboutSitterDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<CommentAboutSitterDto> updateCommentById(
            @RequestBody CommentAboutSitterDto commentAboutSitterDto){
        return ResponseEntity.ok(service.update(commentAboutSitterDto));
    }

    @Operation(summary = "Deleting a comment by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id){
        service.delete(CommentAboutSitterDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
