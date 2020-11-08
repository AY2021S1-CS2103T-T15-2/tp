package seedu.flashnotes.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.commons.core.LogsCenter;
import seedu.flashnotes.logic.commands.Command;
import seedu.flashnotes.logic.commands.CommandResult;
import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.logic.parser.FlashNotesParser;
import seedu.flashnotes.logic.parser.exceptions.ParseException;
import seedu.flashnotes.model.Model;
import seedu.flashnotes.model.ReadOnlyFlashNotes;
import seedu.flashnotes.model.deck.Deck;
import seedu.flashnotes.model.flashcard.Flashcard;
import seedu.flashnotes.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FlashNotesParser flashNotesParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        flashNotesParser = new FlashNotesParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        boolean isReviewMode = model.getIsReviewMode();
        boolean isInDeck = model.getIsInDeck();
        String deckName = model.getCurrentDeckName();

        if (deckName == null) {
            deckName = Model.getDefaultDeckName();
        }
        Command command = flashNotesParser.parseCommand(commandText, isReviewMode, isInDeck, deckName);
        commandResult = command.execute(model);

        try {
            storage.saveFlashNotes(model.getFlashNotes(), model.getUniqueDeckList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFlashNotes getFlashNotes() {
        return model.getFlashNotes();
    }

    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return model.getFilteredFlashcardList();
    }

    @Override
    public ObservableList<Flashcard> getFlashcardsToReview() {
        return model.getFlashcardsToReview();
    }

    @Override
    public ObservableList<Flashcard> getModifiedFlashcardsToReview() {
        return model.getModifiedFlashcardsToReview();
    }

    @Override
    public void resetFlipOfFlashcardBeingReviewed() {
        model.resetFlipOfFlashcardBeingReviewed();
    }

    public ObservableList<Deck> getFilteredCardDeckList() {
        return model.getFilteredDeckList();
    };

    // User preferences methods

    @Override
    public Path getFlashNotesFilePath() {
        return model.getFlashNotesFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public long getReviewCardLimit() {
        return model.getReviewCardLimit();
    }

    @Override
    public void setReviewCardLimit(long reviewCardLimit) {
        model.setReviewCardLimit(reviewCardLimit);
    }

    /**
     * Update the user's review score for deck used in review.
     * @param reviewScore Integer value of user's review session score.
     */
    @Override
    public void updateDeckPerformanceScore(Double reviewScore) {
        if (!model.getCurrentDeckName().equals(Model.getReservedDeckName())) {
            model.updateDeckPerformanceScore(reviewScore, model.getCurrentDeckName());
        }
    }

    /**
     * Turn off review mode.
     */
    @Override
    public void setIsReviewModeFalse() {
        model.setIsReviewModeFalse();
    }
}
