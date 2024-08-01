package com.example.library_management.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "members")
@Getter
@Setter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false, updatable = false)
	private UUID id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private User user;
	
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
