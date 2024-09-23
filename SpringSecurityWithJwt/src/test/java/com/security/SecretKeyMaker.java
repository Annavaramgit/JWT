//package com.security;
//
//import javax.crypto.SecretKey;
//
//import org.junit.jupiter.api.Test;
//
//import io.jsonwebtoken.Jwts;
//import jakarta.xml.bind.DatatypeConverter;
//
//public class SecretKeyMaker {
//	@Test
//	public static void generateSecretKey() {
//		SecretKey key = Jwts.SIG.HS512.key().build();
//		String encodedKey = DatatypeConverter.printHexBinary(key.getEncoded());
//		System.out.printf("\nKey = [%s]\n", encodedKey);
//	}
//	public static void main(String[] args) {
//		generateSecretKey();
//	}
//}
