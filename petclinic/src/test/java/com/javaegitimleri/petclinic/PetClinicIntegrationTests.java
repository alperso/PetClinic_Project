package com.javaegitimleri.petclinic;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.model.Vet;
import com.javaegitimleri.petclinic.service.PetClinicService;


/*
 * @RunWith(SpringRunner.class) bir JUnit 4 anotasyonudur ve Spring test altyapısını JUnit testleriyle entegre etmek için kullanılır. 
 * Bu anotasyon sayesinde Spring'in test desteği etkinleştirilir ve Spring uygulama bağlamı (ApplicationContext) test sırasında yüklenir.
 * */
@RunWith(SpringRunner.class)
@SpringBootTest(properties= {"spring.profiles.active=dev"})
public class PetClinicIntegrationTests {
	
	@Autowired
	private PetClinicService petClinicService;

	
	@Test
	public void testFindOwners() {
		List<Owner> owners = petClinicService.findOwners();
		MatcherAssert.assertThat(owners.size(),Matchers.equalTo(10));
	}
	
	@Test
	public void testFindVets() {
		List<Vet> vets = petClinicService.findVets();
		MatcherAssert.assertThat(vets.size(),Matchers.equalTo(3));
		System.out.println("Tum vets sayisi:"+vets.size());
	}

}
/*
	Bu kod, bir Spring Boot uygulamasının entegrasyon testini gerçekleştirmek için yazılmıştır.
	Kodun her bir kısmını detaylı bir şekilde açıklayalım:

		Kodun Yapısı ve Anlamı
1. @RunWith(SpringRunner.class)
		Bu anotasyon, Spring'in JUnit 4 ile entegre edilmesini sağlar. Spring'in test desteği etkinleştirilir ve Spring uygulama bağlamı (ApplicationContext) test sırasında yüklenir.

2. @SpringBootTest(properties = {"spring.profiles.active=dev"})
		@SpringBootTest: Spring Boot'un entegrasyon testi için kullanılan anotasyondur. Tüm uygulama bağlamını (ApplicationContext) yükler.
		properties = {"spring.profiles.active=dev"}: Test sırasında dev adlı Spring profili etkinleştirilir.
		Bu, application-dev.properties gibi bir konfigürasyon dosyasının test sırasında kullanılması anlamına gelir.
		Örneğin, test ortamına özgü bir veritabanı veya özel bir yapılandırma olabilir.
3. PetClinicIntegrationTests
		Bu sınıf, PetClinic adlı bir Spring Boot uygulaması için entegrasyon testlerini içeren bir test sınıfıdır.

4. @Autowired
		Bu anotasyon, Spring tarafından yönetilen bir bileşeni (Bean) test sınıfına enjekte etmek için kullanılır.
		petClinicService: Test edilen servis katmanıdır. Bu servis, Owner (sahip) nesnelerini veritabanından almak gibi işlemleri içerir.

5. @Test
JUnit tarafından kullanılan bir anotasyondur. Bu, bir test metodunu belirtir.

6. testFindOwners Metodu
		Bu, PetClinicService'in findOwners metodunu test etmek için yazılmıştır:

		petClinicService.findOwners(): Bu metot, veritabanındaki tüm Owner (sahip) nesnelerini döndürür.
		owners.size(): Dönen listenin boyutunu alır.
		MatcherAssert.assertThat: JUnit'te bir test iddiasıdır.
		Matchers.equalTo(10): Listenin boyutunun 10 olması gerektiğini belirtir.
		Eğer boyut 10 değilse, test başarısız olur.
		Kodun Çalışma Akışı
		Test başlatılır.
		Spring uygulama bağlamı (ApplicationContext) yüklenir.
		dev profiline ait yapılandırma dosyası (ör. application-dev.properties) etkinleştirilir.
		petClinicService, Spring tarafından test sınıfına otomatik olarak enjekte edilir.
		testFindOwners metodu çalışır:
		findOwners metodu çağrılır.
		Dönen listenin boyutunun 10 olduğu doğrulanır.
		Eğer boyut 10 ise test başarılı olur; aksi takdirde test başarısızdır.
		Kodun Amaçları
		Servis katmanının doğru çalıştığını doğrulamak: PetClinicService.findOwners() metodunun beklendiği gibi çalıştığından emin olunur.
		Spring yapılandırmasını test etmek: dev profiliyle yapılan yapılandırmanın test ortamında doğru bir şekilde yüklendiğinden emin olunur.
		Entegrasyon testi: Servis katmanının, veri erişim katmanıyla (ör. bir repository) doğru bir şekilde iletişim kurduğunu doğrular.
		Örnek Durum
		Eğer Owner (sahip) nesneleri aşağıdaki gibi bir yapıdaysa:

		java
		Kodu kopyala
		public class Owner {
			private Long id;
			private String firstName;
			private String lastName;

			// Getters and Setters
		}
		ve veritabanında 10 adet Owner nesnesi varsa, bu test başarılı olacaktır.

		Testin Genişletilmesi
		Daha fazla senaryo için testler eklenebilir:
		Veritabanında hiç kayıt olmadığında.
		Kayıtların farklı filtreleme işlemlerine göre test edilmesi.
		Mock nesneler kullanılarak, veritabanına bağlı kalmadan testler yapılabilir. Örneğin:
		java
		Kodu kopyala
		@MockBean
		private PetClinicService petClinicService;
		Böylece, findOwners metodunun dönüş değerini simüle edebilirsiniz.
		Bu kod, Spring Boot entegrasyon testi için temel bir örnektir ve gerçek bir uygulamanın servis katmanını kapsamlı bir şekilde test etmek için kullanılır.

---junit ile ayrı bir test class olusturarak yaptıgımdan ne farki olacak?
		Bir JUnit test sınıfı oluşturmak ile doğrudan entegrasyon testi sınıfında çalışmak arasında bazı farklar vardır.
		Her bir yöntemin avantajları ve kullanım yerleri şunlardır:

1. Ayrı Bir Test Sınıfı Kullanmanın Avantajları
		Ayrı bir test sınıfı oluşturmak, testlerin daha izole, organize ve yönetilebilir olmasını sağlar. Örneğin:

		Farklar ve Avantajlar:
		a. Testlerin İzolasyonu
		Ayrı sınıf oluşturmak: Her test sınıfı yalnızca bir işlevselliği veya modülü test eder.
		Örneğin, bir sınıf yalnızca OwnerService ile ilgili testler içerirken, diğer bir sınıf PetService ile ilgilenebilir.
		Tüm testleri bir arada yazmak: Farklı modülleri veya katmanları test etmek birbiriyle karışabilir. Bu da kodun anlaşılabilirliğini azaltabilir.
		b. Kod Organizasyonu
		Ayrı test sınıfı: Her bir servis veya bileşen için bir test sınıfı oluşturduğunuzda, kodunuz daha düzenli hale gelir.
		Örneğin:
		java
		Kodu kopyala
		public class OwnerServiceTests {
			// Owner ile ilgili testler
		}

		public class PetServiceTests {
			// Pet ile ilgili testler
		}
		Tek sınıf kullanmak: Tüm testleri tek bir sınıfa koymak, test sınıfını büyütebilir ve yönetimi zorlaştırabilir.
		c. Test Performansı
		Ayrı sınıflar: Test sınıfları daha az bağımlı hale gelir ve sadece ilgili bileşenler yüklenir. Uygulama bağlamı her bir sınıf için gerektiği kadar yüklenir.
		Tek bir sınıf: Eğer bir sınıfta çok fazla bağımlılık varsa, uygulama bağlamı çok büyük hale gelebilir ve bu da test süresini artırabilir.
		d. Spring Profillerini Yönetmek
		Ayrı sınıflarda, her bir test için farklı @SpringBootTest profilleri veya yapılandırmaları tanımlayabilirsiniz:

		Örneğin:
		java

		@SpringBootTest(properties = {"spring.profiles.active=test"})
		public class OwnerServiceTests { }

		@SpringBootTest(properties = {"spring.profiles.active=dev"})
		public class PetServiceTests { }
		Bu sayede her test sınıfı, kendi özel yapılandırmasını kullanabilir.

		e. Testlerin Kolay Takibi
		Ayrı sınıflarda her bir test yöntemi daha kolay bulunabilir ve test sonuçları daha rahat analiz edilebilir.
		Örneğin, bir hata olduğunda OwnerServiceTests sınıfında olduğu doğrudan anlaşılır.
2. Tek Sınıf İçinde Çalışmanın Dezavantajları
		Karmaşıklık: Çok fazla test bir arada olduğunda kod karmaşıklaşabilir.
		Test Bağımlılığı: Testler arasında bağımlılık oluşabilir. Bir test diğer testlerin başarısına bağlı hale gelebilir.
		Bakım Zorluğu: Test sınıfı büyüdükçe yeni testler eklemek ve hataları ayıklamak zorlaşır.
3. Ne Zaman Tek Sınıf Kullanılabilir?
		Küçük ve basit bir projede.
		Tek bir bileşeni test ediyorsanız (örneğin, yalnızca OwnerService ile çalışıyorsanız).
		Uygulama bağlamını her test için yüklemenin maliyeti yüksekse.
4. Ne Zaman Ayrı Sınıf Kullanılmalı?
		Büyük veya karmaşık projelerde.
		Farklı modülleri test ediyorsanız.
		Test organizasyonu ve bakımını önemsiyorsanız.
Sonuç:
		Ayrı bir test sınıfı kullanmak daha iyi bir organizasyon ve test yönetimi sağlar. Ancak, küçük ve basit projelerde tek bir sınıfla çalışmak yeterli olabilir. Büyük projelerde veya farklı modüller test ediliyorsa,
		her modül için ayrı bir test sınıfı oluşturmak en iyi uygulamadır.
*/