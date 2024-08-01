package com.example.library_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BorrowBookDTO {
	@NotNull(message = "memberId is required")
	private UUID memberId;
	
	@NotNull(message = "bookId is required")
	private UUID bookId;
}
