package com.javaegitimleri.petclinic;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTests {
	
	@Test
	public void generateEncodePassowords() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println("{bcrypt}"+passwordEncoder.encode("user1"));
		System.out.println("{bcrypt}"+passwordEncoder.encode("user2"));
		System.out.println("{bcrypt}"+passwordEncoder.encode("user3"));
		System.out.println("{bcrypt}"+passwordEncoder.encode("user4"));
		
		
	}
}
