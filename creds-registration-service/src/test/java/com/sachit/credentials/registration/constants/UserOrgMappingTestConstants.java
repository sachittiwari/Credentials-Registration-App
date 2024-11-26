package com.sachit.credentials.registration.constants;

import com.sachit.credentials.registration.entity.UserOrganizationMapping;
import com.sachit.credentials.registration.model.UserOrgMappingRequestDTO;
import com.sachit.credentials.registration.model.UserOrgMappingResponseDTO;

import java.util.List;

public class UserOrgMappingTestConstants {

    public static final UserOrgMappingRequestDTO REQUEST = UserOrgMappingRequestDTO.builder().userId(1L).organizationIds(List.of(1L,2L)).build();

    public static final UserOrgMappingResponseDTO RESPONSE = UserOrgMappingResponseDTO.builder().userId(1L).organizationIds(List.of(1L,2L)).build();

    public static final UserOrganizationMapping ENTITY_1 = UserOrganizationMapping.builder().userId(1L).organizationId(1L).build();

    public static final UserOrganizationMapping ENTITY_2 = UserOrganizationMapping.builder().userId(1L).organizationId(2L).build();
}
