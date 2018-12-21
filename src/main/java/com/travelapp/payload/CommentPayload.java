package com.travelapp.payload;

import java.io.Serializable;

public class CommentPayload implements Serializable {
    private String commentDetail;
    private Long userId;
    private Long tourId;
    private Long rateTypeId;

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Long getRateTypeId() {
        return rateTypeId;
    }

    public void setRateTypeId(Long rateTypeId) {
        this.rateTypeId = rateTypeId;
    }

    @Override
    public String toString() {
        return "CommentPayload{" +
                "commentDetail='" + commentDetail + '\'' +
                ", userId=" + userId +
                ", tourId=" + tourId +
                ", rateTypeId=" + rateTypeId +
                '}';
    }
}
