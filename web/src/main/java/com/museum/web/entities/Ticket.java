package com.museum.web.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "visit")
public class Ticket extends BaseEntity {
    private Visitor visitor;
    private Location location;
    private Date date;
    private boolean isPaid;

    public Ticket(Visitor visitor, Location location, Date date, boolean isPaid) {
        this.visitor = visitor;
        this.location = location;
        this.date = date;
        this.isPaid = isPaid;
    }

    protected Ticket() {
    }

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    @OneToOne
    @JoinColumn(name = "location_id")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Column(name = "visit_date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "is_paid")
    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean paid) {
        isPaid = paid;
    }
}
