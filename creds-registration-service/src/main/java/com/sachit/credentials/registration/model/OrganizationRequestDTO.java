package com.sachit.credentials.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationRequestDTO {

    private String name;
    private String vatNumber;
    private String sapId;
}
