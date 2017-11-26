package rabbitmq;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

//rabbitmq i�in a�a��daki k�t�phaneleri kullanmam�z gerekmektedir.
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Consumer {

	/*
	 * Secim de�i�kenini static tan�mlalamam�z�n sebebi program �al��t��� s�rece
	 * kullan�lsada kullan�lmasada bir yer a�ar ve ayr�ca main d���ndada
	 * tan�mlamam�z�n sebebi main fonk. d���ndaki di�er fonk. eri�ilmek
	 * istenmesidir.
	 */
	static String Secim;

	public static void main(String[] args) throws Exception {

		try {
			ConnectionFactory factory = new ConnectionFactory();

			factory.setHost("localhost"); // host adresimizi tan�ml�yoruz

			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();

			QueueingConsumer consumer = new QueueingConsumer(channel);

			System.out.println("Queue se�in (a/b)");
			Scanner Giris = new Scanner(System.in);
			Secim = Giris.nextLine();

			if (Secim.equalsIgnoreCase("a")) {
				channel.basicConsume("queueA", consumer);
			}

			if (Secim.equalsIgnoreCase("b")) {
				channel.basicConsume("queueB", consumer);
			}

			boolean removeAllUpTo = true;
			while (true) {
				Delivery delivery = consumer.nextDelivery(5000);
				if (delivery == null) {
					break;
				}

				long deliveryTag = delivery.getEnvelope().getDeliveryTag();
				channel.basicAck(deliveryTag, removeAllUpTo);

				if (processMessage(delivery)) {

				}

			}

			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean processMessage(Delivery delivery) throws UnsupportedEncodingException {
		try {
			String msg = new String(delivery.getBody(), "UTF-8");
			System.out.println("[X] Yeni Mesaj(" + Secim + ")" + "\n" + msg);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}

	}

}
