package com.micro.saleservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "count_on_storage")
    private Integer count_on_storage;

    @JsonIgnore
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;


    @ManyToOne
    @JsonBackReference(value = "organization-product")
    @JoinColumn(name = "organization_id",referencedColumnName = "id")
    private Organization organization;

    @JsonManagedReference(value = "product-sales")
    @OneToMany(mappedBy = "product")
    private List<SalesProduct> salesProducts;

    @JsonManagedReference(value = "product-reviewScore")
    @OneToMany(mappedBy = "product")
    private List<ReviewScore> reviewScores;

    @JsonManagedReference(value = "product-keyword")
    @OneToMany(mappedBy = "product")
    private List<Keyword> keywords;

    @JsonManagedReference(value = "product-characteristic")
    @OneToMany(mappedBy = "product")
    private List<ProductCharacteristic> productCharacteristics;

  //  @JsonBackReference(value = "product-buyHistory")
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<BuyHistory> buyHistories;
}
