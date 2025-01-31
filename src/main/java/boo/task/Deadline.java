package boo.task;

import boo.misc.BooException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task.
 * A deadline task has a description, an end date and a completion status.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a Deadline task.
     *
     * @param description Description of the Deadline task.
     * @param by Date and/or timing that the task is due, i.e. the deadline of the task.
     * @throws BooException If user types a date that was not in the format: dd/MM/yyy or dd/MM/yyy HHmm.
     */
    public Deadline(String description, String by) throws BooException {
        super(description);
        try {
            // Check if time is present in the input
            if (by.contains(" ")) {
                // Time is provided, parse it as LocalDateTime
                this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
            } else {
                // No time, parse it as LocalDate
                this.by = LocalDate.parse(by, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            }
        } catch (DateTimeParseException e) {
            throw new BooException("Oops! You have used the incorrect date format!\n"
                    + "Please try again with the format dd/MM/yyyy or dd/MM/yyyy HHmm!\n");
        }
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string showing the task type, the task's completion status, and task description.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + by.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a")) + ")";
    }

    public String getBy() {
        return by.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
    }
}
