package com.sachit.credentials.registration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrganizationMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ORGANIZATION_MAPPING_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ORGANIZATION_ID")
    private Long organizationId;
}
