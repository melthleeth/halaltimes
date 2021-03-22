package com.web.bigdata.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ReviewDto {
	private String id_review;
	private String id_user;
	private String id_store;
	private float score;
	private String content;
	private String upload_date;
	private String update_date;
	private int likeCnt;
//	private int temp;
	private List<MultipartFile> files;
	private List<String> unmodified;
	private String thumbnail;
//	private String THUMBNAILTwo;
//	private int commentCnt;

	public List<String> getUnmodified() {
		return unmodified;
	}

	public void setUnmodified(List<String> unmodified) {
		this.unmodified = unmodified;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	public String getId_review() {
		return id_review;
	}

	public void setId_review(String id_review) {
		this.id_review = id_review;
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	public String getId_store() {
		return id_store;
	}

	public void setId_store(String id_store) {
		this.id_store = id_store;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "ReviewDto [id_review=" + id_review + ", id_user=" + id_user + ", id_store=" + id_store + ", score="
				+ score + ", content=" + content + ", upload_date=" + upload_date + ", update_date=" + update_date
				+ ", likeCnt=" + likeCnt + ", files=" + files + ", unmodified=" + unmodified + ", thumbnail="
				+ thumbnail + "]";
	}

//	public int getTemp() {
//		return temp;
//	}
//
//	public void setTemp(int temp) {
//		this.temp = temp;
//	}

//	public String getThumbnailTwo() {
//		return thumbnailTwo;
//	}
//
//	public void setThumbnailTwo(String thumbnailTwo) {
//		this.thumbnailTwo = thumbnailTwo;
//	}

//	public int getCommentCnt() {
//		return commentCnt;
//	}
//
//	public void setCommentCnt(int commentCnt) {
//		this.commentCnt = commentCnt;
//	}

}
