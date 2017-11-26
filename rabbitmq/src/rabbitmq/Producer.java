package rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

//rabbitmq için aþaðýdaki kütüphaneleri kullanmamýz gerekmektedir.
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	// IOException -> dosya hatasý
	// TimeoutException -> Bir iþlem veya iþlem için ayrýlan süresi sona erdiðinde
	// oluþturulan özel durum
	public static void main(String[] args) throws IOException, TimeoutException {

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost"); // host adresimizi tanýmlýyoruz

			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();

			// local'de tanýmlý iþlem yapacaðýmýz queue isimlerimizi tanýmlýyoruz
			String queueA = "queueA";
			String queueB = "queueB";

			String Secim;
			String Mesaj;
			Date Zaman = new Date();

			System.out.println("Queue secin (a/b)");
			Scanner Giris = new Scanner(System.in); // Kullanýcý giriþi için Scanner komutu kullanýrýz
			Secim = Giris.nextLine();

			System.out.println("Mesaji giriniz:(" + Secim + ")");
			Mesaj = Giris.nextLine(); // girilen deðeri Mesaj deðiþkenine atadýk
			System.out.println(Mesaj);

			Mesaj = Zaman.toString() + " | " + Mesaj + " |";

			/*
			 * equalsIgnoreCase -> iki stringi büyük küçük harf ayrýmý yapmadan
			 * karþýlaþtýrýr Ör: a,A
			 */
			if (Secim.equalsIgnoreCase("a")) {
				channel.basicPublish("", queueA, null, Mesaj.getBytes());
			}

			if (Secim.equalsIgnoreCase("b")) {
				channel.basicPublish("", queueB, null, Mesaj.getBytes());
			}

			// en sonunda ise channel ve connection'ý kapatýyoruz.
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace(); // istisnai bir durumda hatayý printStackTrace ile yazdýrýrýz.
		}

	}

}
