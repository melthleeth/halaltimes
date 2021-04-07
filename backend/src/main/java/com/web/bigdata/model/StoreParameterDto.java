package com.web.bigdata.model;

public class StoreParameterDto {
	private int pg;
	private int spp;
	private int start;
	private int id_store;
	private String key;
	private String word;
	private String email;
	private String sortBy;

	public int getId_store() {
		return id_store;
	}

	public void setId_store(int id_store) {
		this.id_store = id_store;
	}

	public StoreParameterDto() {
		pg = 1;
		spp = 10;
	}

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		pg = pg == 0 ? 1 : pg;
		this.pg = pg;
	}

	public int getSpp() {
		return spp;
	}

	public void setSpp(int spp) {
		this.spp = spp;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public String toString() {
		return "StoreParameterDto [pg=" + pg + ", spp=" + spp + ", start=" + start + ", id_store=" + id_store + ", key="
				+ key + ", word=" + word + ", email=" + email + ", sortBy=" + sortBy + "]";
	}
	
	
}
