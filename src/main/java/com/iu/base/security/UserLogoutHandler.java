package com.iu.base.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.iu.base.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserLogoutHandler implements LogoutHandler{

	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String restKey;
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String adminKey;
	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String redirectUri;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		this.simpleLogout();
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void simpleLogout() {
		RestTemplate restTemplate = new RestTemplate();

		MemberVO memberVO = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		log.error("회원번호 ::: {} ",memberVO.getAttributes().get("id"));
		//header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","KakaoAK "+ adminKey);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//header이름이 ContentType

		//parameter
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("target_id_type", "user_id");
		params.add("target_id", memberVO.getAttributes().get("id").toString());

		HttpEntity<MultiValueMap<String, String>>req = new HttpEntity<>(params,headers);
		
		String id = restTemplate.postForObject("https://kapi.kakao.com/v1/user/logout", req, String.class);
		log.error("logout Result = {}",id);
	
		
	}
	//	private void logoutAll() {
	//		//카카오계정과 함께 로그아웃
	//		//1. 요청준비
	//		
	//		RestTemplate restTemplate = new RestTemplate();
	//		//2. header
	//		
	//		//3. parameter
	//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	//		params.add("client_id", restKey);
	//		params.add("logout_redirect_uri", redirectUri);
	//		
	//		//4. 2,3을 포함한 요청 객체 생성
	//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null,params);
	//		//5. 요청발생
	////		String response =restTemplate.getForObject("https://kauth.kakao.com/oauth/logout",String.class,params);
	////		ResponseEntity<String> response =restTemplate.getForEntity("https://kauth.kakao.com/oauth/logout?client_id="+restKey+"&logout_redirect_uri=http://localhost/",String.class,params);
	//		
	////		String result= response.getBody();
	////		log.error("Logout Result ::{}",result);
	//		
	//	}




}
