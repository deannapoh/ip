package boo.misc;

import boo.task.Task;

public class UiStub extends Ui {

    @Override
    public void printRemovedTask(int taskId, Task task) {
        String msg = "____________________________________________________________\n"
                + "Noted! Boo has removed/added this task:\n "
                + "[T] [ ] Assignment" + "\n" + "Now you have " + 1 + " tasks in the list.\n"
                + "____________________________________________________________\n";
        System.out.println(msg);
    }

}
