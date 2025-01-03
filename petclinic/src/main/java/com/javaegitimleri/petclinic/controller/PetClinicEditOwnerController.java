package com.javaegitimleri.petclinic.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@Controller
public class PetClinicEditOwnerController {
	
	@Autowired
	private PetClinicService petClinicService;
	
	@RequestMapping(value="/owners/update/{id}",method=RequestMethod.GET)
	public String loadOwner(@PathVariable Long id, ModelMap modelMap) {
		Owner owner = petClinicService.findOwner(id);
		modelMap.put("owner", owner);
		return "editOwner";
	}
	
	@RequestMapping(value="/owners/update/{id}",method=RequestMethod.POST)
	public String handleFormSubmit(@ModelAttribute @Valid Owner owner, BindingResult validationErrorShow) {
		
		//Hata olursa (firstName ve LastName null olamazdı)
		if(validationErrorShow.hasErrors()) {
			return "editOwner";
		}
		
		petClinicService.updateOwner(owner);
		return "redirect:/owners";
		
	}
}