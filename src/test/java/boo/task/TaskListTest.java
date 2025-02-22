package boo.task;


import boo.misc.BooException;
import boo.misc.StorageStub;
import boo.misc.UiStub;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Used to test methods in the TaskList class.
 */
public class TaskListTest {

    /**
     * Tests if the method deleteTask works
     *
     * @throws BooException If task ID is not an integer or if task ID cannot be found.
     */
    @Test
    public void deleteTaskTest() throws BooException {
        TaskList taskList = new TaskList(new StorageStub(), new UiStub());

        // Test with non-integer number
        Exception exception = assertThrows(BooException.class, () -> taskList.deleteTask("delete 1.05"));
        String expectedMessage = "Oops! Boo needs your Task ID to be an integer!\n";

        assertEquals(expectedMessage, exception.getMessage());

        // Test with no description
        Exception exception2 = assertThrows(BooException.class, () -> taskList.deleteTask("delete"));
        String expectedMessage2 = "Oops! Boo needs you to specify a task ID to mark it as done.\n"
                + "Please try again so that Boo can help :)\n";

        assertEquals(expectedMessage, exception.getMessage());

        // Test if a task is deleted
        int currentMapSize = taskList.getTaskMap().size();
        taskList.deleteTask("delete 1");
        String expectedMessage3 = "Got it! Boo has removed this task:\n "
                + "[T] [ ] Assignment" + "\n" + "\nYAY!!! You are now only left with " + 1 + " tasks!\n"
                + "Keep up the good work!\n";

        assertEquals(currentMapSize - 1, taskList.getTaskMap().size());

        // Test if method works with capitalised "Delete"
        int currentMapSize2 = taskList.getTaskMap().size();
        taskList.deleteTask("Delete 1");
        assertEquals(currentMapSize2 - 1, taskList.getTaskMap().size());
    }

}
