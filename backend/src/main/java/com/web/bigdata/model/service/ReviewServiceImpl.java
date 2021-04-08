package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.ReviewLikeDto;
import com.web.bigdata.model.ReviewParameterDto;
import com.web.bigdata.model.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;

	@Override
	public List<ReviewDto> getList(ReviewParameterDto reviewParameterDto) throws Exception {
		int start = reviewParameterDto.getPg() == 0 ? 0
				: (reviewParameterDto.getPg() - 1) * reviewParameterDto.getSpp();
		reviewParameterDto.setStart(start);
		return reviewMapper.getList(reviewParameterDto);
	}

	@Override
	public ReviewDto getDetail(int id_review) throws Exception {
		return reviewMapper.getOne(id_review);
	}

	@Override
	public String getLastReview(String id_user) throws Exception {
		return reviewMapper.getLastReview(id_user);
	}

	@Override
	public boolean write(ReviewDto reviewDto) throws Exception {
		if (reviewDto.getContent() == null) {
			throw new Exception("No Content!");
		}

		return reviewMapper.write(reviewDto) == 1;
	}

	@Override
	@Transactional
	public boolean modify(ReviewDto reviewDto) throws Exception {
		return reviewMapper.modify(reviewDto) == 1;
	}

	@Override
	@Transactional
	public boolean delete(int id_review) throws Exception {
		return reviewMapper.delete(id_review) == 1;
	}

	@Override
	public int likeCount(int id_review) throws Exception {
		return reviewMapper.likeCount(id_review);
	}

	@Override
	public String likeCheck(Map<String, Object> map) throws Exception {
		return reviewMapper.likeCheck(map);
	}

	@Override
	public ReviewLikeDto likeInfo(Map<String, Object> map) throws Exception {
		return reviewMapper.likeInfo(map);
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		if (map.get("id_user") == null || "".equals(map.get("id_user").toString())) {
			throw new Exception();
		}
		return reviewMapper.insertLike(map);
	}

	@Override
	@Transactional
	public int like(Map<String, Object> map) throws Exception {
		return reviewMapper.like(map);
	}

	@Override
	@Transactional
	public int unlike(Map<String, Object> map) throws Exception {
		return reviewMapper.unlike(map);
	}

	@Override
	@Transactional
	public int likeCntUp(int id_review) throws Exception {
		return reviewMapper.likeCntUp(id_review);
	}

	@Override
	@Transactional
	public int likeCntDown(int id_review) throws Exception {
		return reviewMapper.likeCntDown(id_review);
	}

	@Override
	public List<ReviewDto> getStoreReviews(int id_store) throws Exception {
		return reviewMapper.getStoreReviews(id_store);
	}

	@Override
	public int getReviewCount(int id_store) throws Exception {
		return reviewMapper.getReviewCount(id_store);
	}

	@Override
	public String getIdReview(String upload_date) throws Exception {
		return reviewMapper.getIdReview(upload_date);
	}

	@Override
	public String getStoreName(int id_store) throws Exception {
		return reviewMapper.getStoreName(id_store);
	}
}