package com.bookrental.security;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bookrental.exceptions.AppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

//	@Value("${security.jwt.secret-key}")
	private final String secretKey = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String username = userDetails.getUsername();
		return buildToken(Map.of("roles",roles), username, getExpirationTime());
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}

	private String buildToken(Map<String, Object> extraClaims,String username, long expiration) {
		return Jwts.builder().setClaims(extraClaims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		try{
			return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
		}catch(ExpiredJwtException e){
			throw new AppException("Expired JWT token :"+ e.getMessage());
		} catch(UnsupportedJwtException e){
			throw new AppException("Jwt token not supported :" + e.getMessage());
		} catch(MalformedJwtException e){
			throw new AppException("Invalid JWT token :"+ e.getMessage());
		} catch(SignatureException e){
			throw new AppException("JWT signature validation failed :"+ e.getMessage());
		} catch(IllegalArgumentException e){
			throw new AppException("JWT token is null or empty :"+ e.getMessage());
		}
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
