package com.lgt.kakao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoAPI {

	private final String AUTHORIZATION = "Authorization";
	private final String BEARER_PREFIX = "Bearer ";
	
	private final String logoutURL = "https://kapi.kakao.com/v1/user/unlink";
	
	public void logout(String accessToken) {
		try {
			URL url = new URL(logoutURL);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty(AUTHORIZATION, BEARER_PREFIX + accessToken);
			
			conn.disconnect();
			/*
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			String json = "";
			while ((output = br.readLine()) != null) {
				json += output;
			}
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(json);
			log.info(root.toPrettyString());
			*/
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
