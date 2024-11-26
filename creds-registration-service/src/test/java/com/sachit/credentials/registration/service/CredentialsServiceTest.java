package com.sachit.credentials.registration.service;

import com.sachit.credentials.registration.constants.CredentialsApplicationTestConstants;
import com.sachit.credentials.registration.entity.Credentials;
import com.sachit.credentials.registration.exception.CredentialNotFoundException;
import com.sachit.credentials.registration.mapper.CredentialsMapper;
import com.sachit.credentials.registration.model.CredentialsResponseDTO;
import com.sachit.credentials.registration.respository.CredentialsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CredentialsServiceTest {

    @MockBean
    CredentialsRepository credentialsRepository;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private CredentialsMapper credentialsMapper;

    @Test
    public void testGetAllCredentials_returnResponse() {
        //Arrange data
        List<Credentials> credList = List.of(CredentialsApplicationTestConstants.ENTITY_1,CredentialsApplicationTestConstants.ENTITY_2);


        //mock data
        when(credentialsRepository.findAllByOrganizationId(1L)).thenReturn(credList);

        //Act
        List<CredentialsResponseDTO> result = credentialsService.fetchAllCredentials(1L);

        //Assert
        assertEquals(2,result.size());
        assertEquals(result.get(0).getId(),CredentialsApplicationTestConstants.ENTITY_1.getId());
        assertEquals(result.get(1).getId(),CredentialsApplicationTestConstants.ENTITY_2.getId());
        verify(credentialsRepository).findAllByOrganizationId(1L);
    }

    @Test
    public void testGetCredentialById_throwException(){
        //Arrange data
        Optional<Credentials> cred = Optional.empty();

        //mock data
        when(credentialsRepository.findById(1L)).thenReturn(cred);

        //Act and Assert
        assertThrows(CredentialNotFoundException.class,()->credentialsService.getCredentialById(1L));
        verify(credentialsRepository).findById(1L);
    }

    @Test
    public void testGetCredentialById_returnResponse() throws CredentialNotFoundException {
        //Arrange data
        Optional<Credentials> cred = Optional.of(CredentialsApplicationTestConstants.ENTITY_1);

        //mock data
        when(credentialsRepository.findById(1L)).thenReturn(cred);

        //Act
        CredentialsResponseDTO result = credentialsService.getCredentialById(1L);

        //Assert
        assertEquals(1L,result.getId());
        assertEquals("123",result.getClientId());
        assertEquals(1L,result.getUserId());
        assertEquals(1L,result.getOrganizationId());

        verify(credentialsRepository).findById(1L);
    }

    @Test
    public void testCreateCredentials_returnResponse() {
        //mock data
        when(credentialsRepository.save(any())).thenReturn(CredentialsApplicationTestConstants.ENTITY_1);

        //Act
        CredentialsResponseDTO result = credentialsService.createCredentials(CredentialsApplicationTestConstants.REQUEST);

        //Assert
        assertEquals(1L,result.getId());
        assertEquals("123",result.getClientId());
        assertEquals(1L,result.getUserId());
        assertEquals(1L,result.getOrganizationId());
        verify(credentialsRepository).save(any());
    }

    @Test
    public void testCreateCredentials_throwException() {
        //mock data
        when(credentialsRepository.save(any())).thenThrow(new RuntimeException("Error during create"));

        //Act and Assert
        assertThrows(RuntimeException.class,()->credentialsService.createCredentials(CredentialsApplicationTestConstants.REQUEST));
    }

    @Test
    public void testUpdateCredentials_returnResponse() throws CredentialNotFoundException {
        //Arrange data
        Long id = 2L;
        Optional<Credentials> cred = Optional.of(credentialsMapper.toCredentials(CredentialsApplicationTestConstants.REQUEST));
        cred.get().setId(id);

        //mock data
        when(credentialsRepository.findById(id)).thenReturn(cred);
        when(credentialsRepository.save(any())).thenReturn(CredentialsApplicationTestConstants.ENTITY_2);

        //Act
        CredentialsResponseDTO result = credentialsService.updateCredentialsById(id);

        //Assert
        assertEquals(2L,result.getId());
        assertEquals("456",result.getClientId());
        assertEquals(2L,result.getUserId());
        assertEquals(1L,result.getOrganizationId());
        verify(credentialsRepository).findById(id);
        verify(credentialsRepository).save(any());
    }

    @Test
    public void testUpdateCredentials_throwException(){
        Optional<Credentials> cred = Optional.empty();

        //mock data
        when(credentialsRepository.findById(1L)).thenReturn(cred);

        //Act and Assert
        assertThrows(CredentialNotFoundException.class,()->credentialsService.updateCredentialsById(1L));
        verify(credentialsRepository).findById(1L);
    }

    @Test
    public void testDeleteCredentials_success() throws CredentialNotFoundException {
        //Arrange data
        Long id = 1L;

        //mock data
        when(credentialsRepository.existsById(id)).thenReturn(true);
        doNothing().when(credentialsRepository).deleteById(id);

        //Act
        credentialsService.deleteCredentialById(id);

        //Assert
        verify(credentialsRepository).existsById(id);
        verify(credentialsRepository).deleteById(id);
    }

    @Test
    public void testDeleteCredentials_NotFound(){
        //mock data
        when(credentialsRepository.existsById(1L)).thenReturn(false);

        //Act and Assert
        assertThrows(CredentialNotFoundException.class,()->credentialsService.deleteCredentialById(1L));
        verify(credentialsRepository).existsById(1L);
    }


}
