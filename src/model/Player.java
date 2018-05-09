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
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if(!username.equals(other.getUsername()))
			return false; 
		if(username.equals(other.getUsername()) && figure != other.getFigure())
			return false;
		return true;
	}
	
}
