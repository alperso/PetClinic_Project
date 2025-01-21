Spring Validation Nedir?
Spring Validation, Java uygulamalarında kullanıcıdan alınan verilerin doğruluğunu ve bütünlüğünü kontrol etmek için kullanılan bir mekanizmadır. Bu mekanizma, özellikle HTTP istekleriyle gelen veri girişlerini kontrol etmek ve hataları daha yönetilebilir bir şekilde ele almak için oldukça faydalıdır.

Spring Validation genellikle Java Bean Validation (JSR-380) standardını kullanır. Bu standart, veri doğrulama için çeşitli anotasyonlar sağlar ve doğrulama mantığını kolayca entegre etmenize olanak tanır. Spring, bu standardı kendi altyapısına dahil ederek doğrulama işlemlerini daha güçlü ve kullanışlı hale getirmiştir.

Spring Validation'ın Bileşenleri
Anotasyonlar: Spring Validation, Java Bean Validation'ın sağladığı doğrulama anotasyonlarını kullanır. Bu anotasyonlarla model sınıfındaki alanları doğrulama kurallarıyla işaretleyebilirsiniz.

Örnek doğrulama anotasyonları:

@NotNull: Alanın null olamayacağını belirtir.
@NotEmpty: String, Koleksiyon veya Dizi türündeki alanların boş olamayacağını belirtir.
@NotBlank: String türündeki alanların boş veya sadece boşluk karakterleri içermemesini sağlar.
@Size: Bir koleksiyonun, dizinin veya string'in boyutunu sınırlar (örneğin, minimum ve maksimum uzunluk).
@Min ve @Max: Bir sayısal değerin minimum ve maksimum değerlerini sınırlar.
@Pattern: Regex (düzenli ifade) ile belirli bir formata uygunluğu kontrol eder.
@Email: Geçerli bir e-posta adresi olup olmadığını kontrol eder.
@Valid Anotasyonu: Bu anotasyon, bir nesnenin doğrulama kurallarına uygun olup olmadığını kontrol etmek için kullanılır. Spring, otomatik olarak bu nesneyi doğrular ve bir hata durumunda bir istisna fırlatır.

Örnek:

@PostMapping("/addUser")
public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
    // Eğer user doğrulama kurallarına uymazsa bir hata fırlatılır.
    return ResponseEntity.ok("User added successfully!");
}
Global Exception Handling: Spring, doğrulama hatalarını yakalamak ve özelleştirilmiş hata mesajları döndürmek için global hata işleme (exception handling) mekanizması sağlar. @ControllerAdvice ve @ExceptionHandler anotasyonları bu iş için kullanılır.

Örnek:

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
BindingResult: Eğer doğrulama sırasında hata oluşursa, hatalar BindingResult nesnesine kaydedilir. Bu nesneyi kullanarak doğrulama hatalarını program içinde yönetebilirsiniz.

Örnek:

@PostMapping("/addUser")
public ResponseEntity<String> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return ResponseEntity.badRequest().body("Validation errors occurred!");
    }
    return ResponseEntity.ok("User added successfully!");
}
Spring Validation Nasıl Çalışır?
Model Sınıfında Doğrulama Kuralları Belirleme: Kullanıcı girişlerini temsil eden bir sınıf (DTO veya Entity) oluşturulur ve alanlarına doğrulama kuralları uygulanır.


public class User {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    // Getter ve Setter'lar
}
Doğrulama Anotasyonlarını Aktif Etme: Spring Boot projelerinde genellikle spring-boot-starter-validation bağımlılığı kullanılır. Bu bağımlılığı pom.xml dosyanıza ekleyerek doğrulama anotasyonlarını etkinleştirebilirsiniz.


<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
Doğrulama Yapılması: @Valid veya @Validated anotasyonu ile kontrol yapılır. Eğer doğrulama hataları varsa, Spring otomatik olarak hata mesajlarını döner.

Hata Yönetimi: Doğrulama hatalarını özelleştirmek için @ControllerAdvice ile özel bir hata yönetimi sınıfı yazılır.

Spring Validation Kullanımı Örneği
Controller:

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        // Doğrulama başarılıysa bu kısma ulaşılır
        return ResponseEntity.ok("User created successfully!");
    }
}
DTO (Model):

public class User {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    // Getter ve Setter'lar
}
Yanıt: Eğer name alanı boşsa veya email alanı yanlış formatta ise:

{
    "timestamp": "2025-01-21T19:19:11.770+0000",
    "status": 400,
    "errors": [
        {
            "field": "name",
            "message": "Name cannot be empty"
        },
        {
            "field": "email",
            "message": "Invalid email format"
        }
    ]
}
Spring Validation'ın Avantajları
Kolay Kullanım: Doğrulama anotasyonları, birkaç satır kodla güçlü doğrulama kuralları yazmayı sağlar.
Modülerlik: Doğrulama mantığı model sınıfına taşınır, bu da kodun temiz ve modüler olmasını sağlar.
Hata Yönetimi: Hataları özelleştirmek ve kullanıcıya anlamlı mesajlar göstermek kolaydır.
Java Standartlarına Uyumluluk: JSR-380 gibi standartları kullanır, böylece Java ekosisteminde yaygın olarak desteklenir.
Spring Validation, projelerde veri doğruluğunu garanti altına almak ve kullanıcıdan gelen hatalı verileri önlemek için kritik bir araçtır.