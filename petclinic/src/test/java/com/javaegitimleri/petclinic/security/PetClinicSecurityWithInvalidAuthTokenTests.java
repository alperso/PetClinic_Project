package com.javaegitimleri.petclinic.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaegitimleri.petclinic.service.PetClinicService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties="spring.profiles.active=dev")
public class PetClinicSecurityWithInvalidAuthTokenTests {
	
	@Autowired
	private PetClinicService petClinicService;
	
	//Once calissin
	@Before
	public void setUp() {
		TestingAuthenticationToken auth = new TestingAuthenticationToken("user1","user1","ROLE_XXX"); //Basarisiz Access Denied
		//TestingAuthenticationToken auth = new TestingAuthenticationToken("user1","user1","ROLE_USER"); //Basarili
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	//Sonra calissin
	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	//@Test(expected=AuthenticationCredentialsNotFoundException.class) // Bu hata gelirse basarili kabul etmek icin
	//@Test(expected=AccessDeniedException.class) // Bu hata gelirse basarili kabul etmek icin
	public void testFindOwners() {
		petClinicService.findOwners();
	}

}
