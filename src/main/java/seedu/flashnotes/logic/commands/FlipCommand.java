package seedu.flashnotes.logic.commands;

import seedu.flashnotes.model.Model;

/**
 * Flips the card in review mode
 */
public class FlipCommand extends Command {
    public static final String COMMAND_WORD = "flip";

    public static final String MESSAGE_FLIP_ACKNOWLEDGEMENT = "Flipping Flashcard as requested...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_FLIP_ACKNOWLEDGEMENT, false, false, true, true, 0);
    }
}
