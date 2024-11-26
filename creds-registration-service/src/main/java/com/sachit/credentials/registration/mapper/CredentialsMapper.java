package com.sachit.credentials.registration.mapper;

import com.sachit.credentials.registration.entity.Credentials;
import com.sachit.credentials.registration.model.CredentialsRequestDTO;
import com.sachit.credentials.registration.model.CredentialsResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsResponseDTO toCredentialsResponseDTO(Credentials credentials);
    Credentials toCredentials(CredentialsRequestDTO credentialsRequestDTO);
}
