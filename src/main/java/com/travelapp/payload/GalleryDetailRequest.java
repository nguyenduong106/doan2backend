package com.travelapp.payload;

import java.io.Serializable;
import java.util.HashSet;

public class GalleryDetailRequest implements Serializable {
	private long id;
    private LocationDetailRequest location;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocationDetailRequest getLocation() {
		return location;
	}
	public void setLocation(LocationDetailRequest location) {
		this.location = location;
	}
	
}
