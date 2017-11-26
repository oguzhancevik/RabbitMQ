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

			// local'de tan�ml� i�lem yapaca��m�z queue isimlerimizi tan�ml�yoruz
			String queueA = "queueA";
			String queueB = "queueB";

			String Secim;
			String Mesaj;
			Date Zaman = new Date();

			System.out.println("Queue secin (a/b)");
			Scanner Giris = new Scanner(System.in); // Kullan�c� giri�i i�in Scanner komutu kullan�r�z
			Secim = Giris.nextLine();

			System.out.println("Mesaji giriniz:(" + Secim + ")");
			Mesaj = Giris.nextLine(); // girilen de�eri Mesaj de�i�kenine atad�k
			System.out.println(Mesaj);

			Mesaj = Zaman.toString() + " | " + Mesaj + " |";

			/*
			 * equalsIgnoreCase -> iki stringi b�y�k k���k harf ayr�m� yapmadan
			 * kar��la�t�r�r �r: a,A
			 */
			if (Secim.equalsIgnoreCase("a")) {
				channel.basicPublish("", queueA, null, Mesaj.getBytes());
			}

			if (Secim.equalsIgnoreCase("b")) {
				channel.basicPublish("", queueB, null, Mesaj.getBytes());
			}

			// en sonunda ise channel ve connection'� kapat�yoruz.
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace(); // istisnai bir durumda hatay� printStackTrace ile yazd�r�r�z.
		}

	}

}
