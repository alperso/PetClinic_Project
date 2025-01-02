package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(value=1)
@Configuration
public class RestSecurityConfiguration extends AbstractSecurityConfiguration {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//anyRequest() tanımlaması antMatchers gibi özel kurallardan sonra yer alır.
		//super.configure(http);//Kuralları daha spesifikten daha genele doğru sıralamanız gerekir. Yani, antMatchers gibi daha özel yolları önce, anyRequest gibi daha genel kuralları ise en son tanımlamalısınız.
		http.antMatcher("/rest/**");
		http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')"); //rest/** olan endpointlere sadece editor olanlar erisebilir
		http.csrf().disable();
		http.httpBasic();
	}

}
/*
	Özetle

			basicAuth
    user1
			user1 ile sadece login olabildik

			basicAuth
			user2
			user2 basicAuth yaptıgımızda
			ile editor yetkimiz vardı
			http://localhost:8080/rest/owner/1 sayfamızı sorguladıgımızda geldi fakat
			http://localhost:8080/actuator/health sayfası Admin yetkisi olup user2 icin
			tanımlı olmadıgı için 403 yetki hatası verdi.

			basicAuth
			user3
			user3 yaptıgımızda hem editor hemde admin yetkisi oldugu icin
			http://localhost:8080/rest/owner/1
			http://localhost:8080/actuator/health
			sayfalarına erisebildi.
*/
