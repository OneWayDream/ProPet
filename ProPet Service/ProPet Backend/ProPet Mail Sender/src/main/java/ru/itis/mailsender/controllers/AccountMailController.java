package ru.itis.mailsender.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.mailsender.dto.ActivationMailForm;
import ru.itis.mailsender.services.AccountMailSendService;

@RestController
@RequestMapping("/account-mailing")
@RequiredArgsConstructor
@CrossOrigin
public class AccountMailController {

    protected final AccountMailSendService service;

    @Operation(summary = "Send activation mail to a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied")
    })
    @PostMapping(
            value = "/activation-mail",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<?> sendActivationMail(@RequestBody ActivationMailForm form){
        service.sendActivationMail(form);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
