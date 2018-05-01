package model;

public class Player {
	
	public enum Figure{X,O};
	
	private String username;
	private Figure figure;
	
	public Player() {
		setUsername("Gracz");
		setFigure(Figure.X);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Figure getFigure() {
		return figure;
	}
	public void setFigure(Figure figure) {
		this.figure = figure;
	}

}
