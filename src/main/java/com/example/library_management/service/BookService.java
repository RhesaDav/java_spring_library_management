package com.example.library_management.service;

import com.example.library_management.entity.Book;
import com.example.library_management.exception.DataAlreadyExistException;
import com.example.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public Book getBookById(UUID id) {
		return bookRepository.findById(id).orElseThrow(() -> new DataAlreadyExistException("Book not found"));
	}
	
	public Book addBook(Book newBook) {
		Book existingBook = bookRepository.findByTitle(newBook.getTitle());
		if (existingBook != null) {
			throw new DataAlreadyExistException("Book already exists!");
		}
		return bookRepository.save(newBook);
	}
	
	public Book updateBook(UUID id, Book updatedBook) {
		Book existingBook = bookRepository.findById(id).orElseThrow(() -> new DataAlreadyExistException("Book not found"));
		existingBook.setTitle(updatedBook.getTitle());
		existingBook.setAuthor(updatedBook.getAuthor());
		existingBook.setQuantity(updatedBook.getQuantity());
		return bookRepository.save(existingBook);
	}
	
	public String deleteBook(UUID id) {
		Book existingBook = bookRepository.findById(id).orElseThrow(() -> new DataAlreadyExistException("Book not found"));
		bookRepository.delete(existingBook);
		return "Book deleted with id: " + id;
	}
}
