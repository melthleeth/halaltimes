package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.PostDto;
import com.web.bigdata.model.PostLikeDto;
import com.web.bigdata.model.PostParameterDto;
import com.web.bigdata.model.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostMapper postMapper;

	@Override
	public List<PostDto> getList(PostParameterDto postParameterDto) throws Exception {
		int start = postParameterDto.getPg() == 0 ? 0 : (postParameterDto.getPg() - 1) * postParameterDto.getSpp();
		postParameterDto.setStart(start);
		return postMapper.getList(postParameterDto);
	}

	@Override
	public PostDto getLikePost(int no) throws Exception {
		return postMapper.getLikePost(no-1);
	}

	@Override
	public PostDto getDetail(int postNo) throws Exception {
		return postMapper.getOne(postNo);
	}

	@Override
	public int getLastPostNo(String email) throws Exception {
		return postMapper.getLastPostNo(email);
	}

	@Override
	public boolean write(PostDto postDto) throws Exception {
		if (postDto.getTitle() == null || postDto.getContent() == null) {
			throw new Exception("No title or No Content!");
		}

		// 임시저장했던 글이라면
		if (postDto.getPostNo() != -1) {
			System.out.println("?2");
			return postMapper.writeTemptoDB(postDto) == 1;
		}

		if (postDto.getEmail() == null || "".equals(postDto.getEmail())) {
			throw new Exception("You are not Logged In!!");
		}

		return postMapper.write(postDto) == 1;
	}

	@Override
	@Transactional
	public boolean modify(PostDto postDto) throws Exception {
		return postMapper.modify(postDto) == 1;
	}

	@Override
	@Transactional
	public boolean delete(int postNo) throws Exception {
		return postMapper.delete(postNo) == 1;
	}

	@Override
	public int likeCount(int postNo) throws Exception {
		return postMapper.likeCount(postNo);
	}

	@Override
	public int likeCheck(Map<String, Object> map) throws Exception {
		return postMapper.likeCheck(map);
	}

	@Override
	public PostLikeDto likeInfo(Map<String, Object> map) throws Exception {
		return postMapper.likeInfo(map);
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		if (map.get("email") == null || "".equals(map.get("email").toString())) {
			throw new Exception();
		}
		return postMapper.insertLike(map);
	}

	@Override
	@Transactional
	public int like(Map<String, Object> map) throws Exception {
		return postMapper.like(map);
	}

	@Override
	@Transactional
	public int unlike(Map<String, Object> map) throws Exception {
		return postMapper.unlike(map);
	}

	@Override
	@Transactional
	public int likeCntUp(int postNo) throws Exception {
		return postMapper.likeCntUp(postNo);
	}

	@Override
	@Transactional
	public int likeCntDown(int postNo) throws Exception {
		return postMapper.likeCntDown(postNo);
	}

	@Override
	public boolean uploadFile(ImgDto img) throws Exception {
		return postMapper.uploadFile(img) == 1;
	}

	@Override
	public List<ImgDto> getImages(int postNo) throws Exception {
		return postMapper.getImages(postNo);
	}

	@Override
	public ImgDto getImgInfo(String picNo) throws Exception {
		return postMapper.getImgInfo(picNo);
	}

	@Override
	@Transactional
	public boolean deleteImage(String picNo) throws Exception {
		return postMapper.deleteImage(picNo) == 1;
	}

	@Override
	public boolean writeTemp(PostDto postDto) throws Exception {
		if (postDto.getTitle() == null || postDto.getContent() == null) {
			throw new Exception("No title or No Content!");
		}
		if (postDto.getEmail() == null || "".equals(postDto.getEmail())) {
			throw new Exception("You are not Logged In!!");
		}
		return postMapper.writeTemp(postDto) == 1;
	}

	@Override
	public List<PostDto> getTempList(PostParameterDto postParameterDto) throws Exception {
		return postMapper.getTempList(postParameterDto);
	}

	@Override
	public int getTempCount(String email) throws Exception {
		return postMapper.getTempCount(email);
	}
}