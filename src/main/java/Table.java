import javax.websocket.Session;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Table {
    public Session session1 = null;
    public String name1 = null;
    public int result1 = 0;
    public int pass1 = 0;
    public int newGame1 = 0;
    public int aces1 = 0;
    public LinkedList<Card> deck1;
    public Random random1;
    public Session session2 = null;
    public String name2 = null;
    public int result2 = 0;
    public int pass2 = 0;
    public int newGame2 = 0;
    public int aces2 = 0;
    public LinkedList<Card> deck2;
    public Random random2;
    public int numberOfPlayers = 0;
    public int tableNumber;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        deck1 = new LinkedList<>();
        deck2 = new LinkedList<>();
        this.resetDecks();
        random1 = new Random(100000);
        random2 = new Random(200000);
    }

    public void resetDecks() {
        deck1.clear();
        deck2.clear();
        LinkedList<String> figures = new LinkedList<>(Arrays.asList("2","3","4","5","6","7","8","9","10","J","Q","K","A"));
        LinkedList<String> colors = new LinkedList<>(Arrays.asList("D","H","S","C"));
        LinkedList<Integer> values = new LinkedList<>(Arrays.asList(2,3,4,5,6,7,8,9,10,11,11,11,11));
        int i, j;
        for (i = 0, j = 0; i < 52; i++)
        {
            if ((i % 13) == 0 && i != 0) j++;
            deck1.add(new Card(figures.get(i%13),colors.get(j%4),values.get(i%13),i));
            deck2.add(new Card(figures.get(i%13),colors.get(j%4),values.get(i%13),i));
        }
    }

    public void resetTable() {
        session1 = null;
        name1 = null;
        session2 = null;
        name2 = null;
        numberOfPlayers = 0;
        this.resetGame();
    }

    public void resetGame() {
        result1 = 0;
        result2 = 0;
        pass1 = 0;
        pass2 = 0;
        newGame1 = 0;
        newGame2 = 0;
        aces1 = 0;
        aces2 = 0;
        this.resetDecks();
    }

    public Card randomCard(String name) {
        int cardIndex = 0;
        String cardName = null;
        Card card = null;
        if (name.equals(name1)) {
            cardIndex = random1.nextInt(52);
            if (cardIndex > deck1.size()) cardIndex = deck1.size() - 1;
            card = deck1.get(cardIndex);
            if (card.figure.equals("A")) aces1++;
            result1 = result1 + card.value;
            deck1.remove(cardIndex);
        } else {
            cardIndex = random2.nextInt(52);
            if (cardIndex > deck2.size()) cardIndex = deck2.size() - 1;
            card = deck2.get(cardIndex);
            if (card.figure.equals("A")) aces2++;
            result2 = result2 + card.value;
            deck2.remove(cardIndex);
        }
        return card;
    }

    public int resultOfGame() {
        int result = -1;
        if (pass1 == 1 && pass2 == 1) {
            if (aces1 == 0) ;
            else if (result1 > 21) {
                while (aces1 > 0 && result1 > 21)
                {
                    result1 = result1 - 10;
                    aces1--;
                }
            }
            if (aces2 == 0) ;
            else if (result2 > 21) {
                while (aces2 > 0 && result2 > 21)
                {
                    result2 = result2 - 10;
                    aces2--;
                }
            }
            if (result1 == result2) result = 0;
            else if (result1 > 21 && result2 > 21) result = 0;
            else if (result1 > 21) result = 2;
            else if (result2 > 21) result = 1;
            else if (result1 > result2) result = 1;
            else result = 2;
        }
        return result;
    }
}
