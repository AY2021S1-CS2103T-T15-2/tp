package seedu.flashnotes.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashnotes.testutil.TypicalFlashcards.getTypicalFlashNotes;

import org.junit.jupiter.api.Test;

import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.ModelManager;
import seedu.flashnotes.model.UserPrefs;

public class WrongCommandTest {
    private Model model;
    private Model expectedModel;
    @Test
    public void execute_wrong_command_success() {
        model = new ModelManager(getTypicalFlashNotes(), new UserPrefs());
        expectedModel = new ModelManager(model.getFlashNotes(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(WrongCommand.MESSAGE_WRONG_ACKNOWLEDGEMENT,
                false, false, false, false, 1);
        WrongCommand wrongCommand = new WrongCommand();
        assertEquals(wrongCommand.execute(model), expectedCommandResult);
    }

}
