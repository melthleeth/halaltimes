package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;



public interface StoreService {
	// PostController

	/** 게시글 목록 : 검색 기능 */
	public List<StoreDto> getList(StoreParameterDto postParameterDto) throws Exception;
	
	/** like 게시글 */
	public StoreDto getLikeStore(int no) throws Exception;

	/** 하나의 게시글 정보 */
	public StoreDto getDetail(String id_store) throws Exception;

	/** 마지막으로 작성한 게시글 번호 */
	public String getLastIdStore(String email) throws Exception;

	/** 게시글 작성 */
	public boolean write(StoreDto postDto) throws Exception;

	/** 게시글 수정 */
	public boolean modify(StoreDto postDto) throws Exception;

	/** 게시글 삭제 */
	public boolean delete(int postNo) throws Exception;

	/** 해당 게시글의 좋아요 수 */
	public int likeCount(int postNo) throws Exception;

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
	public ImgDto getImgInfo(String picNo) throws Exception;

	/** File DB에서 지우기 */
	public boolean deleteImage(String picNo) throws Exception;

	// PostTempController

	/** 임시저장 게시글 목록 */
	public List<StoreDto> getTempList(StoreParameterDto postParameterDto) throws Exception;

	/** 임시저장 게시글 수 */
	public int getTempCount(String email) throws Exception;

	/** 임시저장글 작성 */
	public boolean writeTemp(StoreDto postDto) throws Exception;	
	
	/** store_image에 사진 저장  */
	public int insertImg(Map<String, Object> map) throws Exception;

	/** index에 해당하는 id_store 가져오기 */
	public String getIdStore(int index) throws Exception;

	public int insertImgUrl(Map<String, Object> map) throws Exception;

	/** store 클릭시 조회수 증가 */
	public int hitsUp(String id_store) throws Exception;
}
