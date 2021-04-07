package com.web.bigdata.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;
import com.web.bigdata.model.UserClusteredDto;

@Mapper
public interface StoreMapper {

	/** 게시글 목록 : 검색 기능 */
	public List<StoreDto> getList(StoreParameterDto storeParameterDto) throws Exception;

	public StoreDto getRecommList(int id_store) throws Exception;

	public int getLabel(int id_user) throws Exception;

	public List<UserClusteredDto> getClusteredStores(int label) throws Exception;

	/** like 게시글 */
	public StoreDto getLikeStore(int no) throws Exception;

	/** 하나의 게시글 정보 */
	public StoreDto getDetail(int id_store) throws Exception;

	/** 마지막으로 작성한 게시글 번호 */
	public String getLastIdStore(String email) throws Exception;

	/** 게시글 작성 */
	public boolean write(StoreDto storeDto) throws Exception;

	/** 게시글 수정 */
	public boolean modify(StoreDto storeDto) throws Exception;

	/** 게시글 삭제 */
	public boolean delete(String id_store) throws Exception;

	/** 해당 게시글의 좋아요 수 */
	public int likeCount(String id_store) throws Exception;

	/** 좋아요 했는지 조회 */
	public int likeCheck(Map<String, Object> map) throws Exception;

	/** 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public BookmarkDto likeInfo(Map<String, Object> map) throws Exception;

	/** 게시글에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 */
	public int like(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws Exception;

	/** 게시글의 좋아요 수 갱신 */
	public int likeCntUp(String id_store) throws Exception;

	public int likeCntDown(String id_store) throws Exception;

	/** File insert to DB */
	public boolean uploadFile(ImgDto img) throws Exception;

	/** 게시글의 이미지 가져오기 */
	public List<ImgDto> getImages(String id_store) throws Exception;

	/** 사진 정보 */
	public ImgDto getImgInfo(String id_store) throws Exception;

	/** File DB에서 지우기 */
	public boolean deleteImage(String id_store) throws Exception;

	// PostTempController

	/** 임시저장 게시글 목록 */
	public List<StoreDto> getTempList(StoreParameterDto storeParameterDto) throws Exception;

	/** 임시저장 게시글 수 */
	public int getTempCount(String email) throws Exception;

	/** 임시저장글 작성 */
	public boolean writeTemp(StoreDto storeDto) throws Exception;

	/** 임시저장글 작성 */
	public String getIdStore(int index) throws Exception;

	/** 임시저장글 작성 */
	public int insertImgUrl(Map<String, Object> map) throws Exception;

	/** store 클릭시 조회수 증가 */
	public int hitsUp(int id_store) throws Exception;

	/** id_store으로 store_name 가져오기 */
	public String getStoreNameByIdStore(int id_store) throws SQLException;

	/** 해당 음식점의 평균 평점 가져오기 */
	public String getStoreAvgScore(int id_store) throws SQLException;

	/** 해당 음식점의 주소 가져오기 */
	public String getStoreAddress(int id_store) throws SQLException;

}
