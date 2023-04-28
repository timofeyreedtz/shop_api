package com.micro.organizationservice.repos;



import com.micro.organizationservice.model.Organization;
import com.micro.organizationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OrganizationRepos extends JpaRepository<Organization,Integer> {
    @Query("SELECT c FROM Organization c WHERE c.name= ?1")
    Optional<Organization> findByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Organization c WHERE c.user= ?1")
    void deleteAllByUser(User user);
}
