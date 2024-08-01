package com.example.library_management.dto;

import com.example.library_management.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private String username;
	private String password;
	private Role role;
}
