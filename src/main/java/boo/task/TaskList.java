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
        assert storage != null : "Storage must not be null!";
        assert ui != null : "Ui must not be null!";

        this.tasksMap = new HashMap<Integer, Task>();
        this.storage = storage;
        this.tasksMap = storage.loadTasksFromFile();
        this.ui = ui;
        if (!tasksMap.isEmpty()) {
            this.taskId = tasksMap.size() + 1;
        } else {
            this.taskId = 1;
        }
        assert taskId > 0 : "Task ID must always be positive";
    }

    /**
     * Saves the task list into a designated hard disk.
     */
    private void save() throws BooException {
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
        updateTasks();
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
        int taskId = parseTaskId(input);
        Task task = getTask(taskId);
        tasksMap.remove(taskId);
        shiftTasks(taskId);
        updateTasks();
        return ui.printRemovedTask(this.taskId, task);
    }

    private void shiftTasks(int deletedTaskId) {
        for (int i = deletedTaskId + 1; i < taskId; i++) {
            Task shiftedTask = tasksMap.get(i);
            if (shiftedTask != null) {
                tasksMap.put(i - 1, shiftedTask);
                tasksMap.remove(i);
            }
        }
        taskId--;
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
        int taskId = parseTaskId(input);
        Task task = tasksMap.get(taskId);
        if (task == null) {
            throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\n"
                    + "Maybe you mixed up the task IDS? Please try again!\nThere are currently "
                    + tasksMap.size() + " tasks in your task list\n");
        }
        task.setAsDone();
        updateTasks();
        return ui.printMarkedTask(task);
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
        int taskId = parseTaskId(input);
        Task task = tasksMap.get(taskId);
        if (task == null) {
            throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\n"
                    + "Maybe you mixed up the task IDS? Please try again!\nThere are currently "
                    + tasksMap.size() + " tasks in your task list\n");
        }
        task.setAsNotDone();
        updateTasks();
        return ui.printUnmarkedTask(task);
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
        String keyword = extractKeyword(input);
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
    }

    private String extractKeyword(String input) throws BooException {
        try {
            String keyword = input.substring(4).trim();
            if (keyword.isEmpty()) {
                throw new BooException("Oops! Boo needs a keyword to find tasks.\n");
            }
            return keyword;
        } catch (Exception e) {
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
        List<Map.Entry<Integer, Task>> sortedEntries = tasksMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(this::compareTasksByDate))
                .toList();
        reassignTaskIds(sortedEntries);
    }

    private int compareTasksByDate(Task task1, Task task2) {
        // If both tasks have no date, they stay in original order
        if (task1.getStartDate() == null && task2.getStartDate() == null) {
            return 0;
        }
        // If task1 has no date, but task2 has a date, task1 should appear first
        if (task1.getStartDate() == null) {
            return -1;
        }
        // If task2 has a date, but task1 has no date, task2 should appear after task1
        if (task2.getStartDate() == null) {
            return 1;
        }
        return task1.getStartDate().compareTo(task2.getStartDate());
    }

    private void reassignTaskIds(List<Map.Entry<Integer, Task>> sortedEntries) {
        tasksMap.clear();
        int newId = 1;
        for (Map.Entry<Integer, Task> entry : sortedEntries) {
            tasksMap.put(newId++, entry.getValue());
        }
        taskId = newId;
    }

    private Task getTask(int taskId) throws BooException {
        Task task = tasksMap.get(taskId);
        if (task == null) {
            throw new BooException("Task with ID " + taskId + " not found.");
        }
        return task;
    }

    private int parseTaskId(String input) throws BooException {
        try {
            return Integer.parseInt(input.split(" ")[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID.\n"
                    + "Please try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }
    }

    private void updateTasks() throws BooException {
        sortTasks();
        save();
    }

}
