package seedu.flashnotes.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.commons.core.Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX;
import static seedu.flashnotes.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.flashnotes.logic.commands.CommandTestUtil.VALID_TAG_NATURE;
import static seedu.flashnotes.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.flashnotes.logic.commands.AddDeckCommand;
import seedu.flashnotes.logic.commands.CommandResult;
import seedu.flashnotes.logic.commands.ListAllCommand;
import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.logic.parser.exceptions.ParseException;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.ModelManager;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.UserPrefs;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.deck.UniqueDeckList;
import seedu.flashnotes.storage.JsonFlashNotesStorage;
import seedu.flashnotes.storage.JsonUserPrefsStorage;
import seedu.flashnotes.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonFlashNotesStorage flashNotesStorage =
                new JsonFlashNotesStorage(temporaryFolder.resolve("flashnotes.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(flashNotesStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deleteDeck 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListAllCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, String.format(ListAllCommand.MESSAGE_SUCCESS,
                0), model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonFlashNotesIoExceptionThrowingStub
        JsonFlashNotesStorage flashNotesStorage =
                new JsonFlashNotesIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionFlashNotes.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(flashNotesStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddDeckCommand.COMMAND_WORD + " n/" + VALID_TAG_NATURE;
        Deck expectedDeck = new Deck(VALID_TAG_NATURE);
        ModelManager expectedModel = new ModelManager();
        expectedModel.addDeck(expectedDeck);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredFlashcardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFlashcardList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getFlashNotes(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFlashNotesIoExceptionThrowingStub extends JsonFlashNotesStorage {
        private JsonFlashNotesIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFlashNotes(ReadOnlyFlashNotes flashNotes, Path filePath, UniqueDeckList deckList)
                throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
