package com.museum.web.entities;

import com.museum.web.entities.enums.Country;
import jakarta.persistence.*;

import java.time.Year;
import java.util.Date;

@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity {
    private Category category;
    private Theme theme;
    private Location location;
    private String name;
    private Year year;
    private Country country;
    private String description;
    private String image;
    private Date date;
    private Note note;

    public Equipment(Category category, Theme theme, Location location, String name,
                     Year year, Country country, String description, String image, Date date) {
        this.category = category;
        this.theme = theme;
        this.location = location;
        this.name = name;
        this.year = year;
        this.country = country;
        this.description = description;
        this.image = image;
        this.date = date;
    }

    protected Equipment() {
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "theme_id")
    public Theme getTheme() {
        return theme;
    }

    @ManyToOne
    @JoinColumn(name = "location_id")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "year")
    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Column(name = "country")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "image_url")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "added_date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToOne(mappedBy = "equipment", optional = true)
    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
