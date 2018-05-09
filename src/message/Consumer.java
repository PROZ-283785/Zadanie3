package message;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Consumer {
	/*
	 * public void receiveQueueMessages() { ConnectionFactory connectionFactory
	 * = new com.sun.messaging.ConnectionFactory(); JMSContext jmsContext =
	 * connectionFactory.createContext(); try {
	 * ((com.sun.messaging.ConnectionFactory) connectionFactory) .setProperty(
	 * com.sun.messaging.ConnectionConfiguration.imqAddressList,
	 * "localhost:7676/jms"); Queue queue = new
	 * com.sun.messaging.Queue("TicTacToeQueu"); JMSConsumer jmsConsumer =
	 * jmsContext.createConsumer(queue);
	 * System.out.println("Czekam na wiadomosci"); String msg; while ((msg =
	 * jmsConsumer.receiveBody(String.class, 10)) != null) {
	 * System.out.printf("Odebrano wiadomosc: '%s' \n", msg); }
	 * jmsConsumer.close();
	 * 
	 * } catch (JMSException e) { e.printStackTrace(); } jmsContext.close(); }
	 */
	
	private boolean messageReceived;
	private MoveMessage moveMessage;
	
	public Consumer() {
		setMessageReceived(false);
		setMoveMessage(null);
	}

	public void receiveQueueMessagesAsynch() {

		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		JMSContext jmsContext = connectionFactory.createContext();
		try {
			((com.sun.messaging.ConnectionFactory) connectionFactory)
					.setProperty(
							com.sun.messaging.ConnectionConfiguration.imqAddressList,
							"localhost:7676/jms");
			Queue queue = new com.sun.messaging.Queue("TicTacToeQueue");
			JMSConsumer jmsConsumer = jmsContext.createConsumer(queue);
			QueueAsynchConsumer queueAsynchConsumer = new QueueAsynchConsumer();
			jmsConsumer.setMessageListener(queueAsynchConsumer);
			
			  /*for (int i = 0; i < 10; ++i) {
			  System.out.println("Konsument dziala"); try { Thread.sleep(1000);
			  } catch (InterruptedException e) { e.printStackTrace(); }
			  
			  }*/
			while(!queueAsynchConsumer.isMessageReceived()) {
				//System.out.println("Czekam");
			}
		
			moveMessage = queueAsynchConsumer.getMoveMessage();
			setMoveMessage(moveMessage); 
			setMessageReceived(true);

			jmsConsumer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		jmsContext.close();
	}
	
	public boolean isMessageReceived() {
		return messageReceived;
	}

	public void setMessageReceived(boolean messageReceived) {
		this.messageReceived = messageReceived;
	}

	public MoveMessage getMoveMessage() {
		return moveMessage;
	}

	public void setMoveMessage(MoveMessage moveMessage) {
		this.moveMessage = moveMessage;
	}

	
}
