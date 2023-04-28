package com.micro.notificationservice.controller;

import com.micro.notificationservice.error.Message;
import com.micro.notificationservice.model.Notification;
import com.micro.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class NotificationRestController {
    private NotificationService notificationService;

    @Autowired
    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user")
    public Map<String,Object> getUser() throws IOException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getTokenAttributes();
    }

    @PostMapping(value = "/send")
    public Message sendNotification(@RequestParam int id, @RequestBody List<Notification> notification){
        return notificationService.sendNotification(id,notification);
    }

    @GetMapping("/get")
    public List<Notification> getNotifications() throws IOException {
        return notificationService.getNotification();
    }
}
