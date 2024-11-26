package com.sachit.credentials.registration.respository;

import com.sachit.credentials.registration.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {


}
