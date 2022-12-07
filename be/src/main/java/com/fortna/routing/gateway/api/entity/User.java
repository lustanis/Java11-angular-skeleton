package com.fortna.routing.gateway.api.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "myuser")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

}
