package com.micro.notificationservice.repos;



import com.micro.notificationservice.model.Notification;
import com.micro.notificationservice.model.User;
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
