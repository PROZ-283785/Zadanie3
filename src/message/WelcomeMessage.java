package message;

/*
 * Sending message and waiting for opponent.
 */

public class WelcomeMessage {
	
	public enum Player{X,O};
	
	private Player player;
	private String opponentUsername;
	
	public WelcomeMessage(String name) {
		setPlayer(Player.X);
		setOpponentUsername(name);
	}
	
	
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public String getOpponentUsername() {
		return opponentUsername;
	}
	public void setOpponentUsername(String opponentUsername) {
		this.opponentUsername = opponentUsername;
	}
	
}
