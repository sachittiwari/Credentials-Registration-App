package com.sachit.credentials.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachit.credentials.registration.constants.UserTestConstants;
import com.sachit.credentials.registration.exception.UserNotFoundException;
import com.sachit.credentials.registration.model.UserResponseDTO;
import com.sachit.credentials.registration.service.UserService;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetAllUsers() throws Exception {
        //Arrange data
        List<UserResponseDTO> userList = List.of(UserTestConstants.RESPONSE_1, UserTestConstants.RESPONSE_2);

        //mock data
        when(userService.fetchAllUsers()).thenReturn(userList);

        //Act and Assert
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].subjectId").value("ST"))
                .andExpect(jsonPath("$[0].name").value("Sachit"))
                .andExpect(jsonPath("$[0].firstName").value("Sachit"))
                .andExpect(jsonPath("$[0].lastName").value("Tiwari"))
                .andExpect(jsonPath("$[0].organizations",hasSize(2)))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].subjectId").value("STE"))
                .andExpect(jsonPath("$[1].name").value("Sachin"))
                .andExpect(jsonPath("$[1].firstName").value("Sachin"))
                .andExpect(jsonPath("$[1].lastName").value("Tiwari"))
                .andExpect(jsonPath("$[1].organizations",hasSize(1)));

    }

    @Test
    public void testGetUserById_success() throws Exception {
        //mock data
        when(userService.getUserById(1L)).thenReturn(UserTestConstants.RESPONSE_1);

        //Act and Assert
        mockMvc.perform(get("/user/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subjectId").value("ST"))
                .andExpect(jsonPath("$.name").value("Sachit"))
                .andExpect(jsonPath("$.firstName").value("Sachit"))
                .andExpect(jsonPath("$.lastName").value("Tiwari"))
                .andExpect(jsonPath("$.organizations",hasSize(2)));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        //Arrange data
        Long userId = 1L;
        //mock data
        when(userService.getUserById(userId)).thenThrow(new UserNotFoundException(UserTestConstants.USER_NOT_FOUND+ userId));

        //Act and Assert
        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void handleLogin_success() throws Exception {

        //mock data
        when(userService.handleLogin(UserTestConstants.REQUEST)).thenReturn(UserTestConstants.RESPONSE_1);

        //Act and Assert
        mockMvc.perform(post("/user/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UserTestConstants.REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subjectId").value("ST"))
                .andExpect(jsonPath("$.name").value("Sachit"))
                .andExpect(jsonPath("$.firstName").value("Sachit"))
                .andExpect(jsonPath("$.lastName").value("Tiwari"))
                .andExpect(jsonPath("$.organizations",hasSize(2)));

    }

    @Test
    public void handleLogin_throwException() throws Exception {
        //mock data
        when(userService.handleLogin(UserTestConstants.REQUEST)).thenThrow(new RuntimeException("Error during Creation"));

        //Act and Assert
        mockMvc.perform(post("/user/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UserTestConstants.REQUEST)))
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void updateUser_success() throws Exception {

        //mock data
        when(userService.updateUserById(1L, UserTestConstants.REQUEST)).thenReturn(UserTestConstants.RESPONSE_1);

        //Act and Assert
        mockMvc.perform(put("/user/{id}",1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UserTestConstants.REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subjectId").value("ST"))
                .andExpect(jsonPath("$.name").value("Sachit"))
                .andExpect(jsonPath("$.firstName").value("Sachit"))
                .andExpect(jsonPath("$.lastName").value("Tiwari"))
                .andExpect(jsonPath("$.organizations",hasSize(2)));

    }

    @Test
    public void updateUser_notFound() throws Exception {
        //Arrange data
        Long userId = 1L;
        //mock data
        when(userService.updateUserById(userId, UserTestConstants.REQUEST)).thenThrow(new UserNotFoundException(UserTestConstants.USER_NOT_FOUND+ userId));

        //Act and Assert
        mockMvc.perform(put("/user/{id}", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UserTestConstants.REQUEST)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteUser_success() throws Exception {
        //mock data
        doNothing().when(userService).deleteUserById(1L);

        //Act and Assert
        mockMvc.perform(delete("/user/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUser_notFound() throws Exception {
        //mock data
        doThrow(new UserNotFoundException(UserTestConstants.USER_NOT_FOUND+1L)).when(userService).deleteUserById(1L);

        //Act and Assert
        mockMvc.perform(delete("/user/{id}",1L))
                .andExpect(status().isNotFound());
    }


}
