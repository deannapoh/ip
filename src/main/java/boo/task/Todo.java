package boo.task;

import boo.misc.BooException;

/**
 * Represents a Todo task.
 * A todo class has a description and a completion status.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task.
     *
     * @param description Description of the Todo task.
     */
    public Todo(String description) {
        super(description);
        assert description != null && !description.trim().isEmpty() : "Description for Todo task should not be empty";
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string showing the task type, the task's completion status, and task description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
