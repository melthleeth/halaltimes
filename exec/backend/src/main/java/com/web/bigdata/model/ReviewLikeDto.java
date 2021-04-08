package com.web.bigdata.model;

public class ReviewLikeDto {
	private int id_review_like;
	private int id_review;
	private int id_user;
	private int active;

	public int getId_review_like() {
		return id_review_like;
	}

	public void setId_review_like(int id_review_like) {
		this.id_review_like = id_review_like;
	}

	public int getId_review() {
		return id_review;
	}

	public void setId_review(int id_review) {
		this.id_review = id_review;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public int getLiked() {
		return active;
	}

	public void setLiked(int active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "ReviewLikeDto [id_review_like=" + id_review_like + ", id_review=" + id_review + ", id_user=" + id_user
				+ ", active=" + active + "]";
	}
	
	
}
