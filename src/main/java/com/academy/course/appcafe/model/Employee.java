package com.academy.course.appcafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "employee", schema = "internet_shop")
public class Employee {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "createDateTime")
    private Instant createDateTime;

    @Column(name = "updateDateTime")
    private Instant updateDateTime;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "passWord", nullable = false)
    private String passWord;

    @Column(name = "role", nullable = false)
    private String role;


}