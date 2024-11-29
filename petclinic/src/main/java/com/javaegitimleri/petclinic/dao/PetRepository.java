package com.javaegitimleri.petclinic.dao;

import java.util.List;

import com.javaegitimleri.petclinic.model.Pet;

public interface PetRepository {

	public Pet findById(Long id);
	public List<Pet> findByOwnerId(Long ownerId);
	public void createPet(Pet owner);
	public Pet updatePet(Pet owner);
	public void deletePet(Long ownerId);
	public void deleteByOwnerId(Long ownerId);

	
}
