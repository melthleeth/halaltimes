package com.web.bigdata.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.bigdata.model.UserDto;
import com.web.bigdata.model.service.ETCService;
import com.web.bigdata.model.service.JwtService;
import com.web.bigdata.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = { "*" })
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService memberService;

	@Autowired
	private ETCService etcService;

	@ApiOperation(value = "로그인", notes = "DB에서 정보를 조회하여 로그인 정보와 일치하면 로그인한다.", response = HashMap.class)
	@PostMapping("/confirm/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password,
			HttpServletResponse response, HttpSession session) {
		logger.info("login - 호출");

		System.out.println(email + " " + password);
		HttpStatus status = null;
		Map<String, Object> resultMap = new HashMap<>();

		UserDto dto = new UserDto();
		dto.setEmail(email);
		dto.setPassword(password);
		// 로그인
		try {
			UserDto loginUser = memberService.login(dto);

			if (loginUser != null) {
				// jwt.io에서 확인
				// 로그인 성공했다면 토큰을 생성한다
				String token = jwtService.create(loginUser);
				logger.trace("로그인 토큰정보 : {}", token);

				// 토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다
				resultMap.put("auth-token", token);
				resultMap.put("user-email", loginUser.getEmail());
				resultMap.put("user-name", loginUser.getNickname());
				resultMap.put("role", loginUser.getRole());
				resultMap.put("profile_image", loginUser.getProfile_image());
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "로그인 실패");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "비밀번호 찾기", notes = "DB에서 정보를 조회하여 비밀번호를 찾을 수 있도록 한다.", response = HashMap.class)
	@GetMapping("/findpwd")
	public ResponseEntity<Map<String, Object>> findPwd(@RequestParam String email) {
		logger.info("findPwd - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		// 비밀번호 조회
		try {
			etcService.findPwd(email);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(status);
	}

}
