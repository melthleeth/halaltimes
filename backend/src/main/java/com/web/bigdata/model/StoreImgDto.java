package com.web.bigdata.model;

public class StoreImgDto {

	private String id_store_image;
	private String id_store;
	private String image;
	private String original_image; // 실제 파일
	private String modified_image; // 저장할 파일
	private String thumb_image; // 저장할 파일
	private int active;
	
	public String getId_store_image() {
		return id_store_image;
	}
	public void setId_store_image(String id_store_image) {
		this.id_store_image = id_store_image;
	}
	public String getId_store() {
		return id_store;
	}
	public void setId_store(String id_store) {
		this.id_store = id_store;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public String getThumb_image() {
		return thumb_image;
	}
	public void setThumb_image(String thumb_image) {
		this.thumb_image = thumb_image;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}