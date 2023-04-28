package com.micro.productservice.controller;

import com.micro.productservice.error.Message;
import com.micro.productservice.model.BuyHistory;
import com.micro.productservice.model.Product;
import com.micro.productservice.service.ProductService;
import com.micro.productservice.utils.CreateProductRequest;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getUser() throws IOException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getTokenAttributes();
    }


    @GetMapping("/buy")
    @ResponseStatus(HttpStatus.OK)
    public Message buyProduct(@RequestParam int id) throws IOException {
        return productService.buyProduct(id);
    }


    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public Message returnProduct(@RequestParam int id) throws IOException {
        return productService.returnProduct(id);
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Message createProduct(@RequestBody CreateProductRequest request) throws IOException {
        return productService.createProduct(request);
    }


    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public List<BuyHistory> getBuyHistory() throws IOException {
        return productService.getBuyHistory();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() throws IOException {
        return productService.getAllProducts();
    }

    @PutMapping(value = "/keyword")
    public Message setKeyword(@RequestParam int id, @RequestParam String new_value){
        return productService.setKeyword(id,new_value);
    }


    @PutMapping(value = "/update")
    public Message updateProduct(@RequestParam int id, @RequestParam String field, @RequestParam String value){
        return productService.updateProduct(id,field,value);
    }
}
