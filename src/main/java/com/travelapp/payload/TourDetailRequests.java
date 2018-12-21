package com.travelapp.payload;

import com.travelapp.model.Category;

import com.travelapp.model.Comment;
import com.travelapp.model.Location;
import com.travelapp.model.Rate;
import com.travelapp.model.Vehicle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TourDetailRequests implements Serializable {
	private Long id;
    private String name;
    private VehicleDetailRequest vehicle;
    private CategoryPayload category;
    private BigDecimal price;
    private Date fromDate;
    private Date toDate;
    private Integer maximumPeople;
    private Integer freeSpace;
    private Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public VehicleDetailRequest getVehicle() {
		return vehicle;
	}
	public void setVehicle(VehicleDetailRequest vehicle) {
		this.vehicle = vehicle;
	}
	public CategoryPayload getCategory() {
		return category;
	}
	public void setCategory(CategoryPayload category) {
		this.category = category;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
