package com.cars.carsRental.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    public Car(){
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "type")
    private String type;
    @Column(name="color")
    private String colour;
    @Column(name="cost")
    private int cost;

    public Car(String type, String colour, int cost) {
        this.type = type;
        this.colour = colour;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", colour='" + colour + '\'' +
                ", cost=" + cost +
                '}';
    }
}
