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
        Task task = new Task(message);
        taskMap.put(taskId, task);
        String addedTask = "____________________________________________________________ \n"
                + "added: " + message + "\n"
                + "____________________________________________________________ \n";
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
                System.out.println(taskId + ". " + task.printTask());
            }
            System.out.println("____________________________________________________________ \n");
        }
    }

    // Mark task as done and print out marked task
    public void markAsDone(int taskId) {
        Task task = taskMap.get(taskId);
        if (task != null) {
            task.markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println("Nice! I've marked this task as done: \n "
                    + task.printTask() + "\n"
                    + "____________________________________________________________ \n");

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
            System.out.println("OK, I've marked this task as not done yet: \n "
                    + task.printTask() + "\n"
                    + "____________________________________________________________ \n");

        } else {
            System.out.println("Error 404. Task not found.");
        }
    }
}
