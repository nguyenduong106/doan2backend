package com.travelapp.payload;

import java.io.Serializable;

import com.travelapp.model.VehicleTypeRq;

public class VehicleDetailRequest implements Serializable {
	private long id;
	private int container;
    private String name;
    private VehicleTypeRq vehicleType;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getContainer() {
		return container;
	}

	public void setContainer(int container) {
		this.container = container;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VehicleTypeRq getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeRq vehicleType) {
		this.vehicleType = vehicleType;
	}

}
