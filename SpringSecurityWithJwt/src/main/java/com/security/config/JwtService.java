package com.security.config;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

	/*
	 * This is the secret key along with this jwt token is produced this key i
	 * genarated at src/test/java com.security.SecretKeyMaker class (it print secret
	 * key used here)
	 */

	private static final String SECRET = "2A3896817DC8FD6EFB98D14D5369E2D0C64C3ED425D46A0A38C0B7F049E90AE998D6EEB3FBF41A30508A964AE5152A221054110A0FC92215B16BBF9CD8143088";
	private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);
	
	public String genarateJwtToken(UserDetails userDetails) {
		Map<String,String> claims =  new HashMap<>();
		claims.put("ISS","Satya");
		log.info("userDetails:::::::======="+userDetails.toString());
		return Jwts.builder()
			.claims(claims)
			.subject(userDetails.getUsername())
			.issuedAt(Date.from(Instant.now()))
			.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
			.signWith(getSignKey())
			.compact();
		
		
	}
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	/*This is for extract subject from the token ,for  token validation process*/
	
	public String getSubjectFromToken(String jwtToken) {
		log.info("getSubjectFrom token method enetered with token  ::::"+jwtToken);
		Claims claims = getClaims(jwtToken);
		return claims.getSubject();
		
	}
	
	/*it extract the claims(claims means what ever there in payload in jwt token)*/
	public Claims getClaims(String jwtToken) {
		log.info("getClaims token method enetered with token  ::::"+jwtToken);
		return Jwts.parser()
				.verifyWith((SecretKey) getSignKey())
				.build()
				.parseSignedClaims(jwtToken)
				.getPayload();
		
	}
	
	/*it is used for check token valid or not means checks expiration time of the token*/
	public boolean isTokenValid(String jwtToken) {
		log.info("isTokenValid  method enetered with token  ::::"+jwtToken);
		Claims claims = getClaims(jwtToken);
		
		return claims.getExpiration().after(Date.from(Instant.now()));
		
	}
	
}
