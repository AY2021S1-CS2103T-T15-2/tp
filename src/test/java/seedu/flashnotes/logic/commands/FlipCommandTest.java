package seedu.flashnotes.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.testutil.Assert.assertThrows;
import static seedu.flashnotes.testutil.TypicalFlashcards.getTypicalFlashNotes;

import org.junit.jupiter.api.Test;

import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.ModelManager;
import seedu.flashnotes.model.UserPrefs;

public class FlipCommandTest {
    private Model model;
    private Model expectedModel;
    @Test
    public void execute_flip_success() {
        model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(FlipCommand.MESSAGE_FLIP_ACKNOWLEDGEMENT,
                false, false, false, true, 0);
        FlipCommand flipCommand = new FlipCommand();
        assertEquals(flipCommand.execute(model), expectedCommandResult);
    }

    @Test
    public void execute_double_flip() {
        model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(FlipCommand.MESSAGE_FLIP_ACKNOWLEDGEMENT,
                false, false, false, true, 0);
        FlipCommand flipCommand = new FlipCommand();
        CommandResult firstResult = flipCommand.execute(model);
        assertEquals(firstResult, expectedCommandResult);
        assertEquals(flipCommand.execute(model), expectedCommandResult);
    }
}
