package com.sachit.credentials.registration.service;

import com.sachit.credentials.registration.entity.Credentials;
import com.sachit.credentials.registration.exception.CredentialNotFoundException;
import com.sachit.credentials.registration.mapper.CredentialsMapper;
import com.sachit.credentials.registration.model.CredentialsRequestDTO;
import com.sachit.credentials.registration.model.CredentialsResponseDTO;
import com.sachit.credentials.registration.respository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CredentialsService {

    private final CredentialsRepository credentialsRepository;

    private final CredentialsMapper credentialsMapper;

    @Value("${credentials.expiry.days}")
    private String expiryInDays;

    private static final String CREDENTIAL_NOT_FOUND = "Credential not found for the given id: ";
    private static final int SECRET_LENGTH = 16;

    /**
     * This method retrieves All Credentials based on requested Org id
     *
     * @param organizationId of the organization whose credentials are to be retrieved
     * @return List of All Credentials owned by the Organization
     */
    public List<CredentialsResponseDTO> fetchAllCredentials(Long organizationId) {
        return credentialsRepository
                .findAllByOrganizationId(organizationId)
                .stream()
                //masking the client secret
                .peek(credential->credential.setClientSecret("*******"))
                .map(credentialsMapper::toCredentialsResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves Credential based on requested Credential id
     *
     * @param id of the credential to be retrieved
     * @return Credential Response
     * @throws CredentialNotFoundException Credential not found for the given id
     */
    public CredentialsResponseDTO getCredentialById(Long id) throws CredentialNotFoundException {
        log.debug("Inside getCredentialById: {}", id);
        Optional<Credentials> credential = credentialsRepository.findById(id);
        if(credential.isPresent()) {
            //masking the client secret
            credential.get().setClientSecret("*******");
            return credentialsMapper.toCredentialsResponseDTO(credential.get());
        }
        throw new CredentialNotFoundException(CREDENTIAL_NOT_FOUND + id);
    }

    /**
     * This method creates Credential based on requested Credential information
     *
     * @param request of the credential information to be created
     * @return Credential Response
     */
    public CredentialsResponseDTO createCredentials(CredentialsRequestDTO request) {
        log.debug("Inside createCredentials: {}",request);
        Credentials credential = credentialsMapper.toCredentials(request);
        credential.setClientId(generateClientId(credential.getUserId(),credential.getOrganizationId()));
        credential.setClientSecret(generateClientSecret());
        credential.setCreationDate(LocalDateTime.now());
        credential.setExpiryDate(credential.getCreationDate().plusDays(Long.parseLong(expiryInDays)));
        return credentialsMapper.toCredentialsResponseDTO(credentialsRepository.save(credential));
    }


    /**
     * This method updates client secret based on requested Credential id
     *
     * @param id of the credential whose client secret is to be updated
     * @return Credential Response
     * @throws CredentialNotFoundException Credential not found for the given id
     */
    public CredentialsResponseDTO updateCredentialsById(Long id) throws CredentialNotFoundException {
        log.debug("Inside updateCredentialsById: {}",id);
        Optional<Credentials> credential = credentialsRepository.findById(id);
        if(credential.isPresent()) {
            credential.get().setClientSecret(generateClientSecret());
            credential.get().setCreationDate(LocalDateTime.now());
            credential.get().setExpiryDate(credential.get().getCreationDate().plusDays(Long.parseLong(expiryInDays)));
            return credentialsMapper.toCredentialsResponseDTO(credentialsRepository.save(credential.get()));
        }
        throw new CredentialNotFoundException(CREDENTIAL_NOT_FOUND + id);
    }

    /**
     * This method deletes Credential based on requested Credential id
     *
     * @param id of the credential which is to be deleted
     * @throws CredentialNotFoundException Credential not found for the given id
     */
    public void deleteCredentialById(Long id) throws CredentialNotFoundException {
        log.debug("Inside deleteCredentialById: {}",id);
        if(credentialsRepository.existsById(id))
            credentialsRepository.deleteById(id);
        else
            throw new CredentialNotFoundException(CREDENTIAL_NOT_FOUND + id);
    }

    /**
     * This method generates Client ID based on requested userId and organizationId
     *
     * @param userId of the User that requests the credential
     * @param organizationId of the Organization that will own it
     * @return Client ID
     */
    private static String generateClientId(Long userId, Long organizationId){
        long epochTime = System.currentTimeMillis();
        log.debug("Time at which Client Id was generated is: {}",epochTime);
        return userId+"-"+organizationId+"-"+epochTime;

    }

    /**
     * This method generates Client Secret by genarating a secure random
     *
     * @return Client Secret
     */
    private static String generateClientSecret(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretBytes = new byte[SECRET_LENGTH];
        secureRandom.nextBytes(secretBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(secretBytes);

    }
}
