package solver;

public class Card {
	
	private String type;
	private boolean flipped;
	private boolean inFinishedPile;
	
	public Card(boolean flipped, String val) {
		
		this.flipped = flipped;
		type = val;
		inFinishedPile = false;
		
	}
	
	public void flip() {
		
		if(flipped) flipped = false;
		else flipped = true;
		
	}
	
	public boolean isFaceUp() {
		
		if(flipped) return false;
		
		return true;
		
	}
	
	public void movedToFinishPile() {
		
		if(!(inFinishedPile)) inFinishedPile = true;
		
	}
	
	public String getType() {
		
		return type;
		
	}
	
	public String getSuit() {
		
		return type.substring(type.lastIndexOf(" ") + 1);
		
	}
	
	public String getValue() {
		
		return type.substring(0, type.indexOf(" "));
		
	}
	
	public String getColor() {
		
		if(getSuit().equals("Spades") || getSuit().equals("Clubs")) {
			
			return "b";
			
		}
		
		return "r";
		
	}
	
}
