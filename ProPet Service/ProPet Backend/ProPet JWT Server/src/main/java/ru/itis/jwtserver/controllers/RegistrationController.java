package ru.itis.jwtserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.jwtserver.dto.JwtUserDto;
import ru.itis.jwtserver.services.JwtUserService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    protected final JwtUserService service;

    @Operation(summary = "Register new jwt user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success registration", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = JwtUserDto.class)
                    ))
            })
    })
    @PostMapping(
            value = "/register",
            headers = {"JWT"}
    )
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<JwtUserDto> registerNewUser(@RequestBody JwtUserDto jwtUserDto){
        return ResponseEntity.ok(service.add(jwtUserDto));
    }

}
