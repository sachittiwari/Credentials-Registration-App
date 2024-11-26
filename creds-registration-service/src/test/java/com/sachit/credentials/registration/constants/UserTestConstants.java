package com.sachit.credentials.registration.constants;

import com.sachit.credentials.registration.entity.User;
import com.sachit.credentials.registration.model.UserRequestDTO;
import com.sachit.credentials.registration.model.UserResponseDTO;

import java.util.List;


public class UserTestConstants {

    public static final String USER_NOT_FOUND = "User not found with the given id: ";

    public static final UserRequestDTO REQUEST = UserRequestDTO.builder().name("Sachit")
            .firstName("Sachit").lastName("Tiwari").subjectId("ST").build();

    public static final UserResponseDTO RESPONSE_1 = UserResponseDTO.builder().id(1L).name("Sachit")
            .firstName("Sachit").lastName("Tiwari").subjectId("ST")
            .organizations(List.of(OrganizationTestConstants.RESPONSE_1,OrganizationTestConstants.RESPONSE_2)).build();

    public static final UserResponseDTO RESPONSE_2 = UserResponseDTO.builder().id(2L).name("Sachin")
            .firstName("Sachin").lastName("Tiwari").subjectId("STE")
            .organizations(List.of(OrganizationTestConstants.RESPONSE_1)).build();

    public static final User ENTITY_1 = User.builder().id(1L).name("Sachit")
            .firstName("Sachit").lastName("Tiwari").subjectId("ST").build();

    public static final User ENTITY_2 = User.builder().id(2L).name("Sachin")
            .firstName("Sachin").lastName("Tiwari").subjectId("STE").build();


}
