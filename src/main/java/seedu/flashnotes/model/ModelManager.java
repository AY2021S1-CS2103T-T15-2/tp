package seedu.flashnotes.model;

import static java.util.Objects.requireNonNull;
import static seedu.flashnotes.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.commons.core.LogsCenter;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.deck.UniqueDeckList;
import seedu.flashnotes.model.flashcard.Flashcard;

/**
 * Represents the in-memory model of the flashnotes data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FlashNotes flashNotes;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;
    private FilteredList<Flashcard> flashcardsToReview;
    private final FilteredList<Deck> filteredDecks;

    /**
     * Initializes a ModelManager with the given flashNotes and userPrefs.
     */
    public ModelManager(ReadOnlyFlashNotes flashNotes, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(flashNotes, userPrefs);

        logger.fine("Initializing with flashnotes: " + flashNotes + " and user prefs " + userPrefs);

        this.flashNotes = new FlashNotes(flashNotes);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<>(this.flashNotes.getFlashcardList());
        flashcardsToReview = new FilteredList<>(this.flashNotes.getFlashcardList());
        filteredDecks = new FilteredList<>(this.flashNotes.getDeckList());
    }

    public ModelManager() {
        this(new FlashNotes(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFlashNotesFilePath() {
        return userPrefs.getFlashNotesFilePath();
    }

    @Override
    public void setFlashNotesFilePath(Path flashNotesFilePath) {
        requireNonNull(flashNotesFilePath);
        userPrefs.setFlashNotesFilePath(flashNotesFilePath);
    }

    @Override
    public Integer getReviewCardLimit() {
        return userPrefs.getReviewCardLimit();
    }

    @Override
    public void setReviewCardLimit(Integer reviewCardLimit) {
        requireNonNull(reviewCardLimit);
        userPrefs.setReviewCardLimit(reviewCardLimit);
    }

    //=========== FlashNotes ================================================================================

    @Override
    public void setFlashNotes(ReadOnlyFlashNotes flashNotes) {
        this.flashNotes.resetData(flashNotes);
    }

    @Override
    public ReadOnlyFlashNotes getFlashNotes() {
        return flashNotes;
    }

    //=========== Decks ================================================================================

    @Override
    public boolean hasDeck(Deck deck) {
        return flashNotes.hasDeck(deck);
    }

    @Override
    public void deleteDeck(Deck target) {
        //todo delete deck - PX
        flashNotes.removeDeck(target);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
    }

    @Override
    public void addDeck(Deck deck) {
        flashNotes.addDeck(deck);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);

    }

    @Override
    public void setDeck(Deck target, Deck editedDeck) {
        flashNotes.setDeck(target, editedDeck);

    }

    @Override
    public boolean getIsInDeck() {
        return flashNotes.getIsInDeck();
    }

    @Override
    public void setIsInDeckTrue() {
        flashNotes.setIsInDeckTrue();
    }

    @Override
    public void setIsInDeckFalse() {
        flashNotes.setIsInDeckFalse();
    }

    @Override
    public void setCurrentDeckName(String deckName) {
        flashNotes.setCurrentDeckName(deckName);
    }

    @Override
    public String getCurrentDeckName() {
        return flashNotes.getCurrentDeckName();
    }

    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return flashNotes.getDeckList();
    }

    @Override
    public void updateFilteredDeckList(Predicate<Deck> predicate) {
        requireNonNull(predicate);
        filteredDecks.setPredicate(predicate);
    }

    /** Returns the unique deck list */
    @Override
    public UniqueDeckList getUniqueDeckList() {
        return flashNotes.getUniqueDeckList();
    }

    /**
     * Update the user's review score for deck used in review.
     * @param reviewScore Integer value of user's review session score.
     */
    @Override
    public void updateDeckPerformanceScore(Integer reviewScore, String deckName) {
        requireNonNull(reviewScore);
        requireNonNull(deckName);
        flashNotes.updateDeckPerformanceScore(reviewScore, deckName);

    }

    //=========== FlashCards ================================================================================
    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashNotes.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        flashNotes.removeFlashcard(target);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        flashNotes.addFlashcard(flashcard);
        //updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        flashNotes.setFlashcard(target, editedFlashcard);
    }

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedFlashNotes}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    //=========== Shuffled Flashcard List Operations =============================================================

    /**
     * Shuffles and trims the list of flashcards to review.
     */
    public void shuffleReviewFlashcards() {
        // Apply shuffling algorithm
        ObservableList<Flashcard> flashcardsToReviewList = FXCollections.observableArrayList(
                getFilteredFlashcardList());
        FXCollections.shuffle(flashcardsToReviewList);

        // Trim review list using card limit from user prefs
        Integer reviewCardLimit = userPrefs.getReviewCardLimit();
        if (reviewCardLimit < flashcardsToReviewList.size() && reviewCardLimit >= 1) {
            flashcardsToReviewList = FXCollections.observableArrayList(
                    flashcardsToReviewList.subList(0, reviewCardLimit));
        }

        // Store shuffled and trimmed list into flashcardsToReview list
        this.flashcardsToReview = new FilteredList<>(flashcardsToReviewList);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} to review.
     */
    @Override
    public ObservableList<Flashcard> getFlashcardsToReview() {
        System.out.println("getter " + flashcardsToReview);
        return flashcardsToReview;
    }

    /**
     * Returns the modified list of flashcards to be reviewed after adding the extra flashcard
     * @param flashcard
     * @return
     */
    @Override
    public ObservableList<Flashcard> addFlashcardToReview(Flashcard flashcard) {
        ObservableList<Flashcard> flashcardsToReviewList = FXCollections.observableArrayList(
                this.flashcardsToReview);
        flashcardsToReviewList.add(flashcard);
        this.flashcardsToReview = new FilteredList<>(flashcardsToReviewList);
        return flashcardsToReview;
    }

    @Override
    public void setIsFlippedTrue() {
        flashNotes.setIsFlippedTrue();
    }

    @Override
    public void setIsFlippedFalse() {
        flashNotes.setIsFlippedFalse();
    }

    // =========== Util methods =============================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return flashNotes.equals(other.flashNotes)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashcards.equals(other.filteredFlashcards);
    }

}
