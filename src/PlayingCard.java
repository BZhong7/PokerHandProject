//Brandon Zhong - CISC 3150 Summer 2017
//Poker Hand Comparator Project

public class PlayingCard implements Comparable<PlayingCard>
{
	//Returns an integer based on ranking of value; ranking of suit is tiebreaker.
  public int compareTo(PlayingCard card) {
    int rankCompare =  this.value.compareTo(card.value);
    return rankCompare != 0 ? rankCompare : this.suit.compareTo(card.suit);
  }
  
  //Each enum variable is associated with a String to allow for inputs from main based on letters and numbers.
  public enum Suit {
    HEART("H"), SPADE("S"), DIAMOND("D"), CLUB("C");
	private String stringSuit;
	
	private Suit(String stringSuit) {
		this.stringSuit = stringSuit;
	}
	
	//returns suit as an enum.
	public static Suit findSuit(String stringSuit) {
		for(Suit s : values()) {
			if(s.stringSuit.equals(stringSuit))
				return s;
		}
		return null;
	}
  };
  
  public enum Value {
    TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
    SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("J"),
    QUEEN("Q"), KING("K"), ACE("A");
	private String stringValue;
	
	private Value(String stringValue) {
		this.stringValue = stringValue;
	}
	
	public String getStringValue() {
		return stringValue;
	}
	
	//returns value as an enum.
	public static Value findValue(String stringValue) {
		for(Value v : values()) {
			if(v.stringValue.equals(stringValue))
				return v;
		}
		return null;
	}
  };

  public Suit suit;
  public Value value;

  //Constructor for PlayingCard.
  public PlayingCard(Suit suit, Value value)
  {
      this.suit = suit;
      this.value = value;
  }
/*
  public static void main(String args[])
  {
    PlayingCard C1 = new PlayingCard(
//      PlayingCard.Suit.HEART,
      Suit.findSuit("H"),
//      PlayingCard.Value.TWO);
      Value.findValue("2"));
    PlayingCard C2 = new PlayingCard(
      PlayingCard.Suit.SPADE,
      PlayingCard.Value.FIVE);
    System.out.println(C1.compareTo(C2));
    System.out.println(C2.compareTo(C1));
    System.out.println(C1.compareTo(C1));
  }
 */
}
