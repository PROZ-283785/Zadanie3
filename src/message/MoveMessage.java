package message;

import javafx.util.Pair;

/*
 * Message with move made by user.
 */


public class MoveMessage {
	
	public enum Player{X,O};
	
	private Pair<Integer, Integer> coords;
	private Player player;
	
	public MoveMessage(Integer x, Integer y, Player player) {
		setCoords(new Pair<>(x,y));
		setPlayer(player);
	}
	
	public Pair<Integer, Integer> getCoords() {
		return coords;
	}


	public void setCoords(Pair<Integer, Integer> coords) {
		this.coords = coords;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	
	
	
}
