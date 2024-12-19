package com.javaegitimleri.petclinic.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.model.Owner;

@Repository("ownerRepository")
public class OwnerRepositoryJpaImpl implements OwnerRepository {
	/*
	@PersistenceContext Nedir?
	JPA (Java Persistence API) bağlamında EntityManager nesnelerini yönetmek için kullanılır.
	İşlevi: Bir EntityManager'i bir bean'e enjekte etmek için kullanılır. 
	Spring, arka planda bir EntityManager nesnesi oluşturur ve onu yönetir.
	*/
	@PersistenceContext
	private EntityManager entityManager;
	
	public OwnerRepositoryJpaImpl() {
	}

	@Override
	public List<Owner> findAll() {
		//Owner.class donus objem olsun diye yazdim
		return entityManager.createQuery("from Owner",Owner.class).getResultList();
	}

	@Override
	public Owner findById(Long id) {
		return entityManager.find(Owner.class,id);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		return entityManager.createQuery("frowm Owner where lastName= :lastname",Owner.class)
				.setParameter("lastname", lastName)
				.getResultList();
	}

	@Override
	public void createOwner(Owner owner) {
		entityManager.persist(owner);
		//persist:kalici hale getir demek

	}

	@Override
	public void deleteOwner(Long ownerId) {
		entityManager.remove(entityManager.getReference(Owner.class, ownerId));

	}

	@Override
	public Owner updateOwner(Owner owner) {
		return entityManager.merge(owner);
	}

}
