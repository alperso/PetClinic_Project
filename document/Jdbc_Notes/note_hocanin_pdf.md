* JDBC Notları
    * Spring Üzerinden JDBC ile Veri Erişimi
        - String veri erişiminde bu tekrarlayan kısımları
        ortadan kaldırmak için Template Method
        örüntüsü tabanlı bir kabiliyet sunar
        - JdbcTemplate isimli bu yapı utility veya helper
        sınıflarına benzetilebilir
        - JdbcTemplate sayesinde Template Method
        tarafından dikte edilen standart bir kullanım şekli kod geneline hakim olur
 
    * JdbcTemplate Kullanım Örnekleri
        String result = jdbcTemplate.queryForObject(
        "select last_name from t_owner where id = ?",
        String.class, 1212L);
 
        Map<String,Object> result = jdbcTemplate.queryForMap(
        "select last_name,first_name from t_owner where id = ?",
        1212L);
        List<String> result = jdbcTemplate.queryForList(
        "select last_name from t_owner", String.class);
 
        List<Map> result = jdbcTemplate.queryForList(
        "select * from t_owner");
 
        int insertCount = jdbcTemplate.update(
        "insert into t_owner (id,first_name,last_name) values (?,?,?)", 101L,
        "Ali", "Yücel");
 
        int updateCount = jdbcTemplate.update(
        "update t_owner set last_name = ? where id = ?", "Güçlü", 1L);
 
        int deleteCount = jdbcTemplate.update(
         "delete from t_owner where id = ?", 1L);
 
        List<Object[]> args = new ArrayList<Object[]>();
 
        args.add(new Object[]{"Kenan","Sevindik",1L});
        args.add(new Object[]{"Muammer","Yücel",2L});
 
        int[] batchUpdateCount = jdbcTemplate.batchUpdate(
        "update t_owner set first_name = ?, last_name = ? where id = ?",
        args);
 
        int[] batchUpdateCount = jdbcTemplate.batchUpdate(
        "delete from t_pet",
        "delete from t_owner");
 
        * Pozisyonel Parametreler
 
        String sql = "select count(*) from t_owner where first_name = ? and
        last_name = ? ";
 
        int count = jdbcTemplate.queryForObject(
        sql, Integer.class, new Object[]{"Kenan","Sevindik"});
 
        int count = jdbcTemplate.queryForObject(
        sql, Integer.class, "Kenan","Sevindik");
 
        * İsimlendirilmiş (Named) Parametreler
 
        String sql = "select count(*) from t_owner where
        first_name = :firstName and last_name = :lastName";
 
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstName", "Kenan");
        paramMap.put("lastName", "Sevindik");
 
        int count = namedParameterJdbcTemplate.queryForObject(
        sql, paramMap, Integer.class);
 
        * NamedParameterJdbcTemplate Bean Konfigürasyonu
        - Spring Boot NamedParameterJdbcTemplate bean'ini
          otomatik olarak tanımlar.
 
        @Repository
        public class OwnerDaoJdbcImpl implements OwnerDao {
        @Autowired
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        ...
        }
 
        IN Clause'una Değişken Sayıda Parametre Geçilmesi
 
        String sql = "select * from t_owner where id in (:idList)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("idList", Arrays.asList(1,2,3));
        RowMapper<Owner> rowMapper = new RowMapper<Owner>() {
            public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
                Owner owner = new Owner();
                owner.setFirstName(rs.getString("first_name"));
                owner.setLastName(rs.getString("last_name"));
                return owner;
            }
        };
        List<Owner> result = namedParameterJdbcTemplate.query(
        sql, paramMap,rowMapper);``