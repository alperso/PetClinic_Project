package com.javaegitimleri.petclinic.web;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;


/*
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) yaziyoruz cunku
Bu test calisirken hicbir sekilde gomulu olarak dahi tomcat web containerin calistirilmadigini onun yerine spring 
boot tarafindan MOCK BİR servlet containerin yaratildigini vurgulamak icin yapiyoruz.
*/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@WithMockUser(username="user2",password="user2",authorities = "EDITOR") //Mockmvc requestlerinde auth tokenleri yerlestirmek icin
public class OwnersWebMvcTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testOwners() throws Exception{
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners");
		
		ResultActions resultActions = mockMvc.perform(requestBuilder);
		
		MvcResult mvcResult= resultActions.andReturn();
		
		ModelAndView mav= mvcResult.getModelAndView();
		
		MatcherAssert.assertThat(mav.getViewName(),Matchers.equalTo("owners"));
		MatcherAssert.assertThat(mav.getModel().containsKey("owners"), Matchers.is(true));

	}

}

/*
 * Bu kod ne iş yapar?
Bu kod, Spring Boot uygulamasındaki bir Web MVC testini içermektedir. 
Bu test sınıfı, Spring Boot’un sunduğu test desteğini kullanarak, bir web katmanının doğru çalışıp çalışmadığını test etmek için yazılmıştır. 
Aşağıda, bu sınıfın her bir bölümünü detaylıca açıklayacağım.

Anlamlı Anotasyonlar ve Açıklamalar

@RunWith(SpringRunner.class):
Bu anotasyon, JUnit testlerinin Spring TestContext Framework ile çalışmasını sağlar. SpringRunner, testlerin Spring ortamında çalışmasını sağlar. Bu anotasyon sayesinde Spring, test sırasında gerekli olan tüm konfigürasyonları sağlar ve Spring konteyneri testlere entegre edilir.

@SpringBootTest(webEnvironment = WebEnvironment.MOCK):
@SpringBootTest anotasyonu, Spring Boot uygulamasını başlatır ve testler sırasında uygulamanın gerçek bir web sunucusu yerine, 
mock bir web ortamı kullanır.
webEnvironment = WebEnvironment.MOCK ayarı, test sırasında gerçek bir web sunucusu (tomcat gibi) başlatılmadan, 
sadece Spring MVC konteynerini başlatarak, tüm HTTP isteklerinin mocklanmasını sağlar. Bu, sadece Web MVC katmanının test edilmesini sağlar.

@ActiveProfiles("dev"):
Bu anotasyon, Spring profili ayarlarını değiştirmek için kullanılır. Burada "dev" profili aktif hale getirilmiştir. 
Uygulamanın konfigürasyonlarını bu profile göre belirler. Örneğin, application-dev.properties veya application-dev.yml dosyasındaki ayarlar kullanılır.

@AutoConfigureMockMvc:
Bu anotasyon, Spring’in MockMvc nesnesini otomatik olarak konfigüre eder. MockMvc, testlerde web katmanını simüle ederek HTTP istekleri 
göndermeyi ve yanıtları kontrol etmeyi sağlar. Yani, gerçek bir HTTP sunucusuna bağlanmadan, web uygulamanızın işlevselliğini test edebilirsiniz.
Bu anotasyon sayesinde, testlerde MockMvc'yi doğrudan kullanabilirsiniz.

@WithMockUser(username = "user1", password = "user1", authorities = "USER"):
Bu anotasyon, testlerde kullanılan kullanıcıyı sahte olarak (mock) tanımlar. 
Yani, testler sırasında user1 adlı bir kullanıcı oturum açmış gibi davranılır.
username: Kullanıcının adı.
password: Kullanıcının şifresi.
authorities: Kullanıcının rolü veya yetkisi. Burada "USER" rolü atanmış.
Bu, özellikle güvenlik testlerinde yararlıdır. MockMvc isteklerini, belirli bir kullanıcı yetkisi ile simüle edebilirsiniz, 
örneğin, bir sayfanın yalnızca belirli bir kullanıcı veya belirli bir rol tarafından erişilebilir olmasını test etmek için.
*/

/*
----MockMvc Nedir?
MockMvc, Spring MVC uygulamalarında web katmanını test etmek için kullanılan bir araçtır. 
Özellikle kontrolcüleri (controller) test etmek için tasarlanmıştır. 
Gerçek bir sunucu başlatmadan Spring MVC bileşenlerini test etmenizi sağlar.

MockMvc Nedir?
MockMvc, Spring MVC uygulamalarında web katmanını test etmek için kullanılan bir araçtır. Özellikle kontrolcüleri (controller) test etmek için tasarlanmıştır. Gerçek bir sunucu başlatmadan Spring MVC bileşenlerini test etmenizi sağlar.
 
MockMvc'nin Avantajları
Sunucu Başlatmaya Gerek Yok:
Gerçek bir uygulama sunucusu başlatılmadığı için testler daha hızlı çalışır.
Spring MVC Katmanını İzole Eder:
Sadece web katmanını (controller, filtreler, validasyonlar, vb.) test etmek istediğinizde, veri tabanı veya servis katmanını çalıştırmanıza gerek kalmaz.
HTTP Çağrıları Simüle Eder:
HTTP GET, POST, PUT, DELETE gibi istekleri simüle edebilir ve kontrolcünün bu isteklere nasıl cevap verdiğini test edebilirsiniz.
Sonuçları Doğrulama:
Dönen HTTP durum kodlarını, header bilgilerini ve JSON, XML gibi yanıtları kontrol edebilirsiniz.


MockMvc Kullanımı ile Entegrasyon Testi Arasındaki Fark
MockMvc: Sadece web katmanını test eder. Servis veya veritabanı gibi diğer katmanlarla doğrudan bağlantısı yoktur.
Entegrasyon Testi: Tüm uygulama bağlamını yükler ve kontrolcü, servis, repository gibi tüm katmanları bir arada test eder.

Ne Zaman MockMvc Kullanılır?
Yalnızca kontrolcülerin (controller) doğru çalışıp çalışmadığını test etmek istediğinizde.
Spring MVC validasyonları, filtreler veya interceptor'ları test etmek istediğinizde.
Gerçek bir sunucu başlatmadan hızlı testler yapmak istediğinizde.
Ne Zaman Entegrasyon Testi Kullanılır?
Kontrolcüyle birlikte servis ve veritabanı gibi diğer katmanları da test etmek istediğinizde.
Uygulamanın uçtan uca doğru çalışıp çalışmadığını doğrulamak istediğinizde.

Sonuç
MockMvc, Spring MVC kontrolcülerinin hızlı ve izole bir şekilde test edilmesini sağlar. 
Bu, özellikle büyük projelerde testleri daha hızlı ve odaklı yaparak geliştirme sürecini kolaylaştırır. 
Ancak, kontrolcü dışında kalan katmanları da 
test etmek için entegrasyon testlerini tamamlayıcı olarak kullanmak gerekir.


Hangisini Ne Zaman Kullanmalıyım?
1. Controller Testlerini Kullanmanız Gereken Durumlar
	Web katmanında bir hata olup olmadığını hızlı bir şekilde görmek istiyorsanız.
	Performansı önemsiyor ve bağımsız bir test istiyorsanız.
	Katmanlar arasındaki bağımlılıkları izole etmek istiyorsanız.
	Bir API endpoint'inin doğru HTTP yanıtları verdiğini doğrulamak istiyorsanız.
2. Entegrasyon Testlerini Kullanmanız Gereken Durumlar
	Uygulamanın tamamının doğru çalıştığını test etmek istiyorsanız.
	Veri tabanına erişimin doğruluğunu kontrol etmek istiyorsanız.
	Servis katmanı ile controller arasındaki iletişimi test etmek istiyorsanız.
	Gerçekçi senaryolar üzerinde çalışıyorsanız (ör. bir HTTP isteği gönderip yanıt alana kadar tüm iş akışını test etmek).

 * */
