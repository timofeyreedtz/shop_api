package com.micro.productservice.repos;




import com.micro.productservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepos extends JpaRepository<User,Integer> {
    @Query("SELECT c FROM User c WHERE c.id= ?1")
    Optional<User> findByUserId(Integer id);

    @Query("SELECT c FROM User c WHERE c.uuid= ?1")
    Optional<User> findByUserUuid(String uuid);


    @Override
    @Transactional
    @Modifying
    @Query("DELETE from User c where c.id = ?1")
    void deleteById(Integer id);
}
