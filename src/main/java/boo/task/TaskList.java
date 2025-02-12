package boo.task;

import boo.misc.BooException;
import boo.misc.Ui;
import boo.misc.Storage;

import java.util.HashMap;

/**
 * Represents a list of tasks that can be manipulated.
 * Tasks can be of any type.
 */
public class TaskList {
    private HashMap<Integer, Task> tasksMap;
    private Ui ui;
    private int taskId;
    private Storage storage;

    /**
     * Constructs a list of tasks, in the form of a Hashmap.
     * Task ID starts at 1 and increases as tasks get added.
     *
     * @param storage Storage to store and load the task list in a hard disk.
     * @param ui Interface that interacts with the user.
     */
    public TaskList(Storage storage, Ui ui) throws BooException {
        this.tasksMap = new HashMap<Integer, Task>();
        this.storage = storage;
        this.tasksMap = storage.loadTasksFromFile();
        this.ui = ui;
        if (!tasksMap.isEmpty()) {
            this.taskId = tasksMap.size() + 1;
        } else {
            this.taskId = 1;
        }
    }

    /**
     * Saves the task list into a designated hard disk.
     */
    private void save() throws BooException {
        storage.saveTasksToFile(tasksMap);
    }

    public HashMap<Integer, Task> getTaskMap() {
        return this.tasksMap;
    }

    /**
     * Adds a specified task to the task list.
     * Prints and saves the added task.
     *
     * @param task Task that is to be added to the task list.
     */
    public String addTask(Task task) throws BooException {
        tasksMap.put(taskId, task);
        this.taskId++;
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
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = tasksMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\n"
                        + "Maybe you mixed up the task IDS? Please try again!\nThere are currently "
                        + tasksMap.size() + " tasks in your task list\n");
            }
            task.setAsDone();
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
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = tasksMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\n"
                        + "Maybe you mixed up the task IDS? Please try again!\nThere are currently "
                        + tasksMap.size() + " tasks in your task list\n");
            }
            task.setAsNotDone();
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
        try {
            String keyword = input.substring(4).trim();
            if (keyword.isEmpty()) {
                throw new BooException("Oops! Boo needs a keyword to find tasks.\n");
            }
            String matchedTask = "Here are the matching tasks in your list:\n";
            int numMatches = 0;
            for (int taskId : tasksMap.keySet()) {
                Task task = tasksMap.get(taskId);
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
}
