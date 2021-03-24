package com.web.bigdata.model;

public class ImgDto {

	private String id_review_image;
	private String id_review;
	private String image;
	private String original_image; // 실제 파일
	private String modified_image; // 저장할 파일
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

	public String getOriginal_image() {
		return original_image;
	}

	public void setOriginal_image(String original_image) {
		this.original_image = original_image;
	}

	public String getModified_image() {
		return modified_image;
	}

	public void setModified_image(String modified_image) {
		this.modified_image = modified_image;
	}

	

//	public long getPicsize() {
//		return picsize;
//	}
//
//	public void setPicsize(long picsize) {
//		this.picsize = picsize;
//	}


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