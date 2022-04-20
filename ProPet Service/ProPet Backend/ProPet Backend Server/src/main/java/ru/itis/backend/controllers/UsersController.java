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
import org.springframework.web.bind.annotation.*;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.services.UsersService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {

    @NonNull
    protected UsersService usersService;

    @Operation(summary = "Getting all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @GetMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @Operation(summary = "Getting user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-id/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(usersService.findById(id));
    }

    @Operation(summary = "Getting user by login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-login/{login}",
            headers = {"JWT"}
    )
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login){
        return ResponseEntity.ok(usersService.findUserByLogin(login));
    }

    @Operation(summary = "Getting user by mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @GetMapping(
            value = "/by-mail/{mail}",
            headers = {"JWT"}
    )
    public ResponseEntity<UserDto> getUserByMail(@PathVariable String mail){
        return ResponseEntity.ok(usersService.findUserByMail(mail));
    }

    @Operation(summary = "Adding a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success adding", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @PostMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(usersService.add(userDto));
    }

    @Operation(summary = "Updating user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @PatchMapping(
            headers = {"JWT"}
    )
    public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto){
        return ResponseEntity.ok(usersService.update(userDto));
    }

    @Operation(summary = "Deleting user by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting")
    })
    @DeleteMapping(
            value = "/{id}",
            headers = {"JWT"}
    )
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        usersService.delete(UserDto.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate user's account by the link id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success activation", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = UserDto.class)
                    ))
            })
    })
    @PostMapping(
            value = "/activate/{linkValue}",
            headers = {"JWT"}
    )
    public ResponseEntity<UserDto> activateAccount(@PathVariable String linkValue){
        return ResponseEntity.ok(usersService.activateUser(linkValue));
    }


}
