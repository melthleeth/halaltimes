package com.web.bigdata.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;
import com.web.bigdata.model.mapper.StoreMapper;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreMapper storeMapper;
	
	@Override
	public List<StoreDto> getList(StoreParameterDto storeParameterDto) throws Exception {
		int start = storeParameterDto.getPg() == 0 ? 0 : (storeParameterDto.getPg() - 1) * storeParameterDto.getSpp();
		storeParameterDto.setStart(start);
		return storeMapper.getList(storeParameterDto);
	}

	@Override
	public StoreDto getLikeStore(int no) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreDto getDetail(String id_store) throws Exception {
		return storeMapper.getDetail(id_store);
	}

	@Override
	public String getLastIdStore(String email) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean write(StoreDto storeDto) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modify(StoreDto storeDto) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int StoreNo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int likeCount(int StoreNo) throws Exception {
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
		return storeMapper.likeInfo(map);
	}

	@Override
	public int insertLike(Map<String, Object> map) throws Exception {
		if (map.get("id_user") == null || "".equals(map.get("id_user").toString())) {
			throw new Exception();
		}
		return storeMapper.insertLike(map);
	}

	@Override
	@Transactional
	public int like(Map<String, Object> map) throws Exception {
		return storeMapper.like(map);
	}

	@Override
	@Transactional
	public int unlike(Map<String, Object> map) throws Exception {
		return storeMapper.unlike(map);
	}

	@Override
	@Transactional
	public int likeCntUp(String id_store) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
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
	public ImgDto getImgInfo(String storeDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteImage(String picNo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<StoreDto> getTempList(StoreParameterDto storeParameterDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTempCount(String email) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean writeTemp(StoreDto storeDto) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertImg(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getIdStore(int index) throws Exception {
		return storeMapper.getIdStore(index);
	}

	@Override
	public int insertImgUrl(Map<String, Object> map) throws Exception {
		return storeMapper.insertImgUrl(map);
	}
}
