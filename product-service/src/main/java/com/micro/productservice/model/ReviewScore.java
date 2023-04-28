package com.micro.productservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.micro.productservice.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review_score")
public class ReviewScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @ManyToOne
    @JsonBackReference(value = "user-reviewScores")
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JsonBackReference(value = "product-reviewScore")
    @JoinColumn(name = "product")
    private Product product;

    @Column(name = "review", length = 100)
    private String review;

    @Column(name = "score", length = 100)
    private Integer score;

}
