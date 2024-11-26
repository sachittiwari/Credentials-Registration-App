package com.sachit.credentials.registration.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CREDENTIAL_ID")
    private Long id;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "EXPIRY_DATE")
    private LocalDateTime expiryDate;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ORGANIZATION_ID")
    private Long organizationId;

}
