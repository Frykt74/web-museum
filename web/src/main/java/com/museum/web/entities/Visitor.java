package com.museum.web.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "visitor")
public class Visitor extends BaseEntity {
    private String surname;
    private String firstname;
    private String patronymic;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private boolean isRemoved;

    public Visitor(String surname, String firstname,
                   String patronymic, String email, String phone,
                   String password, Role role) {
        this.surname = surname;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    protected Visitor() {
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "first_name")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "patronymic")
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone_number")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, updatable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "is_removed", nullable = false)
    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean removed) {
        isRemoved = removed;
    }
}
