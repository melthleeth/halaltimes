package com.web.bigdata.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;
import com.web.bigdata.model.UserClusteredDto;
import com.web.bigdata.model.service.ReviewService;
import com.web.bigdata.model.service.StoreService;
import com.web.bigdata.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("StoreController V1")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/store")
public class StoreController {
	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private StoreService storeService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "식당 목록", notes = "모든 식당의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<StoreDto>> getList(StoreParameterDto storeParameterDto,
			@RequestParam(required = false) String sortBy) throws Exception {
		storeParameterDto.setSortBy(sortBy);
		logger.info("getList - 호출, " + storeParameterDto);
		List<StoreDto> storeList = storeService.getList(storeParameterDto);
		for (StoreDto store : storeList) {
			store.setReviews(reviewService.getReviewCount(store.getId_store()));
			String avgScoreStr = storeService.getStoreAvgScore(store.getId_store());
			double avgScore = avgScoreStr == null ? 0 : Double.parseDouble(avgScoreStr);
			store.setAverageScore(avgScore);
		}

		return new ResponseEntity<List<StoreDto>>(storeList, HttpStatus.OK);
	}

	@ApiOperation(value = "추천 식당 목록", notes = "추천 받은 식당의 정보를 반환한다.", response = List.class)
	@GetMapping("/list/recomm")
	public ResponseEntity<List<StoreDto>> getRecommList(String email) throws Exception {
		logger.info("getRecommList - 호출, " + email);
		int id_user = userService.findUserInfo(email).getId_user();
		List<UserClusteredDto> recomStore = storeService.getClusteredStores(id_user);
		List<StoreDto> recomStoreList = new LinkedList<StoreDto>();

		for (UserClusteredDto storeDto : recomStore) {
			recomStoreList.add(storeService.getRecommList(storeDto.getId_store()));
		}

		for (StoreDto store : recomStoreList) {
			store.setReviews(reviewService.getReviewCount(store.getId_store()));
			String avgScoreStr = storeService.getStoreAvgScore(store.getId_store());
			double avgScore = avgScoreStr == null ? 0 : Double.parseDouble(avgScoreStr);
			store.setAverageScore(avgScore);
		}

		return new ResponseEntity<List<StoreDto>>(recomStoreList, HttpStatus.OK);
	}

	@ApiOperation(value = "음식점 상세 보기", notes = "음식점 번호에 해당하는 음식점의 정보를 반환한다.", response = StoreDto.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> getStoreDetail(@RequestParam int id_store,
			@RequestParam(required = false) String email) throws Exception {
		logger.info("getDetail - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> likeCheckMap = new HashMap<>();

		try {
			// 조회수 증가
			storeService.hitsUp(id_store);

			// 식당 정보
			StoreDto storeDto = storeService.getDetail(id_store);
			String scoreStr = storeService.getStoreAvgScore(id_store);
			double score = scoreStr == null ? 0 : Double.parseDouble(scoreStr);
			score = Math.round(score * 100) / 100.0;
			storeDto.setAverageScore(score);
			resultMap.put("storeInfo : ", storeDto);

			// like(bookmark) 했는지 확인
			Integer id_user_temp = userService.getIdUser(email);
			int id_user = id_user_temp == null ? 0 : id_user_temp;
			likeCheckMap.put("id_user", id_user);
			likeCheckMap.put("id_store", id_store);
			BookmarkDto bookmarkDto = storeService.likeInfo(likeCheckMap);

			// 해당 식당 like 누른적 한 번도 없다면
			if (bookmarkDto == null) {
				resultMap.put("like", 0);
			}

			// like를 누른 적이 있으면
			else {
				resultMap.put("like", bookmarkDto.getActive());
			}

			List<ReviewDto> reviewList = reviewService.getStoreReviews(id_store);
			for (ReviewDto review : reviewList) {
				Map<String, Object> map = new HashMap<>();
				map.put("id_review", review.getId_review());
				map.put("id_user", id_user);
				
				String check = reviewService.likeCheck(map);
				int likeCheck = check == null ? 0 : Integer.parseInt(check);
				review.setLikeCheck(likeCheck);
				review.setNickname(userService.getNickName(review.getId_user()));
				String id_userStr = userService.getIdUserIdReview(review.getId_review());
				int id_user2 = id_userStr == null ? 0 : Integer.parseInt(id_userStr);
				
				review.setThumbnail(userService.getImage(id_user2));
			}
			resultMap.put("reviewList", reviewList);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "식당 북마크", notes = "store_id에 해당하는 식당의 북마크를 토글한다.", response = HashMap.class)
	@PutMapping("/bookmark")
	public ResponseEntity<Map<String, Object>> like(@RequestParam int id_store, @RequestParam String email) {
		logger.info("like - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int id_user = userService.getIdUser(email);
			map.put("id_store", id_store);
			map.put("id_user", id_user);

			BookmarkDto bookmarkDto = storeService.likeInfo(map);
			// 해당 식당에 한번도 북마크 한 적 없다면
			if (bookmarkDto == null) {
				storeService.insertLike(map);
				bookmarkDto = storeService.likeInfo(map);
			}

			int active = 0;
			active = bookmarkDto.getActive(); // like 체크 값

			if (active == 0) {
				storeService.like(map);
				active = 1;
			} else {
				storeService.unlike(map);
				active = 0;
			}

			resultMap.put("id_store", id_store);
			resultMap.put("active", active);
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "북마크 정보 조회", notes = "회원의 북마크 정보를 가지고 온다.", response = HashMap.class)
	@GetMapping("/bookmark/all")
	public ResponseEntity<List<BookmarkDto>> getBookmarks(@RequestParam String email, HttpServletRequest req)
			throws Exception {
		logger.info("getBookmarks - 호출");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		List<BookmarkDto> bookmarkList = new ArrayList<>();
		// 회원 정보 조회
		try {
			int id_user = userService.findUserInfo(email).getId_user();
			bookmarkList = userService.getBookmarkList(id_user);
			for (BookmarkDto bookmark : bookmarkList) {
				bookmark.setStore_name(storeService.getStoreNameByIdStore(bookmark.getId_store()));
				double score = Double.parseDouble(storeService.getStoreAvgScore(bookmark.getId_store()));
				score = Math.round(score * 100) / 100.0;
				bookmark.setScore(score + "");
				bookmark.setAddress(storeService.getStoreAddress(bookmark.getId_store()));
			}
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<List<BookmarkDto>>(bookmarkList, status);
	}

}
