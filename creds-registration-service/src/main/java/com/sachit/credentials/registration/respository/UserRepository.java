package com.sachit.credentials.registration.respository;

import com.sachit.credentials.registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findBySubjectId(String subjectId);

}
