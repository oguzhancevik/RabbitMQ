package rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

//rabbitmq için aşağıdaki kütüphaneleri kullanmamız gerekmektedir.
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	// IOException -> dosya hatası
	// TimeoutException -> Bir işlem veya işlem için ayrılan süresi sona erdiğinde
	// oluşturulan özel durum
	public static void main(String[] args) throws IOException, TimeoutException {

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost"); // host adresimizi tanımlıyoruz

			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();

			// local'de tanımlı işlem yapacağımız queue isimlerimizi tanımlıyoruz
			String queueA = "queueA";
			String queueB = "queueB";

			String Secim;
			String Mesaj;
			Date Zaman = new Date();

			System.out.println("Queue secin (a/b)");
			Scanner Giris = new Scanner(System.in); // Kullanıcı girişi için Scanner komutunu kullanırız
			Secim = Giris.nextLine();

			System.out.println("Mesaji giriniz:(" + Secim + ")");
			Mesaj = Giris.nextLine(); // Girilen değeri Mesaj değişkenine atadık
			System.out.println(Mesaj);

			Mesaj = Zaman.toString() + " | " + Mesaj + " |";

			/*
			 * equalsIgnoreCase -> iki stringi büyük küçük harf ayrımı yapmadan
			 * karşılaştırır: a,A
			 */
			if (Secim.equalsIgnoreCase("a")) {
				channel.basicPublish("", queueA, null, Mesaj.getBytes());
			}

			if (Secim.equalsIgnoreCase("b")) {
				channel.basicPublish("", queueB, null, Mesaj.getBytes());
			}

			// en sonunda ise channel ve connection'ı kapatıyoruz.
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace(); // istisnai bir durumda hatayı printStackTrace ile yazdırırız.
		}

	}

}
