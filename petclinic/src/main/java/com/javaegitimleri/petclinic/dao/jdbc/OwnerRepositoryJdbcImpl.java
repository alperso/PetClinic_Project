package com.javaegitimleri.petclinic.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.model.Owner;

/****************************************************
 * Dublicate impl hatası neden olusur?
 * 
 * Sadece @Repository yazarsam problem yaratır? Neden ?
 * Bizim OwnerRepository'nin impl yaptıgı 2 tane repomuz var.
 * 
 * 1-OwnerRepositoryInMemoryImpl (OwnerRepository impl edilmiş)
 
 	@Repository
	public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
	
 * 2-OwnerRepositoryJdbcImpl (OwnerRepository impl edilmiş)
 
	@Repository
	public class OwnerRepositoryJdbcImpl implements OwnerRepository {
 
 * @Service katmanından ornegin findAll ile cektigimizde hangi repoya gidecek ?
 * Sadece @Repository yazarsak hata verir. 
 * 
 * @Service katmanindan (Buraya OwnerRepository(bu bir interface) impl edilmiş) impl edildiğinde
 * OwnerRepository içindeki findAll,findById,findByLastName,createOwner,deleteOwner metotlari vardır.
 * 
 * Altta bir hata var.
 * 
 	***************************
	APPLICATION FAILED TO START
 	***************************

	Description:
	
	Field ownerRepository in com.javaegitimleri.petclinic.service.PetClinicServiceImpl required a single bean, but 2 were found:
		- ownerRepositoryJdbcImpl: defined in file [C:\DEV\PetClinic_Project\petclinic\target\classes\com\javaegitimleri\petclinic\dao\jdbc\OwnerRepositoryJdbcImpl.class]
		- ownerRepositoryInMemoryImpl: defined in file [C:\DEV\PetClinic_Project\petclinic\target\classes\com\javaegitimleri\petclinic\dao\mem\OwnerRepositoryInMemoryImpl.class]
	
	
	Action:
	
	Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed

 * Burada diyor ki iki tane bean var hangisine gidecegimi bilmiyorum. Ne yapilmali?
 * Bu hata, Spring Framework içinde OwnerRepository tipinde iki farklı bean tanımlandığını ve Spring'in bu iki bean'den hangisini kullanacağını seçemediğini belirtir.
 * 
 * Çözüm:
 * 1. @Primary Anotasyonu Kullanmak
 * 2. @Qualifier Anotasyonu Kullanmak
 * 3. Configuration Sınıfında Bean Tanımlama
 * 4. @Repository("ownerRepository") ile isimlendirmek
 * 
 * Hangi yöntemi seçerseniz seçin, bu hatanın temel nedeni, Spring'in birden fazla aynı türde bean bulmasıdır.
 * Bu nedenle, ya bir bean'i öncelikli yapmanız ya da açıkça seçmeniz gerekir.
 * Tavsiye Edilen: @Qualifier kullanımı, daha fazla kontrol sağladığı için genellikle tercih edilir.
 * 
 * Detay:
 * 1. @Primary Anotasyonu Kullanmak:
  		Eğer bir bean'in varsayılan olarak seçilmesini istiyorsanız, o bean üzerine @Primary anotasyonu ekleyebilirsiniz.
  
			@Repository
			@Primary
			public class OwnerRepositoryJdbcImpl implements OwnerRepository {
	    		// Implementasyon
			}
			
  		Bu durumda, Spring diğer seçeneklere bakmadan OwnerRepositoryJdbcImpl sınıfını kullanır.
  
 * 2. @Qualifier Anotasyonu Kullanmak
  		Eğer hangi bean'in kullanılacağını daha belirgin şekilde belirtmek istiyorsanız, @Qualifier anotasyonunu kullanabilirsiniz.
  		
  		@Service
		public class PetClinicServiceImpl {

    		private final OwnerRepository ownerRepository;

    		public PetClinicServiceImpl(@Qualifier("ownerRepositoryJdbcImpl") OwnerRepository ownerRepository) {
        		this.ownerRepository = ownerRepository;
    		}

    		// Diğer metotlar
		}
		
 * 3. Configuration Sınıfında Bean Tanımlama 
  		Eğer yukarıdaki iki yöntem işinizi görmüyorsa, bir @Configuration sınıfında hangi bean'in kullanılacağını belirtebilirsiniz:
		
		AppConfig.java
		
 		@Configuration
		public class AppConfig {
		
		    // OwnerRepositoryJdbcImpl bean'ini oluştur ve @Primary olarak işaretle
		    @Bean
		    @Primary
		    public OwnerRepository ownerRepositoryJdbcImpl() {
		        return new OwnerRepositoryJdbcImpl();
		    }
		
		    // OwnerRepositoryInMemoryImpl bean'ini oluştur
		    @Bean
		    public OwnerRepository ownerRepositoryInMemoryImpl() {
		        return new OwnerRepositoryInMemoryImpl();
		    }
		}
		
		Service sinifimiz:
		
		@Service
		public class PetClinicServiceImpl {
		
		    private final OwnerRepository ownerRepository;
		
		    // Spring varsayılan olarak @Primary bean'ini kullanır
		    public PetClinicServiceImpl(OwnerRepository ownerRepository) {
		        this.ownerRepository = ownerRepository;
		    }
		
		    // Eğer belirli bir bean istiyorsanız, @Qualifier kullanabilirsiniz
		    // public PetClinicServiceImpl(@Qualifier("ownerRepositoryInMemoryImpl") OwnerRepository ownerRepository) {
		    //     this.ownerRepository = ownerRepository;
		    // }
		}
		
		Bu durumda, Spring @Primary anotasyonu sayesinde varsayılan olarak OwnerRepositoryJdbcImpl'i kullanır.
		
		----@Qualifier("ownerRepositoryInMemoryImpl") bunun ownerRepositoryInMemoryImpl oldugunu nasil anliyor?
		Spring, bean'leri tanımlarken @Bean anotasyonu ile metod adını varsayılan olarak bean adı olarak kullanır. 
		Bu yüzden, aşağıdaki örnekte @Bean olarak tanımlanan ownerRepositoryInMemoryImpl bean'inin adı ownerRepositoryInMemoryImpl olacaktır.
		
		Nasıl Çalışır?
		Bean İsmi Varsayılan Olarak Metod Adıdır:
		
		@Bean anotasyonu kullanıldığında, Spring, varsayılan olarak o metodun adını bean adı olarak alır.
		Yukarıdaki örnekte, bean adı ownerRepositoryInMemoryImpl olacaktır.
		@Qualifier ile Eşleşme:
		
		@Qualifier("ownerRepositoryInMemoryImpl") yazdığınızda, Spring bu adı kullanarak ilgili bean'i bulur.
		Bean adının @Qualifier ile verilen adla birebir eşleşmesi gerekir.
		
		Ayrıca 
		@Bean(name = "customOwnerRepository") ismini elle verebilirsin.
		
		
 * 4. @Repository("ownerRepository") ile isimlendirmek
 * @Repository ile bean adını belirleyebilirsiniz. Spring, @Repository, @Service, ve @Component 
 * gibi anotasyonlarla tanımlanan sınıfları otomatik olarak bir Spring bean'i haline getirir. 
 * Eğer @Repository anotasyonunda bir isim (bean adı) belirtirseniz, Spring o bean'e bu adı verir.
  
		@Repository("ownerRepositoryJdbcImpl")
		public class OwnerRepositoryJdbcImpl implements OwnerRepository {
		    // JDBC ile ilgili işlemler burada
		}
		
		@Repository("ownerRepositoryInMemoryImpl")
		public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
		    // In-memory işlemler burada
		}
		
		Eğer bu bean'lerden birini açıkça seçmek isterseniz, @Qualifier kullanabilirsiniz:
		
		@Service
		public class PetClinicServiceImpl {
		
		    private final OwnerRepository ownerRepository;
		
		    // ownerRepositoryJdbcImpl bean'ini seçiyoruz
		    public PetClinicServiceImpl(@Qualifier("ownerRepositoryJdbcImpl") OwnerRepository ownerRepository) {
		        this.ownerRepository = ownerRepository;
		    }
		}
		
		Avantajları:
		Daha Az Konfigürasyon: @Repository ile otomatik olarak bean tanımlaması yapıldığı için @Configuration sınıfında @Bean metotları yazmanıza gerek kalmaz.
		Açık Belirleme: Bean adını @Repository anotasyonunda belirterek, hangi bean'in kullanılacağını daha kolay yönetebilirsiniz.
 * 
 * */

//@Repository
//@Repository("ownerRepository")  jpa ile deneyecegim icin dao.jpa->OwnerRepositoryJpaImpl içine yazdım
@Repository
public class OwnerRepositoryJdbcImpl implements OwnerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Owner> rowMapper = new RowMapper<Owner>() {
		@Override
		public Owner mapRow(ResultSet rs, int rowNum) throws SQLException{
			Owner owner = new Owner();
			owner.setId(rs.getLong("id"));
			owner.setFirstName(rs.getString("first_Name"));
			owner.setLastName(rs.getString("last_Name"));
			return owner;
		}
	};
	
	public OwnerRepositoryJdbcImpl() {
	}

	@Override
	public List<Owner> findAll() {
		String sql = "select id,first_name,last_name from t_owner";
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Owner findById(Long id) {
		String sql = "select id,first_name,last_name from t_owner where id=?";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, rowMapper, id));
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		String sql = "select id,first_name,last_name from t_owner where last_name like ?";
		return jdbcTemplate.query(sql, rowMapper,"%"+ lastName + "%");
	}

	@Override
	public void createOwner(Owner owner) {
		

	}

	@Override
	public void deleteOwner(Long ownerId) {
	String sql = "delete from public.t_owner where id = ?";
	jdbcTemplate.update(sql,ownerId);
	//Bu tablo bir tabloya bagli ise once child tablodaki verileri silinmesi gereklidir.
	}

	@Override
	public Owner updateOwner(Owner owner) {
		
		return null;
	}
	
	
	/*
	 * JDBC Notları Spring Üzerinden JDBC ile Veri Erişimi
	 * 
	 * - String veri erişiminde bu tekrarlayan kısımları ortadan kaldırmak için
	 * Template Method örüntüsü tabanlı bir kabiliyet sunar
	 * 
	 * - JdbcTemplate isimli bu yapı utility veya helper sınıflarına benzetilebilir
	 * - JdbcTemplate sayesinde Template Method tarafından dikte edilen standart bir
	 * kullanım şekli kod geneline hakim olur
	 * 
	 * JdbcTemplate Kullanım Örnekleri String result = jdbcTemplate.queryForObject(
	 * "select last_name from t_owner where id = ?", String.class, 1212L);
	 * 
	 * Map<String,Object> result = jdbcTemplate.queryForMap(
	 * "select last_name,first_name from t_owner where id = ?", 1212L);
	 * 
	 * List<String> result = jdbcTemplate.queryForList(
	 * "select last_name from t_owner", String.class);
	 * 
	 * List<Map> result = jdbcTemplate.queryForList( "select * from t_owner");
	 * 
	 * int insertCount = jdbcTemplate.update(
	 * "insert into t_owner (id,first_name,last_name) values (?,?,?)", 101L, "Ali",
	 * "Yücel");
	 * 
	 * int updateCount = jdbcTemplate.update(
	 * "update t_owner set last_name = ? where id = ?", "Güçlü", 1L);
	 * 
	 * int deleteCount = jdbcTemplate.update( "delete from t_owner where id = ?",
	 * 1L);
	 * 
	 * List<Object[]> args = new ArrayList<Object[]>();
	 * 
	 * args.add(new Object[]{"Kenan","Sevindik",1L}); args.add(new
	 * Object[]{"Muammer","Yücel",2L});
	 * 
	 * int[] batchUpdateCount = jdbcTemplate.batchUpdate(
	 * "update t_owner set first_name = ?, last_name = ? where id = ?", args);
	 * 
	 * int[] batchUpdateCount = jdbcTemplate.batchUpdate( "delete from t_pet",
	 * "delete from t_owner");
	 * 
	 * 
	 * Pozisyonel Parametreler
	 *
	 *        String sql = "select count(*) from t_owner where first_name = ? and
	 *        last_name = ? ";
	 *
	 *        int count = jdbcTemplate.queryForObject(
	 *        sql, Integer.class, new Object[]{"Kenan","Sevindik"});
	 *
	 *        int count = jdbcTemplate.queryForObject(
	 *        sql, Integer.class, "Kenan","Sevindik");
	 *
   	 *        * İsimlendirilmiş (Named) Parametreler
	 *
     *        String sql = "select count(*) from t_owner where
   	 *        first_name = :firstName and last_name = :lastName";
	 *
   	 *        Map<String, Object> paramMap = new HashMap<>();
   	 *        paramMap.put("firstName", "Kenan");
   	 *        paramMap.put("lastName", "Sevindik");
	 *
   	 *        int count = namedParameterJdbcTemplate.queryForObject(
   	 *        sql, paramMap, Integer.class);
	 *
   	 *        * NamedParameterJdbcTemplate Bean Konfigürasyonu
   	 *        
   	 *        - Spring Boot NamedParameterJdbcTemplate bean'ini
   	 *          otomatik olarak tanımlar.
	 *
   	 *        @Repository
   	 *        public class OwnerDaoJdbcImpl implements OwnerDao {
   	 *        @Autowired
   	 *        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
   	 *        ...
   	 *        }
	 *
   	 *        IN Clause'una Değişken Sayıda Parametre Geçilmesi
	 *
   	 *        String sql = "select * from t_owner where id in (:idList)";
   	 *        
   	 *        Map<String, Object> paramMap = new HashMap<>();
   	 *        paramMap.put("idList", Arrays.asList(1,2,3));
   	 *        RowMapper<Owner> rowMapper = new RowMapper<Owner>() {
   	 *            public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
   	 *                Owner owner = new Owner();
   	 *                owner.setFirstName(rs.getString("first_name"));
   	 *                owner.setLastName(rs.getString("last_name"));
   	 *                return owner;
   	 *            }
   	 *        };
   	 *        List<Owner> result = namedParameterJdbcTemplate.query(
   	 *        sql, paramMap,rowMapper);
	 */
	
//Farkli Bir Not --------------------------------------------------	
//	* Dublike impl hatası neden olusur?
//		    OwnerRepository interface inden 
//		        - OwnerRepositoryInMemoryImpl
//		        - OwnerRepositoryJdbcImpl
//		    yaptıgımızda spring ayaga kalkarken hata almaktadır.
//		    public interface OwnerRepository {}
//		 
//		    @Repository
//		    public class OwnerRepositoryJdbcImpl implements OwnerRepository {}
//		 
//		    @Repository
//		    public class OwnerRepositoryInMemoryImpl implements OwnerRepository {}
//		 
//		    ***************************
//		    APPLICATION FAILED TO START
//		    ***************************
//		    Description:
//		    Field ownerRepository in com.javaegitimleri.petclinic.service.PetClinicServiceImpl required a single bean, but 2 were found:
//		    	- ownerRepositoryJdbcImpl: defined in file [C:\Dev\petclinic-project\petclinic\target\classes\com\javaegitimleri\petclinic\dao\jdbc\OwnerRepositoryJdbcImpl.class]
//		    	- ownerRepositoryInMemoryImpl: defined in file [C:\Dev\petclinic-project\petclinic\target\classes\com\javaegitimleri\petclinic\dao\mem\OwnerRepositoryInMemoryImpl.class]
//
//		    Action:
//		    Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
//		 
//		    ----------------------------------------------------------------------------------------------------------------
//		    Burada birkaç farklı çözüm var 
//		    1-Springboot için ya impl yaptıklarımızdan birisine @Repository("ownerRepository") olarak yazdıgımızda hata almayacaktır.
//		 
//		    
//		        public class OwnerRepositoryJdbcImpl implements OwnerRepository {
//		            // uygulama detayları
//		        }
//		        @Repository("ownerRepositoryInMemoryImpl")
//		        public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
//		            // uygulama detayları
//		        }
//
//		 
//		        @Service
//		        public class OrderService {
//		            @Autowired
//		            private final OrderService paymentService;
//		        }
//		 
//		    2-autowire ettigimiz Petclinic clasında @Qualifier("") olarak seçilim yaparak configure etmemiz gereklidir.
//		 
//		        @Repository("ownerRepositoryJdbcImpl")
//		        public class OwnerRepositoryJdbcImpl implements OwnerRepository {
//		            // uygulama detayları
//		        }
//		        @Repository("ownerRepositoryInMemoryImpl")
//		        public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
//		            // uygulama detayları
//		        }
//		        //Çagıracagımız servis katmanında Qualifier kullanarak taglerinden seçim yaptırabiliriz.
//		        @Service
//		        public class OrderService {
//		 
//		            private final OrderService paymentService;
//		            @Autowired
//		            public OrderService(@Qualifier("creditCardPaymentService") PaymentService paymentService) {
//		                this.paymentService = paymentService;
//		            }
//		        }
//		 
//		 
//		    3-Birini @Primary Olarak İşaretleme
//		 
//		        Aynı OwnerRepository arayüzünü implement eden iki farklı sınıfın (OwnerRepositoryJdbcImpl ve OwnerRepositoryInMemoryImpl) aynı anda bulunması 
//		        nedeniyle Spring Boot, hangi sınıfı kullanacağı konusunda karışıklık yaşıyor. Bu hatayı çözmek için birkaç farklı yöntem izleyebilirsiniz:
//		        Birini @Primary olarak işaretlemek, Spring'e bu bean'i tercih edilen bean olarak kullanmasını söyler.
//		        @Repository
//		        public class OwnerRepositoryJdbcImpl implements OwnerRepository {
//		            // uygulama detayları
//		        }
//		        @Repository
//		        @Primary
//		        public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
//		            // uygulama detayları
//		        }
//		 
//		        @Service
//		        public class OrderService {
//		            @Autowired
//		            private final OrderService paymentService;
//		        }
//		 
//		    4. Configuration Sınıfında Bean Tanımlama 
//		      		Eğer yukarıdaki iki yöntem işinizi görmüyorsa, bir @Configuration sınıfında hangi bean'in kullanılacağını belirtebilirsiniz:
//		    		AppConfig.java
//		    		@Configuration
//		    		public class AppConfig {
//		    		    // OwnerRepositoryJdbcImpl bean'ini oluştur ve @Primary olarak işaretle
//		    		    @Bean
//		    		    @Primary
//		    		    public OwnerRepository ownerRepositoryJdbcImpl() {
//		    		        return new OwnerRepositoryJdbcImpl();
//		    		    }
//		    		    // OwnerRepositoryInMemoryImpl bean'ini oluştur
//		    		    @Bean
//		    		    public OwnerRepository ownerRepositoryInMemoryImpl() {
//		    		        return new OwnerRepositoryInMemoryImpl();
//		    		    }
//		    		}
//		    		Service sinifimiz:
//		    		@Service
//		    		public class PetClinicServiceImpl {
//		    		    private final OwnerRepository ownerRepository;
//		    		    // Spring varsayılan olarak @Primary bean'ini kullanır
//		    		    public PetClinicServiceImpl(OwnerRepository ownerRepository) {
//		    		        this.ownerRepository = ownerRepository;
//		    		    }
//		    		    // Eğer belirli bir bean istiyorsanız, @Qualifier kullanabilirsiniz
//		    		    // public PetClinicServiceImpl(@Qualifier("ownerRepositoryInMemoryImpl") OwnerRepository ownerRepository) {
//		    		    //     this.ownerRepository = ownerRepository;
//		    		    // }
//		    		}
//		    		Bu durumda, Spring @Primary anotasyonu sayesinde varsayılan olarak OwnerRepositoryJdbcImpl'i kullanır.
//		    		----@Qualifier("ownerRepositoryInMemoryImpl") bunun ownerRepositoryInMemoryImpl oldugunu nasil anliyor?
//		    		Spring, bean'leri tanımlarken @Bean anotasyonu ile metod adını varsayılan olarak bean adı olarak kullanır. 
//		    		Bu yüzden, aşağıdaki örnekte @Bean olarak tanımlanan ownerRepositoryInMemoryImpl bean'inin adı ownerRepositoryInMemoryImpl olacaktır.
//		    		Nasıl Çalışır?
//		    		Bean İsmi Varsayılan Olarak Metod Adıdır:
//		    		@Bean anotasyonu kullanıldığında, Spring, varsayılan olarak o metodun adını bean adı olarak alır.
//		    		Yukarıdaki örnekte, bean adı ownerRepositoryInMemoryImpl olacaktır.
//		    		@Qualifier ile Eşleşme:
//		    		@Qualifier("ownerRepositoryInMemoryImpl") yazdığınızda, Spring bu adı kullanarak ilgili bean'i bulur.
//		    		Bean adının @Qualifier ile verilen adla birebir eşleşmesi gerekir.
//		    		Ayrıca 
//		    		@Bean(name = "customOwnerRepository") ismini elle verebilirsin.		
//
//		 
//		    Buradaki islemi eski projelerdeki gibi factory olusturup hangi impl yapacagımıza göre return etmiştik.

}
