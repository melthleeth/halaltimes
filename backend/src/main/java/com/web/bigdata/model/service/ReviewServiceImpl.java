package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.ImgDto;
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
	public ReviewDto getLikeReview(String id_review) throws Exception {
		return reviewMapper.getLikeReview(id_review);
	}

	@Override
	public ReviewDto getDetail(String id_review) throws Exception {
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

//		// 임시저장했던 글이라면
//		if (reviewDto.getPostNo() != -1) {
//			System.out.println("?2");
//			return reviewMapper.writeTemptoDB(reviewDto) == 1;
//		}

		if (reviewDto.getId_user() == null || "".equals(reviewDto.getId_user())) {
			throw new Exception("You are not Logged In!!");
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
	public boolean delete(String id_review) throws Exception {
		return reviewMapper.delete(id_review) == 1;
	}

	@Override
	public int likeCount(String id_review) throws Exception {
		return reviewMapper.likeCount(id_review);
	}

	@Override
	public int likeCheck(Map<String, Object> map) throws Exception {
		return reviewMapper.likeCheck(map);
	}

	@Override
	public ReviewLikeDto likeInfo(Map<String, Object> map) throws Exception {
		return reviewMapper.likeInfo(map);
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		System.out.println("serviceImpl 까지옴");
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
	public int likeCntUp(String id_review) throws Exception {
		return reviewMapper.likeCntUp(id_review);
	}

	@Override
	@Transactional
	public int likeCntDown(String id_review) throws Exception {
		return reviewMapper.likeCntDown(id_review);
	}

	@Override
	public boolean uploadFile(ImgDto img) throws Exception {
		return reviewMapper.uploadFile(img) == 1;
	}

	@Override
	public List<ImgDto> getImages(String id_review) throws Exception {
		return reviewMapper.getImages(id_review);
	}

	@Override
	public ImgDto getImgInfo(String id_review_image) throws Exception {
		return reviewMapper.getImgInfo(id_review_image);
	}

	@Override
	@Transactional
	public boolean deleteImage(String id_review_image) throws Exception {
		return reviewMapper.deleteImage(id_review_image) == 1;
	}

//	@Override
//	public boolean writeTemp(ReviewDto reviewDto) throws Exception {
//		if (reviewDto.getTitle() == null || reviewDto.getContent() == null) {
//			throw new Exception("No title or No Content!");
//		}
//		if (reviewDto.getEmail() == null || "".equals(reviewDto.getEmail())) {
//			throw new Exception("You are not Logged In!!");
//		}
//		return reviewMapper.writeTemp(reviewDto) == 1;
//	}
//
//	@Override
//	public List<ReviewDto> getTempList(ReviewParameterDto reviewParameterDto) throws Exception {
//		return reviewMapper.getTempList(reviewParameterDto);
//	}
//
//	@Override
//	public int getTempCount(String email) throws Exception {
//		return reviewMapper.getTempCount(email);
//	}
}