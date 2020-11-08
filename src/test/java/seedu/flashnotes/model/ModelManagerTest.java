package seedu.flashnotes.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashnotes.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.flashnotes.testutil.Assert.assertThrows;
import static seedu.flashnotes.testutil.TypicalFlashcards.WHAT;
import static seedu.flashnotes.testutil.TypicalFlashcards.WHO;
import static seedu.flashnotes.testutil.TypicalFlashcards.getTypicalFlashNotes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.model.flashcard.Flashcard;
import seedu.flashnotes.model.flashcard.QuestionContainsKeywordsPredicate;
import seedu.flashnotes.testutil.FlashNotesBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        FlashNotes fn = new FlashNotes(new FlashNotes());
        FlashNotes modelFN = new FlashNotes(modelManager.getFlashNotes());
        System.out.println(fn);
        System.out.println(modelFN);
        assertEquals(fn, modelFN);
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFlashNotesFilePath(Paths.get("flashnotes/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFlashNotesFilePath(Paths.get("new/flashnotes/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setFlashNotesFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setFlashNotesFilePath(null));
    }

    @Test
    public void setFlashNotesFilePath_validPath_setsFlashNotesFilePath() {
        Path path = Paths.get("flashnotes/file/path");
        modelManager.setFlashNotesFilePath(path);
        assertEquals(path, modelManager.getFlashNotesFilePath());
    }

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFlashcard(null));
    }

    @Test
    public void hasFlashcard_flashcardNotInFlashNotes_returnsFalse() {
        assertFalse(modelManager.hasFlashcard(WHAT));
    }

    @Test
    public void hasFlashcard_flashcardInFlashNotes_returnsTrue() {
        modelManager.addFlashcard(WHAT);
        assertTrue(modelManager.hasFlashcard(WHAT));
    }

    @Test
    public void getFilteredFlashcardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager
                .getFilteredFlashcardList().remove(0));
    }

    @Test
    public void execute_addFlashcardToReview() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getFlashNotes(), new UserPrefs());
        int size = model.getFlashcardsToReview().size();
        expectedModel.addFlashcardToReview();
        assertEquals(size + 1, expectedModel.getModifiedFlashcardsToReview().size());
        Flashcard newFlashcard = expectedModel.getFlashcardsToReview().get(size);
        assertEquals(model.getFlashcardBeingReviewed(), newFlashcard);
    }

    @Test
    public void execute_updateFlashcardBeingReviewedForCorrectCommand() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getFlashNotes(), new UserPrefs());
        Flashcard flashcard = expectedModel.getFlashcardBeingReviewed();
        assertEquals(model.getFlashcardBeingReviewed(), flashcard);
        expectedModel.updateFlashcardBeingReviewed(2);
        assertEquals(2, flashcard.getIsCorrect());
        Flashcard nextFlashcard = expectedModel.getFlashcardBeingReviewed();
        assertNotEquals(flashcard, nextFlashcard);
    }

    @Test
    public void execute_updateFlashcardBeingReviewedForWrongCommand() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getFlashNotes(), new UserPrefs());
        Flashcard flashcard = expectedModel.getFlashcardBeingReviewed();
        assertEquals(model.getFlashcardBeingReviewed(), flashcard);

        expectedModel.updateFlashcardBeingReviewed(1);
        assertEquals(1, flashcard.getIsCorrect());

        Flashcard nextFlashcard = expectedModel.getFlashcardBeingReviewed();
        assertNotEquals(flashcard, nextFlashcard);
    }

    @Test
    public void execute_resetFlipOfFlashcardBeingReviewedWhenFlipped() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        // To get a new flashcard
        model.updateFlashcardBeingReviewed(2);
        Flashcard flashcard = model.getFlashcardBeingReviewed();

        //flip flashcard to test whether it can be reset
        flashcard.flipFlashcard();
        assertEquals(true, flashcard.getIsFlipped());

        model.resetFlipOfFlashcardBeingReviewed();
        assertEquals(false, flashcard.getIsFlipped());
    }

    @Test
    public void execute_resetFlipOfFlashcardBeingReviewedWhenNotFlipped() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        // To get a new flashcard
        model.updateFlashcardBeingReviewed(2);
        Flashcard flashcard = model.getFlashcardBeingReviewed();

        assertEquals(false, flashcard.getIsFlipped());

        model.resetFlipOfFlashcardBeingReviewed();
        assertEquals(false, flashcard.getIsFlipped());
    }

    @Test
    public void execute_getFlashcardBeingReviewed() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getFlashNotes(), new UserPrefs());

        Flashcard expectedFlashcard = expectedModel.getFlashcardsToReview().get(0);
        Flashcard flashcard = model.getFlashcardBeingReviewed();

        assertEquals(expectedFlashcard, flashcard);
    }

    @Test
    public void execute_getIsFlashcardFlipped() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        Flashcard flashcard = model.getFlashcardBeingReviewed();

        assertEquals(false, flashcard.getIsFlipped());
        assertEquals(false, model.getIsFlashcardFlipped());

        model.carryOutFlipCommand();
        assertEquals(true, flashcard.getIsFlipped());
        assertEquals(true, model.getIsFlashcardFlipped());
    }

    @Test
    public void execute_carryOutFlipCommand() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        // To get a new flashcard
        model.updateFlashcardBeingReviewed(2);
        Flashcard flashcard = model.getFlashcardBeingReviewed();

        assertEquals(false, flashcard.getIsFlipped());

        model.carryOutFlipCommand();
        assertEquals(true, flashcard.getIsFlipped());

        model.carryOutFlipCommand();
        assertEquals(false, flashcard.getIsFlipped());
    }

    @Test
    public void execute_markFlashcardBeingReviewedAsCorrect() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        // To get a new flashcard
        model.updateFlashcardBeingReviewed(2);
        Flashcard flashcard = model.getFlashcardBeingReviewed();

        model.markFlashcardBeingReviewed(flashcard, 2);
        assertEquals(2, flashcard.getIsCorrect());
    }

    @Test
    public void execute_markFlashcardBeingReviewedAsWrong() {
        Model model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        // To get a new flashcard
        model.updateFlashcardBeingReviewed(2);
        Flashcard flashcard = model.getFlashcardBeingReviewed();

        model.markFlashcardBeingReviewed(flashcard, 1);
        assertEquals(1, flashcard.getIsCorrect());
    }

    @Test
    public void equals() {
        FlashNotes flashNotes = new FlashNotesBuilder().withFlashcard(WHAT).withFlashcard(WHO).build();
        FlashNotes differentFlashNotes = new FlashNotes();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(flashNotes, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(flashNotes, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different flashNotes -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFlashNotes, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = WHAT.getQuestion().question.split("\\s+");
        modelManager.updateFilteredFlashcardList(new QuestionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(flashNotes, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFlashNotesFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(flashNotes, differentUserPrefs)));
    }
}
