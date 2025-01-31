package boo.misc;

import boo.task.Task;

/**
 * Represents a simple implementation of the Ui class that is used for unit testing.
 */
public class UiStub extends Ui {

    /**
     * Mock printRemovedTask method.
     * Same message is always printed.
     *
     * @param taskId ID of the task that was removed.
     * @param task Task that was removed.
     */
    @Override
    public void printRemovedTask(int taskId, Task task) {
        String msg = "____________________________________________________________\n"
                + "Noted! Boo has removed/added this task:\n "
                + "[T] [ ] Assignment" + "\n" + "Now you have " + 1 + " tasks in the list.\n"
                + "____________________________________________________________\n";
        System.out.println(msg);
    }

}
