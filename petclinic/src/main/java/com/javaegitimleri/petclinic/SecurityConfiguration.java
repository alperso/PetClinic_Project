package com.javaegitimleri.petclinic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends AbstractSecurityConfiguration {
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//yaziyordu abstract yaparak araya abstract class koyduk // Yaparak Projede birden fazla güvenlik yapılandırmasi

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Burayi override edip icerisini bos biraktigimizde defaultsecurityconfig ayarlari(username,pass gibi) ezilir.
		
		http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**","/login.html").permitAll();
		//http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')"); //rest/** olan endpointlere sadece editor olanlar erisebilir//RestSecurityConfiguration.java ya taşındı
		http.authorizeRequests().antMatchers("/actuator/**").access("hasRole('ADMIN')"); //actuator/** olan endpointlere sadece admin olanlar erisebilir
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin();
		http.httpBasic();
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//her istek de dogru basic auth yapsin
		
		//***************************************************************************
		//http.authorizeRequests().anyRequest().authenticated(); // tüm gelen isteklerin kimlik doğrulama gerektirdiğini belirtir
		//http.formLogin(); gelen istekler login default sayfasina yonlendirilsin
		//http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**").permitAll(); bu endpointlere herkes erissin
		//http.httpBasic();Http Basic Auth icin (popup acilip username,password girisi icin)
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//Bunu yazmadan yaptigimizda bir kere login basic auth oluyoruz. 
		//fakat tekrar username password ile istek attigimizda cache de sessionid,username,password onceki istek icin girdigimiz veriler saklandigi icin 
		//yanlis sifre girsek bile basarili bir sekilde istek atabiliyorduk. Bunu yazarak
		
		//***************************************************************************
		/*
		httpBasic() kimlik doğrulamasında, sunucu kimlik bilgilerini her istekle birlikte kontrol eder. Ancak Postman veya tarayıcı gibi istemciler, başarılı bir kimlik doğrulama yaptıktan sonra oturumu yönetebilir ve kimlik doğrulama bilgilerinin yeniden kontrol edilmesini atlayabilir.

		Çözüm: Her istekte kimlik bilgilerini temizleyecek şekilde Postman'i ayarlayın veya sunucunun oturum yönetimini devre dışı bırakmayı düşünebilirsiniz:

		http.sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		Bu ayar, Spring Security'ye oturumsuz bir politika uygulamasını söyler. Böylece, her istek bağımsız olarak doğrulanır.
		*/
		//***************************************************************************
		/*
		 Web Sayfalarinin (.access("hasRole('EDITOR'),.access("hasRole('ADMIN')) yetkilendirilmesi??
		 
		 http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')"); //rest/** olan endpointlere sadece editor olanlar erisebilir
		 http.authorizeRequests().antMatchers("/actuator/**").access("hasRole('ADMIN')"); //actuator/** olan endpointlere sadece admin olanlar erisebilir
		 
		 Spring Security, kullanıcı rolleri için ROLE_ ön ekini kullanır.
		 Örneğin, bir kullanıcının "EDITOR" rolü varsa, bu genellikle veritabanında ROLE_EDITOR olarak saklanır. 
		 Ancak, Spring Security, kullanıcıya bir rol atandığında, bu rolün başına ROLE_ ön ekini ekler.
		 
		 Bu acces olarak hasRole eklenmesi spesifik olandan genel olana gore sirayla yazilmalidir. 
		 
		 user4 rolu (ROLE_SECRETER) ile giris yaptigimda rest ve actuator path lere giris yapamayacaktır. Diger pathlere giris yapabilir.
		 
		 
		Burada userlara yetki rolleri verilmiştir.
	    SELECT * FROM AUTHORITIES;
	 
	    USERNAME  		AUTHORITY  
	    user1			ROLE_USER
	    user2			ROLE_EDITOR
	    user2			ROLE_USER
	    user3			ROLE_ADMIN
	    user3			ROLE_EDITOR
	    user3			ROLE_USER
	    user4			ROLE_SECRETER
	    SELECT * FROM USERS;
	 
	    USERNAME  	PASSWORD  			ENABLED  
	    user1		{bcrypt}$2a....		TRUE
	    user2		{bcrypt}$2a....		TRUE
	    user3		{bcrypt}$2a....		TRUE
	 
	    SecurityConfiguration configure Methodu

	    Burada
	    "/rest/**" -> .access("hasRole('EDITOR')"); //EDITOR rolünde olan userlar girebilir yetkisi yoksa giremez.
	    "/actuator/**" -> .access("hasRole('ADMIN')");  //ADMIN rolünde olan userlar girebilir yetkisi yoksa giremez.
	    http.authorizeRequests().anyRequest().authenticated(); -> anyRequestte geriye kalan tüm istekler basicAut olunarak girilmelidir.
		//***************************************************************************


		*/
	
	}
}
