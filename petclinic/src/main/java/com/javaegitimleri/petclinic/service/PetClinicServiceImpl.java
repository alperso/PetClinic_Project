package com.javaegitimleri.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;


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

@Service
@Transactional
public class PetClinicServiceImpl implements PetClinicService {

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private PetRepository petRepository;
	
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

	@Override
	public void createOwner(Owner owner) {
	   ownerRepository.createOwner(owner);
	}

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

}
