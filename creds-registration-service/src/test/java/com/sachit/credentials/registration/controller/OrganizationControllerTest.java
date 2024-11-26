package com.sachit.credentials.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachit.credentials.registration.constants.OrganizationTestConstants;
import com.sachit.credentials.registration.exception.OrganizationNotFoundException;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;
import com.sachit.credentials.registration.service.OrganizationService;
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

@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTest {
    
    @MockBean
    OrganizationService organizationService;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllOrganizations() throws Exception {
        //Arrange data
        List<OrganizationResponseDTO> orgList = List.of(OrganizationTestConstants.RESPONSE_1, OrganizationTestConstants.RESPONSE_2);

        //mock data
        when(organizationService.fetchAllOrganizations()).thenReturn(orgList);

        //Act and Assert
        mockMvc.perform(get("/organization/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Org 1"))
                .andExpect(jsonPath("$[0].sapId").value("SAP 1"))
                .andExpect(jsonPath("$[0].vatNumber").value("VAT 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Org 2"))
                .andExpect(jsonPath("$[1].sapId").value("SAP 2"))
                .andExpect(jsonPath("$[1].vatNumber").value("VAT 2"));

    }

    @Test
    public void testGetOrganizationById_success() throws Exception {
        //mock data
        when(organizationService.getOrganizationById(1L)).thenReturn(OrganizationTestConstants.RESPONSE_1);

        //Act and Assert
        mockMvc.perform(get("/organization/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Org 1"))
                .andExpect(jsonPath("$.sapId").value("SAP 1"))
                .andExpect(jsonPath("$.vatNumber").value("VAT 1"));
    }

    @Test
    public void testGetOrganizationById_NotFound() throws Exception {
        //Arrange data
        Long orgId = 1L;
        //mock data
        when(organizationService.getOrganizationById(orgId)).thenThrow(new OrganizationNotFoundException(OrganizationTestConstants.ORGANIZATION_NOT_FOUND+ orgId));

        //Act and Assert
        mockMvc.perform(get("/organization/{id}", orgId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createOrganization_success() throws Exception {

        //mock data
        when(organizationService.createOrganization(OrganizationTestConstants.REQUEST)).thenReturn(OrganizationTestConstants.RESPONSE_1);

        //Act and Assert
        mockMvc.perform(post("/organization/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(OrganizationTestConstants.REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Org 1"))
                .andExpect(jsonPath("$.sapId").value("SAP 1"))
                .andExpect(jsonPath("$.vatNumber").value("VAT 1"));

    }

    @Test
    public void createOrganization_throwException() throws Exception {
        //mock data
        when(organizationService.createOrganization(OrganizationTestConstants.REQUEST)).thenThrow(new RuntimeException("Error during Creation"));

        //Act and Assert
        mockMvc.perform(post("/organization/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(OrganizationTestConstants.REQUEST)))
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void updateOrganization_success() throws Exception {

        //mock data
        when(organizationService.updateOrganizationById(1L, OrganizationTestConstants.REQUEST)).thenReturn(OrganizationTestConstants.RESPONSE_1);

        //Act and Assert
        mockMvc.perform(put("/organization/{id}",1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(OrganizationTestConstants.REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Org 1"))
                .andExpect(jsonPath("$.sapId").value("SAP 1"))
                .andExpect(jsonPath("$.vatNumber").value("VAT 1"));

    }

    @Test
    public void updateOrganization_notFound() throws Exception {
        //Arrange data
        Long organizationId = 1L;
        //mock data
        when(organizationService.updateOrganizationById(organizationId, OrganizationTestConstants.REQUEST)).thenThrow(new OrganizationNotFoundException(OrganizationTestConstants.ORGANIZATION_NOT_FOUND+ organizationId));

        //Act and Assert
        mockMvc.perform(put("/organization/{id}", organizationId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(OrganizationTestConstants.REQUEST)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteOrganization_success() throws Exception {
        //mock data
        doNothing().when(organizationService).deleteOrganizationById(1L);

        //Act and Assert
        mockMvc.perform(delete("/organization/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteOrganization_notFound() throws Exception {
        //mock data
        doThrow(new OrganizationNotFoundException(OrganizationTestConstants.ORGANIZATION_NOT_FOUND+1L)).when(organizationService).deleteOrganizationById(1L);

        //Act and Assert
        mockMvc.perform(delete("/organization/{id}",1L))
                .andExpect(status().isNotFound());
    }
    
    
    
    
}
