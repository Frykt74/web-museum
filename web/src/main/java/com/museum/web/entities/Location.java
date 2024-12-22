package com.museum.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class Location extends BaseEntity {
    private String name;
    private double ticketPrice;

    public Location(String name, double ticketPrice) {
        this.name = name;
        this.ticketPrice = ticketPrice;
    }

    protected Location() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ticket_price")
    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
