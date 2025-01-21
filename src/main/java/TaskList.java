import java.util.HashMap;
import java.util.Map;

public class TaskList {
    private HashMap<Integer, Task> taskMap;
    private int taskId;

    public TaskList() {
        this.taskMap = new HashMap<Integer, Task>();
        this.taskId = 1;
    }

    // Add task to the list and print the added task
    public void addTask(String message) {
        Task task;
        // Create new todo task
        if (message.toLowerCase().startsWith("todo")) {
            task = new Todo(message.substring(5).trim());

            // Create new deadline task
        } else if (message.toLowerCase().startsWith("deadline")) {
            String[] details = message.substring(9).split("/by");
            String description = details[0].trim();
            String by = details[1].trim();
            task = new Deadline(description, by);

            // Create new event task
        } else if (message.toLowerCase().startsWith("event")) {
            String[] details = message.substring(6).split("/from|/to");
            String description = details[0].trim();
            String from = details[1].trim();
            String to = details[2].trim();
            task = new Event(description, from, to);
            // Else, create normal task
        } else {
            task = new Task(message);
        }

        taskMap.put(taskId, task);
        String addedTask = "____________________________________________________________\n"
                + "Got it. I've added this task:\n" + "  " + task.toString() + "\n"
                + "Now you have " + this.taskId + " tasks in the list.\n"
                + "____________________________________________________________\n";
        System.out.println(addedTask);
        this.taskId++;
    }

    // Display the list of tasks
    public void printHistory() {
        if (this.taskMap.isEmpty()) {
            System.out.println("Task List is empty");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println("Here are the tasks in your list:");
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                int taskId = entry.getKey();
                Task task = entry.getValue();
                System.out.println(taskId + ". " + task.toString());
            }
            System.out.println("____________________________________________________________\n");
        }
    }

    // Mark task as done and print out marked task
    public void markAsDone(int taskId) {
        Task task = taskMap.get(taskId);
        if (task != null) {
            task.markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println("Nice! I've marked this task as done:\n "
                    + task.toString() + "\n"
                    + "____________________________________________________________\n");

        } else {
            System.out.println("Error 404. Task not found.");
        }
    }

    // mark task as undone and print out unmarked task
    public void markAsNotDone(int taskId) {
        Task task = taskMap.get(taskId);
        if (task != null) {
            task.markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println("OK, I've marked this task as not done yet:\n "
                    + task.toString() + "\n"
                    + "____________________________________________________________\n");

        } else {
            System.out.println("Error 404. Task not found.");
        }
    }
}
