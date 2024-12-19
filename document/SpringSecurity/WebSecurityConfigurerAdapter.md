Bunlar ne iş yapar?
      .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable(); bunları detaylı açıkla


Bu Spring Security yapılandırma kodu, güvenlik ayarlarını belirlerken çeşitli özellikleri etkinleştirir 
veya devre dışı bırakır. Kodun her bir satırını adım adım açıklayalım.

1. .httpBasic()

    .httpBasic()
    Bu, HTTP Basic Authentication'ı etkinleştirir.
    HTTP Basic Authentication, kullanıcı adı ve şifreyi HTTP başlıkları içinde iletmek için kullanılan bir yöntemdir. Kullanıcı adı ve şifreyi her istekte base64 formatında şifreleyerek gönderir.
    Bu yöntem basittir ancak güvenli değildir, çünkü şifreler şifrelenmeden (ancak base64 kodlamasıyla) gönderildiğinden HTTPS üzerinden kullanılmalıdır.
    Örnek kullanım:
    Kullanıcı girişi, HTTP başlıkları aracılığıyla yapılır. Bir API'ye istek atarken, Authorization: Basic base64encoded(username:password) şeklinde bir başlık gönderilir.

2. .and()

    .and()
    Bu metot, birden fazla güvenlik yapılandırmasını birbirine bağlar.
    İlk başta .httpBasic() ile HTTP Basic Authentication'ı etkinleştirdik. .and() ile bir sonraki güvenlik yapılandırma bloğuna geçiyoruz.
3. .authorizeRequests()

    .authorizeRequests()
    Bu, gelen HTTP isteklerini yetkilendirme için yapılandırmamıza olanak tanır.
    authorizeRequests(), hangi isteklerin hangi yetkilere sahip kullanıcılar tarafından yapılabileceğini belirler.
4. .anyRequest().authenticated()

    .anyRequest()
        .authenticated()
    .anyRequest() tüm gelen HTTP isteklerini temsil eder.
    .authenticated() ise tüm bu isteklere kimlik doğrulaması yapılmış bir kullanıcının erişmesine izin verir.
    Yani, bu yapılandırma, herhangi bir URL veya sayfaya yapılacak tüm isteklerin, önceden giriş yapmış bir kullanıcı tarafından yapılmasını gerektirir.
    Örneğin:
    
    /admin veya /profile gibi sayfalara giriş yapabilmek için, kullanıcı adı ve şifre ile giriş yapılması gerekir.

5. .csrf().disable()

    .csrf().disable()
    CSRF (Cross-Site Request Forgery), bir kullanıcının yetkisiz bir şekilde başka bir kullanıcı adına işlem yapmasını sağlamak için kullanılan bir saldırıdır.
    .csrf().disable() komutu, Spring Security'deki CSRF korumasını devre dışı bırakır.
    Bu genellikle API tabanlı uygulamalarda veya istemci ve sunucu arasında stateless (durumsuz) iletişimde kullanılır. Bu durumda, kullanıcı doğrulama bilgileri genellikle her istekte gönderildiğinden CSRF korumasına ihtiyaç duyulmaz.
    Uyarı: CSRF'yi devre dışı bırakmak güvenlik açısından risk oluşturabilir, bu yüzden yalnızca gerektiğinde kullanılmalıdır.
6. .formLogin().disable()

    .formLogin().disable()
    .formLogin() Spring Security'nin form tabanlı giriş yapmayı sağlar. Kullanıcı adı ve şifreyi bir HTML formu üzerinden göndererek giriş yapılır.
    .formLogin().disable() ile bu form tabanlı giriş işlevselliği devre dışı bırakılır.
    Bu genellikle API veya başka bir oturum yönetimi yöntemi (örneğin JWT veya OAuth) kullanıyorsanız tercih edilir. Eğer bir web formu kullanarak giriş yapmak istemiyorsanız, bu konfigürasyon ile bunu engellemiş olursunuz.

Özet
Yukarıdaki yapılandırma şunları yapar:

HTTP Basic Authentication'ı etkinleştirir: Kullanıcı adı ve şifreyi HTTP başlıkları ile göndererek giriş yapılmasını sağlar.
Tüm isteklerin kimlik doğrulaması yapılmış kullanıcılar tarafından erişilmesini sağlar: Kimlik doğrulaması yapılmamış kullanıcılar, herhangi bir kaynağa erişemez.
CSRF korumasını devre dışı bırakır: API'ler için genellikle tercih edilir, ancak web uygulamaları için CSRF koruması devre dışı bırakılmamalıdır.
Form tabanlı giriş işlemi devre dışı bırakılır: Form tabanlı giriş yerine, HTTP Basic Authentication kullanılır.
Bu yapılandırma genellikle API tabanlı projelerde, şifrelerin HTTP başlıklarında güvenli bir şekilde taşınması gereken durumlar için uygun olacaktır.