package com.example.library_management.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("message", "Unauthorized: " + authException.getMessage());
		responseBody.put("statusCode", HttpServletResponse.SC_UNAUTHORIZED);
		responseBody.put("data", "Authentication error");
		
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getOutputStream().println(new ObjectMapper().writeValueAsString(responseBody));
	}
}
