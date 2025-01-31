package boo;

import boo.misc.BooException;
import boo.misc.Parser;
import boo.misc.Storage;
import boo.misc.Ui;
import boo.task.TaskList;


import java.util.Scanner;

/**
 * Represents the main class of the program.
 */
public class Boo {

    /**
     * Main method of the program.
     *
     * @param args Inputs typed by the user.
     * @throws BooException If there is any problem reading the input.
     */
    public static void main(String[] args) throws BooException {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/Boo.txt");
        TaskList taskList = new TaskList(storage, ui);
        Parser parser = new Parser(taskList, ui);

        // Print greeting message
        ui.printGreeting();

        // Create scanner to read user inputs
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            // Read user input
            String input = scanner.nextLine();
            try{
                if (parser.parseCommand(input)) {
                    break;
                }
            } catch (BooException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
