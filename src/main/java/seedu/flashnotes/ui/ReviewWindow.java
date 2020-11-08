package seedu.flashnotes.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.flashnotes.commons.core.LogsCenter;
import seedu.flashnotes.logic.Logic;
import seedu.flashnotes.logic.commands.CommandResult;
import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.logic.parser.exceptions.ParseException;

/**
 * Controller for a review page
 */
public class ReviewWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(ReviewWindow.class);
    private static final String FXML = "ReviewWindow.fxml";
    // Make use of a standard message for invalid input at end of review session
    private static final String MESSAGE_END_OF_REVIEW = "The review session has ended. "
            + "Please enter 'endReview' to return to the deck screen.";

    private Logic logic;
    private CommandBox commandBox;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private IndividualFlashcard individualFlashcard;

    private MainWindow mainWindow;

    // Use a boolean to check status of review session
    private boolean isComplete;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane individualFlashcardPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private ProgressBar progressBar;

    /**
     * Creates a new ReviewWindow.
     */
    public ReviewWindow(Logic logic, MainWindow mainWindow) {
        super(FXML, new Stage());
        this.logic = logic;
        this.helpWindow = new HelpWindow();
        this.mainWindow = mainWindow;

        this.commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        this.individualFlashcard = new IndividualFlashcard(logic);
        individualFlashcardPlaceholder.getChildren().add(individualFlashcard.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFlashNotesFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        this.progressBar = new ProgressBar(0);
        statusbarPlaceholder.getChildren().add(progressBar);

        // Initial state of isComplete is False
        this.isComplete = false;
    }

    /**
     * Shows the review window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing review page about the application.");
        this.individualFlashcard.init();
        this.individualFlashcard.displayFlashcard();
        getRoot().setAlwaysOnTop(true);
        getRoot().initStyle(StageStyle.UTILITY);
        getRoot().showAndWait();
        // After manual closing
        this.handleExit();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the review window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the review window.
     */
    public void hide() {
        // Hide review window
        getRoot().hide();
    }

    /**
     * Focuses on the review window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Shows the final statistics of the review session.
     * Updates model with new review statistic for the deck.
     */
    public void displayStatistics() {
        // Log
        logger.info("Displaying statistics of current review session.");
        // Update IndividualFlashcard UI
        String message = this.individualFlashcard.displayStatistics();
        // Update resultDisplay
        resultDisplay.setFeedbackToUser(message);
        // Update progressBar display (100% correctly answered)
        progressBar.setProgress(1);
        // Mark the review session at its end
        this.isComplete = true;
    }

    /**
     * Flips the flashcard to show the answer/question
     */
    public void handleFlip() {
        this.individualFlashcard.flipFlashcard();
    }

    /**
     * After marking the card as correct/wrong depending on user input,
     * show the next card. If the card is wrong, add card to the back of
     * the list to be reviewed again later.
     *
     * @param isCorrect
     */
    public void handleNextCard(int isCorrect) {
        assert(isCorrect > 0);
        String result = individualFlashcard.handleNextCard(isCorrect);
        if (result.equals("exit")) {
            displayStatistics();
        } else {
            progressBar.setProgress(Double.parseDouble(result));
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        // Turn off review mode in logic
        logic.setIsReviewModeFalse();
        // Hide help window
        helpWindow.hide();
        RootNode rootNode = new FlashcardListRoot(mainWindow, logic);
        CommandResult commandResult = new CommandResult("Closed Review Window");
        Region root = rootNode.getFxmlLoader().getRoot();
        mainWindow.getPrimaryStage().getScene().setRoot(root);

        rootNode.fillInnerParts();
        rootNode.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.flashnotes.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            if (isComplete && !(commandResult.isExit())) {
                // If session has ended, ban the usage of next command
                throw new CommandException(MESSAGE_END_OF_REVIEW);
            }
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                // Hide review window
                this.hide();
                // Return to Card View
                handleExit();
            }

            if (commandResult.isNext() != 0) {
                handleNextCard(commandResult.isNext());
            }

            if (commandResult.isFlipped()) {
                handleFlip();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

}
