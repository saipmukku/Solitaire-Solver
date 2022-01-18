package solver;

import java.util.ArrayList;
import java.util.Collections;

public class CardShuffler {

	ArrayList<String> returnedArrayList;
	String[] deck;
	
	public CardShuffler() {
		
		returnedArrayList = new ArrayList<String>();
		deck = new String[52];
		
	}

	public void setDeck() {
		
		deck = getCompleteDeck();
			
	}
	
	public void shuffleDeck() {
		
		ArrayList<String> arrListDeck = new ArrayList<String>();
		
		for(int i = 0 ; i < 52; i++) arrListDeck.add(deck[i]);
			
		for(int a = 0; a < 100; a++) Collections.shuffle(arrListDeck);
		
		for(int b = 0; b < 52; b++) deck[b] = arrListDeck.get(b);
		
	}
	
	public ArrayList<String> setUpAndGetDeck() {

		setDeck();
		shuffleDeck();
		
		for(String i : deck) returnedArrayList.add(i);
		
		return returnedArrayList;
			
	}

	public String[] getCompleteDeck() {
		
		String[] newDeck = new String[52];
		String[] suits = {"Spades", "Clubs", "Hearts", "Diamonds"};
		String[] nonNumericValues = {"Ace", "Jack", "Queen", "King"};

		for(int i = 0; i < 52; i++) {
				
			if(i % 13 == 0) {
				
				newDeck[i] = nonNumericValues[0] + " of " + suits[i / 13];
								
			} else if (i % 13 < 10) {
					
				newDeck[i] = (i % 13) + " of " + suits[i / 13];
					
			} else {
					
				newDeck[i] = nonNumericValues[(i % 13) - 9] + " of " + suits[i / 13];
					
			}
				
		}
		
		return newDeck;
		
	}
	
}