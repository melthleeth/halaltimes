package com.web.bigdata.model;

public class MemberDto {
	private String email;
	private String name;
	private String pwd;
	private String role;
	private String joinDate;
	private String introduce;
	private String profileImg;
	private String prePwd;
	private int postCnt;
	private int commentCnt;

	public MemberDto() {
		super();
	}

	protected MemberDto(String email, String name, String pwd, String role, String joinDate, String introduce,
			String profileImg, String prePwd, int postCnt, int commentCnt) {
		super();
		this.email = email;
		this.name = name;
		this.pwd = pwd;
		this.role = role;
		this.joinDate = joinDate;
		this.introduce = introduce;
		this.profileImg = profileImg;
		this.prePwd = prePwd;
		this.postCnt = postCnt;
		this.commentCnt = commentCnt;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPrePwd() {
		return prePwd;
	}

	public void setPrePwd(String prePwd) {
		this.prePwd = prePwd;
	}

	public int getPostCnt() {
		return postCnt;
	}

	public void setPostCnt(int postCnt) {
		this.postCnt = postCnt;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

}
