package ru.itis.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.backend.services.UsersService;

@RestController
@RequiredArgsConstructor
public class AdministrationController {

    @NonNull
    protected UsersService service;

    @Operation(summary = "Ban user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success account blocking")
    })
    @PostMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<?> banUserById(@PathVariable Long id){
        service.banUser(id);
        return ResponseEntity.ok().build();
    }

}
