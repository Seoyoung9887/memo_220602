package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 실제로 파일 업로드 하는 곳
 * @author user
 *
 */
// 스프링 번
@Component //controller, service, repository
public class FileManagerService {
	public final static String FILE_UPLOAD_PATH = "D:\\binbin\\spring_project\\memo\\workspace\\images/";
	
	
	//input: MultipartFile, userLoginId
	//output: String path

	public String saveFile(String userLoginId , MultipartFile file) {
		//파일명이 겹치지 않게 하기 위해서 userLoginId, 현재시간을 경로에 붙여준다
		//파일 디렉토리 결오 예: marobiana_16456789/sun.png
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		//디렉토리 만들기 
		File directory = new File(filePath);
		if(directory.mkdir()== false) {
		   return null;
		}
		//파일 업로드: byte 단위로 업로드 한다.
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename()); // TODO 파일명을 영어로 만들게
			Files.write(path, bytes); // 파일 업로드
			
			//이미지 업로드 성공시 Path를 리턴한다.(WebMvcConfig에서 매핑한 이미지 path)
			//예 : http://localhost/images/marobiana_16456789/sun.png
			return "/images/"+ directoryName + file.getOriginalFilename(); //파일명은 Path랑 일치하게
		} catch (IOException e) {
				
			e.printStackTrace();
		}
		
		return null;
		
	}

}
