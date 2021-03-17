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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.PostDto;
import com.web.bigdata.model.PostLikeDto;
import com.web.bigdata.model.PostParameterDto;
import com.web.bigdata.model.PostVoteDto;
import com.web.bigdata.model.service.PostService;
import com.web.bigdata.model.service.S3FileUploadService;
import com.web.bigdata.model.service.VoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("PostController V1")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/post")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private PostService postService;

	@Autowired
	private S3FileUploadService s3FileUploadService;

	@Autowired
	private VoteService voteService;

	@ApiOperation(value = "게시글 목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<PostDto>> getList(PostParameterDto postParameterDto, @RequestParam(required = false) String sortBy) throws Exception {
		postParameterDto.setSortBy(sortBy);
		logger.info("getList - 호출, " + postParameterDto);

		return new ResponseEntity<List<PostDto>>(postService.getList(postParameterDto), HttpStatus.OK);
	}

	@ApiOperation(value = "해당 숫자의 순서의 Post", notes = "해당 게시글의 정보 반환한다.", response = List.class)
	@GetMapping("/list/{no}")
	public ResponseEntity<PostDto> getLikePost(@PathVariable int no) throws Exception {
		logger.info("getOne - 호출, " + no);
		
		return new ResponseEntity<PostDto>(postService.getLikePost(no), HttpStatus.OK);
	}


	@ApiOperation(value = "게시글 보기", notes = "게시글 번호에 해당하는 게시글의 정보를 반환한다.", response = PostDto.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> getPostDetail(@RequestParam int postNo, @RequestParam String email)
			throws Exception {
		logger.info("getOne - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> likeCheckMap = new HashMap<>();

		try {
			// 게시글 정보
			PostDto postDto = postService.getDetail(postNo);
			resultMap.put("postInfo", postDto);

			// like 했는지 확인
			likeCheckMap.put("email", email);
			likeCheckMap.put("postNo", postNo);

			PostLikeDto likeDto = postService.likeInfo(likeCheckMap);

			// 해당 게시글 like 누른적 한 번도 없다면
			if (likeDto == null) {
				resultMap.put("likeCheck", "N");
			}
			// like를 누른 적이 있으면
			else {
				resultMap.put("likeCheck", likeDto.getLikeCheck());
			}

			// post imgs
			List<ImgDto> postImgs = postService.getImages(postNo);
			for (ImgDto img : postImgs) {
				// 이름에 url 붙여주기
				img.setModPicName(s3FileUploadService.getDefaultUrl() + img.getModPicName());
				img.setThumbnail(s3FileUploadService.getDefaultUrl() + img.getThumbnail());
			}
			resultMap.put("voteCount", voteService.getVoteCountofPost(postNo));
			resultMap.put("fileList", postImgs);
			

			// 투표했었는지 찾기
			PostVoteDto voteSearch = new PostVoteDto();
			voteSearch.setEmail(email);
			voteSearch.setPostNo(postNo);
			PostVoteDto voteCheck = voteService.getVoteInfo(voteSearch);
			// 투표한 적 있으면 투표한 사진 번호 체크
			if(voteCheck != null) {
				resultMap.put("votedPicNo", voteCheck.getPicNo());
			}
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "게시글 작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> write(PostDto postDto) {
		logger.info("write - 호출");

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;

		List<MultipartFile> files = postDto.getFiles();
		List<String> unmodified = postDto.getUnmodified();

		// 게시글 작성 성공 시
		try {
			if (postService.write(postDto)) {
				int postNo = postService.getLastPostNo(postDto.getEmail());

				// 임시저장했던 게시글이었다면
				if (postDto.getPostNo() != -1) {
					// postNo 바꿔주기
					postNo = postDto.getPostNo();
				}

				// 삭제한 파일이 있다면
				if (unmodified != null && unmodified.size() > 0) {
					deleteFiles(unmodified);
				}

				// 추가된 파일이 있다면
				if (files != null && files.size() > 0) {
					saveFiles(postNo, files);
				}
			}
			// 작성 실패시
			else {
				logger.error("게시글 작성 실패!");
				result = FAIL;
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("게시글 작성 실패! 오류 : " + e.getMessage());
			result = FAIL;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "게시글 수정", notes = "새로운 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modify(PostDto post) throws Exception {
		logger.info("modify - 호출");

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;

		List<MultipartFile> files = post.getFiles();
		List<String> unmodified = post.getUnmodified();
		int postNo = post.getPostNo();

		if (postService.modify(post)) {
			// 삭제한 파일이 있다면
			if (unmodified != null && unmodified.size() > 0) {
				deleteFiles(unmodified);
			}

			// 바뀐 파일이 있다면
			if (files != null && files.size() > 0) {
				saveFiles(postNo, files);
			}
		}
		// 수정 실패시
		else {
			logger.error("게시글 수정 실패!");
			result = FAIL;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, status);
	}

	@ApiOperation(value = "게시글 삭제", notes = "게시글 번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam int postNo) {
		logger.info("delete - 호출");

		try {
			// ec2 파일 삭제
			for (ImgDto imgDto : postService.getImages(postNo)) {
				s3FileUploadService.delete(imgDto.getModPicName());
				s3FileUploadService.delete(imgDto.getThumbnail());
			}

			// db post 삭제 --cascade--> post images 삭제
			if (postService.delete(postNo)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "이미지 저장", notes = "게시글의 이미지를 저장한다.")
	@PostMapping("/imgs/save")
	private ResponseEntity<String> saveFiles(int postNo, List<MultipartFile> files) {
		logger.info("saveFiles 호출, " + files.size());

		// s3 업로드 후 db 저장
		try {
			for (MultipartFile file : files) {
				ImgDto img = s3FileUploadService.uploadImage(file);
				img.setPostNo(postNo);

				postService.uploadFile(img);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "이미지 삭제", notes = "게시글의 이미지를 삭제한다.")
	@DeleteMapping("/imgs/delete")
	private ResponseEntity<String> deleteFiles(List<String> unmodified) {
		logger.info("deleteFiles 호출, " + unmodified.size());

		// ec2에서 파일 지우고 db에서 지우기
		try {
			for (String picNo : unmodified) {
				// 원본
				s3FileUploadService.delete(postService.getImgInfo(picNo).getModPicName());
				// 썸네일
				s3FileUploadService.delete(postService.getImgInfo(picNo).getThumbnail());

				postService.deleteImage(picNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 like", notes = "게시글 번호에 해당하는 게시글의 like를 토글한다.", response = HashMap.class)
	@PutMapping("/like")
	public ResponseEntity<Map<String, Object>> like(@RequestParam int postNo, @RequestParam String email) {
		logger.info("like - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<>();

		try {
			map.put("postNo", postNo);
			map.put("email", email);

			PostLikeDto likeDto = postService.likeInfo(map);

			// 해당 게시글에 한번도 like 한 적 없다면
			if (likeDto == null) {
				postService.insertLike(map);
				likeDto = postService.likeInfo(map);
			}

			String like_check = "N";
			like_check = likeDto.getLikeCheck(); // like 체크 값

			if (like_check.equals("N")) {
				postService.like(map);
				like_check = "Y";
				postService.likeCntUp(postNo); // like 갯수 증가
			} else {
				postService.unlike(map);
				like_check = "N";
				postService.likeCntDown(postNo); // like 갯수 감소
			}

			resultMap.put("postNo", postNo);
			resultMap.put("likeCheck", like_check);
			resultMap.put("likeCnt", postService.likeCount(postNo));
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "투표", notes = "게시글의 이미지 중 하나를 투표한다.", response = HashMap.class)
	@PostMapping("/vote")
	public ResponseEntity<Map<String, Object>> vote(PostVoteDto voteInfo) {
		logger.info("vote - 호출, " + voteInfo);
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> resultMap = new HashMap<>();

		try {
			// 투표했었는지 찾기
			PostVoteDto voteCheck = voteService.getVoteInfo(voteInfo);

			// 투표 안했었다면
			if (voteCheck == null) {
				voteService.insertVotePost(voteInfo);
			}
			// 투표 했었다면
			else {
				voteInfo.setvNo(voteCheck.getvNo());
				voteService.updateVotePost(voteInfo);
			}

			List<ImgDto> imgList = postService.getImages(voteInfo.getPostNo());
			for (ImgDto img : imgList) {
				// 이름에 url 붙여주기
				img.setModPicName(s3FileUploadService.getDefaultUrl() + img.getModPicName());
				img.setThumbnail(s3FileUploadService.getDefaultUrl() + img.getThumbnail());
			}

			resultMap.put("voteCount", voteService.getVoteCountofPost(voteInfo.getPostNo()));
			resultMap.put("fileList", imgList);

			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
