package com.javaegitimleri.petclinic.web;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;

public class PetClinicRestControllerTests {
	
	private RestTemplateBuilder restTemplateBuilder;
	
	private RestTemplate restTemplate;

	/*
	 * JUnit'teki @Before anotasyonu, test metodlarından önce çalıştırılması gereken
	 * kodu belirtmek için kullanılır. Yani, her test metodundan önce belirli bir
	 * hazırlık işlemi yapılması gerektiğinde @Before anotasyonu kullanılır.
	 * 
	 * JUnit 4: @Before anotasyonu ile her test metodundan önce belirli bir hazırlık
	 * yapılır. JUnit 5: JUnit 5'te bu fonksiyonaliteyi sağlayan
	 * anotasyon @BeforeEach olarak değiştirilmiştir. (JUnit 5'i kullanmak için
	 * junit-jupiter-api ve junit-jupiter-engine bağımlılıklarını pom.xml'e açıkça
	 * belirtmeniz gerekmektedir.)
	 *
	 *
	 * MatcherAssert metodunu Hamcrest den aldik
	 * Hamcrest, Java'da koşullu testler yazarken daha esnek ve güçlü eşleştirme
	 * yöntemleri sunan bir kütüphanedir. Özellikle, assertion (doğrulama)
	 * işlemlerinde daha doğal, okunabilir ve esnek ifadeler yazmanıza yardımcı
	 * olur. assertThat(5, equalTo(5)); // Beklenen değer 5, gerçek değer de 5. Test
	 * başarılı. gibi
	 */
	@Before
	public void setUp() {
		restTemplate = new RestTemplate();

        // Yeni BasicAuthenticationInterceptor kullanimi
        ClientHttpRequestInterceptor basicAuthInterceptor = new BasicAuthenticationInterceptor("user2", "user2");
        restTemplate.setInterceptors(Collections.singletonList(basicAuthInterceptor));

        //Eskisi
		//BasicAuthenticationInterceptor interceptor=new BasicAuthenticationInterceptor("admin", "admin1");
		//restTemplate.setInterceptors(Arrays.asList(interceptor));
        
        // RestTemplateBuilder ile temel kimlik doğrulama ekleme
		// Kullanıcı adı ve şifre application.properties de sunucuda sakliyor. 
		// Ben buraya test ederken aynı postmande yazidigm gibi Basic Auth ekliyorum.
        //restTemplateBuilder = new RestTemplateBuilder();
        //restTemplate = restTemplateBuilder
        //        .basicAuthentication("admin", "admin") 
        //        .build();
	}

	/**
	 * İlk önce springboot uygulamasını Run -> Spring Boot Application ile ayaga
	 * kaldırırız.Sonrasında Junit testi calistirmamız gerekmektedir. Bu classın
	 * ismine sag tık run -> junit ile calıstırıp testlerini yapılmaktadır. Altta
	 * yazdıgımız testlere göre kontroller yapılmaktadir. pom.xml e junit
	 * dependency'sini test yazmak icin ekledik diger hamcrest kütüphanesinide test
	 * kontrollerini yapmak icin ekledik.
	 * 
	 * Acilan JUnit Penceresi-> Failure Trace kısmından hatanın nedenini
	 * anlayabiliyoruz.
	 */
	@Test
	public void testGetOwnerById() {

		/* Basarili Durum : getOwner/1 den gelen FirstName degeri Alper oldugu icin */
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/getOwner/1",Owner.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200)); // gelen sonuc 200 response mu
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Alper")); // gelen degerin Alper olduguna bakıyoruz.

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
	
	/*---------------------------@Valid ornegi-----------------------------------*/
	
	@Test
	public void testServiceLevelValidation() {
		Owner owner = new Owner();
		/*
		@NotEmpty
		@Column(name="first_name")
		private String firstName;
		
		@NotEmpty
		@Column(name="last_name")
		private String lastName;
		
		@NotEmpty yapmistik bunlari 
		
		//owner.setLastName();
		//owner.setFirstName();
		
		bilerek bos birakiyorum.
		
		java -jar C:\DEV\PetClinic_Project\document\fakeSmtp\fakeSMTP-2.0.jar
		
		*/
		//Failed
		//owner.setFirstName(null);
		//owner.setLastName(null);
		
		//Success
		owner.setFirstName("Alper");
		owner.setLastName("Deneme");
		
		ResponseEntity<URI> responseForEntity = restTemplate.postForEntity("http://localhost:8080/rest/createOwner", owner, URI.class);
		MatcherAssert.assertThat(responseForEntity.getStatusCode(), Matchers.equalTo(HttpStatus.PRECONDITION_FAILED));//HttpStatus.PRECONDITION_FAILED bunun gelmesini bekliyorum.
		
		
	}
}
