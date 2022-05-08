package ru.itis.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.backend.annotations.JwtAccessConstraint;
import ru.itis.backend.utils.ImageLoader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/image")
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class ImagesController {

    protected final ImageLoader imageLoader;

    @Operation(summary = "Getting user's image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting"),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "463", description = "Can't load an image")
    })
    @GetMapping(
            value = "/user/{id}",
            headers = {"JWT"},
            produces = {
                    "image/png; charset=utf-8",
                    "image/jpeg; charset=utf-8"
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getImageForUser(@PathVariable Long id, HttpServletRequest request){
        return packResourceToResponse(imageLoader.getUserImage(id), request);
    }

    @Operation(summary = "Saving user's image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success upload"),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "464", description = "Can't store an image"),
            @ApiResponse(responseCode = "465", description = "Incorrect image type")
    })
    @PostMapping(
            value = "/user/{id}",
            headers = {"JWT"},
            consumes = {
                    "image/png; charset=utf-8",
                    "image/jpeg; charset=utf-8"
            }
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "id",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<?> uploadImageForUser(@RequestParam MultipartFile file, @PathVariable Long id){
        imageLoader.saveUserImage(file, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Getting pet's image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success getting"),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "463", description = "Can't load an image")
    })
    @GetMapping(
            value = "/pet/{userId}/{petId}",
            headers = {"JWT"},
            produces = {
                    "image/png; charset=utf-8",
                    "image/jpeg; charset=utf-8"
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getImageForPet(@PathVariable Long userId, @PathVariable Long petId,
                                            HttpServletRequest request){
        return packResourceToResponse(imageLoader.getPetImage(userId, petId), request);
    }

    @Operation(summary = "Saving pet's image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success upload"),
            @ApiResponse(responseCode = "403", description = "Unexpected exception in the branch being handled"),
            @ApiResponse(responseCode = "418", description = "Unexpected exception"),
            @ApiResponse(responseCode = "458", description = "Access denied"),
            @ApiResponse(responseCode = "464", description = "Can't store an image"),
            @ApiResponse(responseCode = "465", description = "Incorrect image type")
    })
    @PostMapping(
            value = "/pet/{userId}/{petId}",
            headers = {"JWT"},
            consumes = {
                    "image/png; charset=utf-8",
                    "image/jpeg; charset=utf-8"
            }
    )
    @PreAuthorize("isAuthenticated()")
    @JwtAccessConstraint(
            jwtFieldName = "account_id",
            argName = "userId",
            opRoles = true,
            jwtRoleFieldName = "role",
            opRolesArray = {"MODER", "ADMIN"}
    )
    public ResponseEntity<?> uploadImageForPet(@RequestParam MultipartFile file, @PathVariable Long userId,
                                               @PathVariable Long petId){
        imageLoader.savePetImage(file, userId, petId);
        return ResponseEntity.ok().build();
    }

    protected ResponseEntity<?> packResourceToResponse(Resource resource, HttpServletRequest request){
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
