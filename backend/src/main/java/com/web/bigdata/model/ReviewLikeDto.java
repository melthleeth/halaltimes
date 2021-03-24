package com.web.bigdata.model;

public class ReviewLikeDto {
//	private int pmLikeNo;
//	private int postNo;
	private String id_review_like;
	private String id_review;
	private String id_user;
	private int active; // active

	public String getId_review_like() {
		return id_review_like;
	}

	public void setId_review_like(String id_review_like) {
		this.id_review_like = id_review_like;
	}

	public String getId_review() {
		return id_review;
	}

	public void setId_review(String id_review) {
		this.id_review = id_review;
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
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
