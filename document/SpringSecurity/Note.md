Spring Security Nedir?
Spring Security, Java tabanlı uygulamalar için 
kimlik doğrulama (authentication) ve yetkilendirme (authorization) işlemlerini kolaylaştıran bir güvenlik çerçevesidir. 
Web uygulamalarını ve RESTful API'leri saldırılara karşı korumak için yaygın olarak kullanılır.

Spring Security'nin Özellikleri
*   Kimlik Doğrulama (Authentication):
        Kullanıcının kim olduğunu doğrulama.
        Örnek: Kullanıcı adı ve şifre kontrolü.
    
*   Yetkilendirme (Authorization):
        Kullanıcının hangi işlemleri yapabileceğini kontrol etme.
        Örnek: Sadece "admin" rolüne sahip kullanıcıların belirli sayfalara erişebilmesi.
    
*   Web Güvenliği:
        URL tabanlı erişim kontrolleri.
        CSRF (Cross-Site Request Forgery) koruması.
    
*   REST API Güvenliği:
        Token tabanlı kimlik doğrulama (JWT, OAuth2).
    
*   Şifreleme:
        Şifreleri güvenli bir şekilde saklamak için hashing algoritmaları (örneğin, BCrypt) sağlar.
        
Bir kullanıcı adı ve şifre ile korunan bir endpoint oluşturulacak.

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    Bunu ekleyince otomatik login sayfasına yonlendirdi.Herhangi bir config yapmadan
    
    Bu eklendikten sonra gelen tüm isteklerde auth olunması istenir.
        default olarak uygulama ayaga kalkarken console generate ettigi passwordü yazdırır
        ------Loglarda böyle bir pass yazdı
        Using generated security password: a5303675-48b3-4b3d-a223-cf39f2798dc0
        -----
        
        Default olarak birşey girilmesse
        username:user
        password:a5303675-48b3-4b3d-a223-cf39f2798dc0
        
        olarak rest isteginden önce login olunması beklenir.
        Ilk istekte direk login sayfasına yönlendirir.
     
        Ekran goruntusu ektedir.

spring-boot-starter-security bağımlılığı Spring Boot uygulamanıza eklendiğinde, 
Spring Security otomatik olarak temel kimlik doğrulama sağlar. 
Bu, Spring Security'nin varsayılan davranışıdır ve hemen hemen her Spring Boot 
projesinde güvenlik sağlamak için basit bir başlangıç oluşturur.

Neler Olur?
Varsayılan Login Sayfası:

Spring Security, eklediğinizde otomatik olarak bir giriş sayfası oluşturur.
Bu sayfa, kullanıcıdan kullanıcı adı ve şifre ister.
Varsayılan Kullanıcı:

Varsayılan olarak, Spring Security, basit kimlik doğrulama için user adında bir kullanıcı oluşturur. 
Şifre ise konsola yazdırılır.

Varsayılan Login Sayfası
Spring Security'nin varsayılan davranışı, herhangi bir özelleştirme yapılmadığı sürece, kullanıcıyı otomatik olarak login sayfasına yönlendirir. Bu, httpBasic() veya formLogin() gibi metodlar kullanılarak değiştirilmeden kalır.

Örnek Akış:
Uygulamanızı başlattığınızda, herhangi bir URL'yi ziyaret ettiğinizde (örneğin /admin gibi) güvenlik kontrolü devreye girer.
Otomatik olarak bir giriş formu görüntülenir.
Eğer doğru kullanıcı adı ve şifreyi girerseniz, sayfaya erişim sağlarsınız.

Özelleştirme:
Spring Security'nin sunduğu varsayılan login sayfası ve davranışları özelleştirmek mümkündür. Örneğin, kendi giriş sayfanızı tasarlayabilir veya 
HTTP Basic yerine form tabanlı kimlik doğrulama kullanabilirsiniz.

    @Configuration
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll() // İzin verilen sayfalar
                    .anyRequest().authenticated() // Diğer sayfalara kimlik doğrulama gerekir
                    .and()
                .formLogin()
                    .loginPage("/login") // Kendi giriş sayfanızı belirleyebilirsiniz
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
            return http.build();
        }
    }