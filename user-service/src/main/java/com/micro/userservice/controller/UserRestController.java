package com.micro.userservice.controller;

import com.micro.userservice.error.Message;
import com.micro.userservice.model.User;
import com.micro.userservice.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestParam String uuid){
        return userService.getUser(uuid);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/freeze")
    public Message freezeUser(@RequestParam String uuid){
        return userService.freezeUser(uuid);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/balance")
    public Message setUserBalance(@RequestParam String uuid, @RequestParam double balance){
        return userService.balance(uuid,balance);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/delete")
    public Message deleteUser(@RequestParam String uuid){
        return userService.deleteUser(uuid);
    }
}
