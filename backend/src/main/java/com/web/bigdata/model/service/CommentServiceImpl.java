package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.bigdata.model.CommentDto;
import com.web.bigdata.model.CommentLikeDto;
import com.web.bigdata.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentMapper commentMapper;

	@Override
	public CommentDto getOne(int commentNo) throws Exception {
		return commentMapper.getOne(commentNo);
	}

	@Override
	public List<CommentDto> commentList(Map<String, Object> map) throws Exception {
		System.out.println(map.get("postNo"));
		System.out.println(map.get("sortBy"));
		return commentMapper.commentList(map);
	}

	@Override
	public List<CommentDto> infiniteScrollDown(Map<String, Object> map) throws Exception {
		return commentMapper.infiniteScrollDown(map);
	}

	@Override
	public boolean commentWrite(CommentDto dto) throws Exception {
		return commentMapper.commentWrite(dto);
	}

	@Override
	public boolean commentUpdate(CommentDto dto) throws Exception {
		return commentMapper.commentUpdate(dto);
	}

	@Override
	public boolean commentDelete(int commentNo) throws Exception {
		return commentMapper.commentDelete(commentNo);
	}

	@Override
	public int likeCount(int commentNo) throws Exception {
		return commentMapper.likeCount(commentNo);
	}

	@Override
	public int likeCheck(Map<String, Object> map) throws Exception {
		return commentMapper.likeCheck(map);
	}

	@Override
	public CommentLikeDto likeInfo(Map<String, Object> map) throws Exception {
		return commentMapper.likeInfo(map);
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		return commentMapper.insertLike(map);
	}

	@Override
	public int like(Map<String, Object> map) throws Exception {
		return commentMapper.like(map);
	}

	@Override
	public int unlike(Map<String, Object> map) throws Exception {
		return commentMapper.unlike(map);
	}

	@Override
	public int likeCntUp(int commentNo) throws Exception {
		return commentMapper.likeCntUp(commentNo);
	}

	@Override
	public int likeCntDown(int commentNo) throws Exception {
		return commentMapper.likeCntDown(commentNo);
	}


}