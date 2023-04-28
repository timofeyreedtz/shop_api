package com.micro.organizationservice.repos;





import com.micro.organizationservice.model.Organization;
import com.micro.organizationservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductRepos extends JpaRepository<Product,Integer> {
    @Override
    Optional<Product> findById(Integer integer);

    @Transactional
    @Modifying
    @Query("UPDATE Product c set c.name = ?2 where c.id = ?1")
    void updateName(Integer id, String name);

    @Transactional
    @Modifying
    @Query("UPDATE Product c set c.description = ?2 where c.id = ?1")
    void updateDescription(Integer id, String description);

    @Transactional
    @Modifying
    @Query("UPDATE Product c set c.price = ?2 where c.id = ?1")
    void updatePrice(Integer id, Double name);

    @Transactional
    @Modifying
    @Query("UPDATE Product c set c.count_on_storage = ?2 where c.id = ?1")
    void updateCount(Integer id, Integer count);

    @Transactional
    @Modifying
    @Query("UPDATE Product c set c.organization = ?2 where c.id = ?1")
    void updateOrganization(Integer id, Organization organization);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product c WHERE c.organization= ?1")
    void deleteAllByOrganization(Organization organization);

    @Query("SELECT c FROM Product c WHERE c.name= ?1")
    Optional<Product> findByUserUsername(String name);
}
