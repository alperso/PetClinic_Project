package com.javaegitimleri.petclinic.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaegitimleri.petclinic.exception.InternalServerException;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {

	@Autowired
	private PetClinicService petClinicService;

	@Cacheable("allOwners")
	@RequestMapping(method = RequestMethod.GET, value = "/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		System.out.println(">>>inside getOwners....");
		List<Owner> findOwners = petClinicService.findOwners();
		return ResponseEntity.ok(findOwners);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/owners2")
	public List<Owner> getOwners2() {
		List<Owner> findOwners = petClinicService.findOwners();
		return findOwners;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/owners3")
	public ResponseEntity<List<Owner>> getOwners3(){
	    List<Owner> findOwners = petClinicService.findOwners();
	    return ResponseEntity
	        .status(HttpStatus.CREATED) // Durum kodunu değiştirdik
	        .header("Custom-Header", "PetClinicApp") // Özel bir başlık ekledik
	        .body(findOwners);
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
	
	
	/*-------------------------------------------------------------*/
	
	//http://localhost:8080/rest/getOwnersWithLastName?ln=ONER
	
	@RequestMapping(method=RequestMethod.GET,value = "/getOwnersWithLastName")
	public ResponseEntity<List<Owner>> getOwnersWithLastName(@RequestParam("ln") String lastName){
		
		List<Owner> findOwners = petClinicService.findOwners(lastName);
		return ResponseEntity.ok(findOwners);
		
	}
	
	//http://localhost:8080/rest/getOwner/1
	
	@RequestMapping(method=RequestMethod.GET,value="/getOwner/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
		try {
			Owner owner=petClinicService.findOwner(id);
			return ResponseEntity.ok(owner);
		} catch (OwnerNotFoundException ex){
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500 Hata Verdi Aslan");
		}
		
		//notFound(): Yanıtın durum kodunu 404 Not Found olarak ayarlıyor.
		//build(): Yanıtın gövdesiz (boş) olarak dönmesini sağlıyor.
	}
	
	/*-----------------------------createOwner--------------------------------*/
	
	/*
	 * @RequestBody Owner owner:
	 * İstek gövdesindeki (HTTP Request Body) JSON formatında bir Owner nesnesini alır 
	 * ve bunu otomatik olarak bir Owner Java nesnesine dönüştürür.
	 *
	 * */
	
	@RequestMapping(method=RequestMethod.POST,value="/createOwner")
	public ResponseEntity<URI> createOwner(@RequestBody Owner owner){
		try {
			
			petClinicService.createOwner(owner);
			Long id = owner.getId();
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).build();
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*-----------------------------updateOwner--------------------------------*/

	@RequestMapping(method=RequestMethod.PUT,value="/updateOwner/{id}")
	public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner ownerRequest){
		
		try {
			Owner owner = petClinicService.findOwner(id); //once guncellenecek owneri sorguluyorum eger yoksa catch icine dusecek
			owner.setFirstName(ownerRequest.getFirstName());
			owner.setLastName(ownerRequest.getLastName());
			petClinicService.updateOwner(owner);
			
			return ResponseEntity.ok().build();
		} catch(OwnerNotFoundException ex) {
			//bulamaz ise
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	/*-----------------------------deleteOwner--------------------------------*/
	@RequestMapping(method=RequestMethod.DELETE,value="/deleteOwner/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id) {
		try {
			petClinicService.deleteOwner(id);
			
			return ResponseEntity.ok().build();
		} catch(OwnerNotFoundException ex) {
			//bulamaz ise
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/*-----------------------------deleteOwner With ResponseStatus--------------*/
	@RequestMapping(method=RequestMethod.DELETE,value="/deleteOwner2/{id}")
	public ResponseEntity<?> deleteOwner2(@PathVariable("id") Long id) {
		try {
			petClinicService.deleteOwner(id);
			
			return ResponseEntity.ok().build();
		} catch(OwnerNotFoundException ex) {
			//bulamaz ise
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerException(ex);
		}
	}


	/*-------------HATEOAS(Service API'nin işleyişini ve sonraki adımları ogrenmek)----------------*/
	/*
	HATEOAS (Hypermedia As The Engine Of Application State), RESTful API'ler için bir tasarım prensibidir.
	Bu yaklaşım, istemcilere bir kaynağın durumunu ve o kaynakla yapılabilecek işlemleri hypermedia (bağlantılar) aracılığıyla sağlar.
	Yani, bir API yanıtında, sadece veriyi değil, aynı zamanda o veriye ilişkin hangi işlemlerin yapılabileceğini belirten bağlantılar da
	yer alır.

	İstemciler, sunucudan dönen bağlantılar (links) sayesinde API'nin işleyişini ve sonraki adımları öğrenir.
	İstemcilerin API'nin iç yapısını bilmesine gerek kalmaz, çünkü hangi işlemleri yapabileceğini hypermedia bağlantıları belirtir.

	Spring Boot'ta, HATEOAS desteği, spring-boot-starter-hateoas kütüphanesi kullanılarak (pom.xml) kolayca sağlanır.
	Spring HATEOAS, hypermedia ile çalışan REST API'ler geliştirmek için yardımcı araçlar sunar.
	*/

	//produces:HTTP isteği için sunucunun hangi formatta yanıt vereceğini belirtir.
	//@RequestMapping(method = RequestMethod.GET, value = "/owners", produces = {"application/json", "application/xml"})
	@RequestMapping(method=RequestMethod.GET,value="/getOwnerAsHateoasResource/{id}",produces = "application/json")
	public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id) {
		try {
			Owner owner=petClinicService.findOwner(id);

			// HATEOAS bağlantısı oluşturuluyor
			Link self = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/getOwnerAsHateoasResource/"+id).withSelfRel();
			Link createOwner = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/createOwner/").withRel("createOwner");
			Link deleteOwner = ControllerLinkBuilder.linkTo(PetClinicRestController.class).slash("/deleteOwner/"+id).withRel("deleteOwner");
			Link updateOwner = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PetClinicRestController.class).updateOwner(id, owner)).withRel("updateOwner");

			// EntityModel ile Owner nesnesini sarıyoruz ve bağlantıları ekliyoruz
			EntityModel<Owner> resource = new EntityModel<>(owner);
			resource.add(self);
			resource.add(createOwner);
			resource.add(deleteOwner);
			resource.add(updateOwner);

			return ResponseEntity.ok(resource);
		} catch (OwnerNotFoundException ex){
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}


		/*
		*  Request:http://localhost:8080/rest/getOwnerAsHateoasResource/1
		*  Response:
		* {
		  "id": 1,
		  "firstName": "Alper",
		  "lastName": "ONER",
		  "_links": {
			"self": {
			  "href": "http://localhost:8080/rest/getOwnerAsHateoasResource/1"
			},
			"createOwner": {
			  "href": "http://localhost:8080/rest/createOwner"
			},
			"deleteOwner": {
			  "href": "http://localhost:8080/rest/deleteOwner/1"
			},
			"updateOwner": {
			  "href": "http://localhost:8080/rest/updateOwner/1"
			}
		  }
		}*/
	}

}
