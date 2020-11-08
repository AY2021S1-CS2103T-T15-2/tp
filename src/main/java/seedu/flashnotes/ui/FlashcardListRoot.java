package seedu.flashnotes.ui;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.commons.core.LogsCenter;
import seedu.flashnotes.logic.Logic;
import seedu.flashnotes.logic.commands.CommandResult;
import seedu.flashnotes.logic.commands.exceptions.CommandException;
import seedu.flashnotes.logic.parser.exceptions.ParseException;


public class FlashcardListRoot extends UiPart<Region> implements RootNode {
    private static final String FXML = "FlashcardListRoot.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final MainWindow mainWindow;
    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private FlashcardListPanel flashcardListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ReviewWindow reviewWindow;
    private CommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane cardListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;
    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public FlashcardListRoot(MainWindow mainWindow, Logic logic) {
        super(FXML);

        this.mainWindow = mainWindow;
        this.logic = logic;
        setAccelerators();

        helpWindow = new HelpWindow();
        reviewWindow = new ReviewWindow(logic, mainWindow);
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }
    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        flashcardListPanel = new FlashcardListPanel(logic.getFilteredFlashcardList());
        cardListPanelPlaceholder.getChildren().add(flashcardListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFlashNotesFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        this.commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    @Override
    public void setFeedbackToUser(String s) {
        resultDisplay.setFeedbackToUser(s);
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
     * Opens the review window or focuses on it if it's already opened.
     */
    @FXML
    public void handleReview() {
        if (!reviewWindow.isShowing()) {
            disableCommandBox();
            reviewWindow.show();
        } else {
            reviewWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        Stage primaryStage = mainWindow.getPrimaryStage();
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        reviewWindow.hide();
        primaryStage.hide();
    }

    /**
     * Removes functionality for users to enter commands in the command box temporarily.
     */
    private void disableCommandBox() {
        this.commandBox.disable();
    }

    /**
     * Restores functionality for users to enter commands in the command box temporarily.
     */
    public void enableCommandBox() {
        this.commandBox.enable();
    }

    public FlashcardListPanel getFlashcardListPanel() {
        return flashcardListPanel;
    }
    /**
     * Executes the valid commands in card-mode and returns the result.
     *
     * @see seedu.flashnotes.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isStartReview()) {
                handleReview();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isHome()) {
                Stage primaryStage = mainWindow.getPrimaryStage();

                GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                        (int) primaryStage.getX(), (int) primaryStage.getY());
                logic.setGuiSettings(guiSettings);

                RootNode rootNode = new DeckCardListRoot(mainWindow, logic);

                mainWindow.setRootNode(rootNode, commandResult);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

}

