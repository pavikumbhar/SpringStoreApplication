package com.pavikumbhar.javaheart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author pavikumbhar
 *
 */
@Slf4j
@Component
public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("In preHandle");
		String loginUrl = request.getContextPath() + "/login";
		if (request.getSession().getAttribute("loggedInUser") == null) {
			response.sendRedirect(loginUrl);
			return false;
		}
		return true;
	}

}
