package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author mchidambaranathan 4/16/2017
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String age;

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age='" + age + '\'' +
            '}';
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(final String age) {
        this.age = age;
    }

    public Person() {
    }

    public Person(final String name, final String age) {
        this.name = name;
        this.age = age;
    }
}
