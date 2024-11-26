package com.sachit.credentials.registration.constants;

import com.sachit.credentials.registration.entity.Credentials;
import com.sachit.credentials.registration.model.CredentialsRequestDTO;
import com.sachit.credentials.registration.model.CredentialsResponseDTO;

import java.time.LocalDateTime;

public class CredentialsApplicationTestConstants {

    public static final LocalDateTime CREATION_DATE = LocalDateTime.parse("2024-11-17T15:50:06.344382");
    public static final LocalDateTime EXPIRY_DATE = CREATION_DATE.plusDays(2);
    public static final String CREDENTIAL_NOT_FOUND = "Credential not found for the given id: ";
    public static final CredentialsResponseDTO RESPONSE_1 = CredentialsResponseDTO.builder()
                                                            .id(1L).clientId("123-456").clientSecret("******").creationDate(CREATION_DATE)
                                                            .expiryDate(EXPIRY_DATE).userId(1L).organizationId(1L).build();
    public static final CredentialsResponseDTO RESPONSE_2 = CredentialsResponseDTO.builder()
            .id(2L).clientId("456-123").clientSecret("******").creationDate(CREATION_DATE)
            .expiryDate(EXPIRY_DATE).userId(2L).organizationId(1L).build();

    public static final CredentialsRequestDTO REQUEST = CredentialsRequestDTO.builder().userId(1L).organizationId(1L).build();
    public static final CredentialsResponseDTO UNMASKED_RESPONSE = CredentialsResponseDTO.builder()
            .id(1L).clientId("123-456").clientSecret("abcdyeih1234qwer").creationDate(CREATION_DATE)
            .expiryDate(EXPIRY_DATE).userId(1L).organizationId(1L).build();

    public static final Credentials ENTITY_1 = Credentials.builder().id(1L).clientId("123")
            .clientSecret("abcdyeih1234qwer").creationDate(CREATION_DATE).expiryDate(EXPIRY_DATE).userId(1L).organizationId(1L).build();

    public static final Credentials ENTITY_2 = Credentials.builder().id(2L).clientId("456")
            .clientSecret("abcdyeih1234qwee").creationDate(CREATION_DATE).expiryDate(EXPIRY_DATE).userId(2L).organizationId(1L).build();
}
