package com.travelapp.payload;

import java.time.Instant;

public class UserProfile {
	 private Long id;
	    private String username;
	    private String name;
	    private Instant joinedAt;
	    private Long wishCount;

	    public UserProfile(Long id, String username, String name, Instant joinedAt, Long wishCount, Long voteCount) {
	        this.id = id;
	        this.username = username;
	        this.name = name;
	        this.joinedAt = joinedAt;
	        this.wishCount = wishCount;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Instant getJoinedAt() {
	        return joinedAt;
	    }

	    public void setJoinedAt(Instant joinedAt) {
	        this.joinedAt = joinedAt;
	    }

	    public Long getWishCount() {
	        return wishCount;
	    }

	    public void setWishCount(Long pollCount) {
	        this.wishCount = pollCount;
	    }


}
