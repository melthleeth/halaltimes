package com.web.bigdata.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.UserDto;
import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserMapper userMapper;

	@Override
	public List<UserDto> getAllUser() throws Exception {
		return userMapper.getAllUser();
	}

	@Override
	public void join(UserDto dto) throws Exception {
		String encodePassword = passwordEncoder.encode(dto.getPassword());
		dto.setPassword(encodePassword);
		userMapper.join(dto);
	}

	@Override
	public String getRole(String email) throws Exception {
		return userMapper.getRole(email);
	}

	@Override
	public boolean emailCheck(String email) {
		if (userMapper.emailCheck(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean nameCheck(String name) {
		if (userMapper.nameCheck(name) != null) {
			return true;
		}
		return false;
	}

	@Override
	public UserDto findUserInfo(String email) throws Exception {
		return userMapper.findUserInfo(email);
	}

	@Override
	public boolean updateNickname(UserDto userDto) throws Exception {
		if (userDto == null) {
			return false;
		}

		return userMapper.updateNickname(userDto);
	}

	@Override
	public boolean updatePwd(UserDto userDto) throws Exception {
		if (userDto == null) {
			return false;
		}

		String encodedPassword = userMapper.findPwd(userDto.getEmail());

		if (passwordEncoder.matches(userDto.getPrePwd(), encodedPassword)) {
			userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
			return userMapper.updatePwd(userDto);
		}

		return false;
	}

//	@Override
//	public List<String> getStoreName(String id_review) throws Exception {
//		System.out.println("service "+id_review);
//		return userMapper.getStoreName(id_review);
//	}

	@Override
	public boolean delete(String email) throws Exception {
		return userMapper.delete(email);
	}

	@Transactional
	public void saveImg(UserDto dto) throws IOException, SQLException {
		userMapper.saveImg(dto);
	}

	@Override
	public void deleteImg(String email) {
		userMapper.deleteImg(email);
	}

	@Override
	public UserDto login(UserDto dto) throws SQLException {
		String encodedPassword = userMapper.findPwd(dto.getEmail());

		if (passwordEncoder.matches(dto.getPassword(), encodedPassword)) {
			dto.setPassword(dto.getPassword());
			UserDto answer = userMapper.login(dto);

			return answer;
		} else {
			return null;
		}
	}

	@Override
	public List<ReviewDto> getReviewList(String email) throws SQLException {
		return userMapper.getReviewList(email);
	}

	@Override
	public List<BookmarkDto> getBookmarkList(String id_user) throws SQLException {
		return userMapper.getBookmarkList(id_user);
	}


	@Override
	public String getIdUser(String email) throws Exception {
		return userMapper.getIdUser(email);
	}
}
