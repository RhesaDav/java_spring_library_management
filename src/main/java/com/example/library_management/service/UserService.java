package com.example.library_management.service;

import com.example.library_management.entity.User;
import com.example.library_management.exception.UserAlreadyExistException;
import com.example.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(UUID id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public User addUser(User user) {
		User check = userRepository.findByUsername(user.getUsername());
		if (check != null) {
			throw new UserAlreadyExistException("User already exists");
		} else {
			return userRepository.save(user);
		}
	}
	
	@Transactional
	public User updateUser(UUID id, User user) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			user.setId(id);
			return userRepository.save(user);
		} else {
			return null;
		}
	}
	
	@Transactional
	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}
}
