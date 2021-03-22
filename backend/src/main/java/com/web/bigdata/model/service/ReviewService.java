package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.ReviewLikeDto;
import com.web.bigdata.model.ReviewParameterDto;

public interface ReviewService {

	// ReviewController

	/** 리뷰 목록 : 검색 기능 */
	public List<ReviewDto> getList(ReviewParameterDto reviewParameterDto) throws Exception;

	/** like 리뷰 */
	public ReviewDto getLikeReview(String ID_REVIEW) throws Exception;

	/** 하나의 리뷰 정보 */
	public ReviewDto getDetail(String ID_REVIEW) throws Exception;

	/** 마지막으로 작성한 리뷰 번호 */
	public String getLastReview(String ID_USER) throws Exception;

	/** 리뷰 작성 */
	public boolean write(ReviewDto reviewDto) throws Exception;

	/** 리뷰 수정 */
	public boolean modify(ReviewDto reviewDto) throws Exception;

	/** 리뷰 삭제 */
	public boolean delete(String ID_REVIEW) throws Exception;

	/** 해당 리뷰의 좋아요 수 */
	public int likeCount(String ID_REVIEW) throws Exception;

	/** 좋아요 했는지 조회 */
	public int likeCheck(Map<String, Object> map) throws Exception;

	/** 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public ReviewLikeDto likeInfo(Map<String, Object> map) throws Exception;

	/** 리뷰에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 */
	public int like(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws Exception;

	/** 리뷰의 좋아요 수 갱신 */
	public int likeCntUp(String ID_REVIEW) throws Exception;

	public int likeCntDown(String ID_REVIEW) throws Exception;

	/** File insert to DB */
	public boolean uploadFile(ImgDto img) throws Exception;

	/** 리뷰의 이미지 가져오기 */
	public List<ImgDto> getImages(String ID_REVIEW) throws Exception;

	/** 사진 정보 */
	public ImgDto getImgInfo(String id_review_image) throws Exception;

	/** File DB에서 지우기 */
	public boolean deleteImage(String id_review_image) throws Exception;

	// PostTempController

//	/** 임시저장 리뷰 목록 */
//	public List<ReviewDto> getTempList(ReviewParameterDto postParameterDto) throws Exception;
//
//	/** 임시저장 리뷰 수 */
//	public int getTempCount(String email) throws Exception;
//
//	/** 임시저장글 작성 */
//	public boolean writeTemp(ReviewDto postDto) throws Exception;

}
