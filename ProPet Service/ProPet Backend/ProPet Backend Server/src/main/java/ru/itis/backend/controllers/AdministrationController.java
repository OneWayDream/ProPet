package ru.itis.backend.controllers;

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
            @ApiResponse(responseCode = "200", description = "Success account blocking")
    })
    @PostMapping(
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> banUserById(@PathVariable Long id){
        service.banUser(id);
        return ResponseEntity.ok().build();
    }

}
