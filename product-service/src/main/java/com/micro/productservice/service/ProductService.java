package com.micro.productservice.service;

import com.micro.productservice.error.Message;
import com.micro.productservice.model.*;
import com.micro.productservice.repos.*;
import com.micro.productservice.utils.CreateProductRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepos productRepos;
    private SaleProductRepos salesProductRepos;
    private HistoryRepos historyRepos;
    private OrganizationRepos organizationRepos;
    private UserRepos userRepos;
    private KeywordRepos keywordRepos;
    @Autowired
    public ProductService(ProductRepos productRepos,
                          SaleProductRepos salesProductRepos,
                          HistoryRepos historyRepos,
                          OrganizationRepos organizationRepos,
                          UserRepos userRepos,
                          KeywordRepos keywordRepos) {
        this.productRepos = productRepos;
        this.salesProductRepos = salesProductRepos;
        this.historyRepos = historyRepos;
        this.organizationRepos = organizationRepos;
        this.userRepos = userRepos;
        this.keywordRepos = keywordRepos;
    }

    @Transactional
    public Message buyProduct(int id) {
        Product product = productRepos.findById(id).orElseThrow(() -> new IllegalStateException("There is no such product"));
        if (product.getStatus().equals(Status.BANNED)) {
            throw new IllegalStateException("Banned product");
        } else if (product.getCount_on_storage() == 0) {
            throw new IllegalStateException("Storage is empty");
        }
        String userID = getUserID();
        User user = userRepos.findByUserUuid(getUserID()).orElseThrow(()->new IllegalStateException("There is no such user"));
        double price = 0;
        if (salesProductRepos.findByProductAndDate(product, Timestamp.valueOf(LocalDateTime.now())).isPresent()) {
            SalesProduct salesProduct = salesProductRepos.findByProductAndDate(product, Timestamp.valueOf(LocalDateTime.now())).get();
            price = product.getPrice() * ((100 - salesProduct.getSale().getSale_in_percent()) * 0.01);
        } else {
            price = product.getPrice();
        }
        if (user.getBalance() - price < 0) {
            throw new IllegalStateException("Not enough money");
        } else {
            user.setBalance(user.getBalance() - price);
            BuyHistory buy = new BuyHistory();
            buy.setUser(user);
            buy.setProduct(product);
            buy.setBuy_time(Timestamp.valueOf(LocalDateTime.now()));
            historyRepos.save(buy);
            product.setCount_on_storage(product.getCount_on_storage() - 1);
            productRepos.save(product);
            Organization organization = product.getOrganization();
            organization.setProceeds(organization.getProceeds() + price * 0.05);
            organizationRepos.save(organization);
            User proceedUser =  product.getOrganization().getUser();
            proceedUser.setBalance(proceedUser.getBalance()+ price*0.95);
            userRepos.save(proceedUser);
        }
        return new Message(true);
    }
    private String getUserID(){
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getTokenAttributes().get("sub");
    }


    public List<BuyHistory> getBuyHistory() {
        User user = userRepos.findByUserUuid(getUserID()).orElseThrow(()->new IllegalStateException("There is no such user"));
        return user.getBuyHistories();
       /* return productRepos.findAll().stream()
                .filter(x->x.getStatus().equals(Status.ACTIVE))
                .toList();*/
    }
    public List<Product> getAllProducts(){
        return productRepos.findAll().stream()
                .filter(x->x.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Transactional
    public Message createProduct(CreateProductRequest request) {
        User apiUser = userRepos.findByUserUuid(getUserID()).orElseThrow(() -> new IllegalStateException("There is no such user"));
        if (productRepos.findByUserUsername(request.getName()).isPresent()) {
            throw new IllegalStateException("Such product exists");
        } else {
            Product product = new Product();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setCount_on_storage(request.getCount());
            product.setPrice(request.getPrice());
            Organization organization = apiUser.getOrganizations().stream()
                    .filter(x -> x.getName().equals(request.getOrganization()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("You have no such organization"));
            product.setStatus(organization.getStatus());
            product.setOrganization(organization);
            productRepos.save(product);
        }
        return new Message(true);
    }

    @Transactional
    public Message returnProduct(int id) {
        Product product = productRepos.findById(id).orElseThrow(()-> new IllegalStateException("There is no such product"));
        User apiUser = userRepos.findByUserUuid(getUserID()).orElseThrow(() -> new IllegalStateException("There is no such user"));
        Optional<BuyHistory> optionalBuyHistory = apiUser.getBuyHistories().stream()
                .filter(x->x.getProduct().equals(product))
                .filter(x->x.getBuy_time().after( Timestamp.valueOf(LocalDateTime.now().minusDays(1)))).findFirst();
        if(optionalBuyHistory.isEmpty()){
            throw new IllegalStateException("You cant return this product");
        }
        else {
            apiUser.setBalance(apiUser.getBalance()+product.getPrice());
            product.setCount_on_storage(product.getCount_on_storage()+1);
            historyRepos.delete(optionalBuyHistory.get());
            userRepos.save(apiUser);
            productRepos.save(product);
            return new Message(true);
        }
    }

    @Transactional
    public Message updateProduct(int id, String field, String value) {
        if (!productRepos.existsById(id)){
            throw  new IllegalStateException("There is no such product");
        }
        else{
            switch (field){
                case "name":
                    productRepos.updateName(id,value);
                    break;
                case "description":
                    productRepos.updateDescription(id,value);
                    break;
                case "price":
                    if(value.matches("\\d+")){
                        productRepos.updatePrice(id,Double.parseDouble(value));
                    }
                    else throw new IllegalStateException("Value should be type of double");
                    break;
                case "count":
                    if(value.matches("\\d+")){
                        productRepos.updateCount(id,Integer.parseInt(value));
                    }
                    else throw new IllegalStateException("Value should be type of int");
                    break;
                default:
                    throw new IllegalStateException("Unknown field");
            }
        }
        return new Message(true);
    }

    public Message setKeyword(int id, String new_value) {
        Keyword keyword = keywordRepos.findById(id).orElseThrow(()->new IllegalStateException("There is no such keyword"));
        keyword.setKeyword(new_value);
        keywordRepos.save(keyword);
        return new Message(true);
    }
}
