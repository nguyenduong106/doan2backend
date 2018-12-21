package com.travelapp.payload;

import java.io.Serializable;

public class VehicleTypeDetailRequest implements Serializable {
	private long id;
	private int price;
    private String name;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
