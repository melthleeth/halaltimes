package com.web.bigdata.model;

public class ImgDto {

	private int picNo;
	private int postNo;
	private String oriPicName; // 실제 파일
	private String modPicName; // 저장할 파일
	private String thumbnail; // 저장할 파일
	private long picSize;
	private String uploadDate;
	private int voteCnt;

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getPicNo() {
		return picNo;
	}

	public void setPicNo(int picNo) {
		this.picNo = picNo;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getModPicName() {
		return modPicName;
	}

	public void setModPicName(String modPicName) {
		this.modPicName = modPicName;
	}

	public String getOriPicName() {
		return oriPicName;
	}

	public void setOriPicName(String oriPicName) {
		this.oriPicName = oriPicName;
	}

	public long getPicSize() {
		return picSize;
	}

	public void setPicSize(long picSize) {
		this.picSize = picSize;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getVoteCnt() {
		return voteCnt;
	}

	public void setVoteCnt(int voteCnt) {
		this.voteCnt = voteCnt;
	}

}