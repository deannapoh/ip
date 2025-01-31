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
        this.tasksMap = storage.loadTasks();
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
        storage.saveTask(tasksMap);
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
    public void addTask(Task task) throws BooException {
        tasksMap.put(taskId, task);
        ui.printAddedTask(taskId, task);
        this.taskId++;
        save();
    }

    /**
     * Deletes a specified task from the task list.
     * Prints the deleted task.
     *
     * @param input Input provided by the user to pinpoint the task to be deleted.
     * @throws BooException If task ID could not be found or if task ID is not an integer.
     */
    public void deleteTask(String input) throws BooException {
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
            ui.printRemovedTask(this.taskId, task);
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
    public void markAsDone(String input) throws BooException {
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
            ui.printMarkedTask(task);
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
    public void markAsNotDone(String input) throws BooException {
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
            ui.printUnmarkedTask(task);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to mark it as done.\n"
                    + "Please try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your task ID to be an integer!\n");
        }
    }
}
