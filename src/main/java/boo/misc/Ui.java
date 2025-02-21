package boo.misc;

import boo.task.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a user interface that is in charge of interacting with the user.
 */
public class Ui {
    /**
     * Prints messages output by the chatbot in the correct format.
     *
     * @param msg Message that chatbot outputs.
     */
    public String printMessage(String msg) {
        return msg;
    }

    /**
     * Prints the greeting of the Chatbot when it first starts up.
     */
    public String printGreeting() {
        return "Hello! I'm Boo! Are you ready to stop ghosting your assignments?\n";
    }

    /**
     * Prints the goodbye message of the Chatbot when it terminates.
     */
    public String printGoodbyeMessage() {
        return "NOOO please don't go!! Boo will miss you :( Come back soon!!\n";
    }

    /**
     * Prints the corresponding message when task is successfully added to task list.
     *
     * @param taskId ID of the task that was just added.
     * @param task Task that was added.
     */
    public String printAddedTask(int taskId, Task task) {
        String msg = "New task? On it. Boo has added this task:\n" + "  " + task.toString() + "\n"
                + "Now you have " + (taskId-1) + " tasks altogether.\n"
                + "\nBut don't worry—take it one step at a time. You've got this! Boo believes in you!\n";
        return msg;
    }

    /**
     * Prints the corresponding message when task is successfully removed from the task list.
     *
     * @param taskId ID of the task that was just removed.
     * @param task Task that was removed.
     */
    public String printRemovedTask(int taskId, Task task) {
        String msg = "Got it! Boo has removed this task:\n "
                + task.toString() + "\n" + "\nYAY!!! You are now only left with " + (taskId-1) + " tasks!\n"
                + "Keep up the good work!\n";
        return msg;
    }

    /**
     * Prints the corresponding message when task is successfully marked.
     *
     * @param task Task that was marked as done.
     */
    public String printMarkedTask(Task task) {
        String msg = "YAY!!! Boo has marked this task as done:\n "
                + task.toString() + "\n" + "\nBoo knew you could do it! Boo is so proud of you <3\n";
        return msg;
    }

    /**
     * Prints the corresponding message when task is successfully unmarked.
     *
     * @param task Task that was unmarked.
     */
    public String printUnmarkedTask(Task task) {
        String msg = "Noted! Boo has marked this task as not done yet:\n "
                + task.toString() + "\n" + "\nNo worries—these things happen! You got this!\n";
        return msg;
    }

    /**
     * Prints the task history.
     *
     * @param taskMap HashMap of the task history.
     */
    public String printTaskHistory(HashMap<Integer, Task> taskMap) {
        if (taskMap.isEmpty()) {
            return "Yay! You currently have no tasks :)\nBoo couldn't be prouder <3\n";
        } else {
            String msg = "These are the tasks you have:\n";
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                int taskId = entry.getKey();
                Task task = entry.getValue();
                msg += taskId + ". " + task.toString() + "\n";
            }
            msg += "\nThis might seem overwhelming, but don't stress!\n" +
                    "Take it one step at a time and you'll get them all done in no time! "
                    + "Boo is rooting for you!\n";
            return msg;
        }
    }
}
