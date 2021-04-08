package com.web.bigdata.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.ReviewLikeDto;
import com.web.bigdata.model.ReviewParameterDto;
import com.web.bigdata.model.service.ReviewService;
import com.web.bigdata.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("PostController V1")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/review")
public class ReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "리뷰 목록", notes = "모든 리뷰의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<ReviewDto>> getList(ReviewParameterDto reviewParameterDto,
			@RequestParam(required = false) String sortBy) throws Exception {
		reviewParameterDto.setSortBy(sortBy);
		logger.info("getList - 호출, " + reviewParameterDto);
		List<ReviewDto> reviewList = reviewService.getList(reviewParameterDto);
		for (ReviewDto x : reviewList) {
			String store_name = reviewService.getStoreName(x.getId_store());
			x.setStore_name(store_name);
			
			System.out.println(userService.getImage(x.getId_user()));
			x.setThumbnail(userService.getImage(x.getId_user()));
			System.out.println(x);
		}
		return new ResponseEntity<List<ReviewDto>>(reviewList, HttpStatus.OK);
	}

	@ApiOperation(value = "리뷰 상세 보기", notes = "리뷰 번호에 해당하는 리뷰의 정보를 반환한다.", response = ReviewDto.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> getReviewDetail(@RequestParam int id_review,
			@RequestParam(required = false) String email) throws Exception {
		logger.info("getOne - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> likeCheckMap = new HashMap<>();

		try {
			// 리뷰 정보
			ReviewDto reviewDto = reviewService.getDetail(id_review);
			resultMap.put("reviewInfo", reviewDto);

			// like 했는지 확인
			likeCheckMap.put("email", email);
			likeCheckMap.put("id_review", id_review);

			ReviewLikeDto likeDto = reviewService.likeInfo(likeCheckMap);

			// 해당 리뷰 like 누른적 한 번도 없다면
			if (likeDto == null) {
				resultMap.put("likeCheck", 0);
			}
			// like를 누른 적이 있으면
			else {
				resultMap.put("likeCheck", likeDto.getLiked());
			}

			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "리뷰 작성", notes = "새로운 리뷰 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> write(ReviewDto reviewDto) {
		logger.info("write - 호출");

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;

		// 리뷰 작성 성공 시
		try {
			int id_user = userService.getIdUser(reviewDto.getEmail());
			reviewDto.setId_user(id_user);
			if (reviewService.write(reviewDto)) {
				String id_userStr = Integer.toString(reviewDto.getId_user());
				String ID_REVIEW = reviewService.getLastReview(id_userStr);
				int id_review = ID_REVIEW == null ? 0 : Integer.parseInt(ID_REVIEW);
				reviewDto.setId_review(id_review);
			}
			// 작성 실패시
			else {
				logger.error("리뷰 작성 실패!");
				result = FAIL;
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("리뷰 작성 실패! 오류 : " + e.getMessage());
			result = FAIL;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "리뷰 수정", notes = "새로운 리뷰 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> modify(@RequestBody ReviewDto reviewDto) throws Exception {
		logger.info("modify - 호출");

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;

		if (reviewService.modify(reviewDto)) {
			logger.info("modify 성공");
		}
		// 수정 실패시
		else {
			logger.error("리뷰 수정 실패!");
			result = FAIL;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, status);

	}

	@ApiOperation(value = "리뷰 삭제", notes = "리뷰 번호에 해당하는 리뷰의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam int id_review) {
		logger.info("delete - 호출");

		try {
			// db review 삭제
			if (reviewService.delete(id_review)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "리뷰 like", notes = "리뷰 번호에 해당하는 리뷰의 like를 토글한다.", response = HashMap.class)
	@PutMapping("/like")
	public ResponseEntity<Map<String, Object>> like(@RequestParam int id_review, @RequestParam String email) {
		logger.info("like - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<>();

		try {
			int id_user = userService.getIdUser(email);
			map.put("id_review", id_review);
			map.put("id_user", id_user);
			ReviewLikeDto likeDto = reviewService.likeInfo(map);
			// 해당 리뷰에 한번도 like 한 적 없다면
			if (likeDto == null) {
				reviewService.insertLike(map);
				likeDto = reviewService.likeInfo(map);
			}

			int like_check = 0;
			like_check = likeDto.getLiked(); // like 체크 값
			if (like_check == 0) {
				reviewService.like(map);
				like_check = 1;
				reviewService.likeCntUp(id_review); // like 갯수 증가
			} else {
				reviewService.unlike(map);
				like_check = 0;
				reviewService.likeCntDown(id_review); // like 갯수 감소
			}

			resultMap.put("id_review", id_review);
			resultMap.put("likeCheck", like_check);
			resultMap.put("likeCnt", reviewService.likeCount(id_review));
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
