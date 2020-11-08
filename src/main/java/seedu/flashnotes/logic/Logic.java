package seedu.flashnotes.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.logic.commands.CommandResult;
import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.logic.parser.exceptions.ParseException;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.flashcard.Flashcard;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the FlashNotes.
     *
     * @see seedu.flashnotes.model.Model#getFlashNotes()
     */
    ReadOnlyFlashNotes getFlashNotes();

    /** Returns an unmodifiable view of the filtered list of flashcards */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /** Returns an unmodifiable view of the filtered list of decks */
    ObservableList<Deck> getFilteredCardDeckList();

    /**
     * Returns an unmodifiable view of the flashcards to review.
     */
    ObservableList<Flashcard> getFlashcardsToReview();

    /**
     * Returns the modified list of flashcards to be reviewed
     */
    ObservableList<Flashcard> getModifiedFlashcardsToReview();

    /** Reset flashcard being reviewed back to false */
    public void resetFlipOfFlashcardBeingReviewed();

    /**
     * Returns the user prefs' flashnotes file path.
     */
    Path getFlashNotesFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' review card limit.
     */
    long getReviewCardLimit();

    /**
     * Set the user prefs' review card limit.
     */
    void setReviewCardLimit(long reviewCardLimit);

    /**
     * Update the user's review score for deck used in review.
     * @param reviewScore Integer value of user's review session score.
     */
    void updateDeckPerformanceScore(Double reviewScore);

    /**
     * Turn off review mode.
     */
    void setIsReviewModeFalse();
}
