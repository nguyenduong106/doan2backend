package com.travelapp.payload;

import com.travelapp.model.Comment;
import com.travelapp.model.Location;
import com.travelapp.model.Rate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TourDetailRequest implements Serializable {
    private String name;
    private Set<Location> locations = new HashSet<>();
    private Set<Rate> rates = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private BigDecimal price;
    private Date fromDate;
    private Date toDate;
    private Integer maximumPeople;
    private Integer freeSpace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getMaximumPeople() {
        return maximumPeople;
    }

    public void setMaximumPeople(Integer maximumPeople) {
        this.maximumPeople = maximumPeople;
    }

    public Integer getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(Integer freeSpace) {
        this.freeSpace = freeSpace;
    }
}
