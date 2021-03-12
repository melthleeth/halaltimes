package com.web.bigdata.model;

public class CommentParameterDto {
	private int pg;
	private int spp;
	private int start;

	public CommentParameterDto() {
		super();
	}

	public CommentParameterDto(int pg, int spp, int start) {
		super();
		this.pg = pg;
		this.spp = spp;
		this.start = start;
	}

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
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

}
