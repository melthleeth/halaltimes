package com.web.bigdata.model;

public class ImgDto {

	private String id_review_image;
	private String id_review;
	private String image;
	private String oriPicName; // 실제 파일
	private String modPicName; // 저장할 파일
	private String thumb_image; // 저장할 파일
//	private long picsize;

//	private int picNo;
//	private int postNo;
//	private String uploadDate;
//	private int voteCnt;
//
//	public int getPicNo() {
//		return picNo;
//	}
//
//	public void setPicNo(int picNo) {
//		this.picNo = picNo;
//	}
//
//	public int getPostNo() {
//		return postNo;
//	}
//
//	public void setPostNo(int postNo) {
//		this.postNo = postNo;
//	}
//

	public String getModPicName() {
		return modPicName;
	}

	public String getId_review_image() {
		return id_review_image;
	}

	public void setId_review_image(String id_review_image) {
		this.id_review_image = id_review_image;
	}

	public String getId_review() {
		return id_review;
	}

	public void setId_review(String id_review) {
		this.id_review = id_review;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumb_image() {
		return thumb_image;
	}

	public void setThumb_image(String thumb_image) {
		this.thumb_image = thumb_image;
	}

//	public long getPicsize() {
//		return picsize;
//	}
//
//	public void setPicsize(long picsize) {
//		this.picsize = picsize;
//	}

	public void setModPicName(String modPicName) {
		this.modPicName = modPicName;
	}

	public String getOriPicName() {
		return oriPicName;
	}

	public void setOriPicName(String oriPicName) {
		this.oriPicName = oriPicName;
	}

// 	public String getUploadDate() {
//		return uploadDate;
//	}
//
//	public void setUploadDate(String uploadDate) {
//		this.uploadDate = uploadDate;
//	}
//
//	public int getVoteCnt() {
//		return voteCnt;
//	}
//
//	public void setVoteCnt(int voteCnt) {
//		this.voteCnt = voteCnt;
//	}

}