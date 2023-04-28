package com.iu.base.security;

import java.io.IOException;
import java.net.URLEncoder;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLoginFailHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {		
		log.error("======================={}==============",exception.getMessage());
		log.error("======================={}==============",exception);
		String errorMessage="";
		if(exception instanceof BadCredentialsException) {
			errorMessage="비번오류";
		}
		else if(exception instanceof InternalAuthenticationServiceException) {
			errorMessage="id오류";
		}
		else if(exception instanceof DisabledException) {
			errorMessage="유효하지 않은 사용자입니다.";
//			enable가 false인 경우
		}
		else if(exception instanceof CredentialsExpiredException) {
			errorMessage="만료된 사용자입니다.";
//			CredentialsNonExpired가 false
		}
		else if(exception instanceof LockedException) {
			errorMessage="잠긴 사용자입니다.";
//			isAccountNonLocked가 false
		}
		else if (exception instanceof AccountExpiredException) {
			errorMessage="유효기간 만료된 사용자입니다.";
		}
		else {
			errorMessage="로그인 실패";
		}
		errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
		response.sendRedirect("/member/login?errorMessage="+errorMessage);
	}

	
}
