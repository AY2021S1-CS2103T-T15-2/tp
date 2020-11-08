package seedu.flashnotes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.flashnotes.model.deck.Deck;

/**
 * An UI component that displays information of a {@code Deck}.
 */
public class DeckCard extends UiPart<Region> {
    private static final String FXML = "DeckCard.fxml";

    // Percentage string message
    private static final String RESULT_STATISTIC_LABEL = "Cards answered correctly on first try during last in-deck "
            + "review session (%): ";
    // No Statistic message
    private static final String NO_RESULT_STATISTIC_LABEL =
            "No statistic available! Conduct a review session for some!";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FlashNotes level 4</a>
     */
    //todo change the link here

    @FXML
    private Deck cardDeck;

    @FXML
    private HBox cardPane;
    @FXML
    private Label deckName;
    @FXML
    private Label id;
    @FXML
    private Label resultStatistics;

    /**
     * Creates a {@code cardDeck} with the given {@code deckName} and index to display.
     */
    public DeckCard(Deck cardDeck, int displayedIndex) {
        super(FXML);
        this.cardDeck = cardDeck;
        deckName.setText(cardDeck.getDeckName());
        id.setText(displayedIndex + ". ");
        if (cardDeck.getResultStatistics().equals("-1.0")) {
            // Update result display with no statistics available yet.
            resultStatistics.setText(NO_RESULT_STATISTIC_LABEL);
        } else {
            resultStatistics.setText(RESULT_STATISTIC_LABEL + cardDeck.getResultStatistics());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeckCard)) {
            return false;
        }

        // state check - name and ID check
        DeckCard card = (DeckCard) other;
        return id.getText().equals(card.id.getText())
                && deckName.equals(card.deckName)
                && cardDeck.equals(card.cardDeck);
    }
}
