package com.javaegitimleri.petclinic.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaegitimleri.petclinic.service.PetClinicService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties="spring.profiles.active=dev")
public class PetClinicSecurityWithoutAuthTokenTests {
	
	@Autowired
	private PetClinicService petClinicService;
	
	
	@Test
	//@Test(expected=AuthenticationCredentialsNotFoundException.class) // Bu hata gelirse basarili kabul etmek icin
	public void testFindOwners() {
		petClinicService.findOwners();
		
		//Token olmadan servise istek attigimizda hata alacagiz cunku metodun yetkisi var. (findOwners -> @Secured(value = {"ROLE_USER","ROLE_EDITOR"}) yaptik)
		
		//Calistirdigimizda
		//Alttaki hatayi aldik
		//org.springframework.security.authentication.AuthenticationCredentialsNotFoundException: An Authentication object was not found in the SecurityContext
	
	}

}
