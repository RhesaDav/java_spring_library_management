package com.example.library_management.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.access-token-expiration}")
	private Long accessToken;
	
	@Value("${jwt.refresh-token-expiration}")
	private Long refreshToken;
	
	public String generateAccessToken(UserDetails userDetails) {
		return generateToken(userDetails, accessToken);
	}
	
	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(userDetails, refreshToken);
	}
	
	private String generateToken(UserDetails userDetails, Long expiresIn) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList())
		);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiresIn))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			System.out.println("Invalid JWT token");
			return false;
		}
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
}
