package com.sachit.credentials.registration.controller;

import com.sachit.credentials.registration.exception.OrganizationNotFoundException;
import com.sachit.credentials.registration.model.OrganizationRequestDTO;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;
import com.sachit.credentials.registration.service.OrganizationService;
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
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @Operation(summary = "Get result of all Organizations",
            description = "This API returns all Organizations present")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of Organizations",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = OrganizationResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to fetch the Organization details",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/")
    public List<OrganizationResponseDTO> getAllOrganizations() {
        return organizationService.fetchAllOrganizations();
    }

    @Operation(summary = "Get specific Organization details based on Org id",
            description = "This API returns Organization details based on the id specified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific Organization",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = OrganizationResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Organization Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/{id}")
    public OrganizationResponseDTO getOrganizationById(@PathVariable Long id) throws OrganizationNotFoundException {
        return organizationService.getOrganizationById(id);
    }

    @Operation(summary = "Create a new Organization",
            description = "This API will create a new Organization based on the information supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the Organization",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = OrganizationResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to create Organization",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PostMapping("/")
    public OrganizationResponseDTO createOrganization(@RequestBody OrganizationRequestDTO request) {
        return organizationService.createOrganization(request);
    }

    @Operation(summary = "Update Organization details for requested id",
            description = "This API will update the Organization details for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the Organization details",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = OrganizationResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Organization Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Unable to update Organization",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PutMapping("/{id}")
    public OrganizationResponseDTO updateOrganization( @PathVariable Long id,@RequestBody OrganizationRequestDTO request) throws OrganizationNotFoundException {
        return organizationService.updateOrganizationById(id,request);
    }

    @Operation(summary = "Delete Organization based on requested id",
            description = "This API will delete the Organization for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the Organization",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Organization Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable Long id) throws OrganizationNotFoundException {
        organizationService.deleteOrganizationById(id);
        return ResponseEntity.noContent().build();
    }

}
