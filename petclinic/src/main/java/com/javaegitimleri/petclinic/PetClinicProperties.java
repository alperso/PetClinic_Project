package com.javaegitimleri.petclinic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

//properties dosyasında petclinic ile baslayan tanımlamanın bu sınıfla eşleşeceğini bildirmek icin petclinic yazdık
@ConfigurationProperties(prefix="petclinic")
public class PetClinicProperties {

    //Eğer sadece bir veya birkaç application.properties ayarını okumak istiyorsanız, @Value anotasyonunu kullanabilirsiniz.
    //@Value("${petclinic.display-owners-with-pets}")
    //private boolean displayOwnersWithPets;

    private boolean displayOwnersWithPets = false;

    public boolean isDisplayOwnersWithPets() {
        return displayOwnersWithPets;
    }

    public void setDisplayOwnersWithPets(boolean displayOwnersWithPets) {
        this.displayOwnersWithPets = displayOwnersWithPets;
    }
}
