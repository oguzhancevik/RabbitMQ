package rabbitmq;

import com.rabbitmq.client.Connection;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Consumer {
	
	static String Secim;

	public static void main(String[] args) throws Exception {
		
		ConnectionFactory factory=new ConnectionFactory();
		
		factory.setHost("localhost");
		
		Connection connection=factory.newConnection();
		
		Channel channel=connection.createChannel();
		
		QueueingConsumer consumer=new QueueingConsumer(channel);
		
		System.out.println("Queue seçin (a/b)");
		Scanner giris=new Scanner(System.in);
		Secim=giris.nextLine();
		
		if(Secim.equalsIgnoreCase("a")||Secim.equalsIgnoreCase("A")){			
			channel.basicConsume("queueA", consumer);
		}
		
		if(Secim.equalsIgnoreCase("b")||Secim.equalsIgnoreCase("B")){			
			channel.basicConsume("queueB", consumer);
		}
		
		
		
		boolean removeAllUpTo=true;
		while (true) {
			Delivery delivery=consumer.nextDelivery(5000);
			if (delivery==null) {
				break;
			}

			long deliveryTag=delivery.getEnvelope().getDeliveryTag();
			channel.basicAck(deliveryTag, removeAllUpTo);
			
			if (processMessage(delivery))
			{
				
			}
			
		}
		
		channel.close();
		connection.close();

	}
	
	private static boolean processMessage(Delivery delivery) throws UnsupportedEncodingException {
		String msg=new String (delivery.getBody(),"UTF-8");
		System.out.println("[X] Yeni Mesaj("+Secim+")"+"\n"+msg);
		return false;

	}

}
