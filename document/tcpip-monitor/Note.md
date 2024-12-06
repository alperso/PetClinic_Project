#TCP/IP Monitor (STS'deki TCP/IP monitor özelliği),

TCP/IP Monitor (STS'deki TCP/IP monitor özelliği),
ağ üzerinden gönderilen ve alınan verileri izlemek, analiz etmek ve incelemek için kullanılan bir araçtır.
Bu özellik, uygulamanın ağ bağlantılarını (özellikle HTTP ve diğer TCP/IP tabanlı protokollerle ilgili) izlemek için oldukça kullanışlıdır.

Örnek:
localhost:8085 den -> localhost:8080 e giderken gelen giden istekleri monitor etmek icin kullanıyoruz

Eğer bir uygulama 8085 portundan veri gönderiyorsa ve bu veri 8080 portuna gidiyorsa, TCP/IP monitor bu veriyi izler ve gösterir.
Bu, özellikle geliştirme ve hata ayıklama sırasında,
veri paketlerinin doğru şekilde iletilip iletilmediğini kontrol etmek için kullanışlıdır.

Özetle: TCP/IP Monitor, ağ üzerinde belirli portlar arasında gerçekleşen veri akışını izleyerek,
ağla ilgili olası hataları tanımlamak ve incelemek için kullanılan güçlü bir araçtır.



Mahmut Notları
Eclipse TCP/IP monitor kullanimi
    STS de java sekmesine gelinip Window->Show View ->Others->TCP/IP Monitor eklenir.
    Panel altta konsol yanında acılır.
    Acılan panelde sol üst kısmında Show Header aktif edilebilir.
    Altında properties'a girilerek TCP/IP Monitor konfigurasyonları yapılır.
    Properties buttonuna tıkladıktan sonra açılan ekranda Add yapılarak yeni monitor eklenir.
    Local monitoring port bizim izlemek istedigimiz kullanacagımız boşta portu yazarız ben 8085 yazdım.
    Diger alanlar doldurulur
        hostname:localhost
        port:8080
    yazılarak OK yapılır.
    Sonrasında ekledigimiz monitor seçilerek start yapılır.
    Artık postman de monitor ettigimiz 8085 portuna istek gönderdigimizde 
    req ve resp gorebilecegiz.