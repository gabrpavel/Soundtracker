package com.website.jdbc.model;

import lombok.Data;

@Data
public class User {

    Long id;
    String login;
    String password;
    String email;

    public User(Long id, String email, String password, String login) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
