package com.travelapp.payload;

import java.io.Serializable;

public class RateTypeDetailRequest implements Serializable {
	private long id;
	private String rate;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
}
