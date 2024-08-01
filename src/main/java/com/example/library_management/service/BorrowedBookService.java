package com.example.library_management.service;

import com.example.library_management.dto.BorrowBookDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.entity.BorrowedBook;
import com.example.library_management.entity.Member;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.BorrowedBookRepository;
import com.example.library_management.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BorrowedBookService {
	@Autowired
	private BorrowedBookRepository borrowedBookRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	public List<BorrowedBook> getAllBorrowedBooks() {
		return borrowedBookRepository.findAll();
	}
	
	public BorrowedBook borrowBook(BorrowBookDTO borrowBook) {
		Book book = bookRepository.findById(borrowBook.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
		Member member = memberRepository.findById(borrowBook.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));
		
		if (book.getQuantity() <= 0) {
			throw new RuntimeException("Book is empty");
		}
		
		BorrowedBook borrowedBook = new BorrowedBook();
		borrowedBook.setBook(book);
		borrowedBook.setMember(member);
		borrowedBook.setBorrowedDate(ZonedDateTime.now());
		
		book.setQuantity(book.getQuantity() - 1);
		bookRepository.save(book);
		
		return borrowedBookRepository.save(borrowedBook);
	}
	
	public List<BorrowedBook> getBorrowedBooksByMember(UUID memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
		return borrowedBookRepository.findByMemberId(memberId);
	}
	
	public Book returnBook(BorrowBookDTO borrowBook) {
		BorrowedBook borrowedBook = borrowedBookRepository.findByMemberIdAndBookId(borrowBook.getMemberId(), borrowBook.getBookId())
				.orElseThrow(() -> new RuntimeException("No record found for the borrowed book"));
		
		borrowedBookRepository.delete(borrowedBook);
		
		Book book = bookRepository.findById(borrowBook.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
		book.setQuantity(book.getQuantity() + 1);
		
		return bookRepository.save(book);
	}
}
