1. RestTemplate
Nedir?
RestTemplate, Spring'in HTTP isteklerini kolayca yapmak için sunduğu bir sınıftır. 
GET, POST, PUT, DELETE gibi HTTP yöntemlerini destekler ve sunucudan gelen yanıtları 
POJO'lara (Plain Old Java Object) dönüştürür.

Özellikler:
Doğrudan kullanıma hazırdır.
Basit ve temel HTTP istekleri yapmak için uygundur.
Aşağıdaki gibi manuel olarak yapılandırılır:

RestTemplate restTemplate = new RestTemplate();
String response = restTemplate.getForObject("https://api.example.com/data", String.class);

Dezavantajlar:
Konfigürasyon ve özel ayarlamalar yapmak için daha fazla kod yazmanız gerekir.
Interceptor, Timeout, ErrorHandler gibi özellikleri ayarlamak manuel bir işlemdir.

2. RestTemplateBuilder
Nedir?
RestTemplateBuilder, RestTemplate için kolayca yapılandırma ve özelleştirme yapmanızı sağlayan bir yardımcı sınıftır. 
Spring Boot ile birlikte gelir ve daha temiz, yeniden kullanılabilir bir yapı sunar.

Özellikler:
Zincirleme Yapılandırma (Fluent API): Ayarları zincirleme olarak yapabilirsiniz.
Varsayılan Değerler: Spring Boot otomatik yapılandırmasıyla uyumlu çalışır.
Kolay Entegrasyon: Otomatik Bean olarak kullanılabilir ve Spring Context'te bağımlılık olarak enjekte edilebilir.

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    public RestTemplate createRestTemplate() {
        return restTemplateBuilder
                .rootUri("https://api.example.com")
                .basicAuthentication("user", "password")
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

Avantajlar:
Özelleştirmeleri daha kolay ve düzenli yapabilirsiniz.
Tekrar kullanılabilir bir yapı oluşturur.
Spring Boot'un otomatik yapılandırmalarından faydalanır.

Karşılaştırma Tablosu
Özellik	                    RestTemplate	                                    RestTemplateBuilder
Kullanım Kolaylığı	        Daha fazla manuel yapılandırma gerektirir.	        Fluent API sayesinde yapılandırması kolaydır.
Spring Boot Entegrasyonu	Spring Boot varsayılanlarıyla uyumlu değildir.	    Spring Boot ile sorunsuz çalışır.
Zincirleme Yapılandırma	    Zincirleme yapılandırma sunmaz.	                    Zincirleme yapılandırma sunar.
Yeniden Kullanılabilirlik	Doğrudan oluşturulan RestTemplate'ler bağımsızdır.	Tek bir RestTemplateBuilder üzerinden RestTemplate üretilebilir.
ErrorHandler Desteği	    Manuel olarak ayarlanır.	                        Fluent API ile kolayca yapılandırılır.
Bağımlılık Enjeksiyonu	    Genellikle doğrudan nesne oluşturulur.	            Spring'in @Bean veya @Autowired desteğiyle kullanılır.

Hangi Durumda Hangisi Kullanılır?
Basit HTTP İstekleri için:
Eğer çok fazla özelleştirme yapmadan doğrudan HTTP istekleri yapıyorsanız, RestTemplate kullanılabilir.

Daha Karmaşık Senaryolar için:

Örneğin:
Varsayılan başlıklar (headers) ayarlamak.
Kimlik doğrulama yapmak (Basic Auth, Bearer Token).
Zaman aşımı ve hata yönetimi ayarlamak.
Bu durumlarda RestTemplateBuilder kullanımı önerilir.
Örnek Kullanım
RestTemplate ile manuel yapılandırma:

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
    restTemplate.setInterceptors(List.of(new BasicAuthorizationInterceptor("admin", "password")));
    String response = restTemplate.getForObject("https://api.example.com/data", String.class);

RestTemplateBuilder ile yapılandırma:

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    public RestTemplate restTemplate() {
        return restTemplateBuilder
                .rootUri("https://api.example.com")
                .basicAuthentication("admin", "password")
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }