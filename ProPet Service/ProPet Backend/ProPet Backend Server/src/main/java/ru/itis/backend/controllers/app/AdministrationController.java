package ru.itis.backend.controllers.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.backend.services.AccountService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AdministrationController {

    @NonNull
    protected AccountService service;

    @Operation(summary = "Ban user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success account blocking"),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "404", description = "Entity does not exists"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> banUserById(@PathVariable Long id){
        service.banAccount(id);
        return ResponseEntity.ok().build();
    }

}
