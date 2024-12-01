package com.javaegitimleri.petclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/*
* @Configuration
* Uygulamanızın çalışma zamanında kullanılacak nesneleri tanımlamak ve yapılandırmak için kullanılır.
*
* Konfigürasyon sınıflarında, veri tabanı ayarları, güvenlik ayarları,
* mesajlaşma servisleri, cache ayarları gibi uygulamaya özgü yapılandırmalar tanımlanabilir.
*
* */
@Configuration
public class PetClinicConfiguration {

    @Autowired
    private PetClinicProperties petClinicProperties;

    /*
    *   @PostConstruct
    * PetClinicConfiguration sınıfından bir bean yaratılıp
    * beanın bağımlılıkları enjekte edildikten sonra
    * spring container tarafından otomatik olarak invoke edilecektir.
    *
    * Spring konteyneri PetClinicApplication nesnesini oluşturup
    * bağımlılıkları enjekte ettikten hemen sonra çalıştırılır.
    * */
    @PostConstruct
    public void init (){
        System.out.println("application.properties dosyasından veriyi aldım. Display owners with pets:"+ petClinicProperties.isDisplayOwnersWithPets());
    }

}
