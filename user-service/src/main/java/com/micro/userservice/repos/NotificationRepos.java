package com.micro.userservice.repos;



import com.micro.userservice.model.Notification;
import com.micro.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepos extends JpaRepository<Notification,Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Notification c WHERE c.user= ?1")
    void deleteAllByUser(User user);

}
