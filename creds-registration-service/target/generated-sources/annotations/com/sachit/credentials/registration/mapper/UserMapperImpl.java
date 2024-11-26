package com.sachit.credentials.registration.mapper;

import com.sachit.credentials.registration.entity.User;
import com.sachit.credentials.registration.model.UserRequestDTO;
import com.sachit.credentials.registration.model.UserResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T02:33:55+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDTO toUserResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO.UserResponseDTOBuilder userResponseDTO = UserResponseDTO.builder();

        userResponseDTO.id( user.getId() );
        userResponseDTO.subjectId( user.getSubjectId() );
        userResponseDTO.name( user.getName() );
        userResponseDTO.firstName( user.getFirstName() );
        userResponseDTO.lastName( user.getLastName() );

        return userResponseDTO.build();
    }

    @Override
    public User toUser(UserRequestDTO userRequestDTO) {
        if ( userRequestDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.subjectId( userRequestDTO.getSubjectId() );
        user.name( userRequestDTO.getName() );
        user.firstName( userRequestDTO.getFirstName() );
        user.lastName( userRequestDTO.getLastName() );

        return user.build();
    }
}
