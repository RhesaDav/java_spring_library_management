package com.example.library_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
	private String access_token;
	private String refresh_token;
}
