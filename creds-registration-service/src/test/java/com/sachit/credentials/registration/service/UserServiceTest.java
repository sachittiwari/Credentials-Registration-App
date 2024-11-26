package com.sachit.credentials.registration.service;

import com.sachit.credentials.registration.constants.OrganizationTestConstants;
import com.sachit.credentials.registration.constants.UserTestConstants;
import com.sachit.credentials.registration.entity.User;
import com.sachit.credentials.registration.exception.UserNotFoundException;
import com.sachit.credentials.registration.mapper.UserMapper;
import com.sachit.credentials.registration.model.UserResponseDTO;
import com.sachit.credentials.registration.respository.UserRepository;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserOrganizationMappingService userOrganizationMappingService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetAllUsers_returnResponse() {
        //Arrange data
        List<User> userList = List.of(UserTestConstants.ENTITY_1,UserTestConstants.ENTITY_2);

        //mock data
        when(userRepository.findAll()).thenReturn(userList);
        when(userOrganizationMappingService.getAllOrganizationsByUserId(1L)).thenReturn(List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));
        when(userOrganizationMappingService.getAllOrganizationsByUserId(2L)).thenReturn(List.of(OrganizationTestConstants.RESPONSE_2));

        //Act
        List<UserResponseDTO> result = userService.fetchAllUsers();

        //Assert
        assertEquals(2,result.size());
        assertEquals(result.get(0).getOrganizations(),List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));
        assertEquals(result.get(1).getOrganizations(),List.of(OrganizationTestConstants.RESPONSE_2));
        assertEquals("Sachit",result.get(0).getName());
        assertEquals("Sachin",result.get(1).getName());
        verify(userRepository).findAll();
        verify(userOrganizationMappingService).getAllOrganizationsByUserId(1L);
        verify(userOrganizationMappingService).getAllOrganizationsByUserId(2L);
    }

    @Test
    public void testGetUserById_throwException(){
        //Arrange data
        Optional<User> user = Optional.empty();

        //mock data
        when(userRepository.findById(1L)).thenReturn(user);

        //Act and Assert
        assertThrows(UserNotFoundException.class,()->userService.getUserById(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    public void testGetUserById_returnResponse() throws UserNotFoundException {
        //Arrange data
        Optional<User> user = Optional.of(UserTestConstants.ENTITY_1);

        //mock data
        when(userRepository.findById(1L)).thenReturn(user);
        when(userOrganizationMappingService.getAllOrganizationsByUserId(1L)).thenReturn(List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));

        //Act
        UserResponseDTO result = userService.getUserById(1L);

        //Assert
        assertEquals(1L,result.getId());
        assertEquals("Sachit",result.getName());
        assertEquals("Sachit",result.getFirstName());
        assertEquals("Tiwari",result.getLastName());
        assertEquals("ST",result.getSubjectId());
        assertEquals(result.getOrganizations(),List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));

        verify(userRepository).findById(1L);
        verify(userOrganizationMappingService).getAllOrganizationsByUserId(1L);
    }


    @Test
    public void testHandleLogin_userCreated() {
        //mock data
        when(userRepository.save(userMapper.toUser(UserTestConstants.REQUEST))).thenReturn(UserTestConstants.ENTITY_1);
        when(userOrganizationMappingService.getAllOrganizationsByUserId(1L)).thenReturn(List.of());

        //Act
        UserResponseDTO result = userService.handleLogin(UserTestConstants.REQUEST);

        //Assert
        assertEquals(1L,result.getId());
        assertEquals("Sachit",result.getName());
        assertEquals("Sachit",result.getFirstName());
        assertEquals("Tiwari",result.getLastName());
        assertEquals("ST",result.getSubjectId());
        assertEquals(result.getOrganizations(),List.of());
        verify(userRepository).save(userMapper.toUser(UserTestConstants.REQUEST));
        verify(userOrganizationMappingService).getAllOrganizationsByUserId(1L);
    }

    @Test
    public void testHandleLogin_userExists() {
        //mock data
        when(userRepository.save(userMapper.toUser(UserTestConstants.REQUEST))).thenReturn(UserTestConstants.ENTITY_1);
        when(userOrganizationMappingService.getAllOrganizationsByUserId(1L)).thenReturn(List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));
        when(userRepository.findBySubjectId("ST")).thenReturn(UserTestConstants.ENTITY_1);

        //Act
        UserResponseDTO result = userService.handleLogin(UserTestConstants.REQUEST);

        //Assert
        assertEquals(1L,result.getId());
        assertEquals("Sachit",result.getName());
        assertEquals("Sachit",result.getFirstName());
        assertEquals("Tiwari",result.getLastName());
        assertEquals("ST",result.getSubjectId());
        assertEquals(result.getOrganizations(),List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));
        verify(userRepository).findBySubjectId("ST");
        verify(userOrganizationMappingService).getAllOrganizationsByUserId(1L);
    }

    @Test
    public void testHandleLogin_throwException() {
        //mock data
        when(userRepository.save(userMapper.toUser(UserTestConstants.REQUEST))).thenThrow(new RuntimeException("Error during create"));

        //Act and Assert
        assertThrows(RuntimeException.class,()->userService.handleLogin(UserTestConstants.REQUEST));
    }


    @Test
    public void testUpdateUser_returnResponse() throws UserNotFoundException {
        //Arrange data
        Long id = 1L;
        User user = userMapper.toUser(UserTestConstants.REQUEST);
        user.setId(id);

        //mock data
        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(UserTestConstants.ENTITY_1);
        when(userOrganizationMappingService.getAllOrganizationsByUserId(1L)).thenReturn(List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));

        //Act
        UserResponseDTO result = userService.updateUserById(id,UserTestConstants.REQUEST);

        //Assert
        assertEquals(1L,result.getId());
        assertEquals("Sachit",result.getName());
        assertEquals("Sachit",result.getFirstName());
        assertEquals("Tiwari",result.getLastName());
        assertEquals("ST",result.getSubjectId());
        assertEquals(result.getOrganizations(),List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2));
        verify(userRepository).existsById(id);
        verify(userRepository).save(user);
        verify(userOrganizationMappingService).getAllOrganizationsByUserId(1L);
    }

    @Test
    public void testUpdateUser_throwException(){
        //mock data
        when(userRepository.existsById(1L)).thenReturn(false);

        //Act and Assert
        assertThrows(UserNotFoundException.class,()->userService.updateUserById(1L,UserTestConstants.REQUEST));
        verify(userRepository).existsById(1L);
    }


    @Test
    public void testDeleteUser_success() throws UserNotFoundException {
        //Arrange data
        Long id = 1L;

        //mock data
        when(userRepository.existsById(id)).thenReturn(true);
        doNothing().when(userRepository).deleteById(id);
        doNothing().when(userOrganizationMappingService).deleteUserOrganizationMappingByUserId(id);


        //Act
        userService.deleteUserById(id);

        //Assert
        verify(userRepository).existsById(id);
        verify(userRepository).deleteById(id);
        verify(userOrganizationMappingService).deleteUserOrganizationMappingByUserId(id);
    }

    @Test
    public void testDeleteUser_NotFound(){
        //mock data
        when(userRepository.existsById(1L)).thenReturn(false);

        //Act and Assert
        assertThrows(UserNotFoundException.class,()->userService.deleteUserById(1L));
        verify(userRepository).existsById(1L);
    }
}
