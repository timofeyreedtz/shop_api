package com.micro.organizationservice.model;

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
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = 100)
    private String description;

    @ManyToOne
    @JsonBackReference(value = "user-organization")
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(name = "proceeds")
    private Double proceeds;

    @JsonIgnore
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @JsonManagedReference(value = "organization-product")
    @OneToMany(mappedBy = "organization")
    private List<Product> products;

}
