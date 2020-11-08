package seedu.flashnotes.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.commons.core.Messages;
import seedu.flashnotes.commons.core.index.Index;
import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.ReadOnlyUserPrefs;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.deck.UniqueDeckList;
import seedu.flashnotes.model.flashcard.Flashcard;


public class DeleteDeckCommandTest {


    @Test
    public void execute_deckAcceptedByModel_deleteSuccessful() throws Exception {
        Deck deck = new Deck("Singapore");
        ModelStubWithFlashcardAndDeck modelStub = new ModelStubWithFlashcardAndDeck(deck);
        assertEquals(1, modelStub.decks.size());
        CommandResult commandResult = new DeleteDeckCommand(Index.fromZeroBased(0)).execute(modelStub);

        assertEquals(String.format(DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS,
                deck.getDeckName()), commandResult.getFeedbackToUser());
        assertEquals(0, modelStub.decks.size());
    }

    @Test
    public void execute_deleteDeck_deckNotFound() throws Exception {
        Deck deck = new Deck("Singapore");
        ModelStubWithFlashcardAndDeck modelStub = new ModelStubWithFlashcardAndDeck(deck);
        DeleteDeckCommand command = new DeleteDeckCommand(Index.fromZeroBased(1));

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX, () -> command.execute(modelStub));
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
        public void deleteDeck(Deck target){

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

        }

        @Override
        public void setIsInDeckFalse() {

        }

        @Override
        public void setCurrentDeckName(String deckName) {

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

        ModelStubWithFlashcardAndDeck(Deck deck) {
            decks.add(deck);
        }

        @Override
        public void deleteDeck(Deck deck) {
            decks.remove(deck);
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            return decks;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            return decks.contains(deck);
        }


    }
}
