package com.javaegitimleri.petclinic;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public abstract class AbstractSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}
	
	/*
	 * AbstractSecurityConfiguration sınıfını neden kullandığınız, projenizin tasarımı ve güvenlik yapılandırması ihtiyaçlarınıza bağlıdır. 
	 * Ancak bu tür bir yapılandırmada genellikle şu sebeplerle bir abstract class tercih edilir:
	 * 
	 1. Yeniden Kullanılabilirlik
		Eğer projede birden fazla güvenlik yapılandırması sınıfı olacaksa, ortak olan kodları bir abstract sınıfta toplamak iyi bir yöntemdir.
		Örneğin, projede farklı modüller için farklı güvenlik yapılandırmaları (API, admin paneli, vs.) gerekiyorsa, 
		bu modüllerin hepsi AbstractSecurityConfiguration'dan türetilerek kolayca özelleştirilebilir.
	2. Ortak İşlevselliğin Merkezi Yönetimi
		Abstract sınıf, tüm türetilen sınıflar için bir "temel yapı" sağlar. Örneğin, sık kullanılan metotlar (şifreleme yapılandırmaları, genel filtreler veya erişim kontrolleri) bu sınıfta tanımlanabilir.
		Ortak davranışları bir kez tanımlayıp türetilen tüm sınıflarda kullanılabilir hale getirirsiniz.
	3. Kodun Daha Modüler ve Anlaşılır Olması
		Eğer doğrudan WebSecurityConfigurerAdapter'dan türetirseniz, her güvenlik yapılandırması sınıfı tamamen bağımsız olur. Bu, kodun modülerliğini azaltabilir.
		Abstract sınıf kullanmak, güvenlik yapılandırmanızı bir temel üzerinden yapılandırmanızı ve özelleştirmenizi sağlar.
	4. Kolay Özelleştirme
		Abstract sınıf, bazı metotları zorunlu olarak override edilmesi gereken abstract metotlar olarak bırakabilir. Bu, türetilen sınıfların belirli yapılandırmaları mutlaka tanımlamasını sağlar.
	 
	 * */
}
