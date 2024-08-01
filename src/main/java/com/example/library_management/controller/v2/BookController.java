package com.example.library_management.controller.v2;

import com.example.library_management.entity.Book;
import com.example.library_management.response.ApiResponse;
import com.example.library_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public ApiResponse<List<Book>> getAllBooks() {
		return new ApiResponse<>(true, "Books Retrieved", HttpStatus.OK.value(), bookService.getAllBooks());
	}
	
	@GetMapping("/{id}")
	public ApiResponse<Book> getBookById(@PathVariable UUID id) {
		return new ApiResponse<>(true, "Book Retrieved", HttpStatus.OK.value(), bookService.getBookById(id));
	}
	
	@PostMapping
	public ApiResponse<Book> createBook(@RequestBody Book book) {
		return new ApiResponse<>(true, "Book Created", HttpStatus.CREATED.value(), bookService.addBook(book));
	}
	
	@PatchMapping("/{id}")
	public ApiResponse<Book> updateBook(@PathVariable UUID id, @RequestBody Book book) {
		return new ApiResponse<>(true, "Book Updated", HttpStatus.OK.value(), bookService.updateBook(id, book));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<Book> deleteBook(@PathVariable UUID id) {
		bookService.deleteBook(id);
		return new ApiResponse<>(true, "Book Deleted", HttpStatus.OK.value(), null);
	}
}
