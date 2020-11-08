package seedu.flashnotes.testutil;

import static seedu.flashnotes.logic.commands.CommandTestUtil.VALID_ANSWER_MACROECONS;
import static seedu.flashnotes.logic.commands.CommandTestUtil.VALID_ANSWER_SKY;
import static seedu.flashnotes.logic.commands.CommandTestUtil.VALID_QUESTION_MACROECONS;
import static seedu.flashnotes.logic.commands.CommandTestUtil.VALID_QUESTION_SKY;
import static seedu.flashnotes.logic.commands.CommandTestUtil.VALID_TAG_DEFAULT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.flashnotes.model.FlashNotes;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.flashcard.Flashcard;

/**
 * A utility class containing a list of {@code Flashcard} objects to be used in tests.
 */
public class TypicalFlashcards {

    public static final Flashcard WHO = new FlashcardBuilder().withQuestion("Who")
            .withAnswer("me")
            .withTag("friends").build();
    public static final Flashcard WHAT = new FlashcardBuilder().withQuestion("What")
            .withAnswer("idk").withTag("owesMoney").build();
    public static final Flashcard WHY = new FlashcardBuilder().withQuestion("Why")
            .withAnswer("because someone has to")
            .withTag("friends").build();
    public static final Flashcard HOW = new FlashcardBuilder().withQuestion("How").withAnswer("use your hands")
            .withTag("friends").build();
    public static final Flashcard WHEN = new FlashcardBuilder().withQuestion("When").withAnswer("at 2pm")
            .withTag("Time")
            .build();
    public static final Flashcard WHERE = new FlashcardBuilder().withQuestion("Where").withAnswer("at home")
            .withTag("Place").build();
    public static final Flashcard HOW_MUCH = new FlashcardBuilder().withQuestion("How much")
            .withAnswer("300 apples").withTag("Math").build();

    // Manually added
    public static final Flashcard WHO_IS_MEIER = new FlashcardBuilder().withQuestion("who is meier")
            .withAnswer("8482424").withTag("test").build();
    public static final Flashcard WHAT_IS_MEIER = new FlashcardBuilder().withQuestion("what is meier")
            .withAnswer("8482131").withTag("test").build();

    // Manually added - Flashcard's details found in {@code CommandTestUtil}
    public static final Flashcard SKY = new FlashcardBuilder().withQuestion(VALID_QUESTION_SKY)
            .withAnswer(VALID_ANSWER_SKY)
            .withTag(VALID_TAG_DEFAULT).build();
    public static final Flashcard MACROECONS = new FlashcardBuilder().withQuestion(VALID_QUESTION_MACROECONS)
            .withAnswer(VALID_ANSWER_MACROECONS)
            .withTag(VALID_TAG_DEFAULT).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFlashcards() {} // prevents instantiation

    /**
     * Returns an {@code FlashNotes} with all the typical flashcards.
     */
    public static FlashNotes getTypicalFlashNotes() {
        FlashNotes ab = new FlashNotes();
        for (Flashcard flashcard : getTypicalFlashcards()) {
            ab.addFlashcard(flashcard);
        }

        for (Deck deck: getTypicalDecks()) {
            ab.addDeck(deck);
        }
        return ab;
    }

    public static List<Deck> getTypicalDecks() {
        Deck friends = new Deck ("friends");
        friends.setResultStatistics("3.3");
        Deck oweMoney = new Deck ("owesMoney");
        oweMoney.setResultStatistics("-1.0");
        Deck time = new Deck("Time");
        time.setResultStatistics("0.0");
        Deck place = new Deck("Place");
        place.setResultStatistics("100.0");
        Deck math = new Deck("Math");
        math.setResultStatistics("75.0");
        return new ArrayList<>(Arrays.asList(friends, oweMoney, time, place, math));
    }

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(WHO, WHAT, WHY, HOW, WHEN, WHERE, HOW_MUCH));
    }
}
