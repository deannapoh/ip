package boo.task;

import boo.misc.BooException;
import boo.misc.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents an event task.
 * An event task has a description, a start date, an end date and a completion status.
 */
public class Event extends Task {
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    /**
     * Constructs an Event task.
     *
     * @param description Description of the Event task.
     * @param startTime Date and/or timing that the event begins.
     * @param endTime Date and/or timing that the event ends.
     * @throws BooException If user types a date that was not in the format: dd/MM/yyy or dd/MM/yyy HHmm, or
     * if the end of the event is earlier than the start of the event.
     */
    public Event(String description, String startTime, String endTime) throws BooException {
        super(description);
        assert description != null && !description.trim().isEmpty() : "Description for Event task should not be empty";

        this.startTime = Parser.parseDateTime(startTime);
        this.endTime = Parser.parseDateTime(endTime);

        if (this.endTime.isBefore(this.startTime)) {
            throw new BooException("Oops! The 'to' time cannot be before the 'from' time.\n"
                    + "Please enter the timing again!\n");
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
                + " (from: " + startTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"))
                + " to: " + endTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a")) + ")";
    }

    /**
     * Returns a string representation of the start time of the event.
     *
     *  @return A formatted string showing the start time of the event, in the format dd MMM yyyy h:mm a.
     */
    public String getFormattedStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
    }

    /**
     * Returns a string representation of the end time of the event.
     *
     *  @return A formatted string showing the end time of the event, in the format dd MMM yyyy h:mm a.
     */
    public String getFormattedEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
    }

    /**
     * Returns the start time of the event as a LocalDateTime.
     *
     *  @return The start time of the event as a LocalDateTime.
     */
    @Override
    public LocalDateTime getStartDate() {
        return startTime;
    }
}
