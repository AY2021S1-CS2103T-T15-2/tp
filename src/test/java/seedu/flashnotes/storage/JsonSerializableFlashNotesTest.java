package seedu.flashnotes.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.flashnotes.commons.exceptions.IllegalValueException;
import seedu.flashnotes.commons.util.JsonUtil;
import seedu.flashnotes.model.FlashNotes;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.testutil.TypicalFlashcards;

public class JsonSerializableFlashNotesTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonSerializableFlashNotesTest");
    private static final Path TYPICAL_FLASHCARDS_FILE = TEST_DATA_FOLDER.resolve("typicalFlashcards.json");
    private static final Path INVALID_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("invalidFlashcard.json");
    private static final Path DUPLICATE_FLASHCARD_FILE = TEST_DATA_FOLDER.resolve("duplicateFlashcard.json");
    private static final Path INVALID_DECK_FILE = TEST_DATA_FOLDER.resolve("invalidDeck.json");
    private static final Path RESERVED_DECK_FILE = TEST_DATA_FOLDER.resolve("reservedDeck.json");


    @Test
    public void toModelType_typicalFlashcardsFile_success() throws Exception {
        JsonSerializableFlashNotes dataFromFile = JsonUtil.readJsonFile(TYPICAL_FLASHCARDS_FILE,
                JsonSerializableFlashNotes.class).get();
        FlashNotes flashNotesFromFile = dataFromFile.toModelType();
        FlashNotes typicalFlashcardsFlashNotes = TypicalFlashcards.getTypicalFlashNotes();
        System.out.println(flashNotesFromFile);
        System.out.println(typicalFlashcardsFlashNotes);
        assertEquals(flashNotesFromFile, typicalFlashcardsFlashNotes);
    }

    @Test
    public void toModelType_invalidFlashcardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashNotes dataFromFile = JsonUtil.readJsonFile(INVALID_FLASHCARD_FILE,
                JsonSerializableFlashNotes.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateFlashcards_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashNotes dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FLASHCARD_FILE,
                JsonSerializableFlashNotes.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFlashNotes.MESSAGE_DUPLICATE_FLASHCARD,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidDeckFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashNotes dataFromFile = JsonUtil.readJsonFile(INVALID_DECK_FILE,
                JsonSerializableFlashNotes.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_reservedDeck_throwsIllegalValueException() throws Exception {
        JsonSerializableFlashNotes dataFromFile = JsonUtil.readJsonFile(RESERVED_DECK_FILE,
                JsonSerializableFlashNotes.class).get();
        assertThrows(IllegalValueException.class, Deck.MESSAGE_CONSTRAINTS_RESERVED,
                dataFromFile::toModelType);
    }

}
