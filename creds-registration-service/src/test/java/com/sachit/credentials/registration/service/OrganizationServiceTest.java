package com.sachit.credentials.registration.service;

import com.sachit.credentials.registration.constants.OrganizationTestConstants;
import com.sachit.credentials.registration.entity.Organization;
import com.sachit.credentials.registration.exception.OrganizationNotFoundException;
import com.sachit.credentials.registration.mapper.OrganizationMapper;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;
import com.sachit.credentials.registration.respository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrganizationServiceTest {

    @MockBean
    OrganizationRepository organizationRepository;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    OrganizationMapper organizationMapper;

    @Test
    public void testGetAllOrganizations_returnResponse() {
        //Arrange data
        List<Organization> orgList = List.of(OrganizationTestConstants.ENTITY_1,OrganizationTestConstants.ENTITY_2);


        //mock data
        when(organizationRepository.findAll()).thenReturn(orgList);

        //Act
        List<OrganizationResponseDTO> result = organizationService.fetchAllOrganizations();

        //Assert
        assertEquals(2,result.size());
        assertEquals(result.get(0).getId(),OrganizationTestConstants.ENTITY_1.getId());
        assertEquals(result.get(1).getId(),OrganizationTestConstants.ENTITY_2.getId());
        verify(organizationRepository).findAll();
    }

    @Test
    public void testGetOrganizationById_throwException(){
        //Arrange data
        Optional<Organization> org = Optional.empty();

        //mock data
        when(organizationRepository.findById(1L)).thenReturn(org);

        //Act and Assert
        assertThrows(OrganizationNotFoundException.class,()->organizationService.getOrganizationById(1L));
        verify(organizationRepository).findById(1L);
    }

    @Test
    public void testGetOrganizationById_returnResponse() throws OrganizationNotFoundException {
        //Arrange data
        Optional<Organization> org = Optional.of(OrganizationTestConstants.ENTITY_1);

        //mock data
        when(organizationRepository.findById(1L)).thenReturn(org);

        //Act
        OrganizationResponseDTO result = organizationService.getOrganizationById(1L);

        //Assert
        assertEquals(1L,result.getId());
        assertEquals("Org 1",result.getName());
        assertEquals("SAP 1",result.getSapId());
        assertEquals("VAT 1",result.getVatNumber());

        verify(organizationRepository).findById(1L);
    }

    @Test
    public void testCreateOrganization_returnResponse() {
        //mock data
        when(organizationRepository.save(organizationMapper.toOrganization(OrganizationTestConstants.REQUEST))).thenReturn(OrganizationTestConstants.ENTITY_1);

        //Act
        OrganizationResponseDTO result = organizationService.createOrganization(OrganizationTestConstants.REQUEST);

        //Assert
        assertEquals(1L,result.getId(),1L);
        assertEquals("Org 1",result.getName());
        assertEquals("SAP 1",result.getSapId());
        assertEquals("VAT 1",result.getVatNumber());
        verify(organizationRepository).save(organizationMapper.toOrganization(OrganizationTestConstants.REQUEST));
    }

    @Test
    public void testCreateOrganization_throwException() {
        //mock data
        when(organizationRepository.save(organizationMapper.toOrganization(OrganizationTestConstants.REQUEST))).thenThrow(new RuntimeException("Error during create"));

        //Act and Assert
        assertThrows(RuntimeException.class,()->organizationService.createOrganization(OrganizationTestConstants.REQUEST));
    }

    @Test
    public void testUpdateOrganization_returnResponse() throws OrganizationNotFoundException {
        //Arrange data
        Long id = 2L;
        Organization org = organizationMapper.toOrganization(OrganizationTestConstants.REQUEST);
        org.setId(id);

        //mock data
        when(organizationRepository.existsById(id)).thenReturn(true);
        when(organizationRepository.save(org)).thenReturn(OrganizationTestConstants.ENTITY_2);

        //Act
        OrganizationResponseDTO result = organizationService.updateOrganizationById(id,OrganizationTestConstants.REQUEST);

        //Assert
        assertEquals(2L,result.getId());
        assertEquals("Org 2",result.getName());
        assertEquals("SAP 2",result.getSapId());
        assertEquals("VAT 2",result.getVatNumber());
        verify(organizationRepository).existsById(id);
        verify(organizationRepository).save(OrganizationTestConstants.ENTITY_2);
    }

    @Test
    public void testUpdateOrganization_throwException(){
        //mock data
        when(organizationRepository.existsById(1L)).thenReturn(false);

        //Act and Assert
        assertThrows(OrganizationNotFoundException.class,()->organizationService.updateOrganizationById(1L,OrganizationTestConstants.REQUEST));
        verify(organizationRepository).existsById(1L);
    }

    @Test
    public void testDeleteOrganization_success() throws OrganizationNotFoundException {
        //Arrange data
        Long id = 1L;

        //mock data
        when(organizationRepository.existsById(id)).thenReturn(true);
        doNothing().when(organizationRepository).deleteById(id);

        //Act
        organizationService.deleteOrganizationById(id);

        //Assert
        verify(organizationRepository).existsById(id);
        verify(organizationRepository).deleteById(id);
    }

    @Test
    public void testDeleteOrganization_NotFound(){
        //mock data
        when(organizationRepository.existsById(1L)).thenReturn(false);

        //Act and Assert
        assertThrows(OrganizationNotFoundException.class,()->organizationService.deleteOrganizationById(1L));
        verify(organizationRepository).existsById(1L);
    }

}
