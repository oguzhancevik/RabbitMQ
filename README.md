# RabbitMQ
RabbitMQ, Erlang programlama dili ile geliştirilen AMQP standartlarına göre geliştirilmiş robust mesajlaşma sistemidir.

Publish ve Subscribe mantığı altında çalışmaktadır. Yani bir uygulamadan bir mesajı alıp, bir başka uygulamaya sırası geldiğinde bunu iletmektir.

RabbitMQ bir çok yazılım diline destek verdiği gibi, bir çok işletim sistemi üzerinde de çalışabilmektedir ve open source‘dur.

Avantajları:
-Ölçeklenebilir uygulamalar yapmak istiyenler.
-Birden fazla uygulamayı belli bir standartta haberleştirmek isteyenler.
-Uygulamanızdaki resim işleme, rapor oluşturma gibi uzun süren işleri arka planda yapmak isteyenler.

Öne çıkan özellikleri:
-Robust mesajlaşma
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



Kanaklar:
http://www.gokhan-gokalp.com/rabbitmq-nedir-ve-windowsa-kurulumu/
http://lab2023.com/rabbitmq.html
