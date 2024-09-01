import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Player extends Participant{

    private String userInput;

    @Override
    public void printParticipantsHand(){
        System.out.println();
        System.out.println(getName() + "'s hand value: " + calculateParticipantsHandValue());
        System.out.println(getName() + "'s hand:");
        for (Card card : getHand()) {
            System.out.println(card.getFaceVaue() + " of " + card.getSuit());
        }
    }

    /* 
     * Ask player for their name
     */
    public void askPlayerName(Scanner scanner, Integer i, List<Player> listOfPlayers) {
        System.out.print("Player " + i + "'s name? ");
        userInput = scanner.nextLine();
        if (!userInput.trim().equals("") && userInput.matches("[A-Za-z]+")){
            setName(userInput);
            listOfPlayers.add(this);
        } else {
            System.out.println("Invalid name. Should contain only alphabets");
            askPlayerName(scanner, i, listOfPlayers);
        }
    }

    public boolean hasReached21() {
        return calculateParticipantsHandValue()==21;
    }

    /* 
     * To ask a player if they want to hit or stand
     */
    public void playerAction(Scanner scanner, Stack<Card> deck) {
        // Player cannot make more hits if his hand value is 21
        if (hasReached21()){
            return;
        }
        System.out.print(getName() + ", do you want to hit or stand? ");
        userInput = scanner.nextLine().trim();
        if (userInput.equals("hit")) {
            addCardToHand(deck.pop());
            printParticipantsHand();
            if (checkIfParticipantIsBusted()) {
                System.out.println(getName() + " is busted!!");
                setIsBusted(true);
            } else{
                playerAction(scanner, deck);
            }
        } 
        else if (!userInput.equals("stand")){
            System.out.println("Invalid action. Its either 'hit' or'stand'");
            playerAction(scanner, deck);
        }
    }
}