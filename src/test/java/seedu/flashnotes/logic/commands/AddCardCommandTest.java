package seedu.flashnotes.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashnotes.testutil.Assert.assertThrows;

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
import seedu.flashnotes.testutil.FlashcardBuilder;

public class AddCardCommandTest {

    @Test
    public void constructor_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCardCommand(null));
    }

    @Test
    public void execute_flashcardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFlashcardAdded modelStub = new ModelStubAcceptingFlashcardAdded();
        Flashcard validFlashcard = new FlashcardBuilder().build();

        CommandResult commandResult = new AddCardCommand(validFlashcard).execute(modelStub);

        assertEquals(String.format(AddCardCommand.MESSAGE_SUCCESS, validFlashcard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFlashcard), modelStub.flashcardsAdded);
    }

    @Test
    public void execute_duplicateFlashcard_throwsCommandException() {
        Flashcard validFlashcard = new FlashcardBuilder().build();
        AddCardCommand addCardCommand = new AddCardCommand(validFlashcard);
        ModelStub modelStub = new ModelStubWithFlashcard(validFlashcard);

        assertThrows(CommandException.class,
                AddCardCommand.MESSAGE_DUPLICATE_FLASHCARD, () -> addCardCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Flashcard alice = new FlashcardBuilder().withQuestion("Why?").build();
        Flashcard bob = new FlashcardBuilder().withQuestion("How?").build();
        AddCardCommand addAliceCommand = new AddCardCommand(alice);
        AddCardCommand addBobCommand = new AddCardCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCardCommand addAliceCommandCopy = new AddCardCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public boolean getIsReviewMode() {
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
    }

    /**
     * A Model stub that contains a single flashcard.
     */
    private class ModelStubWithFlashcard extends ModelStub {
        private final Flashcard flashcard;

        ModelStubWithFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            this.flashcard = flashcard;
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return this.flashcard.isSameFlashcard(flashcard);
        }
    }

    /**
     * A Model stub that always accept the flashcard being added.
     */
    private class ModelStubAcceptingFlashcardAdded extends ModelStub {
        final ArrayList<Flashcard> flashcardsAdded = new ArrayList<>();

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return flashcardsAdded.stream().anyMatch(flashcard::isSameFlashcard);
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            flashcardsAdded.add(flashcard);
        }

        @Override
        public void addDeck(Deck deck) {
            requireNonNull(deck);
        }
        @Override
        public ReadOnlyFlashNotes getFlashNotes() {
            return new FlashNotes();
        }
    }

}
