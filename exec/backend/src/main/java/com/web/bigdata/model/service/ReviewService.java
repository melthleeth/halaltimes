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

	/** 하나의 리뷰 정보 */
	public ReviewDto getDetail(int id_review) throws Exception;

	/** 마지막으로 작성한 리뷰 번호 */
	public String getLastReview(String id_user) throws Exception;

	/** 리뷰 작성 */
	public boolean write(ReviewDto reviewDto) throws Exception;

	/** 리뷰 수정 */
	public boolean modify(ReviewDto reviewDto) throws Exception;

	/** 리뷰 삭제 */
	public boolean delete(int id_review) throws Exception;

	/** 해당 리뷰의 좋아요 수 */
	public int likeCount(int id_review) throws Exception;

	/** 좋아요 했는지 조회 */
	public String likeCheck(Map<String, Object> map) throws Exception;

	/** 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public ReviewLikeDto likeInfo(Map<String, Object> map) throws Exception;

	/** 리뷰에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 */
	public int like(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws Exception;

	/** 리뷰의 좋아요 수 갱신 */
	public int likeCntUp(int id_review) throws Exception;

	public int likeCntDown(int id_review) throws Exception;

	/** 해당 store의 review들 가져오기 */
	public List<ReviewDto> getStoreReviews(int id_store) throws Exception;

	/** 해당 store의 review 갯수 가져오기 */
	public int getReviewCount(int id_store) throws Exception;

	/** review 를 만들자 마자 upload_date로 id_review 가져오기 */
	public String getIdReview(String upload_date) throws Exception;

	/** id_review로 store_name 가져오기 */
	public String getStoreName(int id_store) throws Exception;

}
