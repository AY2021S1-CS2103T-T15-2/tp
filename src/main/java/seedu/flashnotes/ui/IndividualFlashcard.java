package seedu.flashnotes.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.flashnotes.commons.core.LogsCenter;
import seedu.flashnotes.logic.Logic;
import seedu.flashnotes.model.flashcard.Flashcard;

public class IndividualFlashcard extends UiPart<Region> {
    private static final String FXML = "IndividualFlashcard.fxml";
    private final Logger logger = LogsCenter.getLogger(IndividualFlashcard.class);

    private final Logic logic;
    private ObservableList<Flashcard> flashcardsToReview;
    private int index;
    private double count;
    private boolean flipped;
    private int numOfFlashcards;
    /**
     * Update these below to match flashcard.
     */
    @FXML
    private Label question;

    @FXML
    private Label answer;

    /**
     * Constructs an individual flashcard display panel.
     * @param logic
     */
    public IndividualFlashcard(Logic logic) {
        super(FXML);
        this.index = 0;
        this.count = 0;
        this.logic = logic;
    }

    /**
     * Initializes the content of the flashcards to review in display.
     */
    public void init() {
        this.flashcardsToReview = logic.getFlashcardsToReview();
        this.numOfFlashcards = flashcardsToReview.size();
    }

    /**
     * Displays the flashcard in the GUI
     */
    public void displayFlashcard() {
        Flashcard flashcardToDisplay = this.flashcardsToReview.get(this.index);
        question.setText("Question: " + flashcardToDisplay.getQuestion().question);
        answer.setText("Answer: " + flashcardToDisplay.getAnswer().value);
        question.setVisible(true);
        answer.setVisible(false);
    }

    /**
     * Flips the flashcard to show the answer/question
     */
    public void flipFlashcard() {
        this.flipped = !flipped;
        if (flipped) {
            question.setVisible(false);
            answer.setVisible(true);
        } else {
            question.setVisible(true);
            answer.setVisible(false);
        }
    }

    /**
     * After marking the card as correct/wrong depending on user input,
     * show the next card. If the card is wrong, add card to the back of
     * the list to be reviewed again later.
     *
     * @param isCorrect
     */
    public String handleNextCard(int isCorrect) {
        if (isCorrect == 2) {
            this.count += 1;
        } else {
            Flashcard incorrectFlashcard = flashcardsToReview.get(this.index);
            this.flashcardsToReview = logic.addFlashcardToReview(incorrectFlashcard);
        }
        this.index += 1;
        if (count == numOfFlashcards) {
            return "exit";
        } else {
            this.flipped = false;
            displayFlashcard();
            return Double.toString(count / numOfFlashcards);
        }
    }
}
