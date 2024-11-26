package com.sachit.credentials.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationResponseDTO {

    private Long id;
    private String name;
    private String vatNumber;
    private String sapId;
}
