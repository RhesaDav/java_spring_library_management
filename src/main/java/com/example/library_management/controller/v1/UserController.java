package com.example.library_management.controller.v1;

import com.example.library_management.dto.ApiResponse;
import com.example.library_management.dto.UpdateUserDTO;
import com.example.library_management.entity.User;
import com.example.library_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ApiResponse<List<User>> getAll() {
		return new ApiResponse<>(true, HttpStatus.OK.name(), HttpStatus.OK.value(), userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ApiResponse<User> getUser(@PathVariable UUID id) {
		return new ApiResponse<>(true, HttpStatus.OK.name(), HttpStatus.OK.value(), userService.getUserById(id));
	}
	
	@PostMapping
	public ApiResponse<User> createUser(@RequestBody User user) {
		return new ApiResponse<>(true, "User Created", HttpStatus.CREATED.value(), userService.addUser(user));
	}
	
	@PatchMapping("/{id}")
	public ApiResponse<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDTO user) {
		return new ApiResponse<>(true, "User Updated", HttpStatus.OK.value(), userService.updateUser(id, user));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<String> deleteUser(@PathVariable UUID id) {
		return new ApiResponse<>(true, "User Deleted", HttpStatus.OK.value(), userService.deleteUser(id));
	}
}
