package com.sachit.credentials.registration.service;

import com.sachit.credentials.registration.constants.OrganizationTestConstants;
import com.sachit.credentials.registration.constants.UserOrgMappingTestConstants;
import com.sachit.credentials.registration.entity.UserOrganizationMapping;
import com.sachit.credentials.registration.exception.OrganizationNotFoundException;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;
import com.sachit.credentials.registration.model.UserOrgMappingRequestDTO;
import com.sachit.credentials.registration.model.UserOrgMappingResponseDTO;
import com.sachit.credentials.registration.respository.UserOrganizationMappingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserOrganizationMappingServiceTest {

    @MockBean
    UserOrganizationMappingRepository userOrganizationMappingRepository;

    @MockBean
    OrganizationService organizationService;

    @Autowired
    UserOrganizationMappingService userOrganizationMappingService;

    @Test
    public void testDeleteUserOrganizationMappingByUserId_Success(){
        //Arrange
        Long userId = 1L;

        //Mock
        doNothing().when(userOrganizationMappingRepository).deleteAllByUserId(userId);

        //Act
        userOrganizationMappingService.deleteUserOrganizationMappingByUserId(userId);

        //Assert
        verify(userOrganizationMappingRepository).deleteAllByUserId(userId);
    }

    @Test
    public void testGetAllOrganizationsByUserId_success() throws OrganizationNotFoundException {
        //Arrange
        Long userId = 1L;
        List<UserOrganizationMapping> userOrganizationMappingList = List.of(UserOrgMappingTestConstants.ENTITY_1,UserOrgMappingTestConstants.ENTITY_2);

        //Mock
        when(userOrganizationMappingRepository.findByUserId(userId)).thenReturn(userOrganizationMappingList);
        when(organizationService.getOrganizationById(1L)).thenReturn(OrganizationTestConstants.RESPONSE_1);
        when(organizationService.getOrganizationById(2L)).thenReturn(OrganizationTestConstants.RESPONSE_2);

        //Act
        List<OrganizationResponseDTO> result = userOrganizationMappingService.getAllOrganizationsByUserId(userId);

        //Assert
        assertEquals(2,result.size());
        assertEquals(1L,result.get(0).getId());
        assertEquals(2L,result.get(1).getId());
        verify(userOrganizationMappingRepository).findByUserId(userId);
        verify(organizationService).getOrganizationById(1L);
        verify(organizationService).getOrganizationById(2L);
    }

    @Test
    public void testCreateUserOrgMapping_success() {
        //Arrange
        UserOrgMappingRequestDTO requestDTO = UserOrgMappingRequestDTO.builder().userId(1L).organizationIds(List.of(1L,2L)).build();

        //Mock
        when(userOrganizationMappingRepository.save(any())).thenReturn(UserOrgMappingTestConstants.ENTITY_1);

        //Act
        UserOrgMappingResponseDTO result = userOrganizationMappingService.createUserOrgMapping(requestDTO);

        //Assert
        assertEquals(1L,result.getUserId());
        assertEquals(1L,result.getOrganizationIds().get(0));
        assertEquals(2L,result.getOrganizationIds().get(1));
        verify(userOrganizationMappingRepository,times(2)).save(any());
    }
}
