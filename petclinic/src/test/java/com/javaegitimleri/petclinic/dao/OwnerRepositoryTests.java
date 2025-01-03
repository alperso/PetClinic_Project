package com.javaegitimleri.petclinic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.javaegitimleri.petclinic.model.Owner;

//@Transactional anotasyonunun etkisi nedeniyle eklediğiniz veriler veritabanına kalıcı olarak kaydedilmez. 
//Bunun nedeni, @Transactional anotasyonunun, her bir test metodunun başında bir işlem başlatıp, metodun sonunda işlemi rollback etmesidir.

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
public class OwnerRepositoryTests {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
//	@Autowired //autowired ile de enjecte edilebilir veya @PersistenceContext ile de enjecte edilebilir.
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void testCreateOwner() {
		//1.adim
//		Owner owner = new Owner();
//		owner.setFirstName(null);
//		owner.setLastName(null);
//		ownerRepository.createOwner(owner);
		
		//FirstName ve LastName Owner tablosunda not null yazmistik hata almaliydik. Sql insert sorgusu calismadi. Test Failed etmesi gerekirken etmedi.
		
		//2.adim cozum
		
		Owner owner = new Owner();
		owner.setFirstName(null);
		owner.setLastName(null);
		ownerRepository.createOwner(owner);
		
		entityManager.flush();
		//burada flush yazarsak database e yazdırır bunu yazmassak False positive durumundan dolayı hata alıp rollback yapacagı 
		//için basarılı alacaktı buda bizi yanlıs yönlendirecekti
		//inserti db ye yazdırmasını sagladi.
		
		//Alttaki sekilde hata aldik. FIRST_NAME alani not null olmali diye uyari aldik. Test failed verdi. Biz bunu bekliyorduk
		//burada flush yazarsak database e yazdırır bunu yazmassak False positive durumundan dolayı hata alıp rollback yapacagı 
		
//		Hibernate: call next value for petclinic_sequence
//		Hibernate: /* insert com.javaegitimleri.petclinic.model.Owner */ insert into t_owner (first_name, last_name, id) values (?, ?, ?)
//		2025-01-03 03:04:51.174  WARN 20560 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 23502, SQLState: 23502
//		2025-01-03 03:04:51.174 ERROR 20560 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : NULL not allowed for column "FIRST_NAME"; SQL statement:
//		/* insert com.javaegitimleri.petclinic.model.Owner */ insert into t_owner (first_name, last_name, id) values (?, ?, ?) [23502-200]
//		2025-01-03 03:04:51.189  INFO 20560 --- [           main] o.s.t.c.transaction.TransactionContext   : Rolled back transaction for test: [DefaultTestContext@6caf0677 testClass = OwnerRepositoryTests, testInstance = com.javaegitimleri.petclinic.dao.OwnerRepositoryTests@2a4cb8ae, testMethod = testCreateOwner@OwnerRepositoryTests, testException = javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException: could not execute statement, mergedContextConfiguration = [WebMergedContextConfiguration@413d1baf testClass = OwnerRepositoryTests, locations = '{}', classes = '{class com.javaegitimleri.petclinic.PetClinicApplication}', contextInitializerClasses = '[]', activeProfiles = '{dev}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@2893de87, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@12d3a4e9, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@258e2e41, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@5e4c8041], resourceBasePath = 'src/main/webapp', contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map['org.springframework.test.context.web.ServletTestExecutionListener.activateListener' -> true, 'org.springframework.test.context.web.ServletTestExecutionListener.populatedRequestContextHolder' -> true, 'org.springframework.test.context.web.ServletTestExecutionListener.resetRequestContextHolder' -> true]]

		//Eger bu hatayi alirsa başarılı olmasini ele almak istiyorsak
		//@Test(expected = PersistenceException.class) yazarak bu hatayı alırsa basarılı olarak ele almamızı sagladı
		//yazmaliyiz

	}
	
	@Test(expected = PersistenceException.class)
	public void testCreateOwner2() {
		
		Owner owner = new Owner();
		owner.setFirstName(null);
		owner.setLastName(null);
		ownerRepository.createOwner(owner);
		
		entityManager.flush();
		
	}
	
	@Test
	public void testCreateOwner3() {
		
		Owner owner = new Owner();
		owner.setFirstName("Alper");
		owner.setLastName("Mahmut");
		ownerRepository.createOwner(owner);
		
		System.out.println(owner.toString());
		
		//OwnerRepositoryTests classi icin @Transactional yaptigim icin testCreateOwner3 metodu icin Owner tablosuna kayit ekler mi ?
		//@Transactional anotasyonunun etkisi nedeniyle testCreateOwner3 metodunda eklediğiniz veriler veritabanına kalıcı olarak kaydedilmez. 
		//Bunun nedeni, @Transactional anotasyonunun, her bir test metodunun başında bir işlem başlatıp, metodun sonunda işlemi rollback etmesidir.
		
	}
}