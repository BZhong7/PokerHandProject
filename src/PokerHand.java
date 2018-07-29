//Brandon Zhong - CISC 3150 Summer 2017
//Poker Hand Comparator Project

/*
 * Hand comparator follows standard Poker rules.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PokerHand implements Comparable<PokerHand>
{	
  public int compareTo(PokerHand otherHand) {
    return this.rank.compareTo(otherHand.rank);
  }
  
  public enum Rank {
	  HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT,
	  FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH, ROYAL_FLUSH
  };
  
  //Checks for number of pairs, three-of-a-kind, and four-of-a-kind.
  //If two cards have matching values, same[] evaluates to true and increments a counter.
  //Determining the rank of the matching occurs when same evaluates to false or after
  //the final comparison has been made.
  public static void checkHandCombo(PokerHand otherHand) {
	  int countSameCard = 0; //Number of matching cards.
	  
	  if(otherHand.hand.get(0).value == otherHand.hand.get(1).value) {
		  otherHand.same[0] = true;
		  countSameCard++;
	  }
	  else
		  otherHand.same[0] = false;
		  
	  if(otherHand.hand.get(1).value == otherHand.hand.get(2).value) {
		  otherHand.same[1] = true;
		  countSameCard++;
	  }
	  else {
		  otherHand.same[1] = false;
		  
		  if(countSameCard == 1)
			  otherHand.pair++;
		
		  countSameCard = 0;
	  }
		  
	  if(otherHand.hand.get(2).value == otherHand.hand.get(3).value) {
		  otherHand.same[2] = true;
		  countSameCard++;
	  }
	  else {
		  otherHand.same[2] = false;
		  
		  if(countSameCard == 1)
			  otherHand.pair++;
		  else if(countSameCard == 2)
			  otherHand.threeKind++;
			  
		  countSameCard = 0;
	  }
	  
	  if(otherHand.hand.get(3).value == otherHand.hand.get(4).value) {
		  otherHand.same[3] = true;
		  countSameCard++;
		  
		  if(countSameCard == 1)
			  otherHand.pair++;		  
		  else if(countSameCard == 2)
			  otherHand.threeKind++;		  
		  else if(countSameCard == 3)
			  otherHand.fourKind++;
	  }
	  else {
		  otherHand.same[3] = false;
		  
		  if(countSameCard == 1)
			  otherHand.pair++;
		  else if(countSameCard == 2)
			  otherHand.threeKind++;
		  else if(countSameCard == 3)
			  otherHand.fourKind++;
	  }
  }
  
  //Assigns rank based on results of checkHandCombo.
  public static void assignRank(PokerHand otherHand) {
	  
	  if(otherHand.pair == 2)
		  checkTwoPair(otherHand);
	  else if(otherHand.pair == 1) {
		  if(otherHand.threeKind == 1)
			  checkFullHouse(otherHand);
		  else
			  checkOnePair(otherHand);
	  }
	  else if(otherHand.threeKind == 1)
		  checkThreeKind(otherHand);
	  else if(otherHand.fourKind == 1)
		  checkFourKind(otherHand);
	  else
		  checkStraightOrFlush(otherHand);
  }
  
  //Checks all possible two pair locations.
  public static void checkTwoPair(PokerHand otherHand) {
	  otherHand.rank = Rank.TWO_PAIR;
	  otherHand.otherCards.add(otherHand.hand.get(1));	//Based on hand rank, certain cards can always be found in certain areas.
	  otherHand.otherCards.add(otherHand.hand.get(3));	//Guaranteed locations for two pair cards.
	  
	  System.out.print("(" + otherHand.rank + ", " + otherHand.hand.get(1).value.getStringValue()
				+ ", " + otherHand.hand.get(3).value.getStringValue());
	  
	  //hand[0] and hand[1]; hand[2] and hand[3].
	  if(otherHand.same[0] == true && otherHand.same[2] == true) {
		  otherHand.otherCards.add(otherHand.hand.get(4));
		  System.out.println(", " + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
	  //hand[0] and hand[1]; hand[3] and hand[4].
	  else if(otherHand.same[0] == true && otherHand.same[3] == true) {
		  otherHand.otherCards.add(otherHand.hand.get(2));
		  System.out.println(", " + otherHand.hand.get(2).value.getStringValue() + ")");
	  }
	  //hand[1] and hand[2]; hand[3] and hand[4].
	  else {
		  otherHand.otherCards.add(otherHand.hand.get(0));
		  System.out.println(", " + otherHand.hand.get(0).value.getStringValue() + ")");
	  }
		  
  }
  
  //Searches for possible pair and three-of-a-kind locations.
  public static void checkFullHouse(PokerHand otherHand) {
	  otherHand.rank = Rank.FULL_HOUSE;
	  otherHand.otherCards.add(otherHand.hand.get(2));	//Guaranteed location for a card from a pair or three-of-a-kind.
	  
	  //If hand[2] == hand[3], three-of-a-kind is the last 3 cards with one pair being the first two.
	  if(otherHand.same[2] == true) {
		  otherHand.otherCards.add(otherHand.hand.get(1));
		  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(2).value.getStringValue()
				+ ", " + otherHand.hand.get(1).value.getStringValue() + ")");
	  }
	  //If hand[1] == hand[2], three-of-a-kind is the first 3 cards.
	  else {
		  otherHand.otherCards.add(otherHand.hand.get(3));
		  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(2).value.getStringValue()
				+ ", " + otherHand.hand.get(3).value.getStringValue() + ")");
	  }
  }
  
  //Searches for one_pair location.
  public static void checkOnePair(PokerHand otherHand) {
	  otherHand.rank = Rank.ONE_PAIR;
	  
	  System.out.print("(" + otherHand.rank + ", ");	//No guaranteed location for card from pair.
	  
	  //hand[0] == hand[1]
	  if(otherHand.same[0] == true) {
		  otherHand.otherCards.add(otherHand.hand.get(0));	//Add card from one pair first before adding the rest.
		  otherHand.otherCards.add(otherHand.hand.get(2));
		  otherHand.otherCards.add(otherHand.hand.get(3));
		  otherHand.otherCards.add(otherHand.hand.get(4));
		  System.out.println(otherHand.hand.get(0).value.getStringValue() + ", "
				  			 + otherHand.hand.get(2).value.getStringValue() + ", "
				  			 + otherHand.hand.get(3).value.getStringValue() + ", "
				  			 + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
	  //hand[1] == hand[2]
	  else if(otherHand.same[1] == true) {
		  otherHand.otherCards.add(otherHand.hand.get(1));
		  otherHand.otherCards.add(otherHand.hand.get(0));
		  otherHand.otherCards.add(otherHand.hand.get(3));
		  otherHand.otherCards.add(otherHand.hand.get(4));
		  System.out.println(otherHand.hand.get(1).value.getStringValue() + ", "
		  			 + otherHand.hand.get(0).value.getStringValue() + ", "
		  			 + otherHand.hand.get(3).value.getStringValue() + ", "
		  			 + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
	  //hand[2] == hand[3]
	  else if(otherHand.same[2] == true) {
		  otherHand.otherCards.add(otherHand.hand.get(2));
		  otherHand.otherCards.add(otherHand.hand.get(0));
		  otherHand.otherCards.add(otherHand.hand.get(1));
		  otherHand.otherCards.add(otherHand.hand.get(4));
		  System.out.println(otherHand.hand.get(2).value.getStringValue() + ", "
		  			 + otherHand.hand.get(0).value.getStringValue() + ", "
		  			 + otherHand.hand.get(1).value.getStringValue() + ", "
		  			 + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
	  //hand[3] == hand[4]
	  else {
		  otherHand.otherCards.add(otherHand.hand.get(3));
		  otherHand.otherCards.add(otherHand.hand.get(0));
		  otherHand.otherCards.add(otherHand.hand.get(1));
		  otherHand.otherCards.add(otherHand.hand.get(2));
		  System.out.println(otherHand.hand.get(3).value.getStringValue() + ", "
		  			 + otherHand.hand.get(0).value.getStringValue() + ", "
		  			 + otherHand.hand.get(1).value.getStringValue() + ", "
		  			 + otherHand.hand.get(2).value.getStringValue() + ")");
	  }
  }
  
  //Checks for three-of-a-kind location.
  public static void checkThreeKind(PokerHand otherHand) {
	  otherHand.rank = Rank.THREE_OF_A_KIND;
	  otherHand.otherCards.add(otherHand.hand.get(2));	//Guaranteed location for card from three-of-a-kind.
	  
	  //If true, three-of-a-kind is last 3 cards.
	  if(otherHand.same[0] == false && otherHand.same[1] == false) {
		  otherHand.otherCards.add(otherHand.hand.get(0));		  
		  otherHand.otherCards.add(otherHand.hand.get(1));
		  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(2).value.getStringValue()
				+ ", " + otherHand.hand.get(0).value.getStringValue()
				+ ", " + otherHand.hand.get(1).value.getStringValue() + ")");
	  }
	  //Else if true, three-of-a-kind is middle 3 cards.
	  else if(otherHand.same[0] == false && otherHand.same[3] == false) {
		  otherHand.otherCards.add(otherHand.hand.get(0));		  
		  otherHand.otherCards.add(otherHand.hand.get(4));
		  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(2).value.getStringValue()
				+ ", " + otherHand.hand.get(0).value.getStringValue()
				+ ", " + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
	  //Otherwise, three-of-a-kind is first 3 cards.
	  else {
		  otherHand.otherCards.add(otherHand.hand.get(3));		  
		  otherHand.otherCards.add(otherHand.hand.get(4));
		  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(2).value.getStringValue()
				+ ", " + otherHand.hand.get(3).value.getStringValue()
				+ ", " + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
  }
  
  //Checks for four-of-a-kind location.
  public static void checkFourKind(PokerHand otherHand) {
	  otherHand.rank = Rank.FOUR_OF_A_KIND;
	  otherHand.otherCards.add(otherHand.hand.get(2));	//Guaranteed location for card from four-of-a-kind.
	  
	  //If true, four-of-a-kind is first 4 cards.
	  if(otherHand.same[3] == false) {
		  otherHand.otherCards.add(otherHand.hand.get(4));		  
		  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(2).value.getStringValue()
				+ ", " + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
	  //Otherwise, four-of-a-kind is last 4 cards.
	  else {
		  otherHand.otherCards.add(otherHand.hand.get(0));		  
		  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(2).value.getStringValue()
				+ ", " + otherHand.hand.get(0).value.getStringValue() + ")");
	  }
  }
  
  //Checks for straights, flushes, straight flushes, and royal flushes.
  //Defaults to high card if no possible combination can be found.
  public static void checkStraightOrFlush(PokerHand otherHand) {
	  //Test for exceptional case of hand A,5,4,3,2.
	  //5 is the high card/kicker.
	  if(otherHand.hand.get(0).value.getStringValue() == "A"
			  && otherHand.hand.get(1).value.getStringValue() == "5"
			  && otherHand.hand.get(2).value.getStringValue() == "4"
			  && otherHand.hand.get(3).value.getStringValue() == "3"
			  && otherHand.hand.get(4).value.getStringValue() == "2") {
		  
		  otherHand.otherCards.add(otherHand.hand.get(1)); //Add 5 as the high card/kicker.
		  
		  //Determine if exceptional case is straight flush or straight,
		  if(otherHand.sameSuit == true) {
			  otherHand.rank = Rank.STRAIGHT_FLUSH;
			  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(1).value.getStringValue() + ")");
		  }
		  else {
			  otherHand.rank = Rank.STRAIGHT;
			  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(1).value.getStringValue() + ")");
		  }
	  }
	  
	  //Check for straight first, then find out if it's royal flush, straight flush, or straight.
	  else if(otherHand.hand.get(0).compareTo(otherHand.hand.get(1)) == 1
			  && otherHand.hand.get(1).compareTo(otherHand.hand.get(2)) == 1
			  && otherHand.hand.get(2).compareTo(otherHand.hand.get(3)) == 1
			  && otherHand.hand.get(3).compareTo(otherHand.hand.get(4)) == 1) {
		  
		  otherHand.otherCards.add(otherHand.hand.get(0));	//First card of a straight is always added as the kicker.
		  
		  //Test exceptional case of hand A, K, Q, J, 10; If same suit, royal flush.
		  //Otherwise, straight flush.
		  if(otherHand.sameSuit == true) {
			  if(otherHand.hand.get(0).value.getStringValue() == "A"
					  && otherHand.hand.get(1).value.getStringValue() == "K"
					  && otherHand.hand.get(2).value.getStringValue() == "Q"
					  && otherHand.hand.get(3).value.getStringValue() == "J"
					  && otherHand.hand.get(4).value.getStringValue() == "10") {
				  otherHand.rank = Rank.ROYAL_FLUSH;
				  System.out.println("(" + otherHand.rank + ", A)");
			  }
			  else {
				  otherHand.rank = Rank.STRAIGHT_FLUSH;
				  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(0).value.getStringValue() + ")");
			  }
		  }
		  //If not same suit, just straight.
		  else {
			  otherHand.rank = Rank.STRAIGHT;
			  System.out.println("(" + otherHand.rank + ", " + otherHand.hand.get(0).value.getStringValue() + ")");
		  }
	  }
	  //If not a straight, check for flush.
	  else if(otherHand.sameSuit == true) {
		  otherHand.rank = Rank.FLUSH;
		  for(int i = 0; i < 5; i++)
			  otherHand.otherCards.add(otherHand.hand.get(i));	//All the cards of a flush are added as kickers.
		  
		  System.out.println("(" + otherHand.rank + ", "
				  + otherHand.hand.get(0).value.getStringValue() + ", "
				  + otherHand.hand.get(1).value.getStringValue() + ", "
				  + otherHand.hand.get(2).value.getStringValue() + ", "
				  + otherHand.hand.get(3).value.getStringValue() + ", "
				  + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
	  //If not a flush, it's a high card.
	  else {
		  otherHand.rank = Rank.HIGH_CARD;
		  for(int i = 0; i < 5; i++)
			  otherHand.otherCards.add(otherHand.hand.get(i));	//All the cards of a high card are added as kickers.
		  
		  System.out.println("(" + otherHand.rank + ", "
				  + otherHand.hand.get(0).value.getStringValue() + ", "
				  + otherHand.hand.get(1).value.getStringValue() + ", "
				  + otherHand.hand.get(2).value.getStringValue() + ", "
				  + otherHand.hand.get(3).value.getStringValue() + ", "
				  + otherHand.hand.get(4).value.getStringValue() + ")");
	  }
  }
  
  //Global variables for PokerHand class.
  public ArrayList<PlayingCard> hand, otherCards; // hand is sorted PokerHand; otherCards holds all the kickers/high cards of ranked PokerHand.
  public Rank rank; //Poker hand rank.
  public int pair, threeKind, fourKind;
  public boolean same[], sameSuit; //same[] is used to check for various card combos;
  								   //sameSuit checks to see if all cards are the same suit or not.
  
  //PokerHand constructor.
  public PokerHand(PlayingCard C1,
                   PlayingCard C2,
                   PlayingCard C3,
                   PlayingCard C4,
                   PlayingCard C5) {
	  
	hand = new ArrayList<PlayingCard>();
    hand.add(C1);
    hand.add(C2);
    hand.add(C3);
    hand.add(C4);
    hand.add(C5);
    
    try {
    	Collections.sort(hand);
    	Collections.swap(hand, 0, 4);
    	Collections.swap(hand, 1, 3);	//Sorted from highest to lowest.
    }
    catch (NullPointerException e){
    	System.err.println("Invalid card(s) entered.");
    }
    
    pair = threeKind = fourKind = 0;
    same = new boolean [4];
    otherCards = new ArrayList<PlayingCard>();
    
    for(int i = 0; i < 4; i++)
    	same[i] = false;
    
    if(hand.get(0).suit == hand.get(1).suit
    		&& hand.get(1).suit == hand.get(2).suit
    		&& hand.get(2).suit == hand.get(3).suit
    		&& hand.get(3).suit == hand.get(4).suit)
    	sameSuit = true;
    else
    	sameSuit = false;
  }
  
  public static void main(String arg[]) {
	  PlayingCard[] cards = new PlayingCard[10];
	  
		Scanner cin = new Scanner(System.in);
	  
		System.out.println("Enter Player 1's hand: ");
	  	for(int i = 0; i < 5; i++) {
	  		String stringValue = cin.next();
	  		String stringSuit = cin.next();
	  		cards[i] = new PlayingCard(PlayingCard.Suit.findSuit(stringSuit), PlayingCard.Value.findValue(stringValue));
	  	}
	  	PokerHand player1 = new PokerHand(cards[0], cards[1], cards[2], cards[3], cards[4]);
	  		
	  	System.out.println("Enter Player 2's hand: ");
	  	for(int j = 5; j < 10; j++) {
	  		String stringValue = cin.next();
	  		String stringSuit = cin.next();
	  		cards[j] = new PlayingCard(PlayingCard.Suit.findSuit(stringSuit), PlayingCard.Value.findValue(stringValue));
	  	}
	  	PokerHand player2 = new PokerHand(cards[5], cards[6], cards[7], cards[8], cards[9]);
	  
	  		checkHandCombo(player1);
	  		checkHandCombo(player2);
	  		
	  	try {
	  		System.out.print("Player 1: ");
	  		assignRank(player1);
	  		
	  		System.out.print("Player 2: ");
	  		assignRank(player2);
	  		
	  		int rankCompare = player1.compareTo(player2);
		  
	  		 if(rankCompare < 0)
	  			System.out.println("Player 2 wins.");
	  		else if(rankCompare > 0)
	  			System.out.println("Player 1 wins.");
	  		else if(rankCompare == 0) {		//If ranks are tied, use kickers as tiebreaker.
	  			int valueCompare = 0;		//Only the value of the card matters for tiebreakers. Suit does not matter.
	  			for(int j = 0; j < player1.otherCards.size(); j++) {
	  				valueCompare = player1.otherCards.get(j).value.compareTo(player2.otherCards.get(j).value);
	  				if(valueCompare < 0) {
	  					System.out.println("Player 2 wins.");
	  					break;
	  				}
	  				else if(valueCompare > 0) {
	  					System.out.println("Player 1 wins.");
	  					break;
	  				}
	  			}
	  			if(valueCompare == 0)
	  				System.out.println("Draw.");
	  		}	
	  	}
	  	catch (NullPointerException e){
	  		System.err.println("Unable to assign rank.");
	  	}
	  	cin.close();
  }
  
}

