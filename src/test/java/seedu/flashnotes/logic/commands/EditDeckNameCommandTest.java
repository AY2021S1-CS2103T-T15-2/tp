package seedu.flashnotes.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.commons.core.index.Index;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.ReadOnlyUserPrefs;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.deck.UniqueDeckList;
import seedu.flashnotes.model.flashcard.Answer;
import seedu.flashnotes.model.flashcard.Flashcard;
import seedu.flashnotes.model.flashcard.Question;
import seedu.flashnotes.model.tag.Tag;


public class EditDeckNameCommandTest {
    private static final String NAME = "Singapore";
    private static final String NEW_NAME = "Malaysia";
    private static final String QUESTION = "Testq";
    private static final String ANSWER = "Testa";

    @Test
    public void constructor_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditDeckNameCommand(null, null));
    }

    @Test
    public void execute_editDeck_editSuccessful() throws Exception {
        Deck deck = new Deck(NAME);
        Flashcard flashcard = new Flashcard(new Question(QUESTION),
                new Answer(ANSWER), new Tag(NAME));
        Deck expectedDeck = new Deck(NEW_NAME);
        Flashcard expectedFlashcard = new Flashcard(new Question(QUESTION),
                new Answer(ANSWER), new Tag(NEW_NAME));
        ModelStubWithFlashcardAndDeck modelStub = new ModelStubWithFlashcardAndDeck(deck, flashcard);
        ModelStubWithFlashcardAndDeck expectedModelStub =
                new ModelStubWithFlashcardAndDeck(expectedDeck, expectedFlashcard);

        CommandResult commandResult = new EditDeckNameCommand(Index.fromZeroBased(0), new Deck(NEW_NAME))
                .execute(modelStub);
        assertEquals(String.format(EditDeckNameCommand.MESSAGE_SUCCESS, NEW_NAME), commandResult.getFeedbackToUser());
        assertEquals(expectedModelStub.flashcards, modelStub.newFlashcards);
        assertEquals(expectedModelStub.decks, modelStub.decks);


    }

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
        public boolean getIsFlashcardFlipped() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Flashcard getFlashcardBeingReviewed() {
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
            throw new AssertionError("This method should not be called.");
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

    private class ModelStubWithFlashcardAndDeck extends ModelStub {
        final ObservableList<Deck> decks = FXCollections.observableArrayList();
        final ObservableList<Flashcard> flashcards = FXCollections.observableArrayList();
        final ObservableList<Flashcard> newFlashcards = FXCollections.observableArrayList();

        ModelStubWithFlashcardAndDeck(Deck deck, Flashcard flashcard) {
            decks.add(deck);
            flashcards.add(flashcard);
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            return decks;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            return decks.contains(deck);
        }

        @Override
        public void setDeck(Deck original, Deck newDeck) {
            int index = decks.indexOf(original);
            decks.set(index, newDeck);
        }

        @Override
        public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {

        }

        @Override
        public ObservableList<Flashcard> getFilteredFlashcardList() {
            return flashcards;
        }

        @Override
        public void setFlashcard(Flashcard original, Flashcard newCard) {
            newFlashcards.add(newCard);
            flashcards.remove(original);
        }
    }
}
