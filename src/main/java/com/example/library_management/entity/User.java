package com.example.library_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@NotNull(message = "Username is required")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String username;
	
	@NotNull(message = "Password is required")
	@Size(min = 6, message = "Password must be minimal 6 characters")
	private String password;
	
	@NotNull(message = "Role is required")
	@Enumerated(EnumType.STRING)
	private Role role;
	
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
