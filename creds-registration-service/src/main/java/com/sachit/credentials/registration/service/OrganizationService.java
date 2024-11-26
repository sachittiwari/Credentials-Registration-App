package com.sachit.credentials.registration.service;

import com.sachit.credentials.registration.exception.OrganizationNotFoundException;
import com.sachit.credentials.registration.entity.Organization;
import com.sachit.credentials.registration.mapper.OrganizationMapper;
import com.sachit.credentials.registration.model.OrganizationRequestDTO;
import com.sachit.credentials.registration.model.OrganizationResponseDTO;
import com.sachit.credentials.registration.respository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private static final String ORGANIZATION_NOT_FOUND = "Organization Not Found with the given id: ";

    /**
    * This method retrieves all the organizations
    *
    * @return List of All Organizations
    */
    public List<OrganizationResponseDTO> fetchAllOrganizations() {
        return organizationRepository
                .findAll()
                .stream()
                .map(organizationMapper::toOrganizationResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves Organization based on requested Org id
     *
     * @param id of the organization to be retrieved
     * @return List of All Organizations
     * @throws OrganizationNotFoundException Organization is not found for given id
     */
    public OrganizationResponseDTO getOrganizationById(Long id) throws OrganizationNotFoundException {
        log.debug("Inside getOrganizationById: {}",id);
        Optional<Organization> organization = organizationRepository.findById(id);
        if(organization.isPresent()) {
            return organizationMapper.toOrganizationResponseDTO(organization.get());
        }
        throw new OrganizationNotFoundException(ORGANIZATION_NOT_FOUND + id);
    }

    /**
     * This method creates Organization based on details provided
     *
     * @param request of the organization to be created
     * @return newly created Organization
     */
    public OrganizationResponseDTO createOrganization(OrganizationRequestDTO request) {
        log.debug("Inside createOrganization: {}",request);
        Organization organization = organizationMapper.toOrganization(request);
            return organizationMapper.toOrganizationResponseDTO(organizationRepository.save(organization));
    }

    /**
     * This method updates Organization based on details provided
     *
     * @param id of the organization to be updated
     * @param request of the organization to be updated
     * @return updated Organization
     * @throws OrganizationNotFoundException Organization is not found for given id
     */
    public OrganizationResponseDTO updateOrganizationById(Long id, OrganizationRequestDTO request) throws OrganizationNotFoundException {
        log.debug("Inside updateOrganizationById: {}",id);
        if(organizationRepository.existsById(id)) {
            Organization organization = organizationMapper.toOrganization(request);
            organization.setId(id);
            return organizationMapper.toOrganizationResponseDTO(organizationRepository.save(organization));
        }
        throw new OrganizationNotFoundException(ORGANIZATION_NOT_FOUND + id);
    }

    /**
     * This method deletes Organization based on id provided
     *
     * @param id of the organization to be deleted
     * @throws OrganizationNotFoundException Organization is not found for given id
     */
    public void deleteOrganizationById(Long id) throws OrganizationNotFoundException {
        log.debug("Inside deleteOrganizationById: {}",id);
        if(organizationRepository.existsById(id))
            organizationRepository.deleteById(id);
        else
            throw new OrganizationNotFoundException(ORGANIZATION_NOT_FOUND + id);
    }

}
