package boo.misc;

import boo.task.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user interface that is in charge of interacting with the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________\n";

    /**
     * Prints messages output by the chatbot in the correct format.
     *
     * @param msg Message that chatbot outputs.
     */
    public void printMessage(String msg) {
        String message = LINE + msg + LINE;
        System.out.println(message);
    }

    /**
     * Prints the greeting of the Chatbot when it first starts up.
     */
    public void printGreeting() {
        printMessage("Hello! I'm Boo\n" + "What can I do for you?\n");
    }

    /**
     * Prints the goodbye message of the Chatbot when it terminates.
     */
    public void printGoodbyeMessage() {
        printMessage("Bye. Hope to see you again soon!\n");
    }

    /**
     * Prints the corresponding message when task is successfully added to task list.
     *
     * @param taskId ID of the task that was just added.
     * @param task Task that was added.
     */
    public void printAddedTask(int taskId, Task task) {
        String msg = "Got it. Boo has added this task:\n" + "  " + task.toString() + "\n"
                + "Now you have " + taskId + " tasks in the list.\n";
        printMessage(msg);
    }

    /**
     * Prints the corresponding message when task is successfully removed from the task list.
     *
     * @param taskId ID of the task that was just removed.
     * @param task Task that was removed.
     */
    public void printRemovedTask(int taskId, Task task) {
        String msg = "Noted! Boo has removed this task:\n "
                + task.toString() + "\n" + "Now you have " + (taskId-1) + " tasks in the list.\n";
        printMessage(msg);
    }

    /**
     * Prints the corresponding message when task is successfully marked.
     *
     * @param task Task that was marked as done.
     */
    public void printMarkedTask(Task task) {
        String msg = "Nice! Boo has marked this task as done:\n "
                + task.toString() + "\n";
        printMessage(msg);
    }

    /**
     * Prints the corresponding message when task is successfully unmarked.
     *
     * @param task Task that was unmarked.
     */
    public void printUnmarkedTask(Task task) {
        String msg = "OK, Boo has marked this task as not done yet:\n "
                + task.toString() + "\n";
        printMessage(msg);
    }

    /**
     * Prints the task history.
     *
     * @param taskMap HashMap of the task history.
     */
    public void printTaskHistory(HashMap<Integer, Task> taskMap) {
        if (taskMap.isEmpty()) {
            printMessage("Yay! You currently have no tasks :)\nBoo couldn't be prouder <3\n");
        } else {
            String msg = "Here are the tasks in your list:\n";
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                int taskId = entry.getKey();
                Task task = entry.getValue();
                msg += taskId + ". " + task.toString() + "\n";
            }
            printMessage(msg);
        }
    }
}
