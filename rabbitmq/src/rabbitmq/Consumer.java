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
	 * kullanılsada kullanılmasada bir yer açar ve ayrıca main dışındada
	 * tanımlamamızın sebebi main fonk. dışındaki diğer fonk. erişilmek
	 * istenmesidir.
	 */
	static String Secim;

	public static void main(String[] args) throws Exception {

		try {
			ConnectionFactory factory = new ConnectionFactory();

			factory.setHost("localhost"); // host adresimizi tanımlıyoruz

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

			// en sonunda ise channel ve connection'ı kapatıyoruz.
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace(); // istisnai bir durumda hatayı printStackTrace ile yazdırırız.
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
