package com.micro.productservice.repos;



import com.micro.productservice.model.ReviewScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ReviewScoreRepos extends JpaRepository<ReviewScore,Integer> {
    @Override
    Optional<ReviewScore> findById(Integer integer);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReviewScore c WHERE c.user.uuid= ?1")
    void deleteAllByUser(String uuid);
}
