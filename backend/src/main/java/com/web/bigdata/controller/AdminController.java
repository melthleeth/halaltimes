package com.web.bigdata.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.bigdata.model.service.UserService;
import com.web.bigdata.model.service.S3FileUploadService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = { "*" })
public class AdminController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	UserService userService;

	@Autowired
	private S3FileUploadService s3FileUploadService;

	@ApiOperation(value = "회원 목록 조회", notes = "회원들의 정보(이메일, 이름, 가입일, 권한, 프로필, 게시글 수, 댓글 수)을 반환한다.", response = HashMap.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> getMembers(@RequestParam String email) throws Exception {
		logger.info("getMembers - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		// 회원 정보 조회
		try {
			if (!"admin".equals(userService.getRole(email))) {
				resultMap.put("message", "Not a Admin Account");
				status = HttpStatus.METHOD_NOT_ALLOWED;
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			resultMap.put("members", userService.getAllUser());
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원 탈퇴 처리", notes = "회원들을 탈퇴 처리하여 성공 여부에 따라 true, false를 반환한다.", response = Boolean.class)
	@DeleteMapping
	public ResponseEntity<Boolean> deleteMembers(@RequestBody String[] targets) {
		logger.info("deleteMembers - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 탈퇴
		try {
			for (String email : targets) {
				String fileName = userService.findUserInfo(email).getProfile_image();
				s3FileUploadService.delete(fileName);
				flag = userService.delete(email);
				if (!flag) {
					return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
				}
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

}