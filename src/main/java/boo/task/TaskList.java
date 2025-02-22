package boo.task;

import boo.misc.BooException;
import boo.misc.Ui;
import boo.misc.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a list of tasks that can be manipulated.
 * Tasks can be of any type.
 */
public class TaskList {
    private HashMap<Integer, Task> tasksMap;
    private final Ui ui;
    private int taskId;
    private final Storage storage;

    /**
     * Constructs a list of tasks, in the form of a Hashmap.
     * Task ID starts at 1 and increases as tasks get added.
     *
     * @param storage Storage to store and load the task list in a hard disk.
     * @param ui Interface that interacts with the user.
     */
    public TaskList(Storage storage, Ui ui) throws BooException {
        this.tasksMap = new HashMap<Integer, Task>();
        // Storage should not be null
        assert storage != null: "Storage must no be null!";
        this.storage = storage;
        this.tasksMap = storage.loadTasksFromFile();
        //Ui should not be null
        assert ui != null: "Ui must not be null!";
        this.ui = ui;
        if (!tasksMap.isEmpty()) {
            this.taskId = tasksMap.size() + 1;
        } else {
            this.taskId = 1;
        }
        // Task ID cannot be negative
        assert taskId > 0 : "Task ID must always be positive";
    }

    /**
     * Saves the task list into a designated hard disk.
     */
    private void save() throws BooException {
        assert storage != null : "Storage must not be null when saving";
        storage.saveTasksToFile(tasksMap);
    }

    public HashMap<Integer, Task> getTaskMap() {
        sortTasks();
        return this.tasksMap;
    }

    /**
     * Adds a specified task to the task list.
     * Prints and saves the added task.
     *
     * @param task Task that is to be added to the task list.
     */
    public String addTask(Task task) throws BooException {
        assert task != null : "Task must not be null";
        tasksMap.put(taskId, task);
        this.taskId++;
        sortTasks();
        save();
        return ui.printAddedTask(taskId, task);
    }

    /**
     * Deletes a specified task from the task list.
     * Prints the deleted task.
     *
     * @param input Input provided by the user to pinpoint the task to be deleted.
     * @throws BooException If task ID could not be found or if task ID is not an integer.
     */
    public String deleteTask(String input) throws BooException {
        assert input != null && !input.trim().isEmpty() : "Input must not be null or empty";
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = tasksMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\n"
                        + "Maybe you mixed up the task IDS? Please try again!\nThere are currently "
                        + tasksMap.size() + " tasks in your task list\n");
            }
            this.tasksMap.remove(taskId);

            //Shift the remaining tasks
            for (int i = taskId + 1; i <= this.taskId - 1; i++) {
                Task shiftedTask = tasksMap.get(i);
                if (shiftedTask != null) {
                    tasksMap.put(i - 1, shiftedTask);
                    tasksMap.remove(i);
                }
            }
            // Total taskID - 1 since one task was deleted
            this.taskId--;
            sortTasks();
            save();
            return ui.printRemovedTask(this.taskId, task);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to delete it.\n"
                    + "Please try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }
    }

    /**
     * Marks a specified task as done.
     * Prints the marked task.
     *
     * @param input Input provided by the user to pinpoint the task to be marked as done.
     * @throws BooException If task ID could not be found or if task ID is not an integer.
     */
    public String markAsDone(String input) throws BooException {
        assert input != null && !input.trim().isEmpty() : "Input must not be null or empty";
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = tasksMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\n"
                        + "Maybe you mixed up the task IDS? Please try again!\nThere are currently "
                        + tasksMap.size() + " tasks in your task list\n");
            }
            task.setAsDone();
            sortTasks();
            save();
            return ui.printMarkedTask(task);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to mark it as done.\n"
                    + "Please try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }
    }

    /**
     * Unmarks a specified task.
     * Prints the unmarked task.
     *
     * @param input Input provided by the user to pinpoint the task to be marked as not done.
     * @throws BooException If task ID could not be found or if task ID is not an integer.
     */
    public String markAsNotDone(String input) throws BooException {
        assert input != null && !input.trim().isEmpty() : "Input must not be null or empty";
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = tasksMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\n"
                        + "Maybe you mixed up the task IDS? Please try again!\nThere are currently "
                        + tasksMap.size() + " tasks in your task list\n");
            }
            task.setAsNotDone();
            sortTasks();
            save();
            return ui.printUnmarkedTask(task);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to mark it as done.\n"
                    + "Please try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your task ID to be an integer!\n");
        }
    }

    /**
     * Finds tasks that contain a specific keyword.
     * Prints those tasks that contain that keyword.
     *
     * @param input Input that contains the keyword used to find the task.
     * @throws BooException If no keyword is provided.
     */
    public String findTask(String input) throws BooException {
        assert input != null && !input.trim().isEmpty() : "Input must not be null or empty";
        try {
            String keyword = input.substring(4).trim();
            if (keyword.isEmpty()) {
                throw new BooException("Oops! Boo needs a keyword to find tasks.\n");
            }
            String matchedTask = "Here are the matching tasks in your list:\n";
            int numMatches = 0;
            for (int taskId : tasksMap.keySet()) {
                Task task = tasksMap.get(taskId);
                assert task != null : "Task should not be null in the task map";
                if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    numMatches++;
                    matchedTask += numMatches + ". " + task + "\n";
                }
            }
            if (numMatches == 0) {
                matchedTask = "Oh no! Boo could not find any tasks that contain that keyword :(\n";
            }
            return ui.printMessage(matchedTask);
        } catch (StringIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs a keyword to find tasks.\n");
        }
    }

    /**
     * Sorts tasks based on their dates.
     * Tasks without dates appear before the tasks with dates.
     * Tasks with dates are sorted in chronological order based on their start dates.
     * Task IDs are re-assigned based on the newly sorted order of the tasks.
     */
    private void sortTasks() {
        List<Map.Entry<Integer, Task>> sortedEntries = this.tasksMap.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    Task task1 = entry1.getValue();
                    Task task2 = entry2.getValue();

                    // If both tasks have no date, they stay in original order
                    if (task1.getStartDate() == null && task2.getStartDate() == null) {
                        return 0;
                    }

                    // If task1 has a date, but task2 has a date, task1 should appear first
                    if (task1.getStartDate() == null) {
                        return -1;
                    }
                    // If task2 has a date, but task1 has no date, task2 should appear after task1
                    if (task2.getStartDate() == null) {
                        return 1;
                    }
                    // Both tasks have dates, sort by start date
                    return task1.getStartDate().compareTo(task2.getStartDate());
                })
                .toList();

        // Reassign task IDs based on the new ordering of tasks
        this.tasksMap.clear();
        int newId = 1;
        for (Map.Entry<Integer, Task> entry : sortedEntries) {
            this.tasksMap.put(newId, entry.getValue());
            newId++;
        }
        // Update taskId
        this.taskId = newId;
    }

}
