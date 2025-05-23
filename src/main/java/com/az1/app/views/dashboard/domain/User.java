package com.az1.app.views.dashboard.domain;

import lombok.Getter;

@Getter
public class User {
    // Getters
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
