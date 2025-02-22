package boo.misc;

import boo.Boo;
import boo.misc.DialogBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Boo boo;
    private Ui ui;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/jiwon.png"));
    private final Image booImage = new Image(this.getClass().getResourceAsStream("/images/Boo.png"));

    /**
     * Initializes the GUI components.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Boo instance */
    public void setBoo(Boo b) {
        boo = b;
        ui = boo.getUi();

        // Show the welcome message right after the GUI is initialized
        String welcomeMessage = ui.printGreeting();  // Get greeting from Boo
        dialogContainer.getChildren().add(DialogBox.getBooDialog(welcomeMessage, booImage));  // Display greeting
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Boo's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = boo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBooDialog(response, booImage)
        );
        userInput.clear();
    }
}
