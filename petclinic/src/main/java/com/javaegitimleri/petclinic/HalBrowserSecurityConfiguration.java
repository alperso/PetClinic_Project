package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Order(value=-1) : Normal WebSecurityConfigurerAdapter dan once devreye girsin diye order yazdik.
@Configuration
@Order(value=-1)
public class HalBrowserSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/hal/**").authorizeRequests().anyRequest().permitAll(); //bu path ile baslayan herhangi biri icin yetki ver
		http.csrf().disable();
		http.headers().frameOptions().disable(); //response header disable edilir.
	}
}
