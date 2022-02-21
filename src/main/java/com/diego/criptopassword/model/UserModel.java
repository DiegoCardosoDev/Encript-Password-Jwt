package com.diego.criptopassword.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(unique = true)
    private  String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;
}
