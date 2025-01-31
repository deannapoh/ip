package boo.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
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

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
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
     * @return A formatted string showing the task's status.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
