package seedu.flashnotes.ui;

import javafx.fxml.FXMLLoader;

/**
 * Interface for Root Nodes of MainWindow object.
 */
public interface RootNode {

    void fillInnerParts();
    FXMLLoader getFxmlLoader();

    void setFeedbackToUser(String s);
    void handleExit();
}
