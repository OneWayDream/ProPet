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
import ru.itis.backend.dto.app.SitterSearchDto;
import ru.itis.backend.entities.SearchVariable;
import ru.itis.backend.entities.SortingOrder;
import ru.itis.backend.entities.SortingVariable;
import ru.itis.backend.services.SitterSearchService;

@RestController
@RequestMapping("/sitter-search")
@RequiredArgsConstructor
@CrossOrigin
public class SitterSearchController {

    protected final SitterSearchService service;

    @Operation(summary = "Getting sitter info cards for the search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = SitterSearchDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "459", description = "Incorrect search option value"),
            @ApiResponse(responseCode = "460", description = "Incorrect filter variable value"),
            @ApiResponse(responseCode = "461", description = "Incorrect search options"),
            @ApiResponse(responseCode = "465", description = "Incorrect search variable")
    })
    @GetMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getSearchPage(@RequestParam int page, @RequestParam int size,
                                           @RequestParam(required = false) String sortedBy,
                                           @RequestParam(required = false) String order,
                                           @RequestParam(required = false) String searchParam,
                                           @RequestParam(required = false) String searchParamValue){
        return ResponseEntity.ok(service.getSearchPage(page, size,
                (sortedBy == null) ? null : SortingVariable.get(sortedBy),
                (order == null) ? null : SortingOrder.get(order),
                (searchParam == null) ? null : SearchVariable.get(searchParam), searchParamValue));
    }

}
