package principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Card {
    String suit;
    String value;
    int points;

    public Card(String suit, String value, int points) {
        this.suit = suit;
        this.value = value;
        this.points = points;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}

class Deck {
    private List<Card> cards;

    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        cards = new ArrayList<>();

        for (String suit : suits) {
            for (int i = 0; i < values.length; i++) {
                int points = (i >= 10) ? 10 : i + 2; // Jack, Queen, King = 10 points; Ace = 11 or 1 point later
                cards.add(new Card(suit, values[i], points));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}

class Player {
    String name;
    int money;
    List<Card> hand;

    public Player(String name, int money) {
        this.name = name;
        this.money = money;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            total += card.points;
            if (card.value.equals("Ace")) {
                aces++;
            }
        }
        
        while (total > 21 && aces > 0) {
            total -= 10; // Treat Ace as 1 instead of 11
            aces--;
        }

        return total;
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public void clearHand() {
        hand.clear();
    }
}

public class Blackjack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de jugadores: ");
        int numPlayers = scanner.nextInt();
        
        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.print("Ingrese la cantidad de dinero con la que comienza " + name + ": ");
            int money = scanner.nextInt();
            players[i] = new Player(name, money);
        }

        boolean gameOn = true;
        while (gameOn) {
            Deck deck = new Deck();
            Player dealer = new Player("Croupier", 0);
            dealer.addCard(deck.drawCard());
            dealer.addCard(deck.drawCard());

            // Mostrar valor inicial del croupier
            System.out.println("\nCroupier tiene una carta oculta y una carta de: " + dealer.hand.get(1).toString());
            System.out.println("Valor inicial del Croupier: " + dealer.hand.get(1).points + "\n");

            for (Player player : players) {
                if (player.money <= 0) {
                    System.out.println(player.name + " se ha quedado sin dinero y sale del juego.");
                    continue;
                }
                
                player.clearHand();
                player.addCard(deck.drawCard());
                player.addCard(deck.drawCard());

                // Solicitar apuesta
                System.out.print(player.name + ", tienes " + player.money + " de dinero. ¿Cuánto quieres apostar? ");
                int bet = scanner.nextInt();
                while (bet > player.money || bet <= 0) {
                    System.out.print("Apuesta inválida. Tienes que apostar un monto entre 1 y " + player.money + ": ");
                    bet = scanner.nextInt();
                }

                System.out.println(player.name + ", tus cartas son: " + player.hand + " Valor: " + player.getHandValue());
                while (true) {
                    System.out.print("¿Quieres pedir otra carta? (s/n): ");
                    String response = scanner.next();
                    if (response.equalsIgnoreCase("s")) {
                        player.addCard(deck.drawCard());
                        System.out.println("Tus cartas son: " + player.hand + " Valor: " + player.getHandValue());
                        if (player.isBusted()) {
                            System.out.println(player.name + " se ha pasado. ¡Has perdido " + bet + "!");
                            player.money -= bet; // Restar la apuesta
                            break;
                        }
                    } else {
                        break;
                    }
                }

                // Mostrar resultado de la mano
                System.out.println("Cartas del Croupier: " + dealer.hand + " Valor: " + dealer.getHandValue());

                if (!player.isBusted()) {
                    if (dealer.getHandValue() > 21 || player.getHandValue() > dealer.getHandValue()) {
                        System.out.println(player.name + " gana " + bet + "!");
                        player.money += bet; // Duplicar la apuesta
                    } else {
                        System.out.println(player.name + " pierde " + bet + ".");
                        player.money -= bet; // Restar la apuesta
                    }
                }
                System.out.println(player.name + ", tu saldo actual es: " + player.money + "\n");
            }

            // Mostrar saldo de cada jugador al final de la ronda
            System.out.println("Saldo final después de esta ronda:");
            for (Player player : players) {
                System.out.println(player.name + ": " + player.money);
            }
            System.out.println();

            // Pregunta si quieren jugar otra ronda
            System.out.print("¿Quieres jugar otra ronda? (s/n): ");
            String playAgain = scanner.next();
            if (playAgain.equalsIgnoreCase("n")) {
                gameOn = false;
            }
        }
        
        System.out.println("Fin del juego. Gracias por jugar!");
        scanner.close();
    }
}
