package com.micro.organizationservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sales_product")
public class SalesProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "period_start", length = 100)
    private Timestamp period_start;

    @Column(name = "period_end", length = 100)
    private Timestamp period_end;

    @ManyToOne
    @JsonBackReference(value = "product-sales")
    @JoinColumn(name = "product",referencedColumnName = "id")
    private Product product;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sale",referencedColumnName = "id")
    private Sale sale;
}
