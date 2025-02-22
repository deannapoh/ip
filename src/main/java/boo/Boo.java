package boo;

import boo.misc.BooException;
import boo.misc.Parser;
import boo.misc.Storage;
import boo.misc.Ui;
import boo.task.TaskList;

/**
 * Represents the main class of the program.
 */
public class Boo {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    /**
     * Constructor for the chatbot
     *
     * @throws BooException If there is any problem reading the input.
     */
    public Boo() throws BooException {
        ui = new Ui();
        storage = new Storage("./data/Boo.txt");
        taskList = new TaskList(storage, ui);
        parser = new Parser(taskList, ui);
    }

    /**
     * Returns Boo's response to user input.
     *
     * @param input The user input.
     * @return Boo's response.
     */
    public String getResponse(String input) {
        ui.printGreeting();
        try {
            if (input.equalsIgnoreCase("bye")) {
                String goodbyeMessage = ui.printGoodbyeMessage();
                System.exit(0);
                return goodbyeMessage;
            }
            if (input.equalsIgnoreCase("list")) {
                return ui.printTaskHistory(taskList.getTaskMap());
            } else if (input.toLowerCase().startsWith("mark")) {
                return taskList.markAsDone(input);
            } else if (input.toLowerCase().startsWith("unmark")) {
                return taskList.markAsNotDone(input);
            } else if (input.toLowerCase().startsWith("delete")) {
                return taskList.deleteTask(input);
            } else if (input.toLowerCase().startsWith("find")) {
                return taskList.findTask(input);
            } else {
                return taskList.addTask(Parser.parseTask(input));
            }
        } catch (BooException e) {
            return e.getMessage();
        }
    }

    public Ui getUi() {
        return ui;
    }

}
