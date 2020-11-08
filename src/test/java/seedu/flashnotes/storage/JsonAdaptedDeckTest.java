package seedu.flashnotes.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.testutil.Assert.assertThrows;
import static seedu.flashnotes.testutil.TypicalDecks.VALID_DECK_1;

import org.junit.jupiter.api.Test;

import seedu.flashnotes.commons.exceptions.IllegalValueException;
import seedu.flashnotes.model.deck.Deck;

public class JsonAdaptedDeckTest {
    private static final String INVALID_DECK_NAME = "";
    private static final String INVALID_DECK_STATISTIC = "";
    private static final String NEGATIVE_DECK_STATISTIC = "-0.5";
    private static final String INVALID_VALUE_DECK_STATISTIC = "100.1";

    private static final String VALID_DECK_NAME = VALID_DECK_1.getDeckName();
    private static final String VALID_DECK_STATISTIC = VALID_DECK_1.getResultStatistics();

    @Test
    public void toModelType_validDeckDetails_returnsDeck() throws Exception {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(VALID_DECK_1);
        assertEquals(VALID_DECK_1, deck.toModelType());
    }

    @Test
    public void toModelType_invalidDeckName_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(INVALID_DECK_NAME, VALID_DECK_STATISTIC);
        String expectedMessage = Deck.MESSAGE_CONSTRAINTS_LENGTH;
        assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }

    @Test
    public void toModelType_invalidDeckStatistic_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(VALID_DECK_NAME, INVALID_DECK_STATISTIC);
        String expectedMessage = Deck.MESSAGE_CONSTRAINTS_STATISTIC;
        assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }

    @Test
    public void toModelType_negativeDeckStatistic_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(VALID_DECK_NAME, NEGATIVE_DECK_STATISTIC);
        String expectedMessage = Deck.MESSAGE_CONSTRAINTS_STATISTIC;
        assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }

    @Test
    public void toModelType_invalidNumberDeckStatistic_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(VALID_DECK_NAME, INVALID_VALUE_DECK_STATISTIC);
        String expectedMessage = Deck.MESSAGE_CONSTRAINTS_STATISTIC;
        assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }


}
