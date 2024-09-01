import java.util.Stack;

public class Dealer extends Participant{

    private boolean isDealersTurn;

    public Dealer() {
        setName("Dealer");
        setIsDealersTurn(false);
    }

    public void setIsDealersTurn(boolean isDealersTurn){
        this.isDealersTurn = isDealersTurn;
    }

    public boolean getIsDealersTurn() {
        return this.isDealersTurn;
    }

    @Override
    public void printParticipantsHand(){
        System.out.println();
        if(getIsDealersTurn()){
            System.out.println(getName() + "'s hand value: " + calculateParticipantsHandValue());
        }
        System.out.println(getName() + "'s hand:");
        for (Card card : getHand()) {
            if(!getIsDealersTurn() && card.isHoleCard()) {
                System.out.println("Hidden Card");
            } else {
                System.out.println(card.getFaceVaue() + " of " + card.getSuit());
            }
        }
    }

    /* 
     * Dealer must hit until his hand value is 16 or less
     * The dealer must stand if their hand value is 17 or more
     */
    public boolean hasNotReached17() {
        return calculateParticipantsHandValue()<17;
    }

    public void dealersTurn(Stack<Card> deck) {
        printParticipantsHand();
        if (checkIfParticipantIsBusted()){
            System.out.println(getName() + " is busted!!");
            setIsBusted(true);
            return;
        }
        // Dealer must hit if his hand is less than 17
        if(hasNotReached17()) {
            System.out.println();
            System.out.println("Dealer hits");
            addCardToHand(deck.pop());
            dealersTurn(deck);
        } 
    }
}