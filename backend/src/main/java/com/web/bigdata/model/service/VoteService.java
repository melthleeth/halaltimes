package com.web.bigdata.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.web.bigdata.model.PostVoteDto;

public interface VoteService {

	/** 투표했는지 여부 */
	public int checkVoted(PostVoteDto voteInfo) throws SQLException;

	/** 투표 정보 */
	public PostVoteDto getVoteInfo(PostVoteDto voteInfo) throws SQLException;

	/** 투표한적 없다면 투표한다 */
	public boolean insertVotePost(PostVoteDto voteInfo) throws SQLException;

	/** 투표한적 있다면 투표를 업데이트 한다 */
	public boolean updateVotePost(PostVoteDto voteInfo) throws SQLException;

	/** 해당 사진 투표 수 */
	public int getVoteCountofPic(int picNo) throws SQLException;

	/** 게시글 투표 수 */
	public int getVoteCountofPost(int postNo) throws SQLException;

	/** 사진 투표수 업데이트 */
	public boolean updateVoteCnt(Map<String, Object> map) throws SQLException;
}
