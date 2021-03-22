package com.example.ApplicantCourseApp.security;

public class UserDetails {
    private Integer id;
    private String email;
    private Boolean isAdmin;

    public UserDetails() {
    }

    public UserDetails(Integer id, String email, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
