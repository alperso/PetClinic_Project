package com.javaegitimleri.petclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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

@Entity
@Table(name="t_owner")
@XmlRootElement
public class Owner extends BaseEntity {
	
	@NotEmpty
	@Column(name="first_name")
	private String firstName;
	
	@NotEmpty
	@Column(name="last_name")
	private String lastName;

	@OneToMany(mappedBy="owner")
	private Set<Pet> pets = new HashSet<>();
	
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
		return "Owner [id=" + getId() + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
