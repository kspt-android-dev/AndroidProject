package kamiko.klondike_java;

import org.junit.Test;

import static kamiko.klondike_java.Card.Suit.CLUBS;
import static kamiko.klondike_java.Card.Suit.DIAMONDS;
import static kamiko.klondike_java.Card.Suit.HEARTS;
import static kamiko.klondike_java.Card.Suit.SPADES;
import static org.junit.Assert.assertArrayEquals;

public class MainActivityTest {

    private MainActivity mainActivity = new MainActivity();

    @Test
    public void makeDeck() {
        Card.Suit[] testSuits = {DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS, DIAMONDS,
                                CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS, CLUBS,
                                HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS, HEARTS,
                                SPADES, SPADES, SPADES, SPADES, SPADES, SPADES, SPADES, SPADES, SPADES, SPADES, SPADES, SPADES, SPADES};

        int[] testValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12,
                            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12,
                            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12,
                            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 , 12};

        Card.Suit[] resultSuit = new Card.Suit[52];
        int[] resultValues = new int[52];

        mainActivity.makeDeck();

        for (int i = 0; i < mainActivity.COUNT_OF_CARDS; i++) {
            resultSuit[i] = mainActivity.deck[i].getSuit();
            resultValues[i] = mainActivity.deck[i].getValue();
        }

        assertArrayEquals(testSuits, resultSuit);
        assertArrayEquals(testValues, resultValues);
    }
        // Для тестирования логики в момент игры использовалось логирование
}