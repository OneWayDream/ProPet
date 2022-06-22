package ru.itis.backend.controllers.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.backend.annotations.JwtAccessConstraint;
import ru.itis.backend.services.PdfGenerationService;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/pdf-generation")
@RequiredArgsConstructor
@CrossOrigin
public class PdfGenerationController {

    protected final PdfGenerationService service;

    @Operation(summary = "Generate a pet transfer treaty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/pdf", schema = @Schema(type = "file", format = "binary"))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "469", description = "Pdf generation fault"),
            @ApiResponse(responseCode = "470", description = "Treaty apply is unconfirmed"),
    })
    @GetMapping(
            value = "/pet-transfer-treaty",
            headers = {"JWT"}
    )
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "accountId"
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getByCustomerId(@RequestParam Long applyId,
                                             @RequestParam Long accountId,
                                             @RequestHeader("JWT") String token,
                                             HttpServletResponse response)
            throws AccessDeniedException {

        byte[] contents = service.generatePetTransferTreaty(applyId, accountId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        response.setHeader("Content-Disposition", "attachment; filename=Pet Transfer Treaty.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

}
