package com.sachit.credentials.registration.controller;

import com.sachit.credentials.registration.exception.UserNotFoundException;
import com.sachit.credentials.registration.model.UserRequestDTO;
import com.sachit.credentials.registration.model.UserResponseDTO;
import com.sachit.credentials.registration.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get result of all Users",
            description = "This API returns all Users present")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of Users",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to fetch the User details",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/")
    public List<UserResponseDTO> getAllUsers() {
        return userService.fetchAllUsers();
    }

    @Operation(summary = "Get specific User details",
            description = "This API returns User details based on the id specified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific User",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @Operation(summary = "Create a new User",
            description = "This API will create a new User based on the information supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the User",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to create User",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PostMapping("/")
    public UserResponseDTO handleLogin(@RequestBody UserRequestDTO request) {
        return userService.handleLogin(request);
    }

    @Operation(summary = "Update User details for requested id",
            description = "This API will update the User details for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the User details",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Unable to update user",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PutMapping("/{id}")
    public UserResponseDTO updateUser( @PathVariable Long id,@RequestBody UserRequestDTO request) throws UserNotFoundException {
        return userService.updateUserById(id,request);
    }

    @Operation(summary = "Delete User based on requested id",
            description = "This API will delete the User for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the User",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "User Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
