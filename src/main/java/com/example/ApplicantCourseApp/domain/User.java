package com.example.ApplicantCourseApp.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @NotNull
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    public User() {
    }

    public User(Long id, String email, String password, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
