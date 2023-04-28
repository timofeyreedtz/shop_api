package com.micro.userservice.repos;




import com.micro.userservice.model.BuyHistory;
import com.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepos extends JpaRepository<BuyHistory,Integer> {
    @Override
    Optional<BuyHistory> findById(Integer integer);

    @Query("SELECT c FROM BuyHistory c WHERE c.user.uuid= ?1")
    List<BuyHistory> findByUserId(String uuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM BuyHistory c WHERE c.user= ?1")
    void deleteAllByUser(User user);
}
