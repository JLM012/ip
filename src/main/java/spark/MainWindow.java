package spark;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Spark spark;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image sparkImage = new Image(this.getClass().getResourceAsStream("/images/spark.png"));


    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane.setFitToWidth(true);
        showWelcome();
    }

    /**
     * Sets the Spark instance.
     *
     * @param spark The Spark instance
     */
    public void setSpark(Spark spark) {
        this.spark = spark;
    }


    private void showWelcome() {
        String welcomeMessage = "Hello! I'm Spark\nWhat can I do for you?";
        dialogContainer.getChildren().add(DialogBox.getSparkDialog(welcomeMessage, sparkImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Spark's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }

        // Create user dialog
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        // Get response from Spark
        String response = spark.getResponse(input);
        dialogContainer.getChildren().add(DialogBox.getSparkDialog(response, sparkImage));
        userInput.clear();

        if (spark.isExit()) {
            Platform.exit();

        }
    }
}