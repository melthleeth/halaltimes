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
	public ReviewDto getOne(int id_review) throws SQLException;

	/** 리뷰 작성 */
	public int write(ReviewDto reviewDto) throws SQLException;

	/** 리뷰 수정 */
	public int modify(ReviewDto reviewDto) throws SQLException;

	/** 리뷰 삭제 */
	public int delete(int id_review) throws SQLException;

	/** 해당 리뷰의 좋아요 수 */
	public int likeCount(int id_review) throws SQLException;

	/** 좋아요 했는지 조회 */
	public String likeCheck(Map<String, Object> map) throws SQLException;

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
	public int likeCntUp(int id_review) throws SQLException;

	public int likeCntDown(int id_review) throws SQLException;

	/** File insert to DB */
	public int uploadFile(ImgDto img) throws SQLException;

	/** 리뷰의 이미지 가져오기 */
	public List<ImgDto> getImages(int id_review) throws SQLException;

	/** 사진 정보 */
	public ImgDto getImgInfo(String id_review_image) throws SQLException;

	public int deleteAllImage(String id_review) throws SQLException;
	
	/** File DB에서 지우기 */
	public int deleteImage(String id_review_image) throws SQLException;
	
	/** 해당 store의 review들 가져오기 */
	public List<ReviewDto> getStoreReviews(int id_store)throws SQLException;

	/** 해당 store의 review 갯수 가져오기 */
	public int getReviewCount(int id_store)throws SQLException;

	/** review 를 만들자 마자 upload_date로 id_review 가져오기*/
	public String getIdReview(String upload_date)throws SQLException;

	/** review 를 삭제(active = 0) 할 시 review_image도 삭제 (active = 0) */
	public int deleteCascade(int id_review)throws SQLException;

	/** review 를 삭제(active = 0) 할 시 review_image도 삭제 (active = 0) */
	public String getStoreName(int id_store)throws SQLException;

}
