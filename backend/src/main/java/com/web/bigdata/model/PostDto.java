package com.web.bigdata.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PostDto {
	private int postNo;
	private String title;
	private String content;
	private String uploadDate;
	private String modifyDate;
	private int likeCnt;
	private String email;
	private String name;
	private int temp;
	private List<MultipartFile> files;
	private List<String> unmodified;
	private String thumbnail;
	private String thumbnailTwo;
	private int commentCnt;

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

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnailTwo() {
		return thumbnailTwo;
	}

	public void setThumbnailTwo(String thumbnailTwo) {
		this.thumbnailTwo = thumbnailTwo;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
}
