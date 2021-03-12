package com.web.bigdata.model;

public class CommentLikeDto {
	private int cmLikeNo;
	private int commentNo;
	private String email;
	private String likeCheck;

	public int getCmLikeNo() {
		return cmLikeNo;
	}

	public void setCmLikeNo(int cmLikeNo) {
		this.cmLikeNo = cmLikeNo;
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
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
