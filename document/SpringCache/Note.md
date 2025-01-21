Spring Cache nedir ? Ne yapılır ?
Spring Cache, Spring Framework'ün sunduğu bir özellik olup, uygulamalarda verilerin daha 
hızlı erişimini sağlamak için cache (önbellek) mekanizmasını kolayca kullanmanıza olanak tanır. 
Önbellekleme, sık erişilen veya hesaplanması pahalı olan verileri bir süreliğine bellekte tutarak, 
sonraki erişimlerde bu verileri hızlı bir şekilde kullanmayı sağlar. 
Bu da performansı artırır ve sistem üzerindeki yükü azaltır.

Spring Cache'in Temel İşlevleri:
Performansı Artırma: Önbelleğe alınmış verilere erişim çok hızlıdır, 
bu da veritabanı sorgularını, ağır işleme gerektiren işlemleri veya uzaktan çağrıları azaltır.
Maliyeti Azaltma: Tekrar tekrar hesaplanması pahalı olan işlemler, önbellekte saklanarak işlem maliyetleri düşürülür.
Kullanımı Kolaylaştırma: Spring Cache ile kodunuzu fazla değiştirmeden mevcut projelere önbellekleme özelliği ekleyebilirsiniz.

Spring Cache Nasıl Çalışır?
Spring Cache, bir cache abstraction (önbellek soyutlama katmanı) sağlar. Bu soyutlama, cache işlemlerini 
Spring tabanlı bir yapı ile kolayca entegre eder. 
Uygulamada, Spring'in önbellekleme özelliklerini kullanmak için sadece birkaç basit adım gereklidir.

Veri Cachelenir (Önbelleğe Alınır): Cache'e alınacak veriler, bir metot tarafından hesaplanır ve sonuç önbellekte saklanır.
Önbellekten Veri Okuma: Eğer aynı işlem tekrar yapılmak istenirse, Spring önce önbellekte bu verinin olup olmadığını kontrol eder. Varsa, önbellekten döner; yoksa metodu çalıştırır ve sonuçları önbelleğe alır.

Spring Cache Kullanımı
Spring Cache, önbellekleme işlemleri için anotasyonlar kullanır. Örneğin:

@EnableCaching: Önbellekleme özelliğini etkinleştirir.
@Cacheable: Bir metodun sonucunu önbelleğe almak için kullanılır.
@CachePut: Metot sonucunu önbellekte günceller.
@CacheEvict: Önbellekten veri temizlemek için kullanılır.
@Caching: Birden fazla cache işlemini bir arada yapmak için kullanılır.

Örnek:
    -->pom.xml
    <!-- Spring Cache-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    
    -->PetClinicApplication.java
    
    @ServletComponentScan
    @SpringBootApplication
    @EnableConfigurationProperties(value=PetClinicProperties.class)
    @EnableJpaAuditing(auditorAwareRef = "petClinicAuditorAware")
    @EnableCaching
    public class PetClinicApplication {
    
    	@Autowired
    	private PetClinicShowService petClinicShowService;
    
    	public static void main(String[] args) {
    		SpringApplication.run(PetClinicApplication.class, args);
    		System.out.println("Hello Alper");
    		}
    }
    
    -->PetClinicRestController
    @Configuration
    @EnableCaching // Caching özelliğini aktif eder
    public class CacheConfig {
        // Cache yapılandırmaları (isteğe bağlı olarak özelleştirilebilir)
    }

    @Service
    public class UserService {
    
        @Cacheable("users") // Sonuç "users" adlı cache'e kaydedilir
        public User getUserById(Long id) {
            // Veritabanından veya başka bir kaynaktan veri çekilir
            return userRepository.findById(id).orElse(null);
        }
    
        @CacheEvict(value = "users", key = "#id") // Belirli bir cache'i temizler
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }
    }
Spring Cache'in Desteklediği Cache Çözümleri
Spring Cache, farklı cache sağlayıcılarıyla entegre çalışabilir. Bunlardan bazıları:

ConcurrentMapCache (Java’nın ConcurrentMap sınıfını kullanır, temel bir cache sağlar)
Ehcache
Hazelcast
Redis
Caffeine
Infinispan
Guava Cache


Spring Cache, Spring Framework'ün sunduğu bir özellik olup, uygulamalarda verilerin daha hızlı erişimini sağlamak için cache (önbellek) mekanizmasını kolayca kullanmanıza olanak tanır. Önbellekleme, sık erişilen veya hesaplanması pahalı olan verileri bir süreliğine bellekte tutarak, sonraki erişimlerde bu verileri hızlı bir şekilde kullanmayı sağlar. Bu da performansı artırır ve sistem üzerindeki yükü azaltır.

Spring Cache'in Temel İşlevleri:
Performansı Artırma: Önbelleğe alınmış verilere erişim çok hızlıdır, bu da veritabanı sorgularını, ağır işleme gerektiren işlemleri veya uzaktan çağrıları azaltır.
Maliyeti Azaltma: Tekrar tekrar hesaplanması pahalı olan işlemler, önbellekte saklanarak işlem maliyetleri düşürülür.
Kullanımı Kolaylaştırma: Spring Cache ile kodunuzu fazla değiştirmeden mevcut projelere önbellekleme özelliği ekleyebilirsiniz.
Spring Cache Nasıl Çalışır?
Spring Cache, bir cache abstraction (önbellek soyutlama katmanı) sağlar. Bu soyutlama, cache işlemlerini Spring tabanlı bir yapı ile kolayca entegre eder. Uygulamada, Spring'in önbellekleme özelliklerini kullanmak için sadece birkaç basit adım gereklidir.

Veri Cachelenir (Önbelleğe Alınır): Cache'e alınacak veriler, bir metot tarafından hesaplanır ve sonuç önbellekte saklanır.
Önbellekten Veri Okuma: Eğer aynı işlem tekrar yapılmak istenirse, Spring önce önbellekte bu verinin olup olmadığını kontrol eder. Varsa, önbellekten döner; yoksa metodu çalıştırır ve sonuçları önbelleğe alır.
Spring Cache Kullanımı
Spring Cache, önbellekleme işlemleri için anotasyonlar kullanır. Örneğin:

@EnableCaching: Önbellekleme özelliğini etkinleştirir.
@Cacheable: Bir metodun sonucunu önbelleğe almak için kullanılır.
@CachePut: Metot sonucunu önbellekte günceller.
@CacheEvict: Önbellekten veri temizlemek için kullanılır.
@Caching: Birden fazla cache işlemini bir arada yapmak için kullanılır.
Örnek:
java
Kopyala
Düzenle
@Configuration
@EnableCaching // Caching özelliğini aktif eder
public class CacheConfig {
    // Cache yapılandırmaları (isteğe bağlı olarak özelleştirilebilir)
}
java
Kopyala
Düzenle
@Service
public class UserService {

    @Cacheable("users") // Sonuç "users" adlı cache'e kaydedilir
    public User getUserById(Long id) {
        // Veritabanından veya başka bir kaynaktan veri çekilir
        return userRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "users", key = "#id") // Belirli bir cache'i temizler
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
Spring Cache'in Desteklediği Cache Çözümleri
Spring Cache, farklı cache sağlayıcılarıyla entegre çalışabilir. Bunlardan bazıları:

ConcurrentMapCache (Java’nın ConcurrentMap sınıfını kullanır, temel bir cache sağlar)
Ehcache
Hazelcast
Redis
Caffeine
Infinispan
Guava Cache

Ne Zaman Spring Cache Kullanılmalı?
Veritabanı sorguları çok sık tekrarlandığında.
Uzun süren ve yoğun işlem gerektiren işlemleri hızlandırmak gerektiğinde.
Dış servislerden veri çekmenin yavaş olduğu durumlarda.
Çıktının sık değişmediği ve geçerliliğini koruduğu durumlarda.
Spring Cache, özellikle yüksek performans gereksinimi olan uygulamalarda oldukça faydalıdır ve uygulamaların ölçeklenebilirliğini artırabilir.

Örnek:
Controller kısmına

    @Cacheable("allOwners")
	@RequestMapping(method = RequestMethod.GET, value = "/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		System.out.println(">>>inside getOwners....");
		List<Owner> findOwners = petClinicService.findOwners();
		return ResponseEntity.ok(findOwners);
	}
böyle bir metot yazdık. İlk istek de buraya düşüyor. Fakat 2. istek de Owners guncellense bile buraya düşmüyor Neden?

/*@Cacheable("allOwners") yazdım buraya ilk defa istek attıgımda servis cagrilir. 
	 * 2. istek attigimde ise buraya girmiyor Cache deki return degerini getirir
	 * Neden?
	 * 
	 * @Cacheable anotasyonunun çalışma prensibinden kaynaklanmaktadır. 
	 * @Cacheable, bir metot çağrıldığında önce önbellekte ilgili sonucu arar. 
	 * Eğer önbellekte bir sonuç varsa, metodu çalıştırmadan doğrudan önbellekteki sonucu döner. 
	 * Bu nedenle, ikinci istek yapıldığında getOwners() metodu çağrılmayacak ve ">>>inside getOwners...." çıktısı konsola yazılmayacaktır.
	 * 
	 * @Cacheable Nasıl Çalışır?
		İlk çağrıda:

		@Cacheable, verilen cache adıyla ("allOwners") önbellekte ilgili bir sonuç arar.
		Eğer önbellekte bir sonuç yoksa, metot çalıştırılır ve dönen sonuç önbelleğe kaydedilir.
		İkinci ve sonraki çağrılarda:
		
		@Cacheable, önbellekte ilgili bir sonuç bulursa, metodu çalıştırmadan doğrudan önbellekteki sonucu döner.
	 * 
	 * Mahmut Duman Açıklama:
	 * Cache kısmında denemelerde restController methoduna caching yapmıstık
		    public class PetClinicRestController {
		        @Cacheable("allOwners")//cachede yoksa methodu çalıstırıyor varsa cacheden getiriyor.
		        @RequestMapping(method = RequestMethod.GET, value = "/owners")
		        public ResponseEntity<List<Owner>> getOwners() {
		            System.out.println(">>>inside getOwners...");
		            List<Owner> owners = petClinicService.findOwners();
		            return ResponseEntity.ok(owners);
		        }
		        //...
		    }
		 
		http://localhost:8080/rest/owners url ine tarayıcıdan get istegi attıgımızda 
		Basic Auth yetkisi olan (user2,user2) ile auth yapılarak istek atılır.
		 
		İlk istekte method cache de olmadıgı için method çalışır.
		Logda >>>inside getOwners... yazarak methoda girdigi anlasılır.
		ikinci http://localhost:8080/rest/owners isteginde tetikledigimizde cache'de oldugu için methoda girmez cache'den getirilir.
	 * 
	 * */
	 
1-Cozum : Cache'i Manuel Temizlemek gerekir.
Eğer veriler zamanla değişiyorsa, 
@CacheEvict anotasyonunu kullanarak önbelleği temizleyebilirsiniz. 
Örneğin, yeni bir Owner kaydedildiğinde:

    @CacheEvict(value = "allOwners", allEntries = true)
    @RequestMapping(method = RequestMethod.POST, value = "/owners")
    public ResponseEntity<Void> addOwner(@RequestBody Owner owner) {
        petClinicService.addOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
2-Cozum: Cache'in Geçerlilik Süresini Belirleme
Bazı cache sağlayıcıları (örneğin, Redis, Ehcache, veya Caffeine) belirli bir süre sonunda 
önbelleğin otomatik temizlenmesini destekler. Bu süreyi yapılandırarak, cache'in eskimesini sağlayabilirsiniz. 
Örneğin:
    yaml dosyası
    
    spring:
      cache:
        caffeine:
          spec: expireAfterWrite=5m,maximumSize=100
3-Cozum: Metodu Her Zaman Çalıştırmak Ama Cache Güncellemek
    @CachePut("allOwners")
    @RequestMapping(method = RequestMethod.GET, value = "/owners")
    public ResponseEntity<List<Owner>> getOwners() {
        System.out.println(">>>inside getOwners....");
        List<Owner> findOwners = petClinicService.findOwners();
        return ResponseEntity.ok(findOwners);
    }
    
Eğer aynı veriyi sık sık döndürmek istiyorsanız ve performansı artırmak öncelikli ise @Cacheable doğru bir tercihtir. Ancak, her istekte metot çalıştırılmasını istiyorsanız, 
ya @Cacheable yerine @CachePut kullanmalı ya da önbellekleme anotasyonlarını tamamen kaldırmalısınız.

Soru:
Başka bir update metodunda bu owner ları guncelledim daha sonra owners a 
istek attıgımda güncellenen veriyi mi getirir yoksa cache dekini mi?

Eğer başka bir update metodu Owner verilerini güncellediyse ve önbellekteki "allOwners" cache temizlenmediyse, 
/owners endpoint'ine yapılan istekte önbellekteki eski veri dönecektir. 
Çünkü @Cacheable anotasyonu, önce önbellekteki veriyi kontrol eder ve eğer orada bir sonuç bulursa metodu çalıştırmaz. 
Bu durumda güncellenen veri yansıtılmaz.

Çözüm Yolları
1. Update İşleminde Cache'i Temizlemek
Update metodu içinde ilgili cache'i temizlemek için @CacheEvict anotasyonunu kullanabilirsiniz. 
Bu, update işleminden sonra cache'in eski veriyi tutmasını engeller. 

Örneğin:

    @CacheEvict(value = "allOwners", allEntries = true)
    @RequestMapping(method = RequestMethod.PUT, value = "/owners/{id}")
    public ResponseEntity<Void> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        petClinicService.updateOwner(id, owner);
        return ResponseEntity.ok().build();
    }
Bu yapılandırma ile updateOwner metodu çağrıldığında "allOwners" cache'i temizlenir. Daha sonra /owners endpoint'ine yapılan istekte @Cacheable anotasyonu devreye girer ve metodu çalıştırarak güncellenmiş veriyi döner.

2. Cache Güncellemek İçin @CachePut Kullanımı
Eğer update işleminden sonra cache'i temizlemek yerine doğrudan güncellenmiş veriyi cache'e eklemek istiyorsanız, 
@CachePut anotasyonunu kullanabilirsiniz. 

Örneğin:

    @CachePut(value = "allOwners")
    @RequestMapping(method = RequestMethod.PUT, value = "/owners/{id}")
    public ResponseEntity<Void> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        petClinicService.updateOwner(id, owner);
        return ResponseEntity.ok().build();
    }
Bu yöntem, güncel veriyi cache'e kaydeder ve eski cache'in üzerine yazar.

3. Cache'in Geçerlilik Süresini Ayarlamak
Eğer güncelleme işlemleri sık değilse, cache sağlayıcınızda bir geçerlilik süresi (TTL - Time to Live) ayarlayabilirsiniz. 
Bu, cache'in belirli bir süre sonunda otomatik olarak temizlenmesini sağlar. 

Örneğin, Redis veya Caffeine kullanıyorsanız:

    yaml

    spring:
      cache:
        caffeine:
          spec: expireAfterWrite=5m,maximumSize=100
Yukarıdaki örnekte, cache her 5 dakikada bir kendini temizler. Ancak, bu yöntem verilerinizi ne kadar sık güncellediğinize bağlı olarak uygun olmayabilir.

4. Manuel Cache Yönetimi
Bazı durumlarda programatik olarak cache'i yönetmek isteyebilirsiniz. 
Spring'in CacheManager veya Cache API'sini kullanarak belirli bir cache'i temizleyebilir veya güncelleyebilirsiniz:

    @Autowired
    private CacheManager cacheManager;
    
    public void evictAllOwnersCache() {
        Cache cache = cacheManager.getCache("allOwners");
        if (cache != null) {
            cache.clear();
        }
    }
Bu yöntemi update metodunuzda çağırarak cache'i manuel olarak temizleyebilirsiniz.

Sonuç
Update işlemi sonrasında /owners endpoint'inin güncellenmiş veriyi döndürmesi için aşağıdaki yöntemlerden birini kullanabilirsiniz:

Update metodunda @CacheEvict ile ilgili cache'i temizleyin.
Update metodunda @CachePut kullanarak cache'i güncelleyin.
Cache sağlayıcınızda bir TTL ayarlayarak eski verilerin belirli süre sonunda temizlenmesini sağlayın.
En yaygın çözüm @CacheEvict kullanarak cache'i temizlemek ve yeni bir istekte cache'in tekrar oluşturulmasına izin vermektir.