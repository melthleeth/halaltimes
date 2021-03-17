package com.web.bigdata.model;

public class PostLikeDto {
	private int pmLikeNo;
	private int postNo;
	private String email;
	private String likeCheck;

	public int getPmLikeNo() {
		return pmLikeNo;
	}

	public void setPmLikeNo(int pmLikeNo) {
		this.pmLikeNo = pmLikeNo;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLikeCheck() {
		return likeCheck;
	}

	public void setLikeCheck(String likeCheck) {
		this.likeCheck = likeCheck;
	}

}
