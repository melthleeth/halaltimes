package com.web.bigdata.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;
import com.web.bigdata.model.UserClusteredDto;
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
	public StoreDto getRecommList(int id_store) throws Exception {
		return storeMapper.getRecommList(id_store);
	}

	@Override
	public List<UserClusteredDto> getClusteredStores(int id_user) throws Exception {
		int label = storeMapper.getLabel(id_user);
		return storeMapper.getClusteredStores(label);
	}

	@Override
	public StoreDto getDetail(int id_store) throws Exception {
		return storeMapper.getDetail(id_store);
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
	public int hitsUp(int id_store) throws Exception {
		return storeMapper.hitsUp(id_store);
	}

	@Override
	public String getStoreNameByIdStore(int id_store) throws Exception {
		return storeMapper.getStoreNameByIdStore(id_store);
	}

	@Override
	public String getStoreAvgScore(int id_store) throws Exception {
		return storeMapper.getStoreAvgScore(id_store);
	}

	@Override
	public String getStoreAddress(int id_store) throws Exception {
		return storeMapper.getStoreAddress(id_store);
	}

}
