package boo.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task object.
     *
     * @param description Description of the task.
     * Task is initially marked as not done.
     */
    public Task(String description) {
        // Assert that the description is not null or empty
        assert description != null && !description.trim().isEmpty() :
                "Description for Deadline task should not be empty";
        this.description = description;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns icon that represents if task is marked.
     *
     * @return String that represents if task is marked.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    public void setAsDone() {
        this.isDone = true;
    }

    public void setAsNotDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string showing the task's completion status and description.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
