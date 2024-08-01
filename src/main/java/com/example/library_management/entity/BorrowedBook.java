package com.example.library_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "borrowed_books")
@Getter
@Setter
public class BorrowedBook {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false, updatable = false)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;
	
	@Column(name = "borrowed_date", nullable = false)
	private ZonedDateTime borrowedDate;
	
	@Column(name = "returned_date")
	private ZonedDateTime returnedDate;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private ZonedDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneId.of("GMT+7"));
		this.updatedAt = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneId.of("GMT+7"));
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneId.of("GMT+7"));
	}
}
