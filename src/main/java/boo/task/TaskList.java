package boo.task;

import boo.misc.BooException;
import boo.misc.Ui;

import java.util.HashMap;
import boo.misc.Storage;

public class TaskList {
    private HashMap<Integer, Task> taskMap;
    private Ui ui;
    private int taskId;
    private Storage storage;

    public TaskList(Storage storage, Ui ui) throws BooException {
        this.taskMap = new HashMap<Integer, Task>();
        this.storage = storage;
        this.taskMap = storage.loadTasks();
        this.ui = ui;
        if (!taskMap.isEmpty()) {
            this.taskId = taskMap.size() + 1;
        } else {
            this.taskId = 1;
        }
    }

    /**
     * Saves the task list into a designated hard disk.
     */
    private void save() throws BooException {
        storage.saveTask(taskMap);
    }

    public HashMap<Integer, Task> getTaskMap() {
        return this.taskMap;
    }

    /**
     * Adds a specified task to the task list.
     * Prints and saves the added task.
     *
     * @param task Task that is to be added to the task list.
     */
    public void addTask(Task task) throws BooException{
        taskMap.put(taskId, task);
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
            throw new BooException("Oops! Boo needs your task ID to be an integer!\n");
        }

    }
}
