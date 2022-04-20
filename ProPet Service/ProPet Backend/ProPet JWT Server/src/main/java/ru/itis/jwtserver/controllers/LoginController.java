package ru.itis.jwtserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.jwtserver.dto.AccessTokenDto;
import ru.itis.jwtserver.dto.LoginPasswordDto;
import ru.itis.jwtserver.dto.RefreshTokenDto;
import ru.itis.jwtserver.exceptions.BannedTokenException;
import ru.itis.jwtserver.exceptions.BannedUserException;
import ru.itis.jwtserver.exceptions.IncorrectJwtException;
import ru.itis.jwtserver.exceptions.IncorrectUserDataException;
import ru.itis.jwtserver.services.LoginService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class LoginController {

    protected final LoginService loginService;

    @Operation(summary = "Login (get refresh token by login and password)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = RefreshTokenDto.class)
                    ))
            })
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPasswordDto loginPasswordDto){
        return ResponseEntity.ok(loginService.login(loginPasswordDto));
    }

    @Operation(summary = "Authenticate (get access token by refresh token)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccessTokenDto.class)
                    ))
            })
    })
    @PostMapping(
            value = "/auth",
            headers = {"JWT"}
    )
    public ResponseEntity<?> getAccessToken(HttpServletRequest request){
        return ResponseEntity.ok(loginService.authenticate(RefreshTokenDto.builder()
                .token(request.getHeader("JWT"))
                .build()));
    }

}
