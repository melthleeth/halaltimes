package com.web.bigdata.model;

public class UserClusteredDto {
	private int id_user_clustered;
	private int id_store;
	private int clustered_no;

	public int getId_user_clustered() {
		return id_user_clustered;
	}

	public void setId_user_clustered(int id_user_clustered) {
		this.id_user_clustered = id_user_clustered;
	}

	public int getId_store() {
		return id_store;
	}

	public void setId_store(int id_store) {
		this.id_store = id_store;
	}

	public int getClustered_no() {
		return clustered_no;
	}

	public void setClustered_no(int clustered_no) {
		this.clustered_no = clustered_no;
	}

	@Override
	public String toString() {
		return "UserClusteredDto [id_user_clustered=" + id_user_clustered + ", id_store=" + id_store + ", clustered_no="
				+ clustered_no + "]";
	}

}
