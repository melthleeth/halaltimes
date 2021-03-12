package com.web.bigdata.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.PostDto;
import com.web.bigdata.model.PostParameterDto;
import com.web.bigdata.model.service.PostService;
import com.web.bigdata.model.service.S3FileUploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("PostController V1")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/temp")
public class PostTempController {

	private static final Logger logger = LoggerFactory.getLogger(PostTempController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private PostService postService;

	@Autowired
	private S3FileUploadService s3FileUploadService;

	@ApiOperation(value = "게시글 목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<PostDto>> getList(PostParameterDto postParameterDto) throws Exception {
		logger.info("getList - 호출");

		return new ResponseEntity<List<PostDto>>(postService.getTempList(postParameterDto), HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 임시저장", notes = "작성하던 게시글을 임시 저장한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> write(PostDto postDto) throws Exception {
		logger.info("write - 호출, " + postDto);

		String result = SUCCESS;
		HttpStatus status = HttpStatus.OK;

		String email = postDto.getEmail();
		List<MultipartFile> files = postDto.getFiles();

		if (postService.getTempCount(email) > 9) {
			return new ResponseEntity<String>("임시저장한 게시글 수가 10개가 넘습니다!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// 게시글 작성 성공 시
		if (postService.writeTemp(postDto)) {
			int postNo = postService.getLastPostNo(email);

			if (files != null && files.size() > 0) {
				for (MultipartFile file : files) {
					// s3 업로드 후 db 저장
					ImgDto img = s3FileUploadService.uploadImage(file);
					img.setPostNo(postNo);

					postService.uploadFile(img);
				}
			}
		}
		// 작성 실패시
		else {
			result = FAIL;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(result, status);
	}

}
