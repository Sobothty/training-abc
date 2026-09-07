package com.example.trainingfullstack.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String uuid;
    @Column(unique = true, length = 50, nullable = false)
    private String username;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Role role = Role.USER;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Task> task = new ArrayList<>();

    @PrePersist
    protected void onCreated(){

        createdDate = LocalDate.now();
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
        }
        if (role == null){
            role = Role.USER;
        }
    }

    @PreUpdate
    protected void onUpdated(){
        updatedDate = LocalDate.now();
    }
}
