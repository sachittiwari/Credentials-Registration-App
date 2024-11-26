package com.sachit.credentials.registration.constants;

import com.sachit.credentials.registration.entity.Organization;
import com.sachit.credentials.registration.model.OrganizationRequestDTO;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;

public class OrganizationTestConstants {

    public static final String ORGANIZATION_NOT_FOUND = "Organization Not Found with the given id: ";

    public static final OrganizationResponseDTO RESPONSE_1 = OrganizationResponseDTO.builder().id(1L)
                                                                .name("Org 1").sapId("SAP 1").vatNumber("VAT 1").build();
    public static final OrganizationResponseDTO RESPONSE_2 = OrganizationResponseDTO.builder().id(2L)
            .name("Org 2").sapId("SAP 2").vatNumber("VAT 2").build();

    public static final OrganizationRequestDTO REQUEST = OrganizationRequestDTO.builder()
            .name("Org 2").sapId("SAP 2").vatNumber("VAT 2").build();

    public static final Organization ENTITY_1 = Organization.builder().id(1L).name("Org 1")
            .sapId("SAP 1").vatNumber("VAT 1").build();

    public static final Organization ENTITY_2 = Organization.builder().id(2L).name("Org 2")
            .sapId("SAP 2").vatNumber("VAT 2").build();


}
