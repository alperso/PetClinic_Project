package com.javaegitimleri.petclinic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {

	@Autowired
	private PetClinicService petClinicService;

	@RequestMapping(method = RequestMethod.GET, value = "/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		List<Owner> findOwners = petClinicService.findOwners();
		return ResponseEntity.ok(findOwners);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/owners2")
	public List<Owner> getOwners2() {
		List<Owner> findOwners = petClinicService.findOwners();
		return findOwners;
	}
	
	/*
	 * ResponseEntity<List<Owner>> ve List<Owner> donus objelerinin arasindaki fark nedir?
	 * 
	 * Her iki yöntem de işlevsel olarak aynı veriyi döndürüyor gibi görünse de,
	 * ResponseEntity<List<Owner>> ve List<Owner> arasında önemli farklar vardır. Bu
	 * farkları açıklayalım:
	 * 
	 * -----ResponseEntity, 
	 * 
	 * HTTP yanıtı üzerinde daha fazla kontrol sağlar. HTTP durum
	 * kodunu, başlıkları ve gövdeyi özelleştirebilirsiniz.
	 * 
	 * return ResponseEntity .status(HttpStatus.CREATED) // Durum kodunu değiştirdik
	 * .header("Custom-Header", "PetClinicApp") // Özel bir başlık ekledik
	 * .body(findOwners);
	 * 
	 * -----List<Owner>
	 * Bu yöntem, sadece JSON olarak yanıt gövdesini döndürür.Spring, arka planda dönüşü JSON'a dönüştürür, 
	 * ancak HTTP yanıtını doğrudan kontrol etmezsiniz. Varsayılan olarak HttpStatus.OK (200) durumu döner.
	 * 
	 * Ne Zaman Hangisini Kullanmalıyız?
	 * 
	 * ResponseEntity: Yanıt başlıklarını, durum kodunu veya diğer meta bilgileri kontrol etmek istiyorsanız.
	 * List<Owner>: Basit bir JSON dönecekseniz ve yanıt üzerinde ekstra kontrol gerekmiyorsa.
	 * 
	 */

}
