package com.web.bigdata.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.bigdata.model.BookmarkDto;
import com.web.bigdata.model.ReviewDto;
import com.web.bigdata.model.StoreDto;
import com.web.bigdata.model.StoreParameterDto;
import com.web.bigdata.model.service.ReviewService;
import com.web.bigdata.model.service.S3FileUploadService;
import com.web.bigdata.model.service.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("StoreController V1")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
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
	private S3FileUploadService s3FileUploadService;
	
	@ApiOperation(value = "식당 목록", notes = "모든 식당의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<StoreDto>> getList(StoreParameterDto storeParameterDto,
			@RequestParam(required = false) String sortBy) throws Exception {
		storeParameterDto.setSortBy(sortBy);
		logger.info("getList - 호출, " + storeParameterDto);
		List<StoreDto> storeList = storeService.getList(storeParameterDto);
		
		for(StoreDto store : storeList) {
//			System.out.println(store.getId_store());
			store.setReviews(reviewService.getReviewCount(store.getId_store()));
		}
		
		return new ResponseEntity<List<StoreDto>>(storeList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 숫자의 순서의 Store", notes = "해당 게시글의 정보 반환한다.", response = List.class)
	@GetMapping("/list/{no}")
	public ResponseEntity<StoreDto> getLikeStore(@PathVariable int no) throws Exception {
		logger.info("getOne - 호출, " + no);
		
		return new ResponseEntity<StoreDto>(storeService.getLikeStore(no), HttpStatus.OK);
	}	


	@ApiOperation(value = "음식점 상세 보기", notes = "음식점 번호에 해당하는 음식점의 정보를 반환한다.", response = StoreDto.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> getStoreDetail(@RequestParam String id_store, @RequestParam String email)
			throws Exception {
		logger.info("getDetail - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> likeCheckMap = new HashMap<>();

		try {
			// 조회수  증가
			storeService.hitsUp(id_store);
			
			// 게시글 정보
			StoreDto storeDto = storeService.getDetail(id_store);
			System.out.println(storeDto);
			resultMap.put("storeInfo", storeDto);
			
			// like(bookmark) 했는지 확인
			likeCheckMap.put("email", email);
			likeCheckMap.put("id_store", id_store);
			BookmarkDto bookmarkDto = storeService.likeInfo(likeCheckMap);

			// 해당 게시글 like 누른적 한 번도 없다면
			if (bookmarkDto == null) {
				resultMap.put("like", 0);
			}
			// like를 누른 적이 있으면
			else {
				resultMap.put("like", bookmarkDto.getActive());
			}
			
			List<ReviewDto> reviewList = reviewService.getStoreReviews(id_store);
			resultMap.put("reviewList", reviewList);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// store_image 테이블을 생성해야 할 듯.
	
//	@ApiOperation(value = "게시글 작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@PostMapping
//	public ResponseEntity<String> write(StoreDto storeDto) {
//		logger.info("write - 호출");
//
//		String result = SUCCESS;
//		HttpStatus status = HttpStatus.OK;
//
//		List<MultipartFile> files = storeDto.getFiles();
//		List<String> unmodified = storeDto.getUnmodified();
//
//		// 게시글 작성 성공 시
//		try {
//			if (storeService.write(storeDto)) {
//				String id_store = storeService.getLastIdStore(storeDto.getId_store());
//
//				// 임시저장했던 게시글이었다면
//				if (storeDto.getId_store() != "-1 임시") {
//					// id_store 바꿔주기
//					id_store = storeDto.getId_store();
//				}
//
//				// 삭제한 파일이 있다면
//				if (unmodified != null && unmodified.size() > 0) {
//					deleteFiles(unmodified);
//				}
//
//				// 추가된 파일이 있다면
//				if (files != null && files.size() > 0) {
//					saveFiles(id_store, files);
//				}
//			}
//			// 작성 실패시
//			else {
//				logger.error("게시글 작성 실패!");
//				result = FAIL;
//				status = HttpStatus.INTERNAL_SERVER_ERROR;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("게시글 작성 실패! 오류 : " + e.getMessage());
//			result = FAIL;
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		return new ResponseEntity<String>(result, status);
//	}
//
//	@ApiOperation(value = "게시글 수정", notes = "새로운 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@PutMapping
//	public ResponseEntity<String> modify(StoreDto store) throws Exception {
//		logger.info("modify - 호출");
//
//		String result = SUCCESS;
//		HttpStatus status = HttpStatus.OK;
//
//		List<MultipartFile> files = store.getFiles();
//		List<String> unmodified = store.getUnmodified();
//		String id_store = store.getId_store();
//
//		if (storeService.modify(store)) {
//			// 삭제한 파일이 있다면
//			if (unmodified != null && unmodified.size() > 0) {
//				deleteFiles(unmodified);
//			}
//
//			// 바뀐 파일이 있다면
//			if (files != null && files.size() > 0) {
//				saveFiles(postNo, files);
//			}
//		}
//		// 수정 실패시
//		else {
//			logger.error("게시글 수정 실패!");
//			result = FAIL;
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		return new ResponseEntity<String>(result, status);
//	}
	
	

//	@ApiOperation(value = "게시글 삭제", notes = "게시글 번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@DeleteMapping
//	public ResponseEntity<String> delete(@RequestParam int postNo) {
//		logger.info("delete - 호출");
//
//		try {
//			// ec2 파일 삭제
//			for (ImgDto imgDto : storeService.getImages(postNo)) {
//				s3FileUploadService.delete(imgDto.getModPicName());
//				s3FileUploadService.delete(imgDto.getThumbnail());
//			}
//
//			// db post 삭제 --cascade--> post images 삭제
//			if (storeService.delete(postNo)) {
//				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}

	
//	@ApiOperation(value = "이미지 저장", notes = "게시글의 이미지를 저장한다.")
//	@PostMapping("/imgs/save")
//	private ResponseEntity<String> saveFiles(String id_store, List<MultipartFile> files) {
//		logger.info("saveFiles 호출, " + files.size());
//
//		// s3 업로드 후 db 저장
//		try {
//			for (MultipartFile file : files) {
//				ImgDto img = s3FileUploadService.uploadImage(file);
//				img.setPostNo(postNo);
//
//				storeService.uploadFile(img);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//	}

//	@ApiOperation(value = "이미지 삭제", notes = "게시글의 이미지를 삭제한다.")
//	@DeleteMapping("/imgs/delete")
//	private ResponseEntity<String> deleteFiles(List<String> unmodified) {
//		logger.info("deleteFiles 호출, " + unmodified.size());
//
//		// ec2에서 파일 지우고 db에서 지우기
//		try {
//			for (String picNo : unmodified) {
//				// 원본
//				s3FileUploadService.delete(storeService.getImgInfo(picNo).getModPicName());
//				// 썸네일
//				s3FileUploadService.delete(storeService.getImgInfo(picNo).getThumbnail());
//
//				storeService.deleteImage(picNo);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//	}

	@ApiOperation(value = "식당 북마크", notes = "store_id에 해당하는 식당의 북마크를 토글한다.", response = HashMap.class)
	@PutMapping("/bookmark")
	public ResponseEntity<Map<String, Object>> like(@RequestParam String id_store, @RequestParam String id_user) {
		logger.info("like - 호출");
		HttpStatus status = HttpStatus.OK;

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<>();

		try {
			map.put("id_store", id_store);
			map.put("id_user", id_user);

			BookmarkDto bookmarkDto = storeService.likeInfo(map);
			System.out.println("bookmarkDto"+bookmarkDto);
			// 해당 게시글에 한번도 북마크 한 적 없다면
			if (bookmarkDto == null) {
				storeService.insertLike(map);
				bookmarkDto = storeService.likeInfo(map);
			}

			int active = 0;
			active = bookmarkDto.getActive(); // like 체크 값

			if (active == 0) {
				storeService.like(map);
				active = 1;
//				storeService.likeCntUp(id_store); // like 갯수 증가
			} else {
				storeService.unlike(map);
				active = 0;
//				storeService.likeCntDown(id_store); // like 갯수 감소
			}

			resultMap.put("id_store", id_store);
			resultMap.put("active", active);
			System.out.println(resultMap);
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "이미지 삽입", notes = "S3에 이미지를 저장 하고 store 테이블에 이미지url 삽입 - 1회용", response = HashMap.class)
	@PutMapping("/insertImg")
	public ResponseEntity<Map<String, Object>> insertImg() throws Exception {
		logger.info("insertImg - 호출");
		HttpStatus status = HttpStatus.OK;
		
		
		Map<String, Object> resultMap = new HashMap<>();

		for(int index=0; index<347; index++) {
			String id_store = storeService.getIdStore(index);
//			System.out.println(id_store);
			String path = "C:\\Users\\yhs\\Desktop\\싸피\\2학기\\특화 플젝\\image";
			path = path + "\\"+index;
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<File> imgs = new ArrayList<>();
			imgs = getImgFileList(path);
//			
			for(File img : imgs) {
				String imgName = img.toString();
//				 확장자를 찾기 위한 코드
				final String ext = imgName.substring(imgName.lastIndexOf('.'));
//				// 파일이름 암호화
				final String saveFileName = getUuid() + ext;
//				s3FileUploadService.uploadOnS3(saveFileName, img);
				final String origName = imgName.substring(path.length()+1,imgName.length());
				map.put("id_store", id_store);
				map.put("origName", origName);
				map.put("saveFileName", "https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/"+saveFileName);
//				System.out.println(map);
//				storeService.insertImgUrl(map);
			}
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 이미지
	private static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
     * 해당 경로의 이미지 파일 목록 반환 
     */
    public static List<File> getImgFileList(String path){        
         
        return getImgFileList(new File(path));
    }    

    /**
     * 해당 경로의 이미지 파일 목록 반환 
     */
    public static List<File> getImgFileList(File file){        
            
        List<File> resultList = new ArrayList<File>(); //이미지 파일을 저장할 리스트 생성
        
         //지정한 이미지폴더가 존재 할지 않을경우 빈 리스트 반환.
        if(!file.exists()) return resultList;
        
        File[] list = file.listFiles(new FileFilter() { //원하는 파일만 가져오기 위해 FileFilter정의
            
            String strImgExt = "jpg|jpeg|png|gif|bmp"; //허용할 이미지타입
            
            @Override
            public boolean accept(File pathname) {                            
                
                //System.out.println(pathname);
                boolean chkResult = false;
                if(pathname.isFile()) {
                    String ext = pathname.getName().substring(pathname.getName().lastIndexOf(".")+1);
                    //System.out.println("확장자:"+ext);
                    chkResult = strImgExt.contains(ext.toLowerCase());        
                    //System.out.println(chkResult +" "+ext.toLowerCase());
                } else {
                    chkResult = true;
                }
                return chkResult;
            }
        });        
        
        for(File f : list) {            
            if(f.isDirectory()) {
                //폴더이면 이미지목록을 가져오는 현재메서드를 재귀호출
                resultList.addAll(getImgFileList(f));                 
            }else {            
                //폴더가 아니고 파일이면 리스트(resultList)에 추가
                resultList.add(f);
            }
        }            
        return resultList; 
    }
    
    //확장자를 제외한 파일 이름 만 출력
    public static String getFileNameNoExt(String filepath){        
        String fileName = filepath.substring(0,  filepath.lastIndexOf("."));
        return fileName;
    }    
    
    //파일 확장자만 출력
    public static String getFileExt(String filepath){
        String ext = filepath.substring(filepath.lastIndexOf(".")+1);
        return ext;
    }
}
