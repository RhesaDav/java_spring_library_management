package com.example.library_management.controller.v1;

import com.example.library_management.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProtectedController {
	@GetMapping("/admin")
	public ApiResponse<String> adminDashboard() {
		return new ApiResponse(true, "Admin", HttpStatus.OK.value(), null);
	}
	
	@GetMapping("/user")
	public ApiResponse userProfile() {
		try {
			return new ApiResponse(true, "User", HttpStatus.OK.value(), null);
		} catch (Exception e) {
			return new ApiResponse(true, "Admin", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
}