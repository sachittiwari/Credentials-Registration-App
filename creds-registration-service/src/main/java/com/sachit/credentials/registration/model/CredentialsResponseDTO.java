package com.sachit.credentials.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsResponseDTO {

    private Long id;
    private String clientId;
    private String clientSecret;
    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;
    private Long userId;
    private Long organizationId;
}
