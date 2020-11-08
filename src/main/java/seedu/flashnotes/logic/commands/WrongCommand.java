package seedu.flashnotes.logic.commands;

import seedu.flashnotes.model.Model;

/**
 * Marks the flashcard being reviewed as wrong.
 */

public class WrongCommand extends Command {
    public static final String COMMAND_WORD = "w";

    public static final String MESSAGE_WRONG_ACKNOWLEDGEMENT = "Marked Flashcard as wrong.";
    public static final String MESSAGE_WRONG_ERROR = "Please flip the card to check your answer before marking"
            + " it as correct or wrong. \nEnter `f` to flip.";
    @Override
    public CommandResult execute(Model model) {
        if (model.getIsFlashcardFlipped()) {
            model.updateFlashcardBeingReviewed(1);
            model.addFlashcardToReview();
        } else {
            return new CommandResult(MESSAGE_WRONG_ERROR, false, false,
                    true, false, 0);
        }
        return new CommandResult(MESSAGE_WRONG_ACKNOWLEDGEMENT, false, false, true, false, 1);
    }
}
