package com.javaegitimleri.petclinic;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Burayi override edip icerisini bos biraktigimizde defaultsecurityconfig ayarlari(username,pass gibi) ezilir.
		
		http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**","/rest/owners","/login.html").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin();
		http.httpBasic();
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//her istek de dogru basic auth yapsin
		
		//http.authorizeRequests().anyRequest().authenticated(); // tüm gelen isteklerin kimlik doğrulama gerektirdiğini belirtir
		//http.formLogin(); gelen istekler login default sayfasina yonlendirilsin
		//http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**").permitAll(); bu endpointlere herkes erissin
		//http.httpBasic();Http Basic Auth icin (popup acilip username,password girisi icin)
		
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//Bunu yazmadan yaptigimizda bir kere login basic auth oluyoruz. 
		//fakat tekrar username password ile istek attigimizda cache de sessionid,username,password onceki istek icin girdigimiz veriler saklandigi icin 
		//yanlis sifre girsek bile basarili bir sekilde istek atabiliyorduk. Bunu yazarak
		
		/*
		httpBasic() kimlik doğrulamasında, sunucu kimlik bilgilerini her istekle birlikte kontrol eder. Ancak Postman veya tarayıcı gibi istemciler, başarılı bir kimlik doğrulama yaptıktan sonra oturumu yönetebilir ve kimlik doğrulama bilgilerinin yeniden kontrol edilmesini atlayabilir.

		Çözüm: Her istekte kimlik bilgilerini temizleyecek şekilde Postman'i ayarlayın veya sunucunun oturum yönetimini devre dışı bırakmayı düşünebilirsiniz:

		http.sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		Bu ayar, Spring Security'ye oturumsuz bir politika uygulamasını söyler. Böylece, her istek bağımsız olarak doğrulanır.
		*/
	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}
}
