Spring Auditing Nedir?
Spring Data Auditing, uygulamadaki entity'ler üzerinde belirli işlemleri (örneğin, bir kaydın kim tarafından oluşturulduğu veya güncellendiği gibi) otomatik olarak izlemek ve bu bilgileri kaydetmek için kullanılan bir özelliktir. Özellikle veritabanı işlemlerinde, kayıtların oluşturulma ve güncellenme süreçlerini takip etmek için kullanılır.

Spring Auditing'in Sağladığı Avantajlar
Otomasyon: createdBy, createdDate, lastModifiedBy, lastModifiedDate gibi alanlar otomatik olarak doldurulur. Elle doldurmanız gerekmez.
Tarihçe (Audit Log): Kayıtların oluşturulma ve değiştirilme süreçlerini izlemek için bir altyapı sağlar.
Standartlaşma: Entity'ler üzerinde aynı türde bilgiler tutulur ve tüm audit işlemleri tek bir yapı altında yönetilir.
Bakım Kolaylığı: Auditing işlemlerini manuel olarak yazmaktan kurtulursunuz.
Spring Auditing ile İzlenen Alanlar
Spring Auditing genellikle şu bilgileri izlemek için kullanılır:

createdBy: Kaydı oluşturan kullanıcı.
createdDate: Kaydın oluşturulma zamanı.
lastModifiedBy: Kaydı en son güncelleyen kullanıcı.
lastModifiedDate: Kaydın en son güncellenme zamanı.
Bu alanlar genellikle entity sınıfında tanımlanır ve Spring Auditing mekanizması tarafından doldurulur.

Nasıl Çalışır?
1. EntityListener Kullanımı
Auditing işlemleri, Spring'in sağladığı bir EntityListener mekanizması ile çalışır. Bu listener, veritabanı işlemleri sırasında (örneğin, bir kayıt oluşturulduğunda veya güncellendiğinde) tetiklenir ve ilgili alanları doldurur.

Spring Auditing'in Kullanımı
1. Auditing'i Etkinleştirme
Spring Auditing özelliğini etkinleştirmek için uygulamanızda @EnableJpaAuditing anotasyonunu ekleyin. Genellikle bu, @SpringBootApplication ile aynı yerde tanımlanır.

java
Kopyala
Düzenle
@Configuration
@EnableJpaAuditing
public class AuditConfig {
}
2. Entity Sınıfında Audit Alanlarını Tanımlama
Entity sınıfınızda audit bilgilerini tutmak için alanlar oluşturun ve bunları Spring'in @CreatedBy, @CreatedDate, @LastModifiedBy ve @LastModifiedDate anotasyonları ile işaretleyin.

java
Kopyala
Düzenle
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Date lastModifiedDate;

    // Getter ve Setter metotları
}
3. AuditingListener ve Principal Ayarları
Auditing işlemlerinde @CreatedBy ve @LastModifiedBy gibi alanlar için oturumdaki kullanıcının bilgisi gereklidir. Bunu sağlamak için Spring Security ile entegrasyon yapılır.

Auditing işlemi sırasında hangi kullanıcı bilgisinin alınacağını belirlemek için bir AuditorAware bean tanımlayın:

java
Kopyala
Düzenle
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Oturumdaki kullanıcı bilgilerini döndürün. Örnek:
        return Optional.of("currentUser"); // Spring Security ile entegrasyon varsa, oturumdaki kullanıcıyı alın.
    }
}
Bu yapı, audit bilgileri doldurulurken Spring tarafından çağrılır.

4. Database'de Audit Alanlarının Otomatik Doldurulması
Auditing aktif hale getirildiğinde, ilgili alanlar (örneğin, createdBy ve createdDate) otomatik olarak doldurulur. Bir kayıt eklendiğinde createdDate, güncellendiğinde ise lastModifiedDate alanı otomatik olarak set edilir.

Örnek Senaryo
Bir "Pet" entity'si üzerinde Spring Auditing'i uyguladığınızda, aşağıdaki gibi çalışır:

Yeni bir kayıt oluşturulduğunda:
createdBy: "admin"
createdDate: 2025-01-20 12:34:56
Kayıt güncellendiğinde:
lastModifiedBy: "user123"
lastModifiedDate: 2025-01-21 09:20:00
Özet
Spring Auditing, entity'lerin oluşturulma ve güncellenme süreçlerini izlemek için kullanışlı bir araçtır. Auditing özelliğiyle birlikte:

Otomasyon sağlanır.
Kodunuzu daha temiz ve standart bir yapıda tutabilirsiniz.
Audit işlemlerini manuel olarak yapmaktan kurtulursunuz.
Spring Security ile entegre edilirse, oturum açmış kullanıcıların bilgileri de otomatik olarak kaydedilir.