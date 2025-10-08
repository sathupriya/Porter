package com.example.Porter.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {


	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long expiredTime;


	
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiredTime)).signWith(getSigningKey()).compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		}catch (Exception ex){
			return false;
		}
	}
	
	public String extractUsername(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		String username = extractUsername(token);
	//	return new UsernamePasswordAuthenticationToken(username,null,List.of(new SimpleGrantedAuthority("Role_user")));
		return new UsernamePasswordAuthenticationToken(username,null,List.of());
	}
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
}
