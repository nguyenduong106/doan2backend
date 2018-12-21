package com.travelapp.payload;

import java.io.Serializable;

import com.travelapp.model.RateType;
import com.travelapp.model.Tour;
import com.travelapp.model.User;

public class RateDetailRequest implements Serializable {
	private long id;
	private String rateType;
    private String user;
    private String tour;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTour() {
		return tour;
	}
	public void setTour(String tour) {
		this.tour = tour;
	}

	
}
