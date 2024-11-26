package com.sachit.credentials.registration.mapper;

import com.sachit.credentials.registration.entity.Organization;
import com.sachit.credentials.registration.model.OrganizationRequestDTO;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T02:33:55+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class OrganizationMapperImpl implements OrganizationMapper {

    @Override
    public OrganizationResponseDTO toOrganizationResponseDTO(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        OrganizationResponseDTO.OrganizationResponseDTOBuilder organizationResponseDTO = OrganizationResponseDTO.builder();

        organizationResponseDTO.id( organization.getId() );
        organizationResponseDTO.name( organization.getName() );
        organizationResponseDTO.vatNumber( organization.getVatNumber() );
        organizationResponseDTO.sapId( organization.getSapId() );

        return organizationResponseDTO.build();
    }

    @Override
    public Organization toOrganization(OrganizationRequestDTO organizationRequestDTO) {
        if ( organizationRequestDTO == null ) {
            return null;
        }

        Organization.OrganizationBuilder organization = Organization.builder();

        organization.name( organizationRequestDTO.getName() );
        organization.vatNumber( organizationRequestDTO.getVatNumber() );
        organization.sapId( organizationRequestDTO.getSapId() );

        return organization.build();
    }
}
