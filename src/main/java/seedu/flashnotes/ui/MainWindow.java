package seedu.flashnotes.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.flashnotes.commons.core.GuiSettings;
import seedu.flashnotes.logic.Logic;
import seedu.flashnotes.logic.commands.CommandResult;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;
    private Logic logic;

    private RootNode rootNode;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        //initialize the deck list root node as the primary scene
        rootNode = new DeckCardListRoot(this, logic);

        Region root = rootNode.getFxmlLoader().getRoot();
        primaryStage.setScene(new Scene(root));

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        rootNode.fillInnerParts();
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Sets the new root node object for the MainWindow.
     *
     * @param rootNode The new root node to be rendered.
     * @param commandResult Command Result which contains the right message for the user.
     */
    public void setRootNode(RootNode rootNode, CommandResult commandResult) {
        this.rootNode = rootNode;
        Region root = rootNode.getFxmlLoader().getRoot();
        primaryStage.getScene().setRoot(root);
        primaryStage.show();

        this.rootNode.fillInnerParts();
        this.rootNode.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        rootNode.handleExit();
    }
}
