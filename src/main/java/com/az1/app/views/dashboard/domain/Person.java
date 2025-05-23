package com.az1.app.views.dashboard.domain;

import lombok.Getter;

@Getter
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
