package com.web.bigdata.model.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.PostVoteDto;
import com.web.bigdata.model.mapper.PostVoteMapper;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	PostVoteMapper voteMapper;

	@Override
	public int checkVoted(PostVoteDto voteInfo) throws SQLException {
		return voteMapper.checkVoted(voteInfo);
	}

	@Override
	public PostVoteDto getVoteInfo(PostVoteDto voteInfo) throws SQLException {
		return voteMapper.getVoteInfo(voteInfo);
	}

	@Override
	public boolean insertVotePost(PostVoteDto voteInfo) throws SQLException {
		return voteMapper.insertVotePost(voteInfo) == 1;
	}

	@Override
	@Transactional
	public boolean updateVotePost(PostVoteDto voteInfo) throws SQLException {
		return voteMapper.updateVotePost(voteInfo) == 1;
	}

	@Override
	public int getVoteCountofPic(int picNo) throws SQLException {
		return voteMapper.getVoteCountofPic(picNo);
	}

	@Override
	public int getVoteCountofPost(int postNo) throws SQLException {
		return voteMapper.getVoteCountofPost(postNo);
	}

	@Override
	@Transactional
	public boolean updateVoteCnt(Map<String, Object> map) throws SQLException {
		return voteMapper.updateVoteCnt(map) == 1;
	}

}
