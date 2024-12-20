package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Order(value=0) : Normal SecurityConfiguration dan once devreye girsin diye order yazdik.
//Hata veriyor cunku WebSecurityConfigurerAdapter ettigi icin hangisini kullanacagini bilmiyor.
//Cunku norm SecurityConfiguration extends WebSecurityConfigurerAdapter ettigi icin tum resource lar icin tanımlanımlamıştık.
//Fakat biz bu configure metodu icinde /h2-console/ bu path ile baslayan tm webmetotlari(url-istekleri) icin devreye girsin.
@Configuration
@Order(value=0)
public class H2SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	//h2-console ile baslayacak tum istekleri yakalayacak antMacther ekleyelim
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/h2-console/**").authorizeRequests().anyRequest().permitAll(); //bu path ile baslayan herhangi biri icin yetki ver
		http.csrf().disable();
		http.headers().frameOptions().disable(); //response header disable edilir.
	}
}


/*
Bunu neden yaptık?
Bu kod Spring Security yapılandırmasıdır ve özel olarak H2 veritabanının konsoluna erişimi düzenlemek için hazırlanmıştır. 
Detaylı açıklama aşağıdadır:

1. @Configuration
	Bu sınıfın bir Spring yapılandırma sınıfı olduğunu belirtir.
	Spring IoC konteyneri, bu sınıfı bir yapılandırma bileşeni olarak tanır ve bu sınıf içerisindeki tanımlamaları uygular.
2. @Order(value=0)
	Bu anotasyon, birden fazla WebSecurityConfigurerAdapter sınıfı bulunduğunda, yapılandırmaların hangi sırayla işleneceğini belirler.
	value=0, bu yapılandırmanın diğer WebSecurityConfigurerAdapter sınıflarından önce işleneceğini belirtir.
3. extends WebSecurityConfigurerAdapter
	Bu sınıf, Spring Security'nin web güvenliği için kullanılan WebSecurityConfigurerAdapter sınıfını genişletir.
	Bu sayede, özel güvenlik yapılandırmaları yapabiliriz.
4. @Override
	Bu, bir üst sınıf olan WebSecurityConfigurerAdapter'ın configure(HttpSecurity http) yöntemini geçersiz kıldığımızı belirtir.
	Bu yöntemde, uygulamamızın güvenlik kurallarını tanımlıyoruz.
5. http.antMatcher("/h2-console/**")
	antMatcher metodu, belirtilen bir desenle eşleşen istekleri işlemek için kullanılır.
	"/h2-console/**", /h2-console/ ile başlayan ve devamında herhangi bir şey olabilecek tüm URL'leri kapsar.
	Örneğin: /h2-console/login.do veya /h2-console/some-path.
6. authorizeRequests().anyRequest().permitAll()
	Bu yapılandırma, "/h2-console/**" ile eşleşen isteklerin tüm kullanıcılar tarafından erişilebilir olmasını sağlar.
	Yani, giriş yapmış olup olmadığınıza veya belirli bir role sahip olup olmadığınıza bakılmaksızın bu endpoint'lere erişebilirsiniz.
7. http.csrf().disable();
	CSRF (Cross-Site Request Forgery) korumasını devre dışı bırakır.
	H2 konsol gibi özel araçlarda CSRF koruması gerekli olmadığından devre dışı bırakılır.
	Eğer aktif kalsaydı, H2 konsolu için ek CSRF token'ları tanımlamanız gerekirdi.
8. http.headers().frameOptions().disable();
	Spring Security, varsayılan olarak Frame Options başlığını kullanır ve tarayıcının iframe içerisinde bir sayfayı yüklemesini engeller.
	H2 konsolu iframe kullanarak çalışır. Bu nedenle, frameOptions devre dışı bırakılır.
	Frame Options: XSS saldırılarını önlemek için sayfanın iframe'de yüklenmesini kısıtlar.
	
Genel Olarak Bu Yapılandırma Ne Yapar?
	/h2-console/ ile başlayan tüm istekler için herhangi bir güvenlik kısıtlaması uygulanmaz.
	CSRF koruması ve Frame Options kontrolü devre dışı bırakılır.
	Bu ayar, yalnızca H2 konsoluna özel olarak yapılır ve diğer endpoint'leri etkilemez.
Neden Gerekli?
	H2 veritabanı konsolu geliştirme sırasında kullanılır ve genellikle güvenlik kısıtlamalarını gereksiz kılmak için böyle bir yapılandırma yapılır.
	Ancak bu yapılandırma sadece geliştirme ortamında kullanılmalıdır; 
	üretim ortamında bu ayarları devre dışı bırakmak güvenlik açıklarına neden olabilir.
*/
