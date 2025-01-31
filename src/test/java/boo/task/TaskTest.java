package boo.task;

import boo.misc.BooException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    Task task = new Task("Homework");

    /**
     * Tests if the method markAsDone works.
     */
    @Test
    public void setAsDoneTest() {
        Task task = new Task("Homework");
        assertFalse(task.isDone(), "Task should not initially be marked as done");
        assertEquals("[ ] Homework", task.toString());
        task.setAsDone();
        assertTrue(task.isDone(), "Task should be marked as done");
        assertEquals("[X] Homework", task.toString());
    }

    /**
     * Tests if the method markAsNotDone works.
     */
    @Test
    public void setAsNotDoneTest() {
        Task task = new Task("Homework");
        task.setAsDone();
        assertTrue(task.isDone(), "Task should be marked as done");
        assertEquals("[X] Homework", task.toString());
        task.setAsNotDone();
        assertFalse(task.isDone(), "Task is no longer marked as done");
        assertEquals("[ ] Homework", task.toString());
    }
}
