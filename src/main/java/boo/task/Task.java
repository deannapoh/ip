package boo.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;

    }

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

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
