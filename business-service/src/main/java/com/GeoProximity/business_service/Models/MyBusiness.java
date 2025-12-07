package com.GeoProximity.business_service.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MyBusiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long business_id;

    private String address,city,country,state;
    private double latitude,longtitude;

    public long getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(long business_id) {
        this.business_id = business_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public MyBusiness() {
    }

    public MyBusiness(long business_id, String address, String city, String country, String state, double latitude, double longtitude) {
        this.business_id = business_id;
        this.address = address;
        this.city = city;
        this.country = country;
        this.state = state;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
}
