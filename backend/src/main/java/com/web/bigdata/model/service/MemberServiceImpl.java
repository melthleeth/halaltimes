package com.web.bigdata.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.bigdata.model.MemberDto;
import com.web.bigdata.model.PostDto;
import com.web.bigdata.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MemberMapper memberMapper;

	@Override
	public List<MemberDto> getAllMember() throws Exception {
		return memberMapper.getAllMember();
	}

	@Override
	public void join(MemberDto dto) throws Exception {
		String encodePassword = passwordEncoder.encode(dto.getPwd());
		dto.setPwd(encodePassword);
		memberMapper.join(dto);
	}
	
	@Override
	public String getRole(String email) throws Exception {
		return memberMapper.getRole(email);
	}

	@Override
	public boolean emailCheck(String email) {
		if (memberMapper.emailCheck(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean nameCheck(String name) {
		if (memberMapper.nameCheck(name) != null) {
			return true;
		}
		return false;
	}

	@Override
	public MemberDto findUserInfo(String email) throws Exception {
		return memberMapper.findUserInfo(email);
	}

	@Override
	public boolean updateIntro(MemberDto memberDto) throws Exception {
		if (memberDto == null) {
			return false;
		}

		return memberMapper.updateIntro(memberDto);
	}

	@Override
	public boolean updatePwd(MemberDto memberDto) throws Exception {
		if (memberDto == null) {
			return false;
		}

		String encodedPassword = memberMapper.findPwd(memberDto.getEmail());

		if (passwordEncoder.matches(memberDto.getPrePwd(), encodedPassword)) {
			memberDto.setPwd(passwordEncoder.encode(memberDto.getPwd()));
			return memberMapper.updatePwd(memberDto);
		}

		return false;
	}

	@Override
	public boolean delete(String email) throws Exception {
		return memberMapper.delete(email);
	}

	@Transactional
	public void saveImg(MemberDto dto) throws IOException, SQLException {
		memberMapper.saveImg(dto);
	}

	@Override
	public void deleteImg(String email) {
		memberMapper.deleteImg(email);
	}

	@Override
	public MemberDto login(MemberDto dto) throws SQLException {
		String encodedPassword = memberMapper.findPwd(dto.getEmail());

		if (passwordEncoder.matches(dto.getPwd(), encodedPassword)) {
			dto.setPwd(dto.getPwd());
			MemberDto answer = memberMapper.login(dto);

			return answer;
		} else {
			return null;
		}
	}

	@Override
	public List<PostDto> getPostList(String email) throws Exception {
		return memberMapper.getPostList(email);
	}
}
