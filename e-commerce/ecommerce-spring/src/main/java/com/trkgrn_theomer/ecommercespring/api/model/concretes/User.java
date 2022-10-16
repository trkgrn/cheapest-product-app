package com.trkgrn_theomer.ecommercespring.api.model.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public",name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_username",unique = true)
    private String username;

    @Column(name ="user_password")
    private String password;

    @Column(name = "user_role")
    private String role;

    @Column(name= "user_name")
    private String name;

    @Column(name = "user_mail",unique = true)
    private String mail;

    @Column(name = "user_tel_no",unique = true)
    private String telNumber;

}
