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
    private int flashcardBeingReviewed = 0;

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
    public long getReviewCardLimit() {
        return userPrefs.getReviewCardLimit();
    }

    @Override
    public void setReviewCardLimit(long reviewCardLimit) {
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
    public void updateDeckPerformanceScore(Double reviewScore, String deckName) {
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

    //=========== Review Operations =============================================================

    @Override
    public boolean getIsReviewMode() {
        return flashNotes.getIsReviewMode();
    }

    @Override
    public void setIsReviewModeTrue() {
        flashNotes.setIsReviewModeTrue();
    }

    @Override
    public void setIsReviewModeFalse() {
        flashNotes.setIsReviewModeFalse();
    }

    /**
     * Shuffles list of flashcards to review
     */
    private ObservableList<Flashcard> shuffleReviewFlashcards() {
        ObservableList<Flashcard> flashcardsToReviewList = FXCollections.observableArrayList(
                getFilteredFlashcardList());
        FXCollections.shuffle(flashcardsToReviewList);
        return flashcardsToReviewList;
    }

    /**
     * Trim list of flashcards to review
     * @param flashcardsToReviewList Shuffled list of flashcards to review
     * @return Trimmed list of flashcards to review
     */
    private ObservableList<Flashcard> trimReviewFlashcards(ObservableList<Flashcard> flashcardsToReviewList) {
        long reviewCardLimit = userPrefs.getReviewCardLimit();
        boolean isReviewLimitValid = reviewCardLimit >= 1;
        boolean isReviewLimitUsed = reviewCardLimit < flashcardsToReviewList.size();

        if (isReviewLimitUsed && isReviewLimitValid) {
            flashcardsToReviewList = FXCollections.observableArrayList(
                    flashcardsToReviewList.subList(0, (int) reviewCardLimit));
        }
        return flashcardsToReviewList;
    }

    /**
     * Shuffles and trims the list of flashcards to review.
     */
    @Override
    public void setUpReviewList() {
        // Apply shuffling algorithm
        ObservableList<Flashcard> flashcardsToReviewList = shuffleReviewFlashcards();

        // Trim review list using card limit from user prefs
        flashcardsToReviewList = trimReviewFlashcards(flashcardsToReviewList);

        // Store shuffled and trimmed list into flashcardsToReview list
        this.flashcardsToReview = new FilteredList<>(flashcardsToReviewList);

        // Initialize first flashcard to be reviewed
        setUpFlashcardToReview();
    }

    /**
     * Set up the first flashcard to be reviewed in the review mode and reset it's flip to false
     */
    private void setUpFlashcardToReview() {
        // Set index of flashcard being reviewed in review mode to 0
        this.flashcardBeingReviewed = 0;
        resetFlipOfFlashcardBeingReviewed();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} to review.
     */
    @Override
    public ObservableList<Flashcard> getFlashcardsToReview() {
        return flashcardsToReview;
    }

    /**
     * Adds flashcard to list of flashcards being reviewed.
     * @return
     */
    @Override
    public void addFlashcardToReview() {
        Flashcard flashcard = this.flashcardsToReview.get(flashcardBeingReviewed);
        ObservableList<Flashcard> flashcardsToReviewList = FXCollections.observableArrayList(
                this.flashcardsToReview);
        flashcardsToReviewList.add(flashcard);
        this.flashcardsToReview = new FilteredList<>(flashcardsToReviewList);
    }

    /** Returns the modified list of flashcards to be reviewed */
    @Override
    public ObservableList<Flashcard> getModifiedFlashcardsToReview() {
        return flashcardsToReview;
    }

    /**
     * Updates the flashcard being reviewed in the review mode
     */
    @Override
    public void updateFlashcardBeingReviewed(int result) {
        resetFlipOfFlashcardBeingReviewed();
        Flashcard flashcard = this.flashcardsToReview.get(flashcardBeingReviewed);
        markFlashcardBeingReviewed(flashcard, result);
        this.flashcardBeingReviewed += 1;
        if (flashcardBeingReviewed >= flashcardsToReview.size()) {
            flashcardBeingReviewed = 0;
        } else {
            Flashcard newFlashcard = this.flashcardsToReview.get(flashcardBeingReviewed);
            newFlashcard.resetFlip();
        }
    }

    /**
     * Reset flashcard being reviewed back to false
     */
    @Override
    public void resetFlipOfFlashcardBeingReviewed() {
        this.flashcardsToReview.get(flashcardBeingReviewed).resetFlip();
    }

    /**
     * Gets the flashcard being reviewed in the review mode and returns it.
     * @return Flashcard being reviewed
     */
    @Override
    public Flashcard getFlashcardBeingReviewed() {
        return this.flashcardsToReview.get(flashcardBeingReviewed);
    }

    /**
     * Checks whether the flashcard being reviewed is flipped
     * @return
     */
    @Override
    public boolean getIsFlashcardFlipped() {
        return this.flashcardsToReview.get(flashcardBeingReviewed).getIsFlipped();
    }

    /**
     * Flip the flashcard currently being reviewed
     */
    @Override
    public void carryOutFlipCommand() {
        Flashcard flashcard = this.flashcardsToReview.get(flashcardBeingReviewed);
        flashcard.flipFlashcard();
    }

    /**
     * Marks flashcard being reviewed as correct or wrong
     */
    @Override
    public void markFlashcardBeingReviewed(Flashcard flashcard, int result) {
        flashcard.markCard(result);
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
