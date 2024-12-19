JDBC (Java Database Connectivity)
    JDBC, Java'nın veritabanı ile iletişim kurmak için sunduğu bir API'dir.
    Java uygulamalarında SQL sorguları çalıştırmak, veri almak, güncellemek gibi işlemleri yapmak için kullanılır.
    JDBC, kodlama açısından düşük seviyede olduğundan, her işlem için tekrar tekrar kod yazmak gerekir (örneğin bağlantı açma, hata yönetimi).
    Amacı: Java uygulamaları ile veritabanları arasında doğrudan iletişim kurmak için düşük seviyeli bir API sağlar.
    Manuel İşlemler:
    SQL sorgularını elle yazmanız gerekir.
    Veritabanından dönen verileri manuel olarak map etmelisiniz (örneğin ResultSet nesnelerini Java nesnelerine dönüştürmek).

JDBC Template
    JdbcTemplate, Spring Framework tarafından sağlanan bir sınıftır. 
    JDBC ile yapılması gereken tekrar eden görevleri basitleştirir ve kodu daha temiz hale getirir.

	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
	</dependency>
	
Bu bağımlılığı projenize eklediğinizde Spring Boot size JDBC ile çalışmak için gerekli altyapıyı sağlar.
JdbcTemplate sınıfı kullanılabilir hale gelir. Bu sınıf JDBC kodlarını kolaylaştırır ve SQL işlemlerini sadeleştirir.

HikariCP Bağlantı Havuzu:
    Bu bağımlılıkla(spring-boot-starter-jdbc) birlikte Spring Boot, varsayılan olarak HikariCP bağlantı havuzunu kullanır. 
    HikariCP hızlı, hafif ve yüksek performanslı bir connection pool'dur.

Yapılandırma
    Bağlantıyı kullanabilmeniz için application.properties dosyanıza veritabanı bilgilerini eklemeniz gerekiyor:

    spring.datasource.url=jdbc:mysql://localhost:3306/db_name
    spring.datasource.username=root
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

Ek Notlar
    Eğer projenizde hem JDBC hem de JPA kullanmak isterseniz spring-boot-starter-jpa bağımlılığını da eklemeniz gerekir.
        
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jpa</artifactId>
    </dependency>

JPA'yı Kullanırken JDBC Kullanmalı mıyım?
    JPA İle Çalışırken Genelde JDBC Kullanılmaz: JPA, JDBC'nin üzerine inşa edilmiş bir soyutlama sağladığı için çoğu durumda 
    JPA ile çalışırken JDBC'ye doğrudan ihtiyaç duymazsınız. 
    Ancak, belirli durumlarda JPA'nın yetmediği düşük seviyeli işlemleri yapmak için JDBC kullanabilirsiniz.


JPA Nedir?
    Açılım: Java Persistence API.
    Amacı: Nesne-ilişkisel eşleme (ORM-Object to Relational Mapping) yapısını sağlar, yani veritabanı ile Java nesneleri arasında otomatik dönüşüm gerçekleştirir.
    Abstraction: SQL sorgularını ve veritabanı işlemlerini daha soyut bir seviyede yönetir.
    Azaltılmış Kod: Boilerplate kod miktarını azaltır ve veri tabanından gelen verileri Java nesnelerine otomatik olarak map eder.
    Kullanım Kolaylığı: Geliştirici SQL yerine HQL veya JPQL kullanabilir. Ayrıca birçok işlemi Spring Data JPA gibi araçlar otomatik hale getirir.

    Örnek Kod:
    @Entity
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        // Getter ve Setter metotları
    }
    
    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        List<User> findByName(String name); // SQL yazmanıza gerek yok!
    }
    
    Popüler ORM Araçları
    Java için:
    
    Hibernate
    EclipseLink
    JPA (Java Persistence API)
**************************************************************************