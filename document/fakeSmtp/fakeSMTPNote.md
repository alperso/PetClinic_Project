********************* FakeSmtp *********************
FakeSMTP bir e-posta sunucusunu simüle etmek için kullanılan, e-posta testi ve hata ayıklama aracı olarak tasarlanmış, 
açık kaynaklı ve ücretsiz bir uygulamadır. Özellikle yazılım geliştirme ve test süreçlerinde kullanılır.

Gerçek e-posta sunucuları yerine, geliştirme ve test ortamlarında e-posta gönderimlerini taklit etmek için kullanılır. Bu sayede:

Yanlışlıkla gerçek alıcılara e-posta gönderilmesini önlersiniz.
E-posta içeriklerini, biçimlendirmelerini ve başlık bilgilerini kontrol edebilirsiniz.
E-posta göndermeye dair hata ayıklama sürecini kolaylaştırırsınız.

Nasıl Çalışır?
Başlatma: FakeSMTP'yi çalıştırmak için, Java JAR dosyasını başlatırsınız:

java -jar fakeSMTP.jar

Varsayılan olarak, 25 numaralı portu dinler, ancak farklı bir port da ayarlanabilir.
Uygulamanızı FakeSMTP'ye Yönlendirme: Test ettiğiniz uygulamada SMTP ayarlarını FakeSMTP'nin çalıştığı sunucu ve port bilgileriyle değiştirirsiniz 
(örneğin, localhost ve 25).
E-postaları Yakalama: FakeSMTP gönderilen e-postaları yakalar ve bir liste halinde gösterir.
E-postaları Görüntüleme: Gönderilen e-postalar FakeSMTP'nin arayüzünden görüntülenebilir veya kaydedilebilir.

Nasıl Yapılır?
1-https://nilhcem.com/FakeSMTP/download.html sitesinden jar indirilir.
2-İndirilen klasöre gelinir ve cmd açılır

java -jar fakeSMTP-2.0.jar

komutuyla jar çalıştırılır. Daha sonra açılan pencereden Server started ile çalıştırılır.
