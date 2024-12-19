package com.javaegitimleri.petclinic.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.model.Pet;

@Repository("petRepository")
public class PetRepositoryJpaImpl implements PetRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public PetRepositoryJpaImpl() {
	}

	@Override
	public Pet findById(Long id) {
		return entityManager.find(Pet.class,id);
	}

	@Override
	public List<Pet> findByOwnerId(Long ownerId) {
		return entityManager.createQuery("from Pet where owner.id=:ownerId",Pet.class)
				.setParameter("ownerId", ownerId)
				.getResultList();
	}

	@Override
	public void createPet(Pet owner) {
		entityManager.persist(owner);
	}

	@Override
	public Pet updatePet(Pet owner) {
		return entityManager.merge(owner);
	}

	@Override
	public void deletePet(Long ownerId) {
		entityManager.remove(entityManager.getReference(Pet.class, ownerId));

	}

	@Override
	public void deleteByOwnerId(Long ownerId) {
		entityManager.createQuery("delete from Pet where owner.id= :ownerId")
		.setParameter("ownerId", ownerId)
		.executeUpdate();

	}

}
