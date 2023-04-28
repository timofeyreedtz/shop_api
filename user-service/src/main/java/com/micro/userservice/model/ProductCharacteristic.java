package com.micro.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product_characteristic")
public class ProductCharacteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "characteristic", length = 100)
    private String characteristic;

    @ManyToOne
    @JsonBackReference(value = "product-characteristic")
    @JoinColumn(name = "product",referencedColumnName = "id")
    private Product product;
}
