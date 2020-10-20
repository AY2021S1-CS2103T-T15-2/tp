package seedu.flashnotes.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.deck.UniqueDeckList;
import seedu.flashnotes.model.flashcard.Flashcard;
import seedu.flashnotes.model.flashcard.UniqueFlashcardList;
import seedu.flashnotes.model.tag.Tag;

/**
 * Wraps all data at the flashnotes level
 * Duplicates are not allowed (by .isSameFlashcard comparison)
 */
public class FlashNotes implements ReadOnlyFlashNotes {

    private final UniqueFlashcardList flashcards;
    private final UniqueDeckList decks;
    private boolean isFlipped;
    private boolean isInDeck;
    private String currentDeckName;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        flashcards = new UniqueFlashcardList();
        decks = new UniqueDeckList();
        isFlipped = false;
        isInDeck = false;
        currentDeckName = null;
    }

    public FlashNotes() {}

    /**
     * Creates an FlashNotes using the Flashcards in the {@code toBeCopied}
     */
    public FlashNotes(ReadOnlyFlashNotes toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the flashcard list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards.setFlashcards(flashcards);
    }

    /**
     * Replaces the contents of the deck list with {@code decks}.
     * {@code decks} must not contain duplicate decks.
     */
    public void setDecks(List<Deck> decks) {
        this.decks.setDecks(decks);
    }

    /**
     * Resets the existing data of this {@code FlashNotes} with {@code newData}.
     */
    public void resetData(ReadOnlyFlashNotes newData) {
        requireNonNull(newData);

        setFlashcards(newData.getFlashcardList());


        //TODO eventually to be changed to read directly from list - PX
        List<Deck> newDeckData = newData.getDeckList();
        List<Deck> newDeckList = new ArrayList<>();
        List<String> uniqueDeckNames = new ArrayList<>();
        //uniqueDeckNames.add("Default");
        for (Flashcard card : newData.getFlashcardList()) {
            Tag tag = card.getTag();
            if (!uniqueDeckNames.contains(tag.tagName)) {
                uniqueDeckNames.add(tag.tagName);
            }
        }

        // For each uniqueDeckName, make sure a deck exist for it
        for (String s : uniqueDeckNames) {
            //Create a deck with that deckName
            Deck newDeck = new Deck(s);
            // Now check to see if deck stats were loaded in for deck
            // Check through each deck to see if it already exist
            for (Deck d : newDeckData) {
                if (d.getDeckName().equals(s)) {
                    // If it does, then set the statistics for the deck
                    newDeck.setResultStatistics(d.getResultStatistics());
                    break;
                }
            }
            // Finally add it to the list
            newDeckList.add(newDeck);
        }
        setDecks(newDeckList);
    }

    //// flashcard-level operations

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the flashnotes.
     */
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);
    }

    /**
     * Adds a flashcard to the flashnotes.
     * The flashcard must not already exist in the flashnotes.
     */
    public void addFlashcard(Flashcard card) {
        flashcards.add(card);
    }

    /**
     * Replaces the given flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the flashnotes.
     * The flashcard identity of {@code editedFlashcard} must not be the same
     * as another existing flashcard in the flashnotes.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireNonNull(editedFlashcard);

        flashcards.setFlashcard(target, editedFlashcard);
    }

    /**
     * Removes {@code key} from this {@code FlashNotes}.
     * {@code key} must exist in the flashnotes.
     */
    public void removeFlashcard(Flashcard key) {
        flashcards.remove(key);
    }

    /**
     * Removes all flashcards with the specified tag.
     * @param tag
     */
    public void removeFlashcardByTag(Tag tag) {
        ArrayList<Flashcard> toBeRemoved = new ArrayList<>();
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getTag().equals(tag)) {
                toBeRemoved.add(flashcard);
            }
        }

        for (int i = 0; i < toBeRemoved.size(); i++) {
            removeFlashcard(toBeRemoved.get(i));
        }
    }


    //// Deck-level operations

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in the flashnotes.
     */
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return decks.contains(deck);
    }

    /**
     * Adds a deck to the flashnotes.
     * The deck must not already exist in the flashnotes.
     */
    public void addDeck(Deck card) {
        decks.add(card);
    }

    /**
     * Replaces the given deck {@code target} in the list with {@code editedDeck}.
     * {@code target} must exist in the flashnotes.
     * The deck identity of {@code editedDeck} must not be the same
     * as another existing deck in the flashnotes.
     */
    public void setDeck(Deck target, Deck editedDeck) {
        requireNonNull(editedDeck);

        decks.setDeck(target, editedDeck);
    }

    /**
     * Removes {@code keyDeck} from this {@code FlashNotes}.
     * {@code keyDeck} must exist in the flashnotes.
     */
    public void removeDeck(Deck keyDeck) {
        removeFlashcardByTag(new Tag(keyDeck.getDeckName()));
        decks.remove(keyDeck);
    }

    public boolean getIsInDeck() {
        return isInDeck;
    }

    public void setIsInDeckTrue() {
        isInDeck = true;
    }

    public void setIsInDeckFalse() {
        isInDeck = false;
    }

    public void setCurrentDeckName(String deckName) {
        this.currentDeckName = deckName;
    }

    public String getCurrentDeckName() {
        if (!isInDeck) {
            return null;
        }
        return currentDeckName;
    }

    /**
     * Update the user's review score for deck used in review.
     * @param reviewScore Integer value of user's review session score.
     * @param deckName String value of deck to update
     */
    public void updateDeckPerformanceScore(Integer reviewScore, String deckName) {
        requireNonNull(reviewScore);
        // Fetch the right deck to update
        Deck currentDeck = decks.findDeck(deckName);
        // Make sure it is an existing deck
        if (currentDeck != null) {
            // Update the deck's statistics
            currentDeck.setResultStatistics(reviewScore.toString());
        }
    }

    /** Returns the unique deck list */
    public UniqueDeckList getUniqueDeckList() {
        return decks;
    }

    /// Review Mode methods

    public void setIsFlippedTrue() {
        isFlipped = true;
    }

    public void setIsFlippedFalse() {
        isFlipped = false;
    }


    //// util methods

    @Override
    public String toString() {
        //return flashcards.asUnmodifiableObservableList().size() + " flashcards";
        // TODO: refine later
        return flashcards.asUnmodifiableObservableList().toString() + decks.asUnmodifiableObservableList().toString();
    }

    @Override
    public ObservableList<Flashcard> getFlashcardList() {
        return flashcards.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Deck> getDeckList() {
        //todo read the tags and update
        //todo change when we have decklist implementation up
        return decks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashNotes // instanceof handles nulls
                && flashcards.equals(((FlashNotes) other).flashcards))
                && decks.equals(((FlashNotes) other).decks);
    }

    @Override
    public int hashCode() {
        return flashcards.hashCode();
    }
}
