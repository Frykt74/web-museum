package com.museum.web.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "note")
public class Note extends BaseEntity {
    private Equipment equipment;
    private String fact;

    public Note(Equipment equipment, String fact) {
        this.equipment = equipment;
        this.fact = fact;
    }

    protected Note() {
    }

    @OneToOne
    @JoinColumn(name = "equipment_id", unique = true, nullable = true)
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Column(name = "fact")
    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }
}
