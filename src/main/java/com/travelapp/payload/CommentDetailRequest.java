package com.travelapp.payload;

import java.io.Serializable;

public class CommentDetailRequest implements Serializable {
	private long id;
    private String commentDetail;
    private UserDetailRequest user;
    private TourDetailRequests tour;

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
    public String toString() {
        return "CommentPayload{" +
                "commentDetail='" + commentDetail + '\'' +
                ", user=" + user +
                ", tour=" + tour +
                '}';
    }
}
