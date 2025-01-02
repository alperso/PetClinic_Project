package com.javaegitimleri.petclinic.web;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
public class PetClinicRestControllerEmbededTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	/* 
	 * TestRestTemplate, Spring Boot uygulamalarındaki REST API'lerini test etmek için kullanılır. Bu araç, özellikle entegrasyon testlerinde faydalıdır ve doğru çalışıp çalışmadığını test etmek amacıyla gerçek HTTP istekleri gönderir. 
	 * RestTemplate'in test için uyarlanmış versiyonudur ve testlerde kullanımı kolaylaştırır.
	 * Spring Boot testlerinde HTTP istemcisi olarak kullanılan bir araçtır ve özellikle bir REST API'yi test etmek amacıyla tasarlanmıştır. 
	 * TestRestTemplate, Spring Boot uygulamalarında endpoint'lerin doğru çalıştığını test etmek için kolay ve esnek bir yöntem sunar.
	 * 
	 TestRestTemplate ve RestTemplate Arasındaki Farklar:
		TestContext Entegrasyonu: TestRestTemplate, test senaryolarında Spring Boot uygulamalarıyla entegrasyonlu çalışmak üzere özelleştirilmiş 
		bir araçtır. 
		RestTemplate ise daha genel bir HTTP istemcisidir ve uygulama ortamında farklı istemci ihtiyaçlarını karşılamak için kullanılır.
		Başka Bir Avantaj: TestRestTemplate testler sırasında başlatılan Spring Boot uygulamasının bağlamını kullanır. 
		Böylece testler sırasında daha kolay bir yapı sunar ve gerçek bir sunucuyla API uç noktalarını test eder.
	*/

	@Before
	public void setUp() {
		restTemplate = restTemplate.withBasicAuth("user2", "user2");

	}

	@Test
	public void testGetOwnerById() {

		/* Basarili Durum : getOwner/1 den gelen FirstName degeri Alper oldugu icin */
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/getOwner/1",Owner.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200)); // gelen sonuc 200 response mu
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Ziya")); // gelen degerin Alper olduguna bakıyoruz.

		/* Basarisiz Durum : getOwner/2 den gelen FirstName degeri Alper olmadıgı Mahmut Oldugu icin */
		// ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/getOwner/1",Owner.class);
		// MatcherAssert.assertThat(response.getStatusCodeValue(),Matchers.equalTo(200)); //gelen sonuc 200 response mu
		// MatcherAssert.assertThat(response.getBody().getFirstName(),Matchers.equalTo("Alper")); //gelen degerin Alper olduguna bakıyoruz.
	}
	
	@Test
	public void testGetOwnerByIdBasicAuthWithRestTemplateBuilder() {

		/* Basarili Durum : getOwner/1 den gelen FirstName degeri Alper oldugu icin */
		
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8085/rest/getOwner/1",Owner.class,new HttpEntity<String>(new HttpHeaders()));
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200)); // gelen sonuc 200 response mu
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Ziya")); // gelen degerin Ziya olduguna bakıyoruz

	}
	
	@Test
	public void testGetOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		List<Map<String,String>> body = response.getBody();
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		//Başarısız Durum
		//MatcherAssert.assertThat(firstNames,Matchers.containsInAnyOrder("Alper","Demir","Asa"));
		//Başarısız Durum
		//MatcherAssert.assertThat(firstNames,Matchers.containsInAnyOrder("Alper","Mahmut","Asa"));
		//Başarılı Durum
		//firstNames icinde Alper,Mahmut,Asa ve Fidan gelmesini bekliyorum
		MatcherAssert.assertThat(firstNames,Matchers.containsInAnyOrder("Alper","Mahmut","Asa","Fidan"));
		
		
		//Başarılı Durum With Contains
		//MatcherAssert.assertThat(firstNames,Matchers.contains("Alper")); //icinde Alper var mı
		
		//Başarılı Durum With hasItem
		//MatcherAssert.assertThat(firstNames,Matchers.hasItem("Alper")); //icinde Alper var mı
	}

	/*--------------------------------------------------------------*/
	
	@Test
	public void testCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Hakan");
		owner.setLastName("Yavaş");
		
		//http://localhost:8080/rest/createOwner/21312312312
		URI postForLocation = restTemplate.postForLocation("http://localhost:8080/rest/createOwner", owner);

		String path = "http://localhost:8080/rest/getOwner/"+splitForUrl(postForLocation.getPath());//ekleneni alıyorum
		Owner owner2= restTemplate.getForObject(path, Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
	}
	
	public String splitForUrl(String url) {
		
		//Eğer URL'deki son long değeri split kullanarak bulmak istiyorsanız, aşağıdaki yöntemi kullanabilirsiniz.
		Long lastLong = 0L;
		String[] parts = url.split("/");

	        // Get the last part
	        String lastPart = parts[parts.length - 1];

	        try {
	            // Convert the last part to a long
	            lastLong = Long.parseLong(lastPart);
	            System.out.println("Last long value in URL: " + lastLong);
	        } catch (NumberFormatException e) {
	            System.out.println("No valid long value found in the URL.");
	        }
	        
	  return lastLong.toString();
	}
	
	/*--------------------------------------------------------------*/

	@Test
	public void testUpdateOwner() {
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/getOwner/1", Owner.class);//1 olan objeyi guncellemek istiyorum
		
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Ziya")); //gelen objenin firstname i fidan mi ?
		
		owner.setFirstName("Ahmet");
		owner.setLastName("AhmetCan"); 
		
		restTemplate.put("http://localhost:8080/rest/updateOwner/1", owner);
		
		Owner owner2 = restTemplate.getForObject("http://localhost:8080/rest/getOwner/1", Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo("Ahmet"));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo("AhmetCan"));
	}
	
	/*--------------------------------------------------------------*/
	
	@Test
	public void testDeleteOwner() {
		
//		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/getOwner/2", Owner.class);//4 olan objeyi silmek istiyorum
//		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Ziya")); //gelen objenin firstname i fidan mi ?
		
		restTemplate.delete("http://localhost:8080/rest/deleteOwner/2");
		
		//Silinen kaydıya bakalım. Bu sefer getForEntity ile yapalım. 
		try {
			restTemplate.getForEntity("http://localhost:8080/rest/getOwner/2", Owner.class);
			Assert.fail("Böyle bir owner bulunamadı"); //belirli bir durum gerçekleşmediğinde testin başarısız olmasını zorlamak için kullanılır.
			//testin başarılı olması için restTemplate.getForEntity çağrısının bir hata fırlatması beklenir. 
			//Eğer hata alınmazsa veya beklenmeyen bir şekilde hata fırlatılmazsa test başarısız olur.
		} catch (RestClientException ex) {
			
		}
	}
	
	//Transactional icin test 
	//if(true) throw new RuntimeException("testing rollback"); vermistik hata vericek
	
	/*--------------------------------------------------------------*/
	
	@Test
	public void testDeleteOwner2() {
		
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/getOwner/4", Owner.class);//4 olan objeyi silmek istiyorum
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Fidan")); //gelen objenin firstname i fidan mi ?
		
		restTemplate.delete("http://localhost:8080/rest/deleteOwner/4");
		
		//Silinen kaydıya bakalım. Bu sefer getForEntity ile yapalım. 
			try {
				restTemplate.getForEntity("http://localhost:8080/rest/getOwner/4", Owner.class);
				Assert.fail("Böyle bir owner bulunamadı"); //belirli bir durum gerçekleşmediğinde testin başarısız olmasını zorlamak için kullanılır.
				//testin başarılı olması için restTemplate.getForEntity çağrısının bir hata fırlatması beklenir. 
				//Eğer hata alınmazsa veya beklenmeyen bir şekilde hata fırlatılmazsa test başarısız olur.
			} catch (HttpClientErrorException ex) {
				MatcherAssert.assertThat(ex.getStatusCode().value(),Matchers.equalTo(404));
				
			}
	}
	
	/*--------------------------------------------------------------*/
	@Test
	public void testDeleteOwner3() {
		
		//restTemplate.delete("http://localhost:8080/rest/deleteOwner/2");
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8080/rest/owner/1", HttpMethod.DELETE,null,Void.class);
		
		try {
			restTemplate.getForEntity("http://localhost:8080/rest/getOwner/2", Owner.class);
			Assert.fail("Böyle bir owner bulunamadı");
		} catch (RestClientException ex) {
			
		}
	}
}
