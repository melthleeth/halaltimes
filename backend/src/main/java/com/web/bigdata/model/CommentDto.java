package com.web.bigdata.model;

public class CommentDto {
	private int commentNo;
	private String content;
	private String email;
	private int postNo;
	private String uploadDate;
	private String modifyDate;
	private int likeCnt;
	private String name;
	private String profileImg;

	
	public CommentDto(int commentNo, String content, String email, int postNo, String uploadDate, String modifyDate,
			int likeCnt, String name, String profileImg) {
		super();
		this.commentNo = commentNo;
		this.content = content;
		this.email = email;
		this.postNo = postNo;
		this.uploadDate = uploadDate;
		this.modifyDate = modifyDate;
		this.likeCnt = likeCnt;
		this.name = name;
		this.profileImg = profileImg;
	}

	public CommentDto() {
		super();
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	@Override
	public String toString() {
		return "CommentDto [commentNo=" + commentNo + ", content=" + content + ", email=" + email + ", postNo=" + postNo
				+ ", uploadDate=" + uploadDate + ", modifyDate=" + modifyDate + ", likeCnt=" + likeCnt + ", name="
				+ name + ", profileImg=" + profileImg + "]";
	}
}
