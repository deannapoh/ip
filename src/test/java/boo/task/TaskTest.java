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
    public void markAsDoneTest() {
        Task task = new Task("Homework");
        assertFalse(task.isDone(), "Task should not initially be marked as done");
        assertEquals("[ ] Homework", task.toString());
        task.markAsDone();
        assertTrue(task.isDone(), "Task should be marked as done");
        assertEquals("[X] Homework", task.toString());
    }

    /**
     * Tests if the method markAsNotDone works.
     */
    @Test
    public void markAsNotDoneTest() {
        Task task = new Task("Homework");
        task.markAsDone();
        assertTrue(task.isDone(), "Task should be marked as done");
        assertEquals("[X] Homework", task.toString());
        task.markAsNotDone();
        assertFalse(task.isDone(), "Task is no longer marked as done");
        assertEquals("[ ] Homework", task.toString());
    }
}
