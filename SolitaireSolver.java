package solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class SolitaireSolver {
	
	/* 
	 * Order of moves by priority:
	 * 1. Move a card to the finishing pile
	 * 2. Move a pile of cards
	 * 3. Draw a card
	 */
	
	private CardShuffler shuffler;
	private String[] exampleDeck;
	private ArrayList<ArrayList<Card>> tableau;
	private ArrayList<ArrayList<Card>> foundations; // Spades, Clubs, Hearts, Diamonds
	private Stack<Card> stock;
	private Stack<Card> talon;
	
	public SolitaireSolver() {
		
		shuffler = new CardShuffler();
		exampleDeck = shuffler.getCompleteDeck();
		tableau = new ArrayList<ArrayList<Card>>();
		foundations = new ArrayList<ArrayList<Card>>();
		stock = new Stack<Card>();
		talon = new Stack<Card>();
		
		for(int i = 0; i < 7; i++) tableau.add(new ArrayList<Card>());
		
		for(int a = 0; a < 3; a++) foundations.add(new ArrayList<Card>());
		
	}
	
	public void setUp() {
		
		Iterator<String> deckIterator = shuffler.setUpAndGetDeck().iterator();
		
		for(int i = 0; i < 7; i++) {
			
			for(int a = i + 1; a > 0; a--) {
				
				if(a != 1) tableau.get(i).add(new Card(true, deckIterator.next()));
					
				else tableau.get(i).add(new Card(false, deckIterator.next()));
				
			}
			
		}
		
		while(deckIterator.hasNext()) stock.push(new Card(false, deckIterator.next()));
		
	}
	
	private void drawCard() {
		
		if(!(stock.isEmpty())) {
			
			Card nextCard = stock.pop();
			nextCard.flip();
			talon.push(nextCard);
		
		} else if(stock.isEmpty() && !(talon.isEmpty())){
			
			while(!(talon.empty())) {
				
				Card nextCard = talon.pop();
				nextCard.flip();
				stock.push(nextCard);
				
			}
			
		}
		
	}
	
	public void talonToFoundation() {
		
		if(moveCardToFoundation(talon.peek())) talon.pop();
		
	}
	
	public void talonToTableau() {
		
		for(int i = 0; i < 7; i++) {
			
			Card currentCard = talon.peek();
			Card tableauCard = tableau.get(i).get(tableau.get(i).size() - 1);

				if(tableauCard.isFaceUp()) {
						
					if(canAddCardOnto(currentCard, tableauCard)) {

						tableau.get(i).add(talon.pop());
						
					}
							
				}
						
			}
					
		}
	
	private boolean moveCardToFoundation(Card card) {

		ArrayList<String> eligibleCards = new ArrayList<String>();
			
		for(int a = 0; a < 4; a++) eligibleCards.add(exampleDeck[foundations.get(a).size()]);

		for(String type : eligibleCards) {
				
			if(card.getType().equals(type)) {
					
				card.movedToFinishPile();
					
				if(card.getSuit().equals("Spades")) {

					foundations.get(0).add(card);
						
				} else if(card.getSuit().equals("Clubs")) {
						
					foundations.get(1).add(card);
						
				} else if(card.getSuit().equals("Hearts")) {
						
					foundations.get(2).add(card);
						
				} else if(card.getSuit().equals("Diamonds")) {
						
					foundations.get(3).add(card);
						
				}
				
				return true;
					
			}
				
		}
		
		return false;
			
	}
	
	public void movePileFromTableau(int firstPile, int secondPile, int start) {

		for(int i = start; i < tableau.get(secondPile).size(); i++) {
			
			tableau.get(firstPile).add(tableau.get(secondPile).get(i));
			
		}
		
		flipTableauCards();

	}
	
	public ArrayList<String> pilesThatCanBeMoved() {
		
		ArrayList<Card> eligibleCards = new ArrayList<Card>();
		ArrayList<String> movePileCodes = new ArrayList<String>();
		
		for(int i = 0; i < 7; i++) {

			Card checkCard = tableau.get(i).get(tableau.get(i).size() - 1);
			
			if(checkCard.isFaceUp()) {
				
				eligibleCards.add(checkCard);
				
			} else {
				
				eligibleCards.add(null);
				
			}
		
		}
		
		for(int a = 0; a < 7; a++) {
			
			for(int b = 0; b < tableau.get(a).size(); b++) {
				
				Card currentCard = tableau.get(a).get(b);
				
				for(int c = 0; c < eligibleCards.size(); c++) {
					
					if(currentCard.isFaceUp() && eligibleCards.get(c) != null) {
						
						if(currentCard.getValue().equals(getPreviousValue(currentCard.getType()))) {
							
							if(!(currentCard.getColor().equals(eligibleCards.get(c).getColor().substring(0, 1)))){
								
								movePileCodes.add(c + a + b + "");
								
							} else if(currentCard.getColor().equals("r") &&
									eligibleCards.get(c).getColor().substring(0, 1).equals("b")) {
								
								movePileCodes.add(c + a + b + "");
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		return movePileCodes;
		
	}
	
	private boolean canAddCardOnto(Card currentCard, Card toAddOnto) {

		String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
		int toAddOntoIndex = 0;
		int currentCardIndex = 0;
		
		for(int i = 0; i < values.length; i++) {
			
			if(values[i].equals(toAddOnto.getType())) toAddOntoIndex = i;
			if(values[i].equals(currentCard.getType())) currentCardIndex = i;
			
		}
		
		if(toAddOntoIndex == 0 || toAddOntoIndex == 1) return false;
		
		return currentCardIndex == toAddOntoIndex - 1;
			
	}
	
	private String getPreviousValue(String currentCardType) {
		
		String thisCardsValue = "";
		
		for(int i = 0; i < exampleDeck.length; i++) {
			
			if(exampleDeck[i].equals(currentCardType)) thisCardsValue = exampleDeck[i - 1];
			break;
			
		}
		
		return thisCardsValue.substring(0, thisCardsValue.indexOf(" "));
		
	}
	
	
	public void moveAllEligibleCardsToFoundation() {

		for(int i = 0; i < 7; i++) {
			
			ArrayList<String> eligibleCards = new ArrayList<String>();
			
			for(int a = 0; a < 4; a++) eligibleCards.add(exampleDeck[foundations.get(a).size()]);
			
			Card foundationCard = tableau.get(i).get(tableau.get(i).size() - 1);
			String currentCardType = foundationCard.getType();
			String currentSuit = foundationCard.getSuit();
			
			for(String type : eligibleCards) {
				
				if(currentCardType.equals(type)) {
					
					foundationCard.movedToFinishPile();
					
					if(currentSuit.equals("Spades")) {

						foundations.get(0).add(tableau.get(i).get(tableau.get(i).size() - 1));
						
					} else if(currentSuit.equals("Clubs")) {
						
						foundations.get(1).add(tableau.get(i).get(tableau.get(i).size() - 1));
						
					} else if(currentSuit.equals("Hearts")) {
						
						foundations.get(2).add(tableau.get(i).get(tableau.get(i).size() - 1));
						
					} else if(currentSuit.equals("Diamonds")) {
						
						foundations.get(3).add(tableau.get(i).get(tableau.get(i).size() - 1));
						
					}
					
					flipTableauCards();
					
				}
				
			}
			
		}
		
	}
	
	
	public boolean canBeFinished() {
		
		ArrayList<String> eligibleCards = new ArrayList<String>();
		
		for(int i = 0; i < 4; i++) eligibleCards.add(exampleDeck[foundations.get(i).size()]);
		
		for(int a = 0; a < 7; a++) {
			
			if(eligibleCards.contains(tableau.get(a).get(tableau.get(a).size() - 1).getType())){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	
	public void flipTableauCards() {
		
		for(int i = 0; i < 7; i++) {
			
			Card tableauCard = tableau.get(i).get(tableau.get(i).size() - 1);
			
			if(!(tableauCard.isFaceUp())) tableauCard.flip();
			
		}
		
	}
	
	
	public boolean gameFinished() {
		
		boolean tableauEmpty = true;
		boolean foundationsFull = true;
		boolean stockEmpty = stock.isEmpty() ? true : false;
		boolean talonEmpty = talon.isEmpty() ? true : false;
		
		for(int i = 0; i < 7; i++) {
			
			if(tableau.get(i).size() > 0) tableauEmpty = false;
			
			if(i < 4 && foundations.get(i).isEmpty()) foundationsFull = false;
			
		}
		
		return tableauEmpty && foundationsFull && stockEmpty && talonEmpty;
		
	}

	
	public static void main(String[] args) {
		
		SolitaireSolver solver = new SolitaireSolver();
		solver.setUp();
		solver.drawCard();
		System.out.println();
		
	}


}
