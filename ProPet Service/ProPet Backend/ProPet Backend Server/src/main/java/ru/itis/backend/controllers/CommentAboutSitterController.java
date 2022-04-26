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
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<List<CommentAboutSitterDto>> getAll() {
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
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<CommentAboutSitterDto> getById(@PathVariable Long id){
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
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "id",
            argName = "id",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<List<CommentAboutSitterDto>> getByUserId(@PathVariable Long id,
                                                                   @RequestHeader("JWT") String token){
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
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "id",
            argName = "commentAboutSitterDto",
            argField = "accountId",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<CommentAboutSitterDto> add(@RequestBody CommentAboutSitterDto commentAboutSitterDto,
                                                     @RequestHeader("JWT") String token){
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
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<CommentAboutSitterDto> updateById(
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
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.delete(CommentAboutSitterDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
