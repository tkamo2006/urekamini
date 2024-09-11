package com.uplus.miniproject2;

import com.uplus.miniproject2.service.ProfileImageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Miniproject2Application {

	public static void main(String[] args) {
		SpringApplication.run(Miniproject2Application.class, args);

//		ProfileImageService profileImageService = context.getBean(ProfileImageService.class);
//
//		// 이미지 파일 경로 (resources/static/image/ 경로에 있는 파일)
//		String imagePath3 = context.getClassLoader().getResource("static/image/3.jpg").getPath();
//		String imagePath2 = context.getClassLoader().getResource("static/image/2.jpg").getPath();
//		String imagePath1 = context.getClassLoader().getResource("static/image/1.jpg").getPath();
//		// 유저 ID 3에 대해 프로필 이미지 업데이트
//		profileImageService.updateProfileImage(3L, imagePath3);
//		profileImageService.updateProfileImage(1L, imagePath1);
//		profileImageService.updateProfileImage(2L, imagePath2);
	}

}
