import java.util.HashMap;
import java.util.Map;

public class TaskList {
    private HashMap<Integer, Task> taskMap;
    private Ui ui;
    private int taskId;
    private Storage storage;

    public TaskList() throws BooException {
        this.taskMap = new HashMap<Integer, Task>();
        this.storage = new Storage("./data/Boo.txt");
        this.taskMap = storage.loadTasks();
        this.ui = new Ui();
        if (!taskMap.isEmpty()) {
            this.taskId = taskMap.size() + 1; // Set taskId based on loaded tasks
        } else {
            this.taskId = 1;
        }
    }

    private void save() throws BooException {
        storage.saveTask(taskMap);
    }

    public HashMap<Integer, Task> getTaskMap() {
        return this.taskMap;
    }

    // Add task to the list and print the added task
    public void addTask(Task task) throws BooException{
        taskMap.put(taskId, task);
        ui.printAddedTask(taskId, task);
        this.taskId++;
        save();
    }

    public void deleteTask(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\nMaybe you mixed up the task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your task list\n");
            }
            this.taskMap.remove(taskId);

            //shift the remaining tasks
            for (int i = taskId + 1; i <= this.taskId - 1; i++) {
                Task shiftedTask = taskMap.get(i);
                if (shiftedTask != null) {
                    taskMap.put(i - 1, shiftedTask);
                    taskMap.remove(i);
                }
            }
            // total taskID - 1 since one task was deleted
            this.taskId--;
            save();
            ui.printRemovedTask(this.taskId, task);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to delete it.\nPlease try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }
    }

    // Mark task as done and print out marked task
    public void markAsDone(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\nMaybe you mixed up the task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your task list\n");
            }
            task.markAsDone();
            save();
            ui.printMarkedTask(task);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to mark it as done.\nPlease try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }

    }

    // mark task as undone and print out unmarked task
    public void markAsNotDone(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\nMaybe you mixed up the task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your task list\n");
            }
            task.markAsNotDone();
            save();
            ui.printUnmarkedTask(task);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to mark it as done.\nPlease try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }

    }
}
