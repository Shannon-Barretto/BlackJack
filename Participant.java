import java.util.ArrayList;
import java.util.List;

public abstract class Participant {

    private String name;
    private List<Card> hand;
    private boolean isBusted;
    private boolean win;

    public Participant() {
        this.hand = new ArrayList<>();
        this.isBusted = false;
        this.win = false;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void addCardToHand(Card card) {
        this.hand.add(card);
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public void setIsBusted(boolean isBusted) {
        this.isBusted = isBusted;
    }

    public boolean getIsBusted() {
        return this.isBusted;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean getWin() {
        return this.win;
    }

    public Integer calculateParticipantsHandValue() {
        int[] sum = {0};
        getHand().forEach(hand -> sum[0]+=hand.getRank());
        return sum[0];
    }
    
    public boolean checkIfParticipantIsBusted() {
        return calculateParticipantsHandValue()>21;
    }

    public boolean hasBlackJack() {
        if (calculateParticipantsHandValue()==21 && getHand().size()==2) {
            setWin(true);
            return true;
        } else {
            return false;
        }
    }

    abstract void printParticipantsHand();
}