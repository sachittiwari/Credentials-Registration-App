package com.sachit.credentials.registration.mapper;

import com.sachit.credentials.registration.entity.Organization;
import com.sachit.credentials.registration.model.OrganizationRequestDTO;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationResponseDTO toOrganizationResponseDTO(Organization organization);
    Organization toOrganization(OrganizationRequestDTO organizationRequestDTO);
}
