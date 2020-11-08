package seedu.flashnotes.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashnotes.logic.parser.CliSyntax.PREFIX_DECK_NAME;

import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.deck.Deck;
/**
 * Adds a deck to the flashnotes.
 */
public class AddDeckCommand extends Command {
    public static final String COMMAND_WORD = "addDeck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Deck to flashnotes. "
            + "Parameters: "
            + PREFIX_DECK_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DECK_NAME + "CNM1046 ";

    public static final String MESSAGE_SUCCESS = "New Deck added: %1$s";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in the FlashNotes.";
    private final Deck toAdd;

    /**
     * Creates an AddDeckCommand to add the specified {@code Deck}
     */
    public AddDeckCommand(Deck cardDeck) {
        requireNonNull(cardDeck);
        toAdd = cardDeck;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDeck(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }

        model.addDeck(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getDeckName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeckCommand // instanceof handles nulls
                && toAdd.equals(((AddDeckCommand) other).toAdd));
    }
}
