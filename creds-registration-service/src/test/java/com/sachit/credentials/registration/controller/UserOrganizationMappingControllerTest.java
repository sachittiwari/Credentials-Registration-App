package com.sachit.credentials.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachit.credentials.registration.constants.OrganizationTestConstants;
import com.sachit.credentials.registration.constants.UserOrgMappingTestConstants;
import com.sachit.credentials.registration.service.UserOrganizationMappingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserOrganizationMappingController.class)
public class UserOrganizationMappingControllerTest {

    @MockBean
    UserOrganizationMappingService userOrganizationMappingService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetOrganizationsByUserId_success() throws Exception {
        //mock data
        when(userOrganizationMappingService.getAllOrganizationsByUserId(1L)).thenReturn(List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));

        //Act and Assert
        mockMvc.perform(get("/user-org-mapping/")
                        .param("userId","1"))
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
    public void createUserOrgMapping_success() throws Exception {

        //mock data
        when(userOrganizationMappingService.createUserOrgMapping(UserOrgMappingTestConstants.REQUEST)).thenReturn(UserOrgMappingTestConstants.RESPONSE);

        //Act and Assert
        mockMvc.perform(post("/user-org-mapping/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UserOrgMappingTestConstants.REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.organizationIds",hasSize(2)));

    }

    @Test
    public void createUserOrgMapping_throwException() throws Exception {
        //mock data
        when(userOrganizationMappingService.createUserOrgMapping(UserOrgMappingTestConstants.REQUEST)).thenThrow(new RuntimeException("Error during Creation"));

        //Act and Assert
        mockMvc.perform(post("/user-org-mapping/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UserOrgMappingTestConstants.REQUEST)))
                .andExpect(status().is5xxServerError());

    }
}
