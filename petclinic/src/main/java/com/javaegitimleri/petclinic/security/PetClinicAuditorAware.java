package com.javaegitimleri.petclinic.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PetClinicAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return Optional.ofNullable(authentication !=null ? authentication.getName() : null);
	}
}


/*
	Bu sınıf, Spring Boot'un auditing (denetim) mekanizmasıyla birlikte çalışan bir yapı sunar.
	
	Ne İşe Yarıyor?
	PetClinicAuditorAware sınıfı, veritabanına bir kayıt eklenirken veya bir kayıt güncellenirken, bu işlemi kimin gerçekleştirdiğini 
	(yani "auditor" / denetçi bilgisini) otomatik olarak sağlar. 
	Bu bilgi, genellikle bir kullanıcının kimlik bilgilerini (örneğin, kullanıcı adını) içerir ve Spring Auditing mekanizması tarafından
	 otomatik olarak @CreatedBy ve @LastModifiedBy gibi alanlara kaydedilir.
	
	Kodun Detaylı Açıklaması
	AuditorAware Arayüzü
	
	AuditorAware arayüzü, Spring Data JPA tarafından sağlanır ve denetçi (auditor) bilgisini belirlemek için kullanılır.
	getCurrentAuditor() metodu, sistemde şu anda kimlik doğrulaması yapmış kullanıcının bilgilerini döner.
	getCurrentAuditor() Metodu
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	return Optional.ofNullable(authentication !=null ? authentication.getName() : null);
	SecurityContextHolder: Spring Security'nin kimlik doğrulama bağlamını sakladığı bir sınıftır. Şu anda oturum açmış kullanıcıya dair tüm bilgileri içerir.
	authentication.getName(): Oturum açmış kullanıcının kullanıcı adını döner.
	Optional.ofNullable: Kullanıcı doğrulanmamışsa null döner; bu da oturum açmamış kullanıcılar için denetim kaydı yapılmamasını sağlar.
	@Component Anotasyonu
	
	Sınıfı bir Spring Bean olarak işaretler ve otomatik olarak Spring konteynerine dahil eder.
	Spring Auditing mekanizması bu bean'i kullanarak denetçi bilgisini alır.
	Sınıfın Kullanımı
	Bu sınıf, Spring Data Auditing ile birlikte çalışır ve genellikle şu alanlarla birlikte kullanılır:
	
	@CreatedBy: Kaydı oluşturan kullanıcı.
	@LastModifiedBy: Kaydı son düzenleyen kullanıcı.
	Örneğin, bir BaseEntity sınıfında:
	
	@MappedSuperclass
	@EntityListeners(AuditingEntityListener.class)
	public abstract class BaseEntity {
	    
	    @CreatedBy
	    private String createdBy;
	    
	    @LastModifiedBy
	    private String updatedBy;
	
	    // Diğer alanlar ve getter/setter metodları
	}
	Bu alanlar, otomatik olarak PetClinicAuditorAware tarafından sağlanan denetçi bilgileriyle doldurulur.
	
	Örnek Çalışma Senaryosu
	Kullanıcı, bir REST API üzerinden oturum açar.
	Oturum açan kullanıcının bilgileri SecurityContextHolder içinde tutulur.
	Kullanıcı bir kayıt oluşturur veya günceller.
	Spring Auditing mekanizması devreye girer ve PetClinicAuditorAware sınıfını çağırarak oturum açmış kullanıcının adını alır.
	Bu kullanıcı bilgisi, veritabanına createdBy ve updatedBy alanları olarak kaydedilir.
	Örnek
	Diyelim ki oturum açan kullanıcı "admin" ve bir yeni kayıt oluşturuyor.
	
	createdBy alanı "admin" olarak doldurulur.
	Kaydı tekrar düzenlediğinde, updatedBy alanı "admin" olarak güncellenir.
	Faydalı Olduğu Yerler
	Loglama: Hangi kullanıcı hangi kaydı oluşturmuş veya değiştirmiş, bu bilgi otomatik olarak tutulur.
	Denetim Gereklilikleri: Özellikle finansal ya da yasal düzenlemelerin geçerli olduğu projelerde, kullanıcı aktivitelerinin izlenmesi gerekir.
	Otomasyon: Kullanıcı bilgilerini manuel olarak her defasında ayarlamaya gerek kalmadan denetim süreçleri otomatikleşir.
	
	
	Özet
	PetClinicAuditorAware, Spring Security ile entegre çalışarak şu anda oturum açmış kullanıcının bilgilerini alır.
	Bu bilgileri, Spring Auditing mekanizması aracılığıyla @CreatedBy ve @LastModifiedBy gibi alanlara otomatik olarak kaydeder.
	Projelerde denetim (auditing) süreçlerini kolaylaştırır.
 */