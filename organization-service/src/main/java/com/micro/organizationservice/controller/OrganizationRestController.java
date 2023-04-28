package com.micro.organizationservice.controller;

import com.micro.organizationservice.error.Message;
import com.micro.organizationservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OrganizationRestController {
    private OrganizationService organizationService;

    @Autowired
    public OrganizationRestController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PutMapping(value = "/status")
    public Message changeStatus(@RequestParam int id, @RequestParam String status){
        return organizationService.changeStatus(id,status);
    }
    @PostMapping("/create")
    public Message createOrganization(@RequestParam String name,@RequestParam String description) throws IOException {
        return organizationService.createOrganization(name,description);
    }
}
