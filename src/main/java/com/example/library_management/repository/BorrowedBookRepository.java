package com.example.library_management.repository;

import com.example.library_management.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, UUID> {
	List<BorrowedBook> findByMemberId(UUID memberId);
	
	Optional<BorrowedBook> findByMemberIdAndBookId(UUID memberId, UUID bookId);
}
