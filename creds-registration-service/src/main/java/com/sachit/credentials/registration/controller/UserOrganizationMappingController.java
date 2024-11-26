package com.sachit.credentials.registration.controller;

import com.sachit.credentials.registration.model.*;
import com.sachit.credentials.registration.service.UserOrganizationMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-org-mapping")
@RequiredArgsConstructor
public class UserOrganizationMappingController {

    private final UserOrganizationMappingService userOrganizationMappingService;

    @Operation(summary = "Get Organization details based on User Id",
            description = "This API returns Organization details based on the user id specified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific Organization details",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = UserOrgMappingResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Organization Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/")
    public List<OrganizationResponseDTO> getOrganizationsByUserId(@RequestParam Long userId){
        return userOrganizationMappingService.getAllOrganizationsByUserId(userId);
    }

    @Operation(summary = "Create a new User Organization Mapping",
            description = "This API will create a new User Organization Mapping based on the information supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the Mapping",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = UserOrgMappingResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to create Mapping",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PostMapping("/")
    public UserOrgMappingResponseDTO createUserOrgMapping(@RequestBody UserOrgMappingRequestDTO request) {
        return userOrganizationMappingService.createUserOrgMapping(request);
    }
}
