package com.micro.notificationservice.service;

import com.micro.notificationservice.error.Message;
import com.micro.notificationservice.model.Notification;
import com.micro.notificationservice.model.User;
import com.micro.notificationservice.repos.NotificationRepos;
import com.micro.notificationservice.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private UserRepos userRepos;
    private NotificationRepos notificationRepos;

    @Autowired
    public NotificationService(UserRepos userRepos,NotificationRepos notificationRepos) {
        this.userRepos = userRepos;
        this.notificationRepos = notificationRepos;
    }

    @Transactional
    public Message sendNotification(int id, List<Notification> notification) {
        User apiUser = userRepos.findByUserId(id).orElseThrow(()->new IllegalStateException("There is no such user"));
        List<Notification> notifications = notification.stream()
                .peek(x -> x.setUser(apiUser)).peek(x -> x.setNotification_time(Timestamp.valueOf(LocalDateTime.now()))).toList();
        notificationRepos.saveAll(notifications);
        userRepos.save(apiUser);
        return new Message(true);
    }

    public List<Notification> getNotification() {
        User apiUser = userRepos.findByUserUuid(getUserID()).orElseThrow();
        return apiUser.getNotifications();
    }
    private String getUserID(){
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getTokenAttributes().get("sub");
    }
}
