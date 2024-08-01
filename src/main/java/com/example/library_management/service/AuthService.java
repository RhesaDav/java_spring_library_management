package com.example.library_management.service;

import com.example.library_management.dto.LoginRequestDTO;
import com.example.library_management.dto.LoginResponseDTO;
import com.example.library_management.dto.UserDTO;
import com.example.library_management.entity.Member;
import com.example.library_management.entity.User;
import com.example.library_management.exception.DataAlreadyExistException;
import com.example.library_management.exception.DataNotFoundException;
import com.example.library_management.repository.MemberRepository;
import com.example.library_management.repository.UserRepository;
import com.example.library_management.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MemberRepository memberRepository;
	
	public User register(UserDTO user) {
		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new DataAlreadyExistException("User already exist");
		}
		
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		
		User savedUser = userRepository.save(newUser);
		
		Member newMember = new Member();
		newMember.setUser(newUser);
		memberRepository.save(newMember);
		
		return savedUser;
	}
	
	public LoginResponseDTO login(LoginRequestDTO request) {
		User user = userRepository.findByUsername(request.getUsername());
		if (user == null) {
			throw new DataAlreadyExistException("User not found");
		}
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new DataNotFoundException("Wrong password");
		}
		
		String accessToken = jwtUtil.generateAccessToken(user);
		String refreshToken = jwtUtil.generateRefreshToken(user);
		
		LoginResponseDTO response = new LoginResponseDTO();
		response.setAccess_token(accessToken);
		response.setRefresh_token(refreshToken);
		return response;
	}
}
