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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.jwtserver.dto.AccessTokenResponse;
import ru.itis.jwtserver.dto.ModuleAuthorizationForm;
import ru.itis.jwtserver.dto.RefreshTokenResponse;
import ru.itis.jwtserver.dto.UserAuthorizationForm;
import ru.itis.jwtserver.services.ModuleLoginService;
import ru.itis.jwtserver.services.UserLoginService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class LoginController {

    protected final ModuleLoginService loginService;
    protected final UserLoginService userLoginService;

    @Operation(summary = "Login a module(get refresh token by login and password)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = RefreshTokenResponse.class)
                    ))
            })
    })
    @PostMapping("/login-module")
    public ResponseEntity<?> loginModule(@RequestBody ModuleAuthorizationForm moduleAuthorizationForm){
        return ResponseEntity.ok(loginService.login(moduleAuthorizationForm));
    }

    @Operation(summary = "Authenticate a module (get access token by refresh token)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccessTokenResponse.class)
                    ))
            })
    })
    @PostMapping(
            value = "/auth-module",
            headers = {"refresh-token"}
    )
    public ResponseEntity<?> authenticateModule(HttpServletRequest request){
        return ResponseEntity.ok(loginService.authenticate(RefreshTokenResponse.builder()
                .token(request.getHeader("refresh-token"))
                .build()));
    }

    @Operation(summary = "Login a user (get refresh token by login and password)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = RefreshTokenResponse.class)
                    ))
            })
    })
    @PostMapping(
            value = "/login-user"
    )
    public ResponseEntity<?> loginUser(@RequestBody UserAuthorizationForm form){
        return ResponseEntity.ok(userLoginService.login(form));
    }

    @Operation(summary = "Authenticate a user (get access token by refresh token)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = AccessTokenResponse.class)
                    ))
            })
    })
    @PostMapping(
            value = "/auth-user",
            headers = {"refresh-token"}
    )
    public ResponseEntity<?> authenticateUser(HttpServletRequest request){
        return ResponseEntity.ok(userLoginService.authenticate(RefreshTokenResponse.builder()
                .token(request.getHeader("refresh-token"))
                .build()));
    }

}
