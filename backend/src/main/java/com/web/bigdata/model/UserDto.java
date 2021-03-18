package com.web.bigdata.model;

public class UserDto {
	private String id_user;
	private String email;
	private String password;
	private String nickname;
	private String born_year;
	private String profile_image;
	private String gender;
	private String prePwd;
	private String role;

	public UserDto() {
		super();
	}

	protected UserDto(String id_user, String email, String password, String nickname, String born_year,
			String profile_image, String gender, String prePwd, String role) {
		super();
		this.id_user = id_user;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.born_year = born_year;
		this.profile_image = profile_image;
		this.gender = gender;
		this.prePwd = prePwd;
		this.role = role;
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBorn_year() {
		return born_year;
	}

	public void setBorn_year(String born_year) {
		this.born_year = born_year;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPrePwd() {
		return prePwd;
	}

	public void setPrePwd(String prePwd) {
		this.prePwd = prePwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDto [id_user=" + id_user + ", email=" + email + ", password=" + password + ", nickname=" + nickname
				+ ", born_year=" + born_year + ", profile_image=" + profile_image + ", gender=" + gender + ", prePwd="
				+ prePwd + ", role=" + role + "]";
	}

}
