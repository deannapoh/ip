package boo.task;


import boo.misc.BooException;
import boo.misc.StorageStub;
import boo.misc.UiStub;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void deleteTaskTest() throws BooException {
        TaskList taskList = new TaskList(new StorageStub(), new UiStub());
        // Test with non-integer number
        Exception exception = assertThrows(BooException.class, () -> taskList.deleteTask("delete 1.05"));
        String expectedMessage = "____________________________________________________________\n"
                + "Oops! Boo needs your Task ID to be an integer!\n" +
                "____________________________________________________________\n";
        assertEquals(expectedMessage, exception.getMessage());

        // Test with no description
        Exception exception2 = assertThrows(BooException.class, () -> taskList.deleteTask("delete"));
        String expectedMessage2 = "____________________________________________________________\n"
                + "Oops! Boo needs you to specify a task ID to mark it as done.\nPlease try again so that Boo can help :)\n"
                + "____________________________________________________________\n";
        assertEquals(expectedMessage, exception.getMessage());

        // Test if a task is deleted
        int currentMapSize = taskList.getTaskMap().size();
        taskList.deleteTask("delete 1");
        String expectedMessage3 = "____________________________________________________________\n"
                + "Noted! Boo has removed this task:\n "
                + "[T] [ ] Assignment" + "\n" + "Now you have " + 1 + " tasks in the list.\n"
                + "____________________________________________________________\n";
        assertEquals(currentMapSize - 1, taskList.getTaskMap().size());

        // Test if method works with capitalised "Delete"
        int currentMapSize2 = taskList.getTaskMap().size();
        taskList.deleteTask("Delete 1");
        assertEquals(currentMapSize2 - 1, taskList.getTaskMap().size());
    }

}
