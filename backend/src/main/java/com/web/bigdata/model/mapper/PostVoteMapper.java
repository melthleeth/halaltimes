package com.web.bigdata.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.bigdata.model.PostVoteDto;

@Mapper
public interface PostVoteMapper {

	/** 투표했는지 여부 */
	public int checkVoted(PostVoteDto voteInfo) throws SQLException;

	/** 투표 정보 */
	public PostVoteDto getVoteInfo(PostVoteDto voteInfo) throws SQLException;

	/** 투표한적 없다면 투표한다 */
	public int insertVotePost(PostVoteDto voteInfo) throws SQLException;

	/** 투표한적 있다면 투표를 업데이트 한다 */
	public int updateVotePost(PostVoteDto voteInfo) throws SQLException;

	/** 해당 사진 투표 수 */
	public int getVoteCountofPic(int picNo) throws SQLException;

	/** 게시글 투표 수 */
	public int getVoteCountofPost(int postNo) throws SQLException;

	/** 사진 투표수 업데이트 */
	public int updateVoteCnt(Map<String, Object> map) throws SQLException;
}
