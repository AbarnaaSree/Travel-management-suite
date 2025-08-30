package com.travel.suite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id") 
    private City city;

    

    private double cost; 
    private int days;
    private String imagePath;
    private String mapLink;

    public Destination() {}

    public Destination(City city, double cost, int days, String imagePath, String mapLink) {
        this.city = city;
        this.cost = cost;
        this.days = days;
        this.imagePath = imagePath;
        this.mapLink = mapLink;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getMapLink() { return mapLink; }
    public void setMapLink(String mapLink) { this.mapLink = mapLink; }

    public String getCountry() {
        return city != null && city.getCountry() != null ? city.getCountry().getName() : "Unknown";
    }

    public double getCostInINR() {
        return Math.round(cost * 83.0);
    }

    @Override
    public String toString() {
        return city.getName() + " in " + getCountry() + " for " + days + " days at cost " + cost;
    }
}
