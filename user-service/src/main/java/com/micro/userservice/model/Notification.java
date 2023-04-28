package com.micro.userservice.model;

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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @ManyToOne
    @JsonBackReference(value = "user-notifications")
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "header")
    private String header;

    @Column(name = "content")
    private String content;

    @Column(name = "notification_time")
    private Timestamp notification_time;
}
