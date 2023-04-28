package com.micro.productservice.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequest {
    private String name,description,organization;
    private double price;
    private int count;
}
