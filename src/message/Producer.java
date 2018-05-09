package message;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.messaging.ConnectionFactory;

public class Producer {

	public void sendQueueMessages(MoveMessage moveMessage) {
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		try {
			((com.sun.messaging.ConnectionFactory) connectionFactory)
					.setProperty(
							com.sun.messaging.ConnectionConfiguration.imqAddressList,
							"localhost:7676/jms");
			JMSContext jmsContext = connectionFactory.createContext();
			JMSProducer jmsProducer = jmsContext.createProducer();
			Queue queue = new com.sun.messaging.Queue("TicTacToeQueue");

			Gson message = new GsonBuilder().create();
			String msg = "Message " + moveMessage.toString();
			jmsProducer.send(queue, message.toJson(moveMessage));
			System.out.printf("Wiadomosc '%s' wyslana \n", msg);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
