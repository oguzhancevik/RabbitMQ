package rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.io.UnsupportedEncodingException;

//rabbitmq için aşağıdaki kütüphaneleri kullanmamız gerekmektedir.
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Chat {

	/*
	 * Değişkenleri static tanımlamamızın sebebi program çalıştığı sürece
	 * kullanılsada kullanılmasada bir yer açar ve ayrıca main dışında da
	 * tanımlamamızın sebebi main fonk. dışındaki diğer fonk. erişilmek
	 * istenmesidir.
	 */
	static String Mesaj;
	static String Secim;

	public static void main(String[] args) throws IOException, TimeoutException {

		try {

			/*
			 * ConnectionFactory -> rabbitmq’ya bağlanmak için, java api tarafından sunulan
			 * soket soyutlama katmanıdır. Kimlik doğrulama vb işlemleri bizim için yapar.
			 * Connection nesnesini ConnectionFactory üzerinden alırız.
			 */
			ConnectionFactory Factory = new ConnectionFactory();
			Factory.setHost("localhost"); // host adresimizi tanımlıyoruz

			// Connection -> Uygulamadan rabbitmq ya açılan TCP connection’dır.
			Connection connection = Factory.newConnection();

			/*
			 * Channel -> tek bir TCP bağlantısını kullanılan sanal bağlantılar olarak
			 * adlandırılabilir. Bazı durumlarda rabbitmq’ya bir den fazla tcp bağlantısı
			 * ihtiyacımız olabilir. TCP bağlantısı açmak hem yeni kaynak tüketimine neden
			 * olur hem de yönetim zorlukları içerir. Bu sebeple Channel interface’i
			 * kullanılır. Her bir thread başına bir channel açılması önemlidir. Channnel
			 * oluşturduktan sonra queue tanımı yapıyoruz. Queue ismi önemli burada.
			 * Consumer uygulamamızda, aynı isimli queue’ya bağlanacak. Bu tanımda, verilen
			 * isim ile daha önceden tanımlanmış bir queue var ise birşey yapılmaz, yok ise
			 * queue oluşturulur. Ardından ilk mesajımızı gönderiyoruz.
			 */
			Channel channel = connection.createChannel();

			QueueingConsumer Consumer = new QueueingConsumer(channel);

			// local'de tanımlı işlem yapacağımız queue isimlerimizi tanımlıyoruz
			String queueA = "queueA";
			String queueB = "queueB";

			Date Zaman = new Date();

			System.out.println("Queue secin (a/b)");
			Scanner Giris = new Scanner(System.in); // Kullanıcı girişi için Scanner komutunu kullanırız
			Secim = Giris.nextLine();

			/*
			 * a ve b kuyruklarından farkı bir kuyruk girmememiz için while ile kontrol
			 * ettim.
			 */
			while (!(Secim.equalsIgnoreCase("a") || Secim.equalsIgnoreCase("b"))) {
				System.out.println("Yanlış Seçim Lütfen a/b kuyruğundan birini seçiniz!");
				System.out.println("Queue secin (a/b)");
				Secim = Giris.nextLine();
			}

			/*
			 * equalsIgnoreCase -> iki stringi büyük küçük harf ayrımı yapmadan
			 * karşılaştırır: a,A
			 */
			if (Secim.equalsIgnoreCase("a")) {
				channel.basicConsume(queueA, Consumer); // bu kısım kuyruktaki mesajları görüntüler
			}

			if (Secim.equalsIgnoreCase("b")) {
				channel.basicConsume(queueB, Consumer); // bu kısım kuyruktaki mesajları görüntüler
			}

			boolean removeAllUpTo = true;
			while (true) {

				// teslim işlemini 0.5 saniyede gerçekleştirir
				Delivery delivery = Consumer.nextDelivery(500);

				if (delivery == null) // mesaj yok ise çıkar
					break;

				long deliveryTag = delivery.getEnvelope().getDeliveryTag();
				channel.basicAck(deliveryTag, removeAllUpTo);

				if (processMessage(delivery)) {
				}

			}

			String Cikis;
			do {
				System.out.println("Mesaji giriniz:(" + Secim + ")");
				Mesaj = Giris.nextLine(); // Girilen değeri Mesaj değişkenine atadık

				// mesajı sonlandırmak için şart ifadesini do while döngüsüyle kontrol ettim
				do {
					System.out.println("Çımak istiyormusunuz E/H");
					Cikis = Giris.nextLine();
				} while (!(Cikis.equalsIgnoreCase("e") || Cikis.equalsIgnoreCase("h")));

				Mesaj = Zaman.toString() + " | " + Mesaj + " |";

				/*
				 * equalsIgnoreCase -> iki stringi büyük küçük harf ayrımı yapmadan
				 * karşılaştırır: a,A
				 */
				if (Secim.equalsIgnoreCase("a")) {
					// bu kısımda producer tarafından mesaj gönderilir.
					channel.basicPublish("", queueB, null, Mesaj.getBytes());
				}

				if (Secim.equalsIgnoreCase("b")) {
					// bu kısımda producer tarafından mesaj gönderilir.
					channel.basicPublish("", queueA, null, Mesaj.getBytes());
				}

			} while (Cikis.equalsIgnoreCase("h"));

			// en sonunda ise channel, connection ve Giris'i kapatıyoruz.
			channel.close();
			connection.close();
			Giris.close();

			System.out.println("Mesajın gönderildi!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean processMessage(Delivery delivery) throws UnsupportedEncodingException {
		try {
			String Mesaj = new String(delivery.getBody(), "UTF-8");
			System.out.println("[X] Yeni Mesaj(" + Secim + ")" + "\n" + Mesaj);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

	}

}
