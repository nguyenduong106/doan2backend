package com.travelapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date toDate;

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

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "maximum_people", nullable = false)
    private Integer maximumPeople;

    @NotNull
    @Column(name = "free_space", nullable = false)
    private Integer freeSpace;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("tours")
    private Category category;

    public Boolean getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "tour",fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "tour",fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Booking> bookings = new HashSet<>();
    @OneToMany(mappedBy = "tour",fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rate> rates = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tour_location",
               joinColumns = @JoinColumn(name = "tours_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "locations_id", referencedColumnName = "id"))
    private Set<Location> locations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tours")
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tour name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Tour price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMaximumPeople() {
        return maximumPeople;
    }

    public Tour maximumPeople(Integer maximumPeople) {
        this.maximumPeople = maximumPeople;
        return this;
    }

    public void setMaximumPeople(Integer maximumPeople) {
        this.maximumPeople = maximumPeople;
    }

    public Integer getFreeSpace() {
        return freeSpace;
    }

    public Tour freeSpace(Integer freeSpace) {
        this.freeSpace = freeSpace;
        return this;
    }

    public void setFreeSpace(Integer freeSpace) {
        this.freeSpace = freeSpace;
    }

    public Boolean isStatus() {
        return status;
    }

    public Tour status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Tour comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Tour addComment(Comment comment) {
        this.comments.add(comment);
        comment.setTour(this);
        return this;
    }

    public Tour removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setTour(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Tour bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public Tour addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setTour(this);
        return this;
    }

    public Tour removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setTour(null);
        return this;
    }
    public Tour addRate(Rate rate){
        rate.setTour(this);
        this.rates.add(rate);
        return this;
    }
    public Tour removeRate(Rate rate){
        this.rates.remove(rate);
        rate.setTour(null);
        return this;
    }
    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Tour locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Tour addLocation(Location location) {
        this.locations.add(location);
        location.getTours().add(this);
        return this;
    }

    public Tour removeLocation(Location location) {
        this.locations.remove(location);
        location.getTours().remove(this);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Tour vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tour tour = (Tour) o;
        if (tour.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }



    public Set<Rate> getRates() {

        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }
}
