package com.sachit.credentials.registration.mapper;

import com.sachit.credentials.registration.entity.Credentials;
import com.sachit.credentials.registration.model.CredentialsRequestDTO;
import com.sachit.credentials.registration.model.CredentialsResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T02:33:55+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class CredentialsMapperImpl implements CredentialsMapper {

    @Override
    public CredentialsResponseDTO toCredentialsResponseDTO(Credentials credentials) {
        if ( credentials == null ) {
            return null;
        }

        CredentialsResponseDTO.CredentialsResponseDTOBuilder credentialsResponseDTO = CredentialsResponseDTO.builder();

        credentialsResponseDTO.id( credentials.getId() );
        credentialsResponseDTO.clientId( credentials.getClientId() );
        credentialsResponseDTO.clientSecret( credentials.getClientSecret() );
        credentialsResponseDTO.creationDate( credentials.getCreationDate() );
        credentialsResponseDTO.expiryDate( credentials.getExpiryDate() );
        credentialsResponseDTO.userId( credentials.getUserId() );
        credentialsResponseDTO.organizationId( credentials.getOrganizationId() );

        return credentialsResponseDTO.build();
    }

    @Override
    public Credentials toCredentials(CredentialsRequestDTO credentialsRequestDTO) {
        if ( credentialsRequestDTO == null ) {
            return null;
        }

        Credentials.CredentialsBuilder credentials = Credentials.builder();

        credentials.userId( credentialsRequestDTO.getUserId() );
        credentials.organizationId( credentialsRequestDTO.getOrganizationId() );

        return credentials.build();
    }
}
