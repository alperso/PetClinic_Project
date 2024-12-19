package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Burayi override edip icerisini bos biraktigimizde defaultsecurityconfig ayarlari(username,pass gibi) ezilir.
		
		//http.authorizeRequests().anyRequest().authenticated(); // tüm gelen isteklerin kimlik doğrulama gerektirdiğini belirtir
		//http.formLogin(); gelen istekler login default sayfasina yonlendirilsin
		//http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**").permitAll(); bu endpointlere herkes erissin
		
		http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**","/rest/owners").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin();
	}
}
