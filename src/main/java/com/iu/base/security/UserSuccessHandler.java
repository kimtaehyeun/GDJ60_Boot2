package com.iu.base.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.error("========== Login 성공 후 실행 ============== ");
		log.error("======== {} ====== ", authentication.);
		//foward
		//RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/");
		//view.forward(request, response);
		//redirect
		response.sendRedirect("/");
	}
	
	

}
