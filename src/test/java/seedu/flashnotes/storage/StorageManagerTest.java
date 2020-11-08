package seedu.flashnotes.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.flashnotes.testutil.TypicalFlashcards.getTypicalFlashNotes;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.model.FlashNotes;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonFlashNotesStorage flashNotesStorage = new JsonFlashNotesStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(flashNotesStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void flashNotesReadSave() throws Exception {
        /*
        * Note: This is an integration test that verifies the StorageManager is properly wired to the
        * {@link JsonFlashNotesStorage} class.
        * More extensive testing of UserPref saving/reading is done in {@link JsonFlashNotesStorageTest} class.
        * */
        FlashNotes original = getTypicalFlashNotes();
        storageManager.saveFlashNotes(original, original.getUniqueDeckList());
        ReadOnlyFlashNotes retrieved = storageManager.readFlashNotes().get();
        assertEquals(original, new FlashNotes(retrieved));
    }

    @Test
    public void getFlashNotesFilePath() {
        assertNotNull(storageManager.getFlashNotesFilePath());
    }

}
