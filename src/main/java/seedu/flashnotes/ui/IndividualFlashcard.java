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

    // Count of number of questions gotten right the first time
    private int correctAnswers;

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
        this.correctAnswers = 0;
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
     * Displays the final statistics of review session in the GUI.
     * Update the deck revision stats with the review session's.
     */
    public String displayStatistics() {
        // Calculate Performance percentage
        int performance = (this.correctAnswers * 100) / this.numOfFlashcards;
        // Store the performance value
        logic.updateDeckPerformanceScore(performance);
        // Use the question label to list total percentage of first time right
        question.setText(String.format("Percentage of questions answered correctly on the first try: %d%s",
                performance,
                "%"));
        // Use the question label to list total questions right on first time right/total card
        answer.setText(String.format("Out of %d questions, you got %d right on the first try!",
                this.numOfFlashcards, this.correctAnswers));
        question.setVisible(true);
        answer.setVisible(true);
        // Successful update of it leads to display of statistic
        return "Here is your score for the review session!";
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
     * Returns whether the current flashcard being reviewed has been flipped
     * @return boolean
     */
    public boolean isCardFlipped() {
        return this.flipped;
    }

    /**
     * After marking the card as correct/wrong depending on user input,
     * show the next card. If the card is wrong, add card to the back of
     * the list to be reviewed again later.
     *
     * @param isCorrect
     */
    public String handleNextCard(int isCorrect) {
        assert(isCorrect > 0);
        if (isCorrect == 2) {
            this.count += 1;
        } else {
            Flashcard incorrectFlashcard = flashcardsToReview.get(this.index);
            this.flashcardsToReview = logic.addFlashcardToReview(incorrectFlashcard);
        }
        // Check if index is still within first run
        if (this.index < numOfFlashcards) {
            // If correctly answered on first try, increment correctAnswers
            if (isCorrect == 2) {
                this.correctAnswers += 1;
            }
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
