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

    public TaskList() throws BooException {
        this.taskMap = new HashMap<Integer, Task>();
        this.storage = new Storage("./data/boo.Boo.txt");
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

    // Add boo.task to the list and print the added boo.task
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
                throw new BooException("Oh no! boo.Boo could not find boo.task with ID " + taskId + ".\nMaybe you mixed up the boo.task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your boo.task list\n");
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
            // total taskID - 1 since one boo.task was deleted
            this.taskId--;
            save();
            ui.printRemovedTask(this.taskId, task);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! boo.Boo needs you to specify a boo.task ID to delete it.\nPlease try again so that boo.Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! boo.Boo needs your boo.task.Task ID to be an integer!\n");
        }
    }

    // Mark boo.task as done and print out marked boo.task
    public void markAsDone(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! boo.Boo could not find boo.task with ID " + taskId + ".\nMaybe you mixed up the boo.task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your boo.task list\n");
            }
            task.markAsDone();
            save();
            ui.printMarkedTask(task);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! boo.Boo needs you to specify a boo.task ID to mark it as done.\nPlease try again so that boo.Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! boo.Boo needs your boo.task.Task ID to be an integer!\n");
        }

    }

    // mark boo.task as undone and print out unmarked boo.task
    public void markAsNotDone(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! boo.Boo could not find boo.task with ID " + taskId + ".\nMaybe you mixed up the boo.task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your boo.task list\n");
            }
            task.markAsNotDone();
            save();
            ui.printUnmarkedTask(task);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! boo.Boo needs you to specify a boo.task ID to mark it as done.\nPlease try again so that boo.Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! boo.Boo needs your boo.task.Task ID to be an integer!\n");
        }

    }
}
