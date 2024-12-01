package com.javaegitimleri.petclinic.service;

import com.javaegitimleri.petclinic.PetClinicProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetClinicShowService {

    @Autowired
    private PetClinicProperties petClinicProperties;

    public void printProperties(){
        System.out.println("Display owners with pets: " + petClinicProperties.isDisplayOwnersWithPets());
    }
}
