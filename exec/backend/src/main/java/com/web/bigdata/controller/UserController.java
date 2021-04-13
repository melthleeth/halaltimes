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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.UserDto;
import com.web.bigdata.model.service.ETCService;
import com.web.bigdata.model.service.S3FileUploadService;
import com.web.bigdata.model.service.StoreService;
import com.web.bigdata.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	StoreService storeService;

	@Autowired
	ETCService etcService;

	@Autowired
	private S3FileUploadService s3FileUploadService;

	@ApiOperation(value = "회원가입", notes = "입력한 정보를 토대로 DB에 정보를 저장한다.", response = Boolean.class)
	@PostMapping
	public ResponseEntity<Boolean> regist(@RequestBody Map<String, String> map) {
		logger.info("regist - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = true;

		// 회원 정보 담기
		UserDto dto = new UserDto();
		dto.setEmail(map.get("email"));
		dto.setPassword(map.get("password"));
		dto.setNickname(map.get("nickname"));
		dto.setBorn_year(map.get("born_year"));
		dto.setGender(map.get("gender"));

		// 회원가입
		try {
			userService.join(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "이메일 중복 체크", notes = "같은 이메일로 가입한 사용자가 있는지 확인한다.", response = Boolean.class)
	@PostMapping("/emailCheck")
	public ResponseEntity<Boolean> emailCheck(@RequestParam String email) {
		logger.info("emailCheck - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;

		boolean isExisted = userService.emailCheck(email);
		if (!isExisted) {
			try {
				etcService.sendSimpleMessage(email);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ResponseEntity<Boolean>(isExisted, status);
	}

	@ApiOperation(value = "닉네임 중복 체크", notes = "같은 이름으로 가입한 사용자가 있는지 확인한다.", response = Boolean.class)
	@GetMapping("/nameCheck")
	public ResponseEntity<Boolean> nameCheck(@RequestParam String nickname) {
		logger.info("nameCheck - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Boolean>(userService.nameCheck(nickname), status);
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
			int id_user = userService.findUserInfo(email).getId_user();
			resultMap.put("info", userService.findUserInfo(email));
			resultMap.put("reviewList", userService.getReviewList(email));
			List<BookmarkDto> bookmarkList = userService.getBookmarkList(id_user);
			for (BookmarkDto bookmark : bookmarkList) {
				bookmark.setStore_name(storeService.getStoreNameByIdStore(bookmark.getId_store()));
				double score = Double.parseDouble(storeService.getStoreAvgScore(bookmark.getId_store()));
				score = Math.round(score * 100) / 100.0;
				bookmark.setScore(score + "");
				bookmark.setAddress(storeService.getStoreAddress(bookmark.getId_store()));
			}
			System.out.println("bookmarkList : " + bookmarkList);
			resultMap.put("bookmarkList", bookmarkList);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "닉네임 수정", notes = "회원의 닉네임을 수정한다.", response = Boolean.class)
	@PutMapping("/nickname")
	public ResponseEntity<Boolean> modifyNickname(@RequestBody Map<String, String> map) {
		logger.info("modifyNickname - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 정보 조회
		UserDto dto = new UserDto();
		dto.setEmail(map.get("email"));
		dto.setNickname(map.get("nickname"));

		// 회원 소개 수정
		try {
			flag = userService.updateNickname(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "회원 탈퇴", notes = "회원탈퇴를 한다.", response = Boolean.class)
	@PutMapping("/delete")
	public ResponseEntity<Boolean> delete(@RequestParam String email) {
		logger.info("delete - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 탈퇴
		try {
//			String email = map.get("email");
			String fileName = userService.findUserInfo(email).getProfile_image();
			if(fileName != null) {
				s3FileUploadService.delete(fileName);
			}
			flag = userService.delete(email);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "사용자의 프로필 이미지 업로드", notes = "회원의 프로필 이미지를 수정한다.")
	@PostMapping("/profilepic/upload")
	public ResponseEntity<Void> updateUserPicture(@RequestParam String email,
			@RequestParam MultipartFile profile_image) {
		logger.info("updateUserPicture - 호출");

		// 프로필 이미지 업로드
		try {
			UserDto dto = s3FileUploadService.upload(email, profile_image);
			userService.saveImg(dto);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "사용자의 프로필 이미지 삭제", notes = "회원의 프로필 이미지를 삭제한다.")
	@GetMapping("/profilepic/delete")
	public ResponseEntity<Void> deleteProfilePicture(@RequestParam String email) {
		logger.info("deleteProfilePicture - 호출");

		String fileName;

		// 이미지 삭제
		try {
			fileName = userService.findUserInfo(email).getProfile_image();
			s3FileUploadService.delete(fileName);
			userService.deleteImg(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().build();
	}

}