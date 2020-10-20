package seedu.flashnotes.logic.commands;

import seedu.flashnotes.model.Model;

/**
 * Marks the flashcard being reviewed as correct.
 */
public class CorrectCommand extends Command {
    public static final String COMMAND_WORD = "c";

    public static final String MESSAGE_CORRECT_ACKNOWLEDGEMENT = "Marking Flashcard as correct as requested...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_CORRECT_ACKNOWLEDGEMENT, false, false, true, false, 2);
    }
}
