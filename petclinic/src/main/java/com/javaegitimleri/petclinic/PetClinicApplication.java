package com.javaegitimleri.petclinic;

import com.javaegitimleri.petclinic.model.Pet;
import com.javaegitimleri.petclinic.service.PetClinicShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
/*
@EnableConfigurationProperties kullanarak
PetClinicProperties sınıfından bir bir bean olusturacak
Properties dosyasıyla erişip değerleri alabilecegiz

@ServletComponentScan
Servletleri scan etmesi icin kullanilir.
claspath de servlet ve Filterlern olan sınıfları otomatik tespit eder.

*/
@ServletComponentScan
@SpringBootApplication
@EnableConfigurationProperties(value=PetClinicProperties.class)
@EnableJpaAuditing(auditorAwareRef = "petClinicAuditorAware")
@EnableCaching
public class PetClinicApplication {

	@Autowired
	private PetClinicShowService petClinicShowService;

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
		System.out.println("Hello Alper");


		/*
		* PetClinicShowService kullanarak içindeki application.properties den aldıgım veriyi burada bastırmak istedim fakat
		* enjekte etmek için
		*
		*     @PostConstruct
    	*	   public void runAfterStartup() {
        *			petClinicShowService.printProperties();
    	*	   }
    	*
    	* Spring, uygulama başlatılırken @PostConstruct anotasyonuna sahip metotları otomatik olarak çağırır.
    	* Bu sayede uygulama başlatıldıktan sonra AnotherService nesnesi kullanılabilir.
    	*
    	* veya
    	*
    	* ApplicationContext ile yapmam lazım.
		*
		* PetClinicShowService'i doğrudan main metodu içinde kullanabilirsiniz.
		* Ancak, Spring konteyneri tarafından yönetilen bir Bean olan petClinicShowService'i
		* kullanmak için Spring'in ApplicationContext'ini almanız gerekir. Spring Boot uygulamalarında main metodu statik
		* olduğu için doğrudan Bean injection yapılamaz. Bu nedenle, ApplicationContext'ten ilgili Bean alınır.
		*
		* */

	}

	@PostConstruct
	public void runAfterStartup() {
		petClinicShowService.printProperties();
	}

}