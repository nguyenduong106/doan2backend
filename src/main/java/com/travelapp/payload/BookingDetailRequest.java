package com.travelapp.payload;

import java.io.Serializable;

public class BookingDetailRequest implements Serializable {
	private long id;
    private int totalPeople;
    private String securityCode;
    private String expirationDate;
    private String cardNumber;
    private String cardName;
    private	UserDetailRequest user;
    private TourDetailRequests tour;
    private boolean status; 
    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

	public UserDetailRequest getUser() {
		return user;
	}

	public void setUser(UserDetailRequest user) {
		this.user = user;
	}

	public TourDetailRequests getTour() {
		return tour;
	}

	public void setTour(TourDetailRequests tour) {
		this.tour = tour;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
 
}
