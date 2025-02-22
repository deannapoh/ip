package boo.task;

import boo.misc.BooException;
import boo.misc.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents a Deadline task.
 * A deadline task has a description, an end date and a completion status.
 */
public class Deadline extends Task {
    protected LocalDateTime deadlineDate;

    /**
     * Constructs a Deadline task.
     *
     * @param description Description of the Deadline task.
     * @param deadlineDate Date and/or timing that the task is due, i.e. the deadline of the task.
     * @throws BooException If user types a date that was not in the format: dd/MM/yyy or dd/MM/yyy HHmm.
     */
    public Deadline(String description, String deadlineDate) throws BooException {
        super(description);
        assert description != null && !description.trim().isEmpty() :
                "Description for Deadline task should not be empty";
        this.deadlineDate = Parser.parseDateTime(deadlineDate);
        assert this.deadlineDate != null : "Deadline date is not properly parsed";
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string showing the task type, the task's completion status, and task description.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + deadlineDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a")) + ")";
    }

    /**
     * Returns a string representation of the deadline.
     *
     * @return A formatted string showing the deadline, in the format dd MMM yyyy h:mm a.
     */
    public String getFormattedDeadline() {
        return deadlineDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
    }

    /**
     * Returns the deadline as a LocalDateTime.
     *
     * @return The deadline of the task as a LocalDateTime.
     */
    @Override
    public LocalDateTime getStartDate() {
        return deadlineDate;
    }
}
