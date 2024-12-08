package com.javaegitimleri.petclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
import java.util.Set;

//Owner kayıtlarının xml e donusturulmesi icin Jaxb - XMLRootElement Anotasyonunun aktif olması gerekir

//@XmlRootElement: Sınıfın XML'de bir kök eleman olarak kullanılmasını sağlar.
//<owner>
//	<id>1</id>
//	<firstName>John</firstName>
//	<lastName>Doe</lastName>
//</owner>

//@XmlTransient: Belirli bir alanın XML çıktısında görünmesini engeller.
//@JsonIgnore: Belirli bir alanın JSON çıktısında görünmesini engeller.

//Eğer Accept: application/json başlığı varsa → JSON döner.
//Eğer Accept: application/xml başlığı varsa → XML döner.
//Hiçbir şey belirtilmezse → Varsayılan (genelde JSON) döner.

@XmlRootElement
public class Owner {
	
	private Long id;
	private String firstName;
	private String lastName;

	private Set<Pet> pets = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//XML ve JSON Response a bu kısmı alma demek(donus kısmında bu gozukmeyecek)
	@XmlTransient
	@JsonIgnore
	public Set<Pet> getPets() {
		return pets;
	}
	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}
	
	@Override
	public String toString() {
		return "Owner [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
