package boo.task;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string showing the task's status and type.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
