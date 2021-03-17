package com.web.bigdata.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.bigdata.model.MemberDto;
import com.web.bigdata.model.PostDto;
import com.web.bigdata.model.service.MemberService;
import com.web.bigdata.model.service.S3FileUploadService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = { "*" })
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberService memberService;

	@Autowired
	private S3FileUploadService s3FileUploadService;

	@ApiOperation(value = "회원가입", notes = "입력한 정보를 토대로 DB에 정보를 저장한다.", response = Boolean.class)
	@PostMapping
	public ResponseEntity<Boolean> regist(@RequestBody Map<String, String> map) {
		logger.info("regist - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = true;

		// 회원 정보 담기
		MemberDto dto = new MemberDto();
		dto.setEmail(map.get("email"));
		dto.setPwd(map.get("pwd"));
		dto.setName(map.get("name"));
		dto.setIntroduce(map.get("introduce"));

		// 회원가입
		try {
			memberService.join(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "이메일 중복 체크", notes = "같은 이메일로 가입한 사용자가 있는지 확인한다.", response = Boolean.class)
	@GetMapping("/emailCheck")
	public ResponseEntity<Boolean> emailCheck(@RequestParam String email) {
		logger.info("emailCheck - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Boolean>(memberService.emailCheck(email), status);
	}

	@ApiOperation(value = "이메일 중복 체크", notes = "같은 이름으로 가입한 사용자가 있는지 확인한다.", response = Boolean.class)
	@GetMapping("/nameCheck")
	public ResponseEntity<Boolean> nameCheck(@RequestParam String name) {
		logger.info("nameCheck - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Boolean>(memberService.nameCheck(name), status);
	}

	@ApiOperation(value = "회원 정보 조회", notes = "회원의 정보를 가지고 온다.", response = HashMap.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> getInfo(@RequestParam String email, HttpServletRequest req)
			throws Exception {
		logger.info("getInfo - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		// 회원 정보 조회
		try {
			resultMap.put("info", memberService.findUserInfo(email));
			resultMap.put("postList", memberService.getPostList(email));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "자기소개 수정", notes = "회원의 자기소개서를 수정한다.", response = Boolean.class)
	@PutMapping("/intro")
	public ResponseEntity<Boolean> modifyIntro(@RequestBody Map<String, String> map) {
		logger.info("modifyIntro - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 정보 조회
		MemberDto dto = new MemberDto();
		dto.setEmail(map.get("email"));
		dto.setIntroduce(map.get("introduce"));

		// 회원 소개 수정
		try {
			flag = memberService.updateIntro(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "회원 비밀번호 변경", notes = "회원의 비밀번호를 수정한다.", response = Boolean.class)
	@PutMapping("/pwd")
	public ResponseEntity<Boolean> modifyPwd(@RequestBody Map<String, String> map) {
		logger.info("modifyPwd - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 정보 조회
		MemberDto dto = new MemberDto();
		dto.setEmail(map.get("email"));
		dto.setPwd(map.get("pwd"));
		dto.setPrePwd(map.get("prePwd"));

		// 회원 비밀번호 변경
		try {
			flag = memberService.updatePwd(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "회원 탈퇴", notes = "회원탈퇴를 한다.", response = Boolean.class)
	@DeleteMapping
	public ResponseEntity<Boolean> delete(@RequestBody Map<String, String> map) {
		logger.info("delete - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 탈퇴
		try {
			String email = map.get("email");
			String fileName = memberService.findUserInfo(email).getProfileImg();
			s3FileUploadService.delete(fileName);
			flag = memberService.delete(email);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "사용자의 프로필 이미지 업로드", notes = "회원의 프로필 이미지를 수정한다.")
	@PostMapping("/upload")
	public ResponseEntity<Void> updateUserPicture(@RequestParam String email, @RequestParam MultipartFile profileImg) {
		logger.info("updateUserPicture - 호출");

		// 프로필 이미지 업로드
		try {
			MemberDto dto = s3FileUploadService.upload(email, profileImg);
			memberService.saveImg(dto);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "사용자의 프로필 이미지 삭제", notes = "회원의 프로필 이미지를 삭제한다.")
	@GetMapping("/delete")
	public ResponseEntity<Void> deleteProfilePicture(@RequestParam String email) {
		logger.info("deleteProfilePicture - 호출");

		String fileName;

		// 이미지 삭제
		try {
			fileName = memberService.findUserInfo(email).getProfileImg();
			s3FileUploadService.delete(fileName);
			memberService.deleteImg(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().build();
	}

}