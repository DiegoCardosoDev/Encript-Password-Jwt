package com.diego.criptopassword.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String name;

    @Column(unique = true)
    private  String login;

    private  String password;
}
