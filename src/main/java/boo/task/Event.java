package boo.task;

import boo.misc.BooException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task.
 * An event task has a description, a start date, an end date and a completion status.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an Event task.
     *
     * @param description Description of the Event task.
     * @param from Date and/or timing that the event begins.
     * @param to Date and/or timing that the event ends.
     * @throws BooException If user types a date that was not in the format: dd/MM/yyy or dd/MM/yyy HHmm, or
     * if the end of the event is earlier than the start of the event.
     */
    public Event(String description, String from, String to) throws BooException {
        super(description);
        try {
            // Check if time is present in the input
            if (from.contains(" ")) {
                // Time is provided, parse it as LocalDateTime
                this.from = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
            } else {
                // No time, parse it as LocalDate
                this.from = LocalDate.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            }
            // Check if time is present in the input
            if (to.contains(" ")) {
                // Time is provided, parse it as LocalDateTime
                this.to = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
            } else {
                // No time, parse it as LocalDate
                this.to = LocalDate.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            }
            // Ensure that 'to' is not before 'from'
            if (this.to.isBefore(this.from)) {
                throw new BooException("Oops! The 'to' time cannot be before the 'from' time.\n"
                        + "Please enter the timing again!\n");
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
        return "[E]" + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a")) + ")";
    }

    public String getFrom() {
        return from.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
    }

    public String getTo() {
        return to.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
    }
}
