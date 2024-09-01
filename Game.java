import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Game {

    private List<Player> listOfPlayers;
    private int numberOfPlayers;
    private String userInput;
    private boolean allPlayersHaveBlackJack;

    Scanner scanner = new Scanner(System.in);

    public void startGame() {
        
        // Ask how many players are playing
        numberOfPlayers = numberOfPlayers(scanner);

        setlistOfPlayers();

        // Ask every player's name
        playersName(scanner, numberOfPlayers, getlistOfPlayers());

        System.out.println();
        System.out.println("------------------------------------------------ START GAME ------------------------------------------------");
        System.out.println();

        Dealer dealer = new Dealer();
        
        Deck deckObject = new Deck();
        deckObject.shuffleDeck();
        Stack<Card> deck = deckObject.getDeck();

        // deal two cards to each player, including the dealer
        deckObject.deal(getlistOfPlayers(), dealer);

        // Show what everyone has
        deckObject.showDeck(getlistOfPlayers(), dealer);

        while (true) {
            setAllPlayersHaveBlackJack(true);
            if(dealer.hasBlackJack()){
                dealer.setIsDealersTurn(true);
                // To print out he won against all players
                dealer.setWin(true);
                dealer.printParticipantsHand();
                System.out.println(dealer.getName() + " HAS BLACKJACK!!");
                System.out.println();
                for (Player player: getlistOfPlayers()) {
                    // if dealer and some player has blackjack: its a push
                    if (player.hasBlackJack()) {
                        dealer.setWin(false);
                        player.printParticipantsHand();
                        System.out.println(player.getName() + " HAS BLACKJACK! " + player.getName() + " has tied with the dealer. ITS A PUSH!!");
                    } else{
                        System.out.println(player.getName() + " lost!!");
                    }
                }
                // if dealer has blackjack and no players has then dealer win
                if (dealer.getWin()) {
                    System.out.println();
                    System.out.println(dealer.getName() + " WON!!");
                }
                // Ask if you want to start again
                askRestartGame();
                break;

            } else {
                for (Player player : getlistOfPlayers()) {
                    player.printParticipantsHand();
                    // if player has blackjack
                    if (player.hasBlackJack()){
                        // To filter players from those who havent won
                        player.setWin(true);
                        System.out.println(player.getName() + " HAS BLACKJACK!!");
                        System.out.println(player.getName() + " wins!!");
                    } else {
                        // All the players turns who do not have BlackJack
                        setAllPlayersHaveBlackJack(false);
                        player.playerAction(scanner, deck);
                    }
                }
            }

            // If all players have blackjack and dealer does not then players won, restart game
            if(getAllPlayersHaveBlackJack()){
                // Ask if you want to start again
                askRestartGame();
                break;
            }

            // Remove players who have been busted or won with blackjack
            setUpdatedlistOfPlayers();

            if(hasPlayers()) {
                dealer.setIsDealersTurn(true);
                dealer.dealersTurn(deck);
            } else {
                System.out.println();
                System.out.println(dealer.getName() + " WON!!");
                // Ask if you want to restart the game
                askRestartGame();
                break;
            }

            if (!dealer.getIsBusted()){
                decideWinner(dealer);
            } else {
                remainingPlayersWon();
            }

            // Ask if you want to restart the game
            askRestartGame();

            break;
        }
        scanner.close();
    }

    public void setAllPlayersHaveBlackJack(boolean allPlayersHaveBlackJack) {
        this.allPlayersHaveBlackJack = allPlayersHaveBlackJack;
    }

    public boolean getAllPlayersHaveBlackJack() {
        return this.allPlayersHaveBlackJack;
    }

    public void setlistOfPlayers() {
        List<Player> listOfPlayers = new ArrayList<>();
        this.listOfPlayers = listOfPlayers;
    }

    public List<Player> getlistOfPlayers() {
        return this.listOfPlayers;
    }

    /* 
     * Remove the list of players that have been busted or have won with blackjack
     */
    public void setUpdatedlistOfPlayers() {
        List<Player> updatedListWithoutBustedPlayers = getlistOfPlayers();
        updatedListWithoutBustedPlayers = updatedListWithoutBustedPlayers.stream()
                                                                    .filter(player -> !player.getIsBusted() && !player.getWin())
                                                                    .collect(Collectors.toList());
        this.listOfPlayers = updatedListWithoutBustedPlayers;
    }

    /* 
     * Return true if there is atleast one player to play
     */
    public boolean hasPlayers(){
        return getlistOfPlayers().size()>0;
    }

    /* 
     * Ask how many players are playing
     */
    public Integer numberOfPlayers(Scanner scanner) {
        System.out.print("How many players are playing? ");
        userInput = scanner.nextLine();
        if (userInput.matches("0|[1-9]\\d*")) {
            return Integer.valueOf(userInput);
        } else {
            System.out.println("Invalid number. Try again");
            return numberOfPlayers(scanner);
        }
    }

    /* 
     * Ask every player's name
     */
    public void playersName(Scanner scanner, Integer numberOfPlayers, List<Player> listOfPlayers) {
        for (int i = 1; i<=numberOfPlayers; i++) {
            Player player = new Player();
            player.askPlayerName(scanner, i, listOfPlayers);
        }
    }

    /* 
     * If dealer gets busted, the players who have not been busted won
     */
    public void remainingPlayersWon() {
        for (Player player: getlistOfPlayers()) {
            System.out.println();
            System.out.println(player.getName() + " WON!!");
        }
    }

    /* 
     * Decide the winner of the game if the dealer did not win by blackjack and there is atleast 1 player who is still in the game
     */
    public void decideWinner(Dealer dealer) {
        int dealerHand = dealer.calculateParticipantsHandValue();
        for (Player player : getlistOfPlayers()) {
            int playerHand = player.calculateParticipantsHandValue();
            System.out.println();
            if (playerHand>dealerHand) {
                System.out.println(player.getName() + " WON!!");
            } else if (playerHand == dealerHand) {
                System.out.println(player.getName() + " has tied with the dealer. ITS A PUSH!!");
            } else {
                System.out.println(dealer.getName() + " won against " + player.getName());
            }
        }
    }

    /* 
     * Ask if you want to start again
     */
    public void askRestartGame() {
        System.out.println();
        System.out.println("Want to play an other round??");
        userInput = scanner.nextLine().trim();
        if(userInput.equals("yes")){
            startGame();
        } else if (!userInput.equals("no")) {
            System.out.println("invalid action. Type either 'yes' or 'no'.");;
            askRestartGame();
        }
    }
}
