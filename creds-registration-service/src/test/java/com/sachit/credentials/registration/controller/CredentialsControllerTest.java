package com.sachit.credentials.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachit.credentials.registration.constants.CredentialsApplicationTestConstants;
import com.sachit.credentials.registration.exception.CredentialNotFoundException;
import com.sachit.credentials.registration.model.CredentialsResponseDTO;
import com.sachit.credentials.registration.service.CredentialsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CredentialsController.class)
public class CredentialsControllerTest {

    @MockBean
    CredentialsService credentialsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetAllCredentials() throws Exception {
        //Arrange data
        List<CredentialsResponseDTO> credList = List.of(CredentialsApplicationTestConstants.RESPONSE_1,CredentialsApplicationTestConstants.RESPONSE_2);

        //mock data
        when(credentialsService.fetchAllCredentials(1L)).thenReturn(credList);

        //Act and Assert
        mockMvc.perform(get("/credentials/")
                        .param("organizationId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].clientId").value("123-456"))
                .andExpect(jsonPath("$[0].clientSecret").value("******"))
                .andExpect(jsonPath("$[0].creationDate").value(CredentialsApplicationTestConstants.CREATION_DATE.toString()))
                .andExpect(jsonPath("$[0].expiryDate").value(CredentialsApplicationTestConstants.EXPIRY_DATE.toString()))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].organizationId").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].clientId").value("456-123"))
                .andExpect(jsonPath("$[1].clientSecret").value("******"))
                .andExpect(jsonPath("$[1].creationDate").value(CredentialsApplicationTestConstants.CREATION_DATE.toString()))
                .andExpect(jsonPath("$[1].expiryDate").value(CredentialsApplicationTestConstants.EXPIRY_DATE.toString()))
                .andExpect(jsonPath("$[1].userId").value(2L))
                .andExpect(jsonPath("$[1].organizationId").value(1L));
    }

    @Test
    public void testGetCredentialById_success() throws Exception {
        //mock data
        when(credentialsService.getCredentialById(1L)).thenReturn(CredentialsApplicationTestConstants.RESPONSE_1);

        //Act and Assert
        mockMvc.perform(get("/credentials/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clientId").value("123-456"))
                .andExpect(jsonPath("$.clientSecret").value("******"))
                .andExpect(jsonPath("$.creationDate").value(CredentialsApplicationTestConstants.CREATION_DATE.toString()))
                .andExpect(jsonPath("$.expiryDate").value(CredentialsApplicationTestConstants.EXPIRY_DATE.toString()))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.organizationId").value(1L));
    }

    @Test
    public void testGetCredentialById_NotFound() throws Exception {
        //Arrange data
        Long credentialId = 1L;
        //mock data
        when(credentialsService.getCredentialById(credentialId)).thenThrow(new CredentialNotFoundException(CredentialsApplicationTestConstants.CREDENTIAL_NOT_FOUND+credentialId));

        //Act and Assert
        mockMvc.perform(get("/credentials/{id}",credentialId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createCredential_success() throws Exception {

        //mock data
        when(credentialsService.createCredentials(CredentialsApplicationTestConstants.REQUEST)).thenReturn(CredentialsApplicationTestConstants.UNMASKED_RESPONSE);

        //Act and Assert
        mockMvc.perform(post("/credentials/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CredentialsApplicationTestConstants.REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clientId").value("123-456"))
                .andExpect(jsonPath("$.clientSecret").value("abcdyeih1234qwer"))
                .andExpect(jsonPath("$.creationDate").value(CredentialsApplicationTestConstants.CREATION_DATE.toString()))
                .andExpect(jsonPath("$.expiryDate").value(CredentialsApplicationTestConstants.EXPIRY_DATE.toString()))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.organizationId").value(1L));

    }

    @Test
    public void createCredentials_throwException() throws Exception {
        //mock data
        when(credentialsService.createCredentials(CredentialsApplicationTestConstants.REQUEST)).thenThrow(new RuntimeException("Error during Creation"));

        //Act and Assert
        mockMvc.perform(post("/credentials/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CredentialsApplicationTestConstants.REQUEST)))
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void updateCredentials_success() throws Exception {

        //mock data
        when(credentialsService.updateCredentialsById(1L)).thenReturn(CredentialsApplicationTestConstants.UNMASKED_RESPONSE);

        //Act and Assert
        mockMvc.perform(patch("/credentials/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clientId").value("123-456"))
                .andExpect(jsonPath("$.clientSecret").value("abcdyeih1234qwer"))
                .andExpect(jsonPath("$.creationDate").value(CredentialsApplicationTestConstants.CREATION_DATE.toString()))
                .andExpect(jsonPath("$.expiryDate").value(CredentialsApplicationTestConstants.EXPIRY_DATE.toString()))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.organizationId").value(1L));

    }

    @Test
    public void updateCredentials_notFound() throws Exception {
        //Arrange data
        Long credentialId = 1L;
        //mock data
        when(credentialsService.updateCredentialsById(credentialId)).thenThrow(new CredentialNotFoundException(CredentialsApplicationTestConstants.CREDENTIAL_NOT_FOUND+credentialId));

        //Act and Assert
        mockMvc.perform(patch("/credentials/{id}",credentialId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteCredentials_success() throws Exception {
        //mock data
        doNothing().when(credentialsService).deleteCredentialById(1L);

        //Act and Assert
        mockMvc.perform(delete("/credentials/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCredentials_notFound() throws Exception {
        //mock data
        doThrow(new CredentialNotFoundException(CredentialsApplicationTestConstants.CREDENTIAL_NOT_FOUND+1L)).when(credentialsService).deleteCredentialById(1L);

        //Act and Assert
        mockMvc.perform(delete("/credentials/{id}",1L))
                .andExpect(status().isNotFound());
    }

}
