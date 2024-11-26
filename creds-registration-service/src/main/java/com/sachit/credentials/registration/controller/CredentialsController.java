package com.sachit.credentials.registration.controller;

import com.sachit.credentials.registration.exception.CredentialNotFoundException;
import com.sachit.credentials.registration.model.CredentialsRequestDTO;
import com.sachit.credentials.registration.model.CredentialsResponseDTO;
import com.sachit.credentials.registration.service.CredentialsService;
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
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class CredentialsController {

    private final CredentialsService credentialsService;

    @Operation(summary = "Get result of all Credentials based on Org id",
            description = "This API returns all credentials present based on Org id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of Credentials",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = CredentialsResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to fetch the Credentials details",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/")
    public List<CredentialsResponseDTO> getAllCredentials(@RequestParam Long organizationId) {
            return credentialsService.fetchAllCredentials(organizationId);
    }

    @Operation(summary = "Get specific Credentials based on Credential id",
            description = "This API returns Credentials based on the Credential id specified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific Credentials",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = CredentialsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Credential Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/{id}")
    public CredentialsResponseDTO getCredentialById(@PathVariable Long id) throws CredentialNotFoundException {
            return credentialsService.getCredentialById(id);
    }

    @Operation(summary = "Create a new Credential",
            description = "This API will create a new Credential based on the information supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the Credentials",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = CredentialsResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to create Credentials",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PostMapping("/")
    public CredentialsResponseDTO createCredential(@RequestBody CredentialsRequestDTO request) {
            return credentialsService.createCredentials(request);
    }

    @Operation(summary = "Update Client Secret for requested id",
            description = "This API will update the Client secret for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the Credential details",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = CredentialsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Credential Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Unable to update Credential",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PatchMapping("/{id}")
    public CredentialsResponseDTO updateCredentials( @PathVariable Long id) throws CredentialNotFoundException {
            return credentialsService.updateCredentialsById(id);
    }

    @Operation(summary = "Delete Credentials based on requested id",
            description = "This API will delete the credential details for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the credential details",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Credentials Not found with the given id",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCredentials(@PathVariable Long id) throws CredentialNotFoundException {
            credentialsService.deleteCredentialById(id);
            return ResponseEntity.noContent().build();
    }

}
