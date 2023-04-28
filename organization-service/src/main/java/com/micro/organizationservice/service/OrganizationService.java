package com.micro.organizationservice.service;

import com.micro.organizationservice.error.Message;
import com.micro.organizationservice.model.Organization;
import com.micro.organizationservice.model.Product;
import com.micro.organizationservice.model.Status;
import com.micro.organizationservice.model.User;
import com.micro.organizationservice.repos.OrganizationRepos;
import com.micro.organizationservice.repos.ProductRepos;
import com.micro.organizationservice.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.micro.organizationservice.model.Status.ACTIVE;
import static com.micro.organizationservice.model.Status.BANNED;

@Service
public class OrganizationService {

    private OrganizationRepos organizationRepos;
    private ProductRepos productRepos;
    private UserRepos userRepos;

    @Autowired
    public OrganizationService(OrganizationRepos organizationRepos, ProductRepos productRepos, UserRepos userRepos) {
        this.organizationRepos = organizationRepos;
        this.productRepos = productRepos;
        this.userRepos = userRepos;
    }

    @Transactional
    public Message changeStatus(int id, String status) {
        Organization organization = organizationRepos.findById(id).orElseThrow(()->new IllegalStateException("There is no such organization"));
        if(status.equalsIgnoreCase("active")){
            for(Product product:organization.getProducts()){
                product.setStatus(ACTIVE);
                productRepos.save(product);
            }
            organization.setStatus(ACTIVE);
            organizationRepos.save(organization);
        }
        else if(status.equalsIgnoreCase("banned")){
            for(Product product:organization.getProducts()){
                product.setStatus(BANNED);
                productRepos.save(product);
            }
            organization.setStatus(BANNED);
            organizationRepos.save(organization);
        }
        else {
            throw new IllegalStateException("Unknown action");
        }
        return new Message(true);
    }

    @Transactional
    public Message createOrganization(String name, String description) {
        if(organizationRepos.findByName(name).isPresent()){
            throw new IllegalStateException("Such organization exists");
        }
        else{
            User apiUser = userRepos.findByUserUuid(getUserID()).orElseThrow();
            Organization organization = new Organization();
            organization.setUser(apiUser);
            organization.setStatus(Status.BANNED);
            organization.setName(name);
            organization.setDescription(description);
            organizationRepos.save(organization);
            userRepos.save(apiUser);
            return new Message(true);
        }
    }
    private String getUserID(){
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getTokenAttributes().get("sub");
    }
}
