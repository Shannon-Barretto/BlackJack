import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    private Stack<Card> deck;
    private String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
    private String[] faceValue = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    public Deck() {
        this.deck = new Stack<>();
    }

    /* 
     * Create the deck at the start of the game, or when dealing a new game
     */
    public void createDeck(){

        for (String suit : suits) {
            for (String face: faceValue) {
                if (face.equals("Jack") || face.equals("Queen") || face.equals("King"))
                    addCardToDeck(new Card(suit, face, 10, false));
                else if (face.equals("Ace"))
                    addCardToDeck(new Card(suit, face, 11, false));
                else
                    addCardToDeck(new Card(suit, face, Integer.parseInt(face), false));
            }
        }
    }

    /* 
     * Shuffle the deck
     */
    public void shuffleDeck() {
        createDeck();
        Collections.shuffle(getDeck());
    }

    public void addCardToDeck(Card card){
        this.deck.add(card);
    }

    public Stack<Card> getDeck() {
        return this.deck;
    }

    /* 
     * Show what everyone has
     */
    public void showDeck(List<Player> listOfPlayers, Dealer dealer){
        System.out.println("---------------- Deck ----------------");
        for (Player player: listOfPlayers) {
            player.printParticipantsHand();
        }
        dealer.printParticipantsHand();
        System.out.println("---------------- End of Deck ----------------");
    }

    /* 
     * Deal two cards to each player
     * For players the cards are shown while for the dealer, one is hidden and one shown
     */
    public void deal(List<Player> listOfPlayers, Dealer dealer) {
        for (Player player: listOfPlayers) {
            player.addCardToHand(deck.pop());
            player.addCardToHand(deck.pop());
        }
        
        Card holeCard = deck.pop();
        holeCard.setIsHoleCard(true);
        dealer.addCardToHand(holeCard);
        dealer.addCardToHand(deck.pop());
    }
}
