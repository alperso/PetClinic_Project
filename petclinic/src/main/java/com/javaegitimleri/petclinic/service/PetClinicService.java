package com.javaegitimleri.petclinic.service;

import java.util.List;

import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.exception.VetNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.model.Vet;

public interface PetClinicService {
	
	public List<Owner> findOwners();
	public List<Owner> findOwners(String lastName);
	public Owner findOwner(Long id) throws OwnerNotFoundException;
	
	public void createOwner(Owner owner);
	public void updateOwner(Owner owner);
	public void deleteOwner(Long id);
	
	List<Vet> findVets();
	Vet findVet(Long id) throws VetNotFoundException;

}