package com.example.library_management.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("message", "Forbidden: " + accessDeniedException.getMessage());
		responseBody.put("statusCode", HttpServletResponse.SC_FORBIDDEN);
		responseBody.put("data", "Access denied error");
		
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getOutputStream().println(new ObjectMapper().writeValueAsString(responseBody));
	}
}
