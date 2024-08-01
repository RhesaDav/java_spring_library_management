package com.example.library_management.repository;

import com.example.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
	Book findByTitle(String title);
}
