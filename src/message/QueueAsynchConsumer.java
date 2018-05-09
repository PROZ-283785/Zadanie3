package message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class QueueAsynchConsumer implements MessageListener{
	
	private boolean messageReceived;
	private MoveMessage moveMessage;
	
	public QueueAsynchConsumer() {
		setMessageReceived(false);
		setMoveMessage(null);
	}
	
	

	@Override
	public void onMessage(Message message) {
		
		TextMessage msg = (TextMessage) message;
		
		Gson gson = new GsonBuilder().create();
		
		try {
			moveMessage = gson.fromJson(msg.getText(), MoveMessage.class);
		} catch (JsonSyntaxException | JMSException e) {
			e.printStackTrace();
		}

		setMessageReceived(true);
		setMoveMessage(moveMessage);
		System.out.printf("Odebrano wiadomosc: '%s' \n", moveMessage.toString());
		
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
