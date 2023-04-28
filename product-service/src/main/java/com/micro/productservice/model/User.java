package com.micro.productservice.model;


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
@Table(name = "keycloak_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "uuid", length = 36)
    private String uuid;


    @Column(name = "balance")
    private Double balance;


    @JsonManagedReference(value = "user-reviewScores")
    @OneToMany(mappedBy = "user")
    private List<ReviewScore> reviewScores;

    @JsonManagedReference(value = "user-buyHistories")
    @OneToMany(mappedBy = "user")
    private List<BuyHistory> buyHistories;

    @JsonManagedReference(value = "user-notifications")
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @JsonManagedReference(value = "user-organization")
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Organization> organizations;
}
