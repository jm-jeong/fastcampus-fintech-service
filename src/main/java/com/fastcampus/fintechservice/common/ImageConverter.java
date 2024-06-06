package com.fastcampus.fintechservice.common;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ImageConverter {
	public static String encodeBase64(String imageName) throws IOException {
		// 은행 이미지 base64로 인코딩
		String imageBase64 = "";
		String imagePath = "static/bankimages/" + imageName + ".png";
		Resource imgFile = new ClassPathResource(imagePath);

		if (!imgFile.exists()) {
			imageBase64 = "Not Found";
		}
		byte[] bytes = Files.readAllBytes(imgFile.getFile().toPath());
		imageBase64 = Base64.getEncoder().encodeToString(bytes);

		return imageBase64;
	}
}
