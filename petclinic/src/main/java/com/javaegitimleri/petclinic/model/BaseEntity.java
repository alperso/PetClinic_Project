package com.javaegitimleri.petclinic.model;


import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*
 * 
	Bu sınıfın abstract (soyut) yapılmasının sebebi, doğrudan bir entity olarak kullanılmaması ve yalnızca diğer entity sınıflarının temel bir 
	yapı taşı (base class) olarak tasarlanmış olmasıdır. 
	Bu sınıf, genel özellikleri ve davranışları sağlayarak diğer entity sınıfları için bir şablon oluşturur. 
	
	ÖNEMLİ BURAYI OKU ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Abstract Yapılmasaydı?
	Eğer abstract olmasaydı:
	
	BaseEntity doğrudan kullanılabilir hale gelirdi (örneğin BaseEntity türünden bir instance oluşturulabilir). Ancak bu mantıklı değildir, 
	çünkü bu sınıfın tek başına bir anlamı yoktur.
	Sınıfın doğrudan kullanılmaması gerektiğini belirtmek için yorumlar yazmanız gerekirdi, ancak bu açıkça anlaşılamayabilirdi.
	
	İşte detaylı açıklama:
	
	1. Doğrudan Kullanılmaması Amaçlanmıştır
	@MappedSuperclass ile işaretlenmiş sınıflar, veritabanında bir tabloya karşılık gelmezler. 
	Bunun yerine, bu sınıfı miras alan sınıflar, BaseEntity içindeki alanları kendi tablolarında kullanabilir. 
	Ancak, BaseEntity sınıfının kendisi bir tabloya eşlenmeyeceği için doğrudan bir entity olarak kullanılmaz. 
	Bu durumda sınıfın abstract yapılması, geliştiricilere bu sınıfın doğrudan kullanılmaması gerektiğini net bir şekilde ifade eder.
	
	2. Ortak Alan ve Davranışların Paylaşımı
	BaseEntity sınıfı, id, createdBy, createdDate, updatedBy, updatedDate gibi birçok sınıfta tekrar eden alanları içerir. 
	Bu sınıfı miras alan diğer entity'ler, bu alanları ve bunlara ait getter/setter gibi davranışları otomatik olarak kazanır. 
	Böylece kod tekrarını önler.
	
	3. Auditing Özelliklerini Barındırması
	Sınıf, Spring Data JPA'nın auditing mekanizmasını kullanıyor (@CreatedBy, @CreatedDate, @LastModifiedBy, @LastModifiedDate). Bu da şu anlama gelir:
	
	createdBy ve createdDate: Kaydı kimin ve ne zaman oluşturduğunu otomatik olarak doldurur.
	updatedBy ve updatedDate: Kaydı en son kimin ve ne zaman güncellediğini otomatik olarak doldurur.
	Bu özellikler, genellikle her entity'de bulunur ve bu yüzden ortak bir sınıfta toplanması mantıklıdır.
	
	4. Kalıtım Amaçlı Tasarlanması
	BaseEntity sınıfı abstract yapılarak, bu sınıfın sadece kalıtım yoluyla kullanılabileceği belirtilir. 
	Örneğin, bir Pet, Owner veya Visit sınıfı BaseEntity'yi miras alabilir ve bu ortak özelliklere sahip olur.
	
	@Entity
	public class Pet extends BaseEntity {
	    private String name;
	    private Date birthDate;
	    // Pet'e özgü diğer alanlar
	}
	
	5. Polimorfizm İçin Bir Temel Sınıf Sağlar
	Abstract sınıf olarak tasarlandığı için, daha sonra bu sınıfa ait tüm entity'ler üzerinde işlemler yapmanız kolaylaşır. 
	Örneğin, tüm BaseEntity türündeki entity'ler üzerinde ortak işlemler gerçekleştirebilirsiniz:
	
	BaseEntity entity = findEntityById(id);
	System.out.println(entity.getCreatedDate());
	
 * 
 * */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="petClinicSeqGen")
	@SequenceGenerator(name = "petClinicSeqGen", sequenceName = "petclinic_sequence", allocationSize = 1)
	private Long id;
	
	@CreatedBy
	private String createdBy;
	
	@CreatedDate
	private Date createdDate;
	
	@LastModifiedBy
	private String updatedBy;
	
	@LastModifiedDate
	private String updatedDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	
	

}
