import java.util.HashMap;
import java.util.Map;

public class Ui {
    private static final String LINE = "____________________________________________________________\n";

    public void printMessage(String msg) {
        String message = LINE + msg + LINE;
        System.out.println(message);
    }

    public void printGreeting() {
        printMessage("Hello! I'm Boo\n" + "What can I do for you?\n");
    }

    public void printGoodbyeMessage() {
        printMessage("Bye. Hope to see you again soon!\n");
    }

    public void printAddedTask(int taskId, Task task) {
        String msg = "Got it. Boo has added this task:\n" + "  " + task.toString() + "\n"
                + "Now you have " + taskId + " tasks in the list.\n";
        printMessage(msg);
    }

    public void printRemovedTask(int taskId, Task task) {
        String msg = "Noted! Boo has removed this task:\n "
                + task.toString() + "\n" + "Now you have " + (taskId-1) + " tasks in the list.\n";
        printMessage(msg);
    }

    public void printMarkedTask(Task task) {
        String msg = "Nice! Boo has marked this task as done:\n "
                + task.toString() + "\n";
        printMessage(msg);
    }

    public void printUnmarkedTask(Task task) {
        String msg = "OK, Boo has marked this task as not done yet:\n "
                + task.toString() + "\n";
        printMessage(msg);
    }

    public void printTaskHistory(HashMap<Integer, Task> taskMap) {
        if (taskMap.isEmpty()) {
            printMessage("Yay! You currently have no tasks :) \n");
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
