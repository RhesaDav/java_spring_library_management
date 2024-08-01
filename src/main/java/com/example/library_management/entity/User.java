package com.example.library_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
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
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
	private Member member;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Jakarta")
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Jakarta")
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
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}
}
