package com.sachit.credentials.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String subjectId;
    private String name;
    private String firstName;
    private String lastName;
    private List<OrganizationResponseDTO> organizations;
}
