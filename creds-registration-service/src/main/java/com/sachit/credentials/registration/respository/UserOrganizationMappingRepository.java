package com.sachit.credentials.registration.respository;

import com.sachit.credentials.registration.entity.UserOrganizationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrganizationMappingRepository extends JpaRepository<UserOrganizationMapping, Long> {

    List<UserOrganizationMapping> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
