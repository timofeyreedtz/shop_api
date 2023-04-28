package com.micro.productservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "keyword")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "keyword", length = 100)
    private String keyword;

    @ManyToOne
    @JsonBackReference(value = "product-keyword")
    @JoinColumn(name = "product",referencedColumnName = "id")
    private Product product;

}