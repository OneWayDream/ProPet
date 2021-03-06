package ru.itis.backend.controllers.app;

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
import ru.itis.backend.dto.forms.RegistrationForm;
import ru.itis.backend.dto.app.AccountDto;
import ru.itis.backend.services.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@CrossOrigin
public class RegistrationController {

    @NonNull
    protected RegistrationService service;

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success registration", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccountDto.class)
                    ))
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "456", description = "This login is already in use"),
            @ApiResponse(responseCode = "457", description = "This mail is already in use"),
            @ApiResponse(responseCode = "502", description = "Something wrong with jwt server connection")
    })
    @PostMapping()
    public ResponseEntity<AccountDto> registerNewUser(@Valid @RequestBody RegistrationForm registrationForm){
        return ResponseEntity.ok(service.registerNewAccount(registrationForm));
    }

}
