package com.example.library_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;
	
	@NotBlank(message = "title is required")
	@Column(nullable = false)
	private String title;
	
	@NotBlank(message = "author is required")
	@Column(nullable = false)
	private String author;
	
	@NotNull(message = "quantity is required")
	@Column(nullable = false)
	@Min(value = 1, message = "Quantity must at least 1")
	private int quantity;
	
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
