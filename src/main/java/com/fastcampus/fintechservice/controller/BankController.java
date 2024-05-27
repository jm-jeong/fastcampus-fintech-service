package com.fastcampus.fintechservice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.fintechservice.service.BankRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BankController {
	
	private final BankRepository bankRepository;
	
	 @RequestMapping("/bankApi")
	    public String bank() throws IOException {

	        String result = "";

	        try {
	            String urlStr = "https://finlife.fss.or.kr/finlifeapi/companySearch.json?auth=ae4416b253c2684f9c135710ec349276&topFinGrpNo=020000&pageNo=1";
	            URL url = new URL(urlStr);
	            BufferedReader br;
	            br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

	            result = br.readLine();
	            
//	            //JSON 파싱 객체를 생성
//	            JSONParser jsonParser = new JSONParser();
//	            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
//	            //response를 받아옴
//	            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
//	            //parseResponse에는 response 내부의 데이터들이 들어있음
//
//	            //body를 받아옴
//	            JSONObject parseBody = (JSONObject) parseResponse.get("body");
//	            //parseBody에는 body 내부의 데이터들이 들어있음
//
//	            //Items를 받아옴
//	            JSONObject parseItems = (JSONObject) parseBody.get("items");
//	            //parseItems에는 items 내부의 데이터들이 들어있음
//
//	            //item 안쪽의 데이터는 [] 즉 배열의 형태이기에 제이슨 배열로 받아온다.
//	            JSONArray array = (JSONArray) parseItems.get("item");


	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "index";
	    }
	}
