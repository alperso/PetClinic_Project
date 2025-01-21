package com.javaegitimleri.petclinic.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.dao.jpa.VetRepository;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.exception.VetNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.model.Vet;


/*
 * @Service anotasyonu, Spring Framework'te bir sınıfı servis katmanı olarak işaretler.
 * Bu anotasyon, sınıfın iş mantığı taşıyan bir bean olduğunu belirtir ve Spring'in bu sınıfı otomatik olarak
 * Spring konteynerine eklemesini sağlar.
 * @Service ile işaretlenmiş sınıflar,
 * genellikle veritabanı işlemleri veya iş mantığı gibi uygulama işlevlerini yerine getirir.
 *
 * @Service, @Component anotasyonunun özel bir türüdür ve yalnızca anlam olarak iş mantığı katmanı olduğunu belirtir.
 *
 * */

/*@Transactional nedir ?
 * @Transactional, Spring Framework'de bir işlemi (transaction)
 * yönetmek için kullanılan bir anotasyondur.
 * Bir işlem birden fazla adım içerir (örneğin, birden fazla tabloya veri yazma).
 * Bu işlem sırasında bir hata olursa, işlem baştan sona geri alınır (rollback),
 * böylece veritabanı tutarsız bir duruma düşmez. Basarili ise commit demek
 * 
 * @Transactional class icine yazilirsa icindeki tum public metotlara uygulanir
 * metot icinyazilirsa sadece o metot icin islemler basarili(commit) basarisiz(rolback) yapilir.
 *
 * @Transactional(timeout = 5) // Eğer işlem 5 saniyeden uzun sürerse TimeoutException oluşur
 * */

@Validated
@Service
@Transactional
public class PetClinicServiceImpl implements PetClinicService {

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired 
	private VetRepository vetRepository;
	
	@Override
	@Secured(value = {"ROLE_USER","ROLE_EDITOR"})
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(id);
		if (owner==null) throw new OwnerNotFoundException("Owner Bulunamadı");
		return owner;

	}

	/*
	 * @CacheEvict(cacheNames="allOwners", allEntries=true)
	 * 
	 * allOwners adını cachelemiştik controllerde /owners de
	 * allEntries ile allOwners adındaki tüm değerleri hepsini cache den sildik.
	 * 
	 * CacheEvict anotasyonunu ekledikten sonra senaryoda 
	 * İlk tetikledigimiz http://localhost:8080/rest/owners sonrasında yeni bir sekmede 
	 * Burada yeni sekmede http://localhost:8080/owners/new çagırılarak yeni ekleme yaptıgımızda cache'i sildigi icin 
	 * method çalısacak ve db sorgusunda yeni eklenen kayıtlarla birlikte güncel liste gelecektir.
	 * */
	
	@Override
	@CacheEvict(cacheNames="allOwners", allEntries=true)
	public void createOwner(@Valid Owner owner) {
	   ownerRepository.createOwner(owner);
	   
	   try {
		   //Mail gondersin
		   SimpleMailMessage msg = new SimpleMailMessage();
		   msg.setFrom("k@s");
		   msg.setTo("m@y");
		   msg.setSubject("Owner created!");
		   msg.setText("Owner entity with id :"+ owner.getId() + " created succesfully.");
		   
		   javaMailSender.send(msg);
	   } catch(Exception e) {
		   //Bir sey yapma
	   }

	}
	
//	@Override
//	public void createOwner(Owner owner) {
//	   ownerRepository.createOwner(owner);
//	   
//	   try {
//		   //Mail gondersin
//		   SimpleMailMessage msg = new SimpleMailMessage();
//		   msg.setFrom("k@s");
//		   msg.setTo("m@y");
//		   msg.setSubject("Owner created!");
//		   msg.setText("Owner entity with id :"+ owner.getId() + " created succesfully.");
//		   
//		   javaMailSender.send(msg);
//	   } catch(Exception e) {
//		   //Bir sey yapma
//	   }
//
//	}
	
//	@Override
//	public void createOwner(Owner owner) {
//	   ownerRepository.createOwner(owner);
//	   
//	   //Mail gondersin
//	   SimpleMailMessage message = new SimpleMailMessage();
//      // message.setFrom("alper@gmail.com");  // Gönderen e-posta adresi yazilmaz ise application.properties den alinir
//       message.setTo("gonderilecek@gmail.com");  // Alıcı e-posta adresi
//       message.setSubject("Deneme");  // E-posta konusu
//       message.setText("Spring Boot Deneme");  // E-posta içeriği
//       
//       javaMailSender.send(message);  // E-posta gönderimi
//	}

	@Override
	public void updateOwner(Owner owner) {
		ownerRepository.updateOwner(owner);
	}

	@Override
	public void deleteOwner(Long id) {
		petRepository.deleteByOwnerId(id); //once child icindeki verileri sil
		ownerRepository.deleteOwner(id);

		//if(true) throw new RuntimeException("testing rollback"); //hata versin ki rollback yapsin
	}

	@Override
	public List<Vet> findVets() {
		return vetRepository.findAll();
	}

	@Override
	public Vet findVet(Long id) throws VetNotFoundException {
		return vetRepository.findById(id).orElseThrow(()->{ 
			return new VetNotFoundException("Vet not found by id:" + id); 
			});
	}

}
