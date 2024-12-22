package com.museum.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "theme")
public class Theme extends BaseEntity {
    private String name;
    private String description;

    public Theme(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Theme() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
