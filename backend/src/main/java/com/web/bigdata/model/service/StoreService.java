package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;
import com.web.bigdata.model.UserClusteredDto;

public interface StoreService {

	/** 식당 목록 : 검색 기능 */
	public List<StoreDto> getList(StoreParameterDto postParameterDto) throws Exception;

	public StoreDto getRecommList(int id_store) throws Exception;

	public List<UserClusteredDto> getClusteredStores(int label) throws Exception;

	/** 하나의 식당 정보 */
	public StoreDto getDetail(int id_store) throws Exception;

	/** 북마크 했었던 적이 있었을 때 가져올 정보 */
	public BookmarkDto likeInfo(Map<String, Object> map) throws Exception;

	/** 식당에 처음으로 북마크 누름 */
	public int insertLike(Map<String, Object> map) throws Exception;

	/** 북마크 체크 */
	public int like(Map<String, Object> map) throws Exception;

	/** 북마크 체크 해제 */
	public int unlike(Map<String, Object> map) throws Exception;

	/** store 클릭시 조회수 증가 */
	public int hitsUp(int id_store) throws Exception;

	/** id_store으로 store_name 가져오기 */
	public String getStoreNameByIdStore(int id_store) throws Exception;

	/** 해당 음식점의 평균 평점 가져오기 */
	public String getStoreAvgScore(int id_store) throws Exception;

	/** 해당 음식점의 주소 가져오기 */
	public String getStoreAddress(int id_store) throws Exception;

}
