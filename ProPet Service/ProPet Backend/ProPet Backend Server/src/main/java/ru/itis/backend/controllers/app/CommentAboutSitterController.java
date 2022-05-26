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
import ru.itis.backend.dto.app.CommentAboutSitterDto;
import ru.itis.backend.dto.rest.CommentAboutSitterRestDto;
import ru.itis.backend.services.CommentAboutSitterPaginationService;
import ru.itis.backend.services.CommentAboutSitterService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment-about-sitter")
@RequiredArgsConstructor
@CrossOrigin
public class CommentAboutSitterController {

    protected final CommentAboutSitterService service;
    protected final CommentAboutSitterPaginationService paginationService;

    @Operation(summary = "Getting comments by account id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = CommentAboutSitterDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity does not exists"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @GetMapping(
            value = "/by-user-id/{id}",
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CommentAboutSitterDto>> getByAccountId(@PathVariable Long id,
                                                                   @RequestParam int page,
                                                                   @RequestParam int size){
        return ResponseEntity.ok(paginationService.getCommentsPage(id, page, size));
    }

    @Operation(summary = "Adding a new comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = CommentAboutSitterRestDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity does not exists"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "467", description = "Try to add a comment to self"),
            @ApiResponse(responseCode = "468", description = "This sitter is already commented")
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "commentAboutSitterDto",
            argField = "accountId"
    )
    public ResponseEntity<CommentAboutSitterRestDto> add(@Valid @RequestBody CommentAboutSitterRestDto commentAboutSitterDto,
                                                         @RequestHeader("JWT") String token){
        return ResponseEntity.ok(service.add(commentAboutSitterDto));
    }

}
