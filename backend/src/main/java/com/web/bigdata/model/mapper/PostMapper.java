package com.web.bigdata.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.PostDto;
import com.web.bigdata.model.PostLikeDto;
import com.web.bigdata.model.PostParameterDto;

@Mapper
public interface PostMapper {

	/** 게시글 목록 : 검색 기능 */
	public List<PostDto> getList(PostParameterDto postParameterDto) throws SQLException;

	/** like 게시글 */
	public PostDto getLikePost(int no) throws SQLException;

	/** 총 게시글 개수 */
	public int getTotalCount(PostParameterDto postParameterDto) throws SQLException;

	/** 게시글 번호 조회 */
	public int getCount() throws SQLException;

	/** 마지막 게시글 번호 가져오기 */
	public int getLastPostNo(String email) throws SQLException;

	/** 하나의 게시글 정보 */
	public PostDto getOne(int postNo) throws SQLException;

	/** 게시글 작성 */
	public int write(PostDto postDto) throws SQLException;

	/** 게시글 수정 */
	public int modify(PostDto postDto) throws SQLException;

	/** 게시글 삭제 */
	public int delete(int postNo) throws SQLException;

	/** 해당 게시글의 좋아요 수 */
	public int likeCount(int postNo) throws SQLException;

	/** 좋아요 했는지 조회 */
	public int likeCheck(Map<String, Object> map) throws SQLException;

	/** 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public PostLikeDto likeInfo(Map<String, Object> map) throws SQLException;

	/** 게시글에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws SQLException;

	/** 좋아요 */
	public int updateLike(Map<String, Object> map) throws SQLException;

	/** 좋아요 체크 */
	public int like(Map<String, Object> map) throws SQLException;

	/** 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws SQLException;

	/** 게시글의 좋아요 수 갱신 */
	public int likeCntUp(int postNo) throws SQLException;

	public int likeCntDown(int postNo) throws SQLException;

	/** File insert to DB */
	public int uploadFile(ImgDto img) throws SQLException;

	/** 게시글의 이미지 가져오기 */
	public List<ImgDto> getImages(int postNo) throws SQLException;

	/** 사진 정보 */
	public ImgDto getImgInfo(String picNo) throws SQLException;

	/** File DB에서 지우기 */
	public int deleteImage(String picNo) throws SQLException;

	// PostTempController

	/** 임시저장 게시글 목록 */
	public List<PostDto> getTempList(PostParameterDto postParameterDto) throws SQLException;

	/** 임시저장 게시글 수 */
	public int getTempCount(String email) throws SQLException;

	/** 임시저장 게시글 작성 */
	public int writeTemp(PostDto postDto) throws SQLException;

	public int writeTemptoDB(PostDto postDto) throws SQLException;
}
