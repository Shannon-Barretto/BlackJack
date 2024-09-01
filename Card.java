public class Card {
    
    private String suit;
    private String faceValue;
    private int rank;
    private boolean isholeCard;

    public Card (String suit, String faceValue, Integer rank, boolean isholeCard) {
        this.suit = suit;
        this.faceValue = faceValue;
        this.rank = rank;
        this.isholeCard = isholeCard;
    }

    public String getSuit() {
        return this.suit;
    }
    
    public String getFaceVaue() {
        return this.faceValue;
    }
    
    public int getRank() {
        return this.rank;
    }

    public void setIsHoleCard(boolean isHoleCard){
        this.isholeCard = isHoleCard;
    }

    public boolean isHoleCard() {
        return this.isholeCard;
    }
}
