package com.GeoProximity.es_worker_service.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "business")
public class MyBusiness {

    @Id
    private Long businessId;

    private String address;
    private String city;
    private String country;
    private String state;

    @GeoPointField
    private GeoPoint location;

    public MyBusiness() {}


    public MyBusiness(Long businessId, String address, String city, String country, String state, GeoPoint location) {
        this.businessId = businessId;
        this.address = address;
        this.city = city;
        this.country = country;
        this.state = state;
        this.location = location;
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

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(double lat, double lon) {
        this.location = new GeoPoint(lat, lon);
    }
}
