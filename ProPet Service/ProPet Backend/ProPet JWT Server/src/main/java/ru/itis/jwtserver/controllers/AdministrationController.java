package ru.itis.jwtserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.jwtserver.services.JwtBlacklistService;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class AdministrationController {

    protected final JwtBlacklistService jwtBlacklistService;

    @Operation(summary = "Ban token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success token blocking")
    })
    @PostMapping("/ban-token")
    @PreAuthorize("hasAnyAuthority('MODER', 'ADMIN')")
    public ResponseEntity<?> login(@RequestBody String token){
        if (token == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .build();
        }
        jwtBlacklistService.add(token);
        return ResponseEntity.ok().build();
    }

}
