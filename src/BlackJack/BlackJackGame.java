
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package BlackJack;
/**
 *
 * @author rajvindersandhu
 */


/**
 *
 * @author rajvindersandhu
 */
public class BlackJackGame {
    ArrayList<Card> deck = new ArrayList<Card>();
    ArrayList<Card> playercard = new ArrayList<Card>();
    ArrayList<Card> dealercard = new ArrayList<Card>();
    Scanner input = new Scanner(System.in);
    private int ptotal = 0;
    private int dtotal = 0;
    private int cash = 1000;
    private int playerbet = 0;

    public void play() {
        System.out.println();

        checkcash();
        showCash();
        bet();

        createdeck();
        shuffleDeck();

        dealToPlayer();
        dealToDealer();
        dealToPlayer();
        dealToDealer();

        showPlayerHand();
        calcPlayerSum();
        System.out.println();
        showPlayerSum();

        hiddenDealerHand();

        System.out.println();
        System.out.println();
        playerTurn();

        showPlayerHand();
        System.out.println();
        showPlayerSum();

        System.out.println();
        showDealerHand();

        System.out.println();
        showdealerSum();

        dealerTurn();
        checkWin();

    }

    private void bet() {

        System.out.println("Place your Bet");
        try {
            playerbet = input.nextInt();

        } catch (InputMismatchException e) {
            System.out.println("Not a Vaild bet");
            input.nextLine();
            bet();

        }

        if (playerbet > cash) {
            System.out.println("Not enough cash to place bet");
            bet();
        }

        if (playerbet == 0) {
            System.out.println("Cannot have a bet of Zero");
            bet();
        }
        cash = cash - playerbet;

        if (cash < 0) {

            System.out.println("Your out of Cash");

            System.exit(0);

        }
        input.nextLine();

    }

    public void checkWin() {
        if (calcDealerSum() < 22 && calcPlayerSum() < 22) {
            if (calcDealerSum() > calcPlayerSum()) {
                System.out.println("Dealer wins");
                showCash();
            }
            if (calcPlayerSum() > calcDealerSum()) {
                System.out.println("You win");
                payout();
                showCash();

            }

            if (calcPlayerSum() == calcDealerSum()) {
                System.out.println("Draw");
                cash = cash + playerbet;
                showCash();
            }
            System.out.println();
            checkcash();
            newHand();
        }

    }

    private void playerTurn() {

        while (true) {
            System.out.println("Hit or Stay");
            String choice = input.nextLine();
            if (choice.toUpperCase().equals("HIT")) {

                dealToPlayer();
                System.out.println();
                showPlayerHand();
                System.out.println();
                showPlayerSum();
                System.out.println();
                playerBust();
                System.out.println();
                hiddenDealerHand();
                System.out.println();

            }

            else if (choice.toUpperCase().equals("STAY")) {
                break;
            } else {
                playerTurn();
            }

        }
    }

    private void dealerTurn() {
        while (true) {

            if (calcDealerSum() < 16) {
                dealToDealer();
                System.out.println();
                showDealerHand();
                System.out.println();
                showdealerSum();
            } else if (calcDealerSum() >= 16) {
                System.out.println();
                if (calcDealerSum() > 21) {
                    payout();
                    System.out.println("Dealer has Busted");
                    System.out.println();
                    showCash();
                    newHand();
                } else {
                    System.out.println("Dealer Stays");
                }

                break;

            }
        }

    }

    private void newHand() {
        checkcash();
        System.out.println("Next hand? Yes or No");
        System.out.println();
        String playagain = input.nextLine();
        if (playagain.toUpperCase().equals("YES")) {
            playercard.clear();
            dealercard.clear();
            deck.clear();
            play();

        } else if (playagain.toUpperCase().equals("NO")) {
            System.exit(0);
        } else {
            newHand();
        }
    }

    private void playerBust() {

        if (calcPlayerSum() > 21) {
            System.out.println("Busted");
            System.out.println();
            newHand();

        }
    }

    public void showdealerSum() {
        System.out.println("Dealer has " + calcDealerSum());

    }

    public void showPlayerSum() {
        System.out.println("You have " + calcPlayerSum());

    }

    public int calcDealerSum() {
        dtotal = 0;

        for (int i = 0; i < dealercard.size(); i++) {
            Card c = dealercard.get(i);
            if (c.rank > 10) {
                dtotal = dtotal + 10;
            } else if (c.rank == 1) {
                dtotal = dtotal + 1;
            } else {
                dtotal = dtotal + c.rank;
            }

        }
        if (dtotal > 21) {
            dtotal = 0;
            for (int i = 0; i < dealercard.size(); i++) {
                Card c = dealercard.get(i);
                if (c.rank > 10) {
                    dtotal = dtotal + 10;

                } else if (c.rank == 1) {
                    dtotal = dtotal + 1;
                } else {
                    dtotal = dtotal + c.rank;

                }

            }

        }

        return dtotal;

    }

    public int calcPlayerSum() {
        ptotal = 0;

        for (int i = 0; i < playercard.size(); i++) {
            Card c = playercard.get(i);
            if (c.rank > 10) {
                ptotal = ptotal + 10;

            } else if (c.rank == 1) {
                ptotal = ptotal + 11;
            } else {
                ptotal = ptotal + c.rank;

            }

        }

        if (ptotal > 21) {
            ptotal = 0;
            for (int i = 0; i < playercard.size(); i++) {
                Card c = playercard.get(i);
                if (c.rank > 10) {
                    ptotal = ptotal + 10;

                } else if (c.rank == 1) {
                    ptotal = ptotal + 1;
                } else {
                    ptotal = ptotal + c.rank;

                }

            }

        }

        return ptotal;

    }

    public void hiddenDealerHand() {
        System.out.println();
        System.out.println("Dealer Cards");

        for (int i = 0; i < dealercard.size(); i++) {
            System.out.print("[ ] ");
        }

    }

    public void showDealerHand() {
        System.out.println();
        System.out.println("Dealer Cards");

        for (int i = 0; i < dealercard.size(); i++) {
            Card dc = dealercard.get(i);

            if (dc.rank == 11) {
                System.out.print("J of " + dc.suit + "  ");
            }
            if (dc.rank == 12) {
                System.out.print("Q of " + dc.suit + "  ");
            }
            if (dc.rank == 13) {
                System.out.print("K of " + dc.suit + "  ");
            }
            if (dc.rank < 11 && dc.rank > 1) {
                System.out.print(dc.rank + " of " + dc.suit + "  ");
            }
            if (dc.rank == 1) {
                System.out.print("Ace of " + dc.suit + "  ");
            }
        }

    }

    public void showPlayerHand() {
        System.out.println();
        System.out.println("Player Cards");

        for (int i = 0; i < playercard.size(); i++) {
            Card pc = playercard.get(i);
            if (pc.rank == 11) {
                System.out.print("J of " + pc.suit + "  ");
            }
            if (pc.rank == 12) {
                System.out.print("Q of " + pc.suit + "  ");
            }
            if (pc.rank == 13) {
                System.out.print("K of " + pc.suit + "  ");
            }
            if (pc.rank < 11 && pc.rank > 1) {
                System.out.print(pc.rank + " of " + pc.suit + "  ");
            }
            if (pc.rank == 1) {
                System.out.print("Ace of " + pc.suit + "  ");
            }

        }
    }

    public void dealToDealer() {
        Card c = deck.remove(0);
        dealercard.add(c);

    }

    public void dealToPlayer() {
        Card c = deck.remove(0);
        playercard.add(c);

    }

    public void createdeck() {

        String[] suit = new String[] { "Spades", "Heart", "Clubs", "Diamonds" };

        for (int j = 0; j < 4; j++) {

            for (int i = 1; i <= 13; i++) {

                Card card = new Card();
                card.suit = suit[j];
                card.rank = i;
                deck.add(card);
            }

        }

    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    private void checkcash() {

        playerbet = 0;

        if (cash <= 0) {
            System.out.println("Your out of Cash");

            System.exit(0);

        }

    }

    private void payout() {
        cash = cash + playerbet + playerbet;

    }

    private void showCash() {

        System.out.println("Cash " + cash);
        if (playerbet > 0) {
            System.out.println("You bet " + playerbet);

        }

    }

}
