package ru.itis.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.backend.dto.AuthorizationForm;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.services.AuthorizationService;

@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    @NonNull
    protected AuthorizationService authorizationService;

    @Operation(summary = "Authorize user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success authorization", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<UserDto> banUserById(@RequestBody AuthorizationForm authorizationForm){
        return ResponseEntity.ok(authorizationService.authorizeUser(authorizationForm));
    }

}
