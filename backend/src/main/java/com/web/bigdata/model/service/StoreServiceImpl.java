package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;

public class StoreServiceImpl implements StoreService {

	@Override
	public List<StoreDto> getList(StoreParameterDto postParameterDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreDto getLikePost(int no) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreDto getDetail(String id_store) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastIdStore(String email) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean write(StoreDto postDto) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modify(StoreDto postDto) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int postNo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int likeCount(int postNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int likeCheck(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BookmarkDto likeInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int like(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unlike(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int likeCntUp(String id_store) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int likeCntDown(String id_store) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean uploadFile(ImgDto img) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ImgDto> getImages(String id_store) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImgDto getImgInfo(String picNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteImage(String picNo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<StoreDto> getTempList(StoreParameterDto postParameterDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTempCount(String email) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean writeTemp(StoreDto postDto) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
