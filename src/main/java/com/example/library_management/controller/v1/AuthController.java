package com.example.library_management.controller.v1;

import com.example.library_management.dto.LoginRequestDTO;
import com.example.library_management.dto.LoginResponseDTO;
import com.example.library_management.dto.UserDTO;
import com.example.library_management.response.ApiResponse;
import com.example.library_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ApiResponse<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO request) {
		return new ApiResponse<LoginResponseDTO>(true, "Login success", HttpStatus.OK.value(), authService.login(request));
	}
	
	@PostMapping("/register")
	public ApiResponse registerUser(@RequestBody UserDTO request) {
		return new ApiResponse(true, "Register success", HttpStatus.OK.value(), authService.register(request));
	}
}
