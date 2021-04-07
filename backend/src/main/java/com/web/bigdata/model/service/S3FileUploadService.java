package com.web.bigdata.model.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.web.bigdata.model.ImgDto;
import com.web.bigdata.model.UserDto;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class S3FileUploadService {

	// 버킷 이름 동적 할당
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// 버킷 주소 동적 할당
	@Value("${cloud.aws.s3.bucket.url}")
	private String defaultUrl;

	private final AmazonS3Client amazonS3Client;
//	private final String IMAGE_DIR = "/home/ubuntu/temp/";
	private final String IMAGE_DIR = "c:\\ssafy\\uploaded\\";

	public S3FileUploadService(AmazonS3Client amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public UserDto upload(String email, MultipartFile uploadFile) throws IOException {
		UserDto member = new UserDto();
		String origName = uploadFile.getOriginalFilename();

		// 확장자를 찾기 위한 코드
		final String ext = origName.substring(origName.lastIndexOf('.'));
		// 파일이름 암호화
		final String saveFileName = getUuid() + ext;

		// 파일 변환
		File file = new File(IMAGE_DIR + saveFileName);
		if (!file.exists()) {
			file.mkdirs();
		}

		// 파일 객체 생성
		// System.getProperty => 시스템 환경에 관한 정보를 얻을 수 있다. (user.dir = 현재 작업 디렉토리를 의미함)
		uploadFile.transferTo(file);

		// S3 파일 업로드
		uploadOnS3(saveFileName, file);

		// 주소 할당
		member.setEmail(email);
		member.setProfile_image("https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/"+saveFileName);

		// 파일 삭제
		file.delete();

		return member;
	}

	public ImgDto uploadImage(MultipartFile uploadFile) throws IOException {
		ImgDto imgDto = new ImgDto();
		String origName = uploadFile.getOriginalFilename();

		// 확장자를 찾기 위한 코드
		final String ext = origName.substring(origName.lastIndexOf('.'));
		// 파일이름 암호화
		final String saveFileName = getUuid() + ext;
//		final String thumbFileName = "t_" + saveFileName;

		// 파일 객체 생성
		// System.getProperty => 시스템 환경에 관한 정보를 얻을 수 있다. (user.dir = 현재 작업 디렉토리를 의미함)
		File file = new File(IMAGE_DIR + saveFileName);
//		File thumb = new File(IMAGE_DIR + thumbFileName);

		if (!file.exists()) {
			file.mkdirs();
		}

		// 변환
		// 썸네일 사이즈 조절
		BufferedImage image = ImageIO.read(uploadFile.getInputStream());

		int THUMB_HEIGHT = image.getHeight();
		int THUMB_WIDTH = image.getWidth();
		final int THUMB_SIZE = 200;

		if (THUMB_HEIGHT >= THUMB_SIZE || THUMB_WIDTH >= THUMB_SIZE) {
			// width가 height보다 크다
			if ((double) THUMB_WIDTH / (double) THUMB_HEIGHT > 1.0) {
				THUMB_WIDTH = (int) ((double) THUMB_WIDTH / (double) THUMB_HEIGHT * THUMB_SIZE);
				THUMB_HEIGHT = THUMB_SIZE;
			}
			// width가 height보다 작다
			else {
				THUMB_HEIGHT = (int) ((double) THUMB_HEIGHT / (double) THUMB_WIDTH * THUMB_SIZE);
				THUMB_WIDTH = THUMB_SIZE;
			}
		}

		// 파일 임시 저장
		uploadFile.transferTo(file);
//		Thumbnails.of(file).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumb);

		// 파일 변환
		// S3 파일 업로드
		uploadOnS3(saveFileName, file);
//		uploadOnS3(thumbFileName, thumb);

		imgDto.setOriginal_image(origName);
		imgDto.setModified_image("https://halaltimesbucket.s3.ap-northeast-2.amazonaws.com/"+saveFileName);
//		imgDto.setThumb_image(thumbFileName);
//		imgDto.setPicsize(uploadFile.getSize());

		// 파일 삭제
		file.delete();
//		thumb.delete();

		return imgDto;
	}

	private static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void delete(String fileName) {
		fileName = fileName.substring(defaultUrl.length());
		try {
			// Delete 객체 생성
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
			// Delete
			this.amazonS3Client.deleteObject(deleteObjectRequest);
			System.out.println(String.format("[%s] deletion complete", fileName));

		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	public void uploadOnS3(final String findName, final File file) {
		// AWS S3 전송 객체 생성
		final TransferManager transferManager = new TransferManager(this.amazonS3Client);
		// 요청 객체 생성
		final PutObjectRequest request = new PutObjectRequest(bucket, findName, file);
		// 업로드 시도
		final Upload upload = transferManager.upload(request);

		try {
			upload.waitForCompletion();
		} catch (AmazonClientException amazonClientException) {
			amazonClientException.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}