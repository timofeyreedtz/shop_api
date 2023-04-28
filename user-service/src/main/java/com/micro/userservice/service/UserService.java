package com.micro.userservice.service;

import com.micro.userservice.error.Message;
import com.micro.userservice.model.Organization;
import com.micro.userservice.model.User;
import com.micro.userservice.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.micro.userservice.model.Status.BANNED;

@Service

public class UserService {
    private UserRepos userRepos;
    private HistoryRepos historyRepos;
    private NotificationRepos notificationRepos;
    private ProductRepos productRepos;
    private OrganizationRepos organizationRepos;
    private ReviewScoreRepos reviewScoreRepos;
    @Autowired
    public UserService(UserRepos userRepos,
                       HistoryRepos historyRepos,
                       NotificationRepos notificationRepos,
                       ProductRepos productRepos,
                       OrganizationRepos organizationRepos,
                       ReviewScoreRepos reviewScoreRepos) {
        this.userRepos = userRepos;
        this.historyRepos = historyRepos;
        this.notificationRepos = notificationRepos;
        this.reviewScoreRepos = reviewScoreRepos;
        this.productRepos = productRepos;
        this.organizationRepos = organizationRepos;
    }


    public User getUser(String uuid) {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authenticationToken.getTokenAttributes().toString());
        return userRepos.findByUserUuid(uuid).orElseThrow(()->new IllegalStateException("There is no such user"));
    }

    public Message freezeUser(String uuid) {
        User apiUser = userRepos.findByUserUuid(uuid).orElseThrow(()->new IllegalStateException("There is no such user"));
        apiUser.setStatus(BANNED);
        userRepos.save(apiUser);
        return new Message(true);
    }

    public Message balance(String uuid, double balance) {
        User apiUser = userRepos.findByUserUuid(uuid).orElseThrow(()->new IllegalStateException("There is no such user"));
        apiUser.setBalance(balance);
        userRepos.save(apiUser);
        return new Message(true);
    }

    @Transactional
    public Message deleteUser(String uuid) {
        User apiUser = userRepos.findByUserUuid(uuid).orElseThrow(()->new IllegalStateException("There is no such user"));
        historyRepos.deleteAllByUser(apiUser);
        notificationRepos.deleteAllByUser(apiUser);
        List<Organization> organizations = apiUser.getOrganizations().stream()
                .filter(x -> x.getProducts() != null).toList();
        for(Organization organization:organizations){
            productRepos.deleteAllByOrganization(organization);
        }
        organizationRepos.deleteAllByUser(apiUser);
        reviewScoreRepos.deleteAllByUser(apiUser);
        userRepos.deleteById(uuid);
        return new Message(true);
    }
}
