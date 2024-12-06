package com.javaegitimleri.petclinic.web;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;

public class PetClinicRestControllerTests {
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

}
