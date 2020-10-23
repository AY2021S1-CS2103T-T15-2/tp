package seedu.flashnotes.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.deck.Deck;

public class DeleteDeckCommand extends Command {
    public static final String COMMAND_WORD = "deleteDeck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a Deck from flashnotes. "
            + "Parameters: "
            + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + "Singapore ";

    public static final String MESSAGE_DELETE_DECK_SUCCESS = "Deleted deck: %1$s";
    public static final String MESSAGE_DECK_NOT_FOUND = "Deck does not exist in flashnotes";

    private final Deck deck;

    public DeleteDeckCommand(Deck deck) {
        requireNonNull(deck);
        this.deck = deck;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasDeck(deck)) {
            throw new CommandException(MESSAGE_DECK_NOT_FOUND);
        }

        model.deleteDeck(deck);
        return new CommandResult(String.format(MESSAGE_DELETE_DECK_SUCCESS, deck.getDeckName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDeckCommand // instanceof handles nulls
                && deck.equals(((DeleteDeckCommand) other).deck)); // state check
    }
}
