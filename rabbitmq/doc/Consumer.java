package rabbitmq;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

//rabbitmq için aşağıdaki kütüphaneleri kullanmamız gerekmektedir.
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Consumer {

	/*
	 * Secim değişkenini static tanımlamamızın sebebi program çalıştığı sürece
	 * kullanılsada kullanılmasada bir yer açar ve ayrıca main dışında da
	 * tanımlamamızın sebebi main fonk. dışındaki diğer fonk. erişilmek
	 * istenmesidir.
	 */
	static String Secim;

	public static void main(String[] args) throws Exception {

		try {

			/*
			 * ConnectionFactory -> rabbitmq’ya bağlanmak için, java api tarafından sunulan
			 * soket soyutlama katmanıdır. Kimlik doğrulama vb işlemleri bizim için yapar.
			 * Connection nesnesini ConnectionFactory üzerinden alırız.
			 */
			ConnectionFactory factory = new ConnectionFactory();

			factory.setHost("localhost"); // host adresimizi tanımlıyoruz

			// Connection -> Uygulamadan rabbitmq ya açılan TCP connection’dır.
			Connection connection = factory.newConnection();

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

			// local'de tanımlı işlem yapacağımız queue isimlerimizi tanımlıyoruz
			String queueA = "queueA";
			String queueB = "queueB";

			QueueingConsumer consumer = new QueueingConsumer(channel);

			System.out.println("Queue secin (a/b)");
			Scanner Giris = new Scanner(System.in);
			Secim = Giris.nextLine();

			if (!(Secim.equalsIgnoreCase("a") || Secim.equalsIgnoreCase("b"))) {
				do {
					System.out.println("Yanlış Seçim Lütfen a/b kuyruğundan birini seçiniz!");
					System.out.println("Queue secin (a/b)");
					Secim = Giris.nextLine();
				} while (!(Secim.equalsIgnoreCase("a") || Secim.equalsIgnoreCase("b")));
			}

			/*
			 * equalsIgnoreCase -> iki stringi büyük küçük harf ayrımı yapmadan
			 * karşılaştırır: a,A
			 */
			if (Secim.equalsIgnoreCase("a")) {
				channel.basicConsume(queueA, consumer); // bu kısım kuyruktaki mesajları görüntüler
			}

			if (Secim.equalsIgnoreCase("b")) {
				channel.basicConsume(queueB, consumer); // bu kısım kuyruktaki mesajları görüntüler
			}

			boolean removeAllUpTo = true;
			while (true) {
				Delivery delivery = consumer.nextDelivery(5000); // teslim işlemini 5 sn. gerçekleştirir

				if (delivery == null) // mesaj yok ise çıkar
					break;

				long deliveryTag = delivery.getEnvelope().getDeliveryTag();
				channel.basicAck(deliveryTag, true);

				if (processMessage(delivery)) {
				}

			}

			// en sonunda ise channel, connection ve Giris'i kapatıyoruz.
			channel.close();
			connection.close();
			Giris.close();
		} catch (Exception e) {
			e.printStackTrace(); // istisnai bir durumda hatayı printStackTrace ile yazdırırız.
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
