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
@Table(name = "buy_history")
public class BuyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @ManyToOne
    @JsonBackReference(value = "user-buyHistories")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
   // @JsonManagedReference(value = "product-buyHistory")
    @JoinColumn(name = "product")
    private Product product;

    @Column(name = "buy_time")
    private Timestamp buy_time;
}
