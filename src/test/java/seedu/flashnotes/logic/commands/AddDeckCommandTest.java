package seedu.flashnotes.logic.commands;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashnotes.testutil.Assert.assertThrows;
import static seedu.flashnotes.testutil.TypicalDecks.VALID_DECK_1;
import static seedu.flashnotes.testutil.TypicalDecks.VALID_DECK_2;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.model.FlashNotes;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.ReadOnlyUserPrefs;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.deck.UniqueDeckList;
import seedu.flashnotes.model.flashcard.Flashcard;

public class AddDeckCommandTest {

    @Test
    public void constructor_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDeckCommand(null));
    }

    @Test
    public void execute_deckAcceptedByModel_success() throws Exception {
        AddDeckCommandTest.ModelStubAcceptingDeckAdded modelStub = new AddDeckCommandTest.ModelStubAcceptingDeckAdded();

        CommandResult commandResult = new AddDeckCommand(VALID_DECK_1).execute(modelStub);

        assertEquals(String.format(AddDeckCommand.MESSAGE_SUCCESS, VALID_DECK_1.getDeckName()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(VALID_DECK_1), modelStub.decksAdded);
    }

    @Test
    public void execute_duplicateDeck_throwsCommandException() {
        Deck validDeck = new Deck("Funky");
        AddDeckCommand addDeckCommand = new AddDeckCommand(validDeck);
        ModelStub modelStub = new ModelStubWithDeck(validDeck);

        assertThrows(CommandException.class,
                AddDeckCommand.MESSAGE_DUPLICATE_DECK, () -> addDeckCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddDeckCommand addFirstValidDeckCommand = new AddDeckCommand(VALID_DECK_1);
        AddDeckCommand addSecondValidDeckCommand = new AddDeckCommand(VALID_DECK_2);

        // same object -> returns true
        assertTrue(addFirstValidDeckCommand.equals(addFirstValidDeckCommand));

        // same values -> returns true
        AddDeckCommand addFirstValidDeckCommandCopy = new AddDeckCommand(VALID_DECK_1);
        assertTrue(addFirstValidDeckCommand.equals(addFirstValidDeckCommandCopy));

        // different types -> returns false
        assertFalse(addFirstValidDeckCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstValidDeckCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(addFirstValidDeckCommand.equals(addSecondValidDeckCommand));
    }
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFlashNotesFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashNotesFilePath(Path flashNotesFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUpReviewList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashNotes(ReadOnlyFlashNotes flashNotes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFlashNotes getFlashNotes() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getFlashcardsToReview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashcardToReview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getModifiedFlashcardsToReview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFlashcardBeingReviewed(int result) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetFlipOfFlashcardBeingReviewed() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Flashcard getFlashcardBeingReviewed() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getIsFlashcardFlipped() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void carryOutFlipCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markFlashcardBeingReviewed(Flashcard flashcard, int result) {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasDeck(Deck deck) {
            return false;
        }

        @Override
        public void deleteDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeck(Deck target, Deck editedDeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getIsInDeck() {
            return false;
        }

        @Override
        public void setIsInDeckTrue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIsInDeckFalse() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentDeckName(String deckName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getCurrentDeckName() {
            return null;
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            return null;
        }

        @Override
        public void updateFilteredDeckList(Predicate<Deck> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashcard(Flashcard target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getFilteredFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public long getReviewCardLimit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReviewCardLimit(long reviewCardLimit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeckPerformanceScore(Double reviewScore, String deckName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueDeckList getUniqueDeckList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setIsReviewModeFalse() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setIsReviewModeTrue() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean getIsReviewMode() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single flashcard.
     */
    private class ModelStubWithDeck extends ModelStub {
        private final Deck deck;

        ModelStubWithDeck(Deck deck) {
            requireNonNull(deck);
            this.deck = deck;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return this.deck.isSameDeck(deck);
        }
    }
    /**
     * A Model stub that always accept the flashcard being added.
     */
    private class ModelStubAcceptingDeckAdded extends AddDeckCommandTest.ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::isSameDeck);
        }
        @Override
        public void addDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
        }

        @Override
        public ReadOnlyFlashNotes getFlashNotes() {
            return new FlashNotes();
        }
    }
}
