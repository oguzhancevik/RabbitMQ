package rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	public static void main(String[] args) throws IOException, TimeoutException {

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");

			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();

			String queueA = "queueA";
			String queueB = "queueB";
			String Secim;
			String Mesaj;
			Date Zaman = new Date();

			System.out.println("Queue secin (a/b)");
			Scanner Giris = new Scanner(System.in);
			Secim = Giris.nextLine();

			System.out.println("Mesaji giriniz:(" + Secim + ")");
			Mesaj = Giris.nextLine();
			System.out.println(Mesaj);

			Mesaj = Zaman.toString() + " | " + Mesaj + " |";

			if (Secim.equalsIgnoreCase("a") || Secim.equalsIgnoreCase("A")) {
				channel.basicPublish("", queueA, null, Mesaj.getBytes());
			}

			if (Secim.equalsIgnoreCase("b") || Secim.equalsIgnoreCase("B")) {
				channel.basicPublish("", queueB, null, Mesaj.getBytes());
			}

			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
