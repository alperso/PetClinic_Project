package com.javaegitimleri.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
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
@Service
public class PetClinicServiceImpl implements PetClinicService {

	@Autowired
	private OwnerRepository ownerRepository;

	@Override
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
		ownerRepository.deleteOwner(id);

	}

}
