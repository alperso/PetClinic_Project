package com.javaegitimleri.petclinic.dao.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.model.Pet;

@Repository
public class PetRepositoryJdbcImpl implements PetRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PetRepositoryJdbcImpl() {
	}

	@Override
	public Pet findById(Long id) {
		return null;
	}

	@Override
	public List<Pet> findByOwnerId(Long ownerId) {
		return null;
	}

	@Override
	public void createPet(Pet owner) {

	}

	@Override
	public Pet updatePet(Pet owner) {
		return null;
	}

	@Override
	public void deletePet(Long ownerId) {

	}

	@Override
	public void deleteByOwnerId(Long ownerId) {
		String sql = "delete from t_pet where owner_id = ?";
		jdbcTemplate.update(sql,ownerId);
	}

}