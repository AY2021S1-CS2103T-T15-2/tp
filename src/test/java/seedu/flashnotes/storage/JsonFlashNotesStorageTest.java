package seedu.flashnotes.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.flashnotes.testutil.Assert.assertThrows;
import static seedu.flashnotes.testutil.TypicalFlashcards.WHAT;
import static seedu.flashnotes.testutil.TypicalFlashcards.WHAT_IS_MEIER;
import static seedu.flashnotes.testutil.TypicalFlashcards.WHO_IS_MEIER;
import static seedu.flashnotes.testutil.TypicalFlashcards.getTypicalFlashNotes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.flashnotes.commons.exceptions.DataConversionException;
import seedu.flashnotes.model.FlashNotes;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.deck.UniqueDeckList;

public class JsonFlashNotesStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonFlashNotesStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFlashNotes_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFlashNotes(null));
    }

    private java.util.Optional<ReadOnlyFlashNotes> readFlashNotes(String filePath) throws Exception {
        return new JsonFlashNotesStorage(Paths.get(filePath)).readFlashNotes(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }


    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFlashNotes("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFlashNotes("notJsonFormatFlashNotes.json"));
    }

    @Test
    public void readFlashNotes_invalidFlashcardFlashNotes_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFlashNotes("invalidFlashcard.json"));
    }

    @Test
    public void readFlashNotes_invalidDeckFlashNotes_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFlashNotes("invalidDeck.json"));
    }

    @Test
    public void readFlashNotes_invalidAndValidFlashcardFlashNotes_throwDataConversionException() {
        assertThrows(DataConversionException
                .class, () -> readFlashNotes("invalidAndValidFlashcard.json"));
    }

    @Test
    public void readAndSaveFlashNotes_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempFlashNotes.json");
        FlashNotes original = getTypicalFlashNotes();
        UniqueDeckList originalDecks = original.getUniqueDeckList();
        JsonFlashNotesStorage jsonFlashNotesStorage = new JsonFlashNotesStorage(filePath);

        // Save in new file and read back
        jsonFlashNotesStorage.saveFlashNotes(original, filePath, originalDecks);
        ReadOnlyFlashNotes readBack = jsonFlashNotesStorage.readFlashNotes(filePath).get();
        assertEquals(original, new FlashNotes(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFlashcard(WHO_IS_MEIER);
        original.removeFlashcard(WHAT);
        original = new FlashNotes(original); //todo remove this line when proper deck impl is up - PX
        jsonFlashNotesStorage.saveFlashNotes(original, filePath, original.getUniqueDeckList());
        readBack = jsonFlashNotesStorage.readFlashNotes(filePath).get();
        assertEquals(original, new FlashNotes(readBack));

        // Save and read without specifying file path
        original.addFlashcard(WHAT_IS_MEIER);
        original = new FlashNotes(original); //todo remove this line when proper deck impl is up - PX
        jsonFlashNotesStorage.saveFlashNotes(original, original.getUniqueDeckList()); // file path not specified
        readBack = jsonFlashNotesStorage.readFlashNotes().get(); // file path not specified
        assertEquals(original, new FlashNotes(readBack));
    }

    @Test
    public void saveFlashNotes_nullFlashNotes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFlashNotes(null,
                null, "SomeFile.json"));
    }

    /**
     * Saves {@code flashNotes} at the specified {@code filePath}.
     */
    private void saveFlashNotes(ReadOnlyFlashNotes flashNotes, UniqueDeckList decks, String filePath) {
        try {
            new JsonFlashNotesStorage(Paths.get(filePath))
                    .saveFlashNotes(flashNotes, addToTestDataPathIfNotNull(filePath), decks);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFlashNotes_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFlashNotes(new FlashNotes(), new UniqueDeckList(),
                null));
    }

}
