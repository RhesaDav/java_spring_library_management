package com.example.library_management.controller.v2;

import com.example.library_management.dto.BorrowBookDTO;
import com.example.library_management.response.ApiResponse;
import com.example.library_management.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/borrowed-books")
public class BorrowedBookController {
	@Autowired
	private BorrowedBookService borrowedBookService;
	
	@GetMapping
	public ApiResponse getAllBorrowedBooks() {
		return new ApiResponse(true, "All Borrowed Books Retrieved", HttpStatus.OK.value(), borrowedBookService.getAllBorrowedBooks());
	}
	
	@GetMapping("/member/{memberId}")
	public ApiResponse getAllBorrowedBooksByMemberId(@PathVariable UUID memberId) {
		return new ApiResponse(true, "Borrowed book memberId : " + memberId, HttpStatus.OK.value(), borrowedBookService.getBorrowedBooksByMember(memberId));
	}
	
	@PostMapping("/borrow")
	public ApiResponse addBorrowedBook(@RequestBody BorrowBookDTO borrowBook) {
		return new ApiResponse(true, "Borrowed book added", HttpStatus.OK.value(), borrowedBookService.borrowBook(borrowBook));
	}
	
	@PostMapping("/return")
	public ApiResponse returnBook(@RequestBody BorrowBookDTO borrowBook) {
		return new ApiResponse(true, "Book Returned", HttpStatus.OK.value(), borrowedBookService.returnBook(borrowBook));
	}
}
