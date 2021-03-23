package com.web.bigdata.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.ReviewLikeDto;
import com.web.bigdata.model.ReviewParameterDto;

@Mapper
public interface ReviewMapper {

	/** 리뷰 목록 : 검색 기능 */
	public List<ReviewDto> getList(ReviewParameterDto reviewParameterDto) throws SQLException;

	/** like 리뷰 */
	public ReviewDto getLikeReview(String id_review) throws SQLException;

	/** 총 리뷰 개수 */
	public int getTotalCount(ReviewParameterDto reviewParameterDto) throws SQLException;

	/** 리뷰 번호 조회 */
	public int getCount() throws SQLException;

	/** 마지막 리뷰 번호 가져오기 */
	public String getLastReview(String id_user) throws SQLException;

	/** 하나의 리뷰 정보 */
	public ReviewDto getOne(String id_review) throws SQLException;

	/** 리뷰 작성 */
	public int write(ReviewDto reviewDto) throws SQLException;

	/** 리뷰 수정 */
	public int modify(ReviewDto reviewDto) throws SQLException;

	/** 리뷰 삭제 */
	public int delete(String id_review) throws SQLException;

	/** 해당 리뷰의 좋아요 수 */
	public int likeCount(String id_review) throws SQLException;

	/** 좋아요 했는지 조회 */
	public int likeCheck(Map<String, Object> map) throws SQLException;

	/** 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public ReviewLikeDto likeInfo(Map<String, Object> map) throws SQLException;

	/** 리뷰에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws SQLException;

	/** 좋아요 */
	public int updateLike(Map<String, Object> map) throws SQLException;

	/** 좋아요 체크 */
	public int like(Map<String, Object> map) throws SQLException;

	/** 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws SQLException;

	/** 리뷰의 좋아요 수 갱신 */
	public int likeCntUp(String id_review) throws SQLException;

	public int likeCntDown(String id_review) throws SQLException;

	/** File insert to DB */
	public int uploadFile(ImgDto img) throws SQLException;

	/** 리뷰의 이미지 가져오기 */
	public List<ImgDto> getImages(String id_review) throws SQLException;

	/** 사진 정보 */
	public ImgDto getImgInfo(String id_review_image) throws SQLException;

	/** File DB에서 지우기 */
	public int deleteImage(String id_review_image) throws SQLException;

	// ReviewTempController
//
//	/** 임시저장 리뷰 목록 */
//	public List<ReviewDto> getTempList(ReviewParameterDto reviewParameterDto) throws SQLException;
//
//	/** 임시저장 리뷰 수 */
//	public int getTempCount(String email) throws SQLException;
//
//	/** 임시저장 리뷰 작성 */
//	public int writeTemp(ReviewDto reviewDto) throws SQLException;
//
//	public int writeTemptoDB(ReviewDto review) throws SQLException;
}
