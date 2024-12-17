H2 Gomulu DataBase


<!-- H2 Gomulu DB / http://localhost:8080/h2-console ile erisim saglariz-->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<!-- Maven'de <scope> etiketi, bir bağımlılığın (dependency) hangi aşamalarda kullanılacağını ve erişilebilir olacağını belirler. -->

<!-- Veriye erismek icin/JDBC ile kullanmak icin -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>


Server File Path
jdbc:h2:file:C:/Users/alperoner/test;AUTO_SERVER=TRUE


*******************************************************
* H2 Database kurulumu Hk
    pom.xml e 2 dependency eklenir.
<dependency>
<groupId>com.h2database</groupId>
<artifactId>h2</artifactId>
<scope>runtime</scope>
</dependency>
 
    <dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
    h2 console a girmek icin -> http://localhost:8080/h2-console/ yapılır.
    Acılan ekranda JDBC Url kısmında jdbc:h2:mem:testdb
    olarak düzeltilerek asagıdaki degerler aynı kalir
    username:sa
    Password:
    http://localhost:8080/h2-console/ acıldıgında Generic H2 (Embedded) olarak gelir bununla login olabiliriz.
    -> Generic H2(Embedded)
    -> Generic H2(Embedded)
    Projede Generic H2 (Server) seçtigimizde hata aldıgından dolayı 
    -> jdbc:h2:tcp://localhost/~/test olarak gelmekteydi
    -> jdbc:h2:file:C:/Users/MahmutDuman/test;AUTO_SERVER=TRUE olarak yaptık
    C:/User/MahmutDuman/test.mw altında oluşturulacaktır. Bunu yedeklersek database i yedeklemiş oluruz.
    spring.datasource.url=jdbc:h2:tcp://localhost/~/test
    spring.datasource.username=sa
    spring.datasource.password=
    olarak verilebilir.
    Burada c:/Users/${username}/altına database dosyaları olusturulur.
 
    application.properties'de 
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
    ekleyerek configure edebiliriz.
