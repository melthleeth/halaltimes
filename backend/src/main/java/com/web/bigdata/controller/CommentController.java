package com.web.bigdata.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.bigdata.model.CommentDto;
import com.web.bigdata.model.CommentLikeDto;
import com.web.bigdata.model.service.CommentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = { "*" })

public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	CommentService commentService;

	@ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.", response = Boolean.class)
	@PostMapping
	public ResponseEntity<Boolean> commentWrite(@RequestBody Map<String, String> map) {
		logger.info("commentWrite - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = true;

		// 댓글 담기
		CommentDto dto = new CommentDto();
		dto.setContent(map.get("content"));
		dto.setEmail(map.get("email"));
		dto.setPostNo(Integer.parseInt(map.get("postNo")));

		// 댓글 작성
		try {
			commentService.commentWrite(dto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modify(@RequestBody CommentDto commentDto) throws Exception {
		logger.info("modify - 호출");

		// 해당 댓글 수정
		if (commentService.commentUpdate(commentDto)) {
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}

		return new ResponseEntity<String>("FAIL", HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.", response = Boolean.class)
	@DeleteMapping
	public ResponseEntity<Boolean> delete(@RequestBody Map<String, String> map) {
		logger.info("delete - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 해당 댓글 삭제
		try {
			flag = commentService.commentDelete(Integer.parseInt(map.get("commentNo")));
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "댓글 like", notes = "댓글번호에 해당하는 댓글의 좋아요를 토글한다. 그리고 DB 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/like")
	public ResponseEntity<Map<String, Object>> like(@RequestParam int commentNo, @RequestParam String email) {
		logger.info("like - 호출");

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			map.put("commentNo", commentNo);
			map.put("email", email);

			// 해당 댓글 like 여부
			CommentLikeDto likeDto = commentService.likeInfo(map);

			// 해당 댓글에 한번도 like 한 적 없다면
			if (likeDto == null) {
				commentService.insertLike(map);
				likeDto = commentService.likeInfo(map);
			}

			String like_check = "N";
			like_check = likeDto.getLikeCheck(); // like 체크 값

			if (like_check.equals("N")) {
				commentService.like(map);
				like_check = "Y";
				commentService.likeCntUp(commentNo); // like 갯수 증가
			} else {
				commentService.unlike(map);
				like_check = "N";
				commentService.likeCntDown(commentNo); // like 갯수 감소
			}

			resultMap.put("commentNo", commentNo);
			resultMap.put("likeCheck", like_check);
			resultMap.put("likeCnt", commentService.likeCount(commentNo));
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "댓글 목록", notes = "댓글의 목록을 가지고 온다.", response = List.class)
	@GetMapping("/{postNo}")
	public ResponseEntity<List<Map<String, Object>>> commentList(@PathVariable int postNo, @RequestParam String email,  @RequestParam(required = false) String sortBy
			) throws Exception {
		logger.info("commentList - 호출");

		HttpStatus status = HttpStatus.OK;
		List<Map<String, Object>> commentDtoList = new ArrayList<Map<String, Object>>();

		// 댓글 목록 조회
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postNo", postNo);
		map.put("sortBy", sortBy);
		
		for (CommentDto comment : commentService.commentList(map)) {
			System.out.println(comment);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> likecheck = new HashMap<String, Object>();
			resultMap.put("Comment", comment);
			likecheck.put("email", email);
			likecheck.put("commentNo", comment.getCommentNo());

			// 댓글 like 여부
			CommentLikeDto likeDto = commentService.likeInfo(likecheck);

			// 해당 게시글 like 누른적 한 번도 없다면
			if (likeDto == null) {
				resultMap.put("likeCheck", "N");
			}
			// 좋아요를 누른 적이 있으면
			else {
				resultMap.put("likeCheck", likeDto.getLikeCheck());
			}
			commentDtoList.add(resultMap);
		}
		return new ResponseEntity<List<Map<String, Object>>>(commentDtoList, status);
	}

	@ApiOperation(value = "댓글 무한 스크롤", notes = "댓글 창을 무한으로 스크롤 할 수 있도록 한다.", response = List.class)
	@PostMapping("/{postNo}")
	public ResponseEntity<List<Map<String, Object>>> infiniteScrollDownComment(@PathVariable int postNo,
			@RequestParam String email, @RequestParam int pg, @RequestParam String sortBy) throws Exception {
		logger.info("infiniteScrollDownComment - 호출");

		HttpStatus status = HttpStatus.OK;
		pg *= 10;
		List<Map<String, Object>> commentDtoList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pg", pg);
		map.put("sortBy", sortBy);
		map.put("postNo", postNo);
		
		
		
		// 댓글 목록 조회
		for (CommentDto comment : commentService.infiniteScrollDown(map)) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> likecheck = new HashMap<String, Object>();
			resultMap.put("Comment", comment);
			likecheck.put("email", email);
			likecheck.put("commentNo", comment.getCommentNo());

			// 댓글 like 여부
			CommentLikeDto likeDto = commentService.likeInfo(likecheck);

			// 해당 댓글 like 누른적 한 번도 없다면
			if (likeDto == null) {
				resultMap.put("likeCheck", "N");
			}
			// like를 누른 적이 있으면
			else {
				resultMap.put("likeCheck", likeDto.getLikeCheck());
			}
			commentDtoList.add(resultMap);

		}
		return new ResponseEntity<List<Map<String, Object>>>(commentDtoList, status);
	}

}
