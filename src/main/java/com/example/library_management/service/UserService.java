package com.example.library_management.service;

import com.example.library_management.dto.UserDTO;
import com.example.library_management.entity.User;
import com.example.library_management.exception.DataAlreadyExistException;
import com.example.library_management.exception.DataNotFoundException;
import com.example.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new DataAlreadyExistException("User not found"));
	}
	
	@Transactional
	public User addUser(User newUser) {
		System.out.println(passwordEncoder.encode(newUser.getPassword()));
		if (userRepository.findByUsername(newUser.getUsername()) != null) {
			throw new DataAlreadyExistException("Username already exists");
		}
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
		
	}
	
	@Transactional
	public User updateUser(UUID id, UserDTO userDto) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("User not found"));
		
		if (userDto.getUsername() != null && !userDto.getUsername().isBlank()) {
			existingUser.setUsername(userDto.getUsername());
		}
		if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
			existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		if (userDto.getRole() != null) {
			existingUser.setRole(userDto.getRole());
		}
		
		return userRepository.save(existingUser);
	}
	
	@Transactional
	public String deleteUser(UUID id) {
		userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.deleteById(id);
		return "User deleted : " + id;
	}
}
