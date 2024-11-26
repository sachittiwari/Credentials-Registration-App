package com.sachit.credentials.registration.respository;

import com.sachit.credentials.registration.entity.Credentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

    List<Credentials> findAllByOrganizationId(Long organizationId);
}
