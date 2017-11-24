# RabbitMQ
RabbitMQ, Erlang programlama dili ile geliştirilen AMQP standartlarına göre geliştirilmiş robust mesajlaşma sistemidir.

Publish ve Subscribe mantığı altında çalışmaktadır. Yani bir uygulamadan bir mesajı alıp, bir başka uygulamaya sırası geldiğinde bunu iletmektir.

RabbitMQ bir çok yazılım diline destek verdiği gibi, bir çok işletim sistemi üzerinde de çalışabilmektedir ve open source‘dur.

Avantajları:
-Ölçeklenebilir uygulamalar yapmak istiyenler.
-Birden fazla uygulamayı belli bir standartta haberleştirmek isteyenler.
-Uygulamanızdaki resim işleme, rapor oluşturma gibi uzun süren işleri arka planda yapmak isteyenler.

Öne çıkan özellikleri:

Robust mesajlaşma
-Kullanım kolaylığı
-Geniş bir işletim sistemini üzerinde çalıştırılabilmesi
-Geniş bir geliştirme platformunu desteklemesi
-Açık kaynaklı ve ticari desteğinin bulunması

Terminoloji:
-Producer, mesajı gönderen taraftır. P ile gösterilir.
-Queue, mesajların biriktiği havuzdur. Bir nevi mailbox gibi düşünülebilir.
-Consumer, mesajı alan taraftır. C ile gösterilir.

Not:
-Producer, Queue ve Consumerlar aynı makine üzerinde olmak zorunda değildir.
-Producer ve Consumerlar aynı programlama dili ile yazılmak zorunda değildir.

Kurulum:
Ben test ortamını Windows 10 makinemde gerçekleştireceğim.İlk olarak bu linkten ( http://www.erlang.org/downloads ) erlang kaynağını yüklememiz lazım. Ben şuan en güncel sürüm olan OTP 20.1 Windows 64 biti indirdim. 

Erlang dilinin kurulumundan sonra RabbitMQ kurulumuna geçebiliriz.Öncelikle bu linkten ( http://www.rabbitmq.com/install-windows.html ) en son sürümünü rabbitmq-server-3.6.14.exe indirdim.

Kurulumları yaptıktan sonra:

RabbitMQ Server klasörü içerisindeki RabbitMQ Command Prompt'u administrator yetkisi ile çalıştırın. Çalıştırdıktan sonra işlem yapabilmemiz için servislerin bulunduğu C:\Program Files\RabbitMQ Server\rabbitmq_server-3.6.14\sbin yolunun içine girelim.



Kanaklar:
http://www.gokhan-gokalp.com/rabbitmq-nedir-ve-windowsa-kurulumu/
http://lab2023.com/rabbitmq.html
