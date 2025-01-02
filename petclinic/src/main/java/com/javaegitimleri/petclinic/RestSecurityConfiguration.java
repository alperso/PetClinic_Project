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
