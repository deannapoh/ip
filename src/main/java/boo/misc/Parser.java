package boo.misc;

import boo.task.Deadline;
import boo.task.Event;
import boo.task.Task;
import boo.task.TaskList;
import boo.task.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a class that handles the parsing of user inputs.
 */
public class Parser {
    private final TaskList taskList;
    private final Ui ui;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Constructs a parser that parses user inputs.
     *
     * @param taskList List of tasks.
     * @param ui Interface in charge of interacting with the user.
     */
    public Parser(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Returns type of task that user inputs.
     * If user types an invalid input, an exception is thrown and an error message is shown.
     *
     * @param message String input by user.
     * @return Task object that user inputs.
     * @throws BooException If input by user is incomplete or if user types an invalid input.
     */
    public static Task parseTask(String message) throws BooException {
        Task task;
        assert message != null : "Message should not be null";
        assert !message.trim().isEmpty() : "Message should not be empty";

        if (message.toLowerCase().startsWith("todo")) {
            return parseTodoTask(message);
        } else if (message.toLowerCase().startsWith("deadline")) {
            return parseDeadlineTask(message);
        } else if (message.toLowerCase().startsWith("event")) {
            return parseEventTask(message);
        } else {
            throw new BooException("Oops, Boo does not understand what you mean :(\n"
                    + "Please use these keywords: \n"
                    + "1. list: list your task list\n"
                    + "2. mark: mark a specific task as done (please specify which taskID)\n"
                    + "3. unmark: unmark a specific task as done (please specify which taskID)\n"
                    + "4. delete: delete a specific task from the list (please specify which taskID)\n"
                    + "5. todo/event/deadline: add a todo/event/deadline task\n");
        }
    }

    /**
     * Parses a todo task input and returns a Todo object.
     *
     * @param message String input by the user.
     * @return Todo object with the description provided by the user.
     * @throws BooException If the input is incomplete or invalid.
     */
    private static Todo parseTodoTask(String message) throws BooException {
        try {
            String description = message.substring(5).trim();
            assert description != null : "Description should not be null";
            validateDescription(description, "todo");
            return new Todo(description);
        } catch (StringIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs to know what todo task to add to the list!\n"
                    + "Please add a description of the todo task so Boo can help you!\n");
        }
    }

    /**
     * Parses a deadline task input and returns a Deadline object.
     *
     * @param message String input by the user.
     * @return Deadline object with description and deadline date provided by the user.
     * @throws BooException If the input is incomplete or invalid.
     */
    private static Deadline parseDeadlineTask(String message) throws BooException {
        try {
            String[] details = message.substring(9).split("/by");
            String description = details[0].trim();
            assert description != null : "Description should not be null";
            validateDescription(description, "deadline");

            // Check if '/by' date is provided
            String deadlineDate = details[1].trim();
            assert deadlineDate != null : "Deadline date should not be null";
            validateDate(deadlineDate, "deadline");

            return new Deadline(description, deadlineDate);
        } catch (StringIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs to know what deadline task to add to the list!\n"
                    + "Please add a description of the deadline task so Boo can help you!\n");
        } catch (IndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs a '/by' time for the deadline task!\n"
                    + "Please provide a '/by' time, in the format of: /by (dd/MM/yyyy HHmm or dd/MM/yyyy)\n");
        }
    }

    /**
     * Parses an event task input and returns an Event task object.
     *
     * @param message String input by the user.
     * @return Event object with description, start time, and end time.
     * @throws BooException If the input is incomplete or invalid.
     */
    private static Event parseEventTask(String message) throws BooException {
        try {
            String[] details = message.substring(6).split("/from|/to");
            String description = details[0].trim();
            assert description != null : "Description should not be null";
            validateDescription(description, "event");

            String startTime = details[1].trim();
            assert startTime != null : "Start time should not be null";
            validateDate(startTime, "event");

            String endTime = details[2].trim();
            assert endTime != null : "End time should not be null";
            validateDate(endTime, "event");

            return new Event(description, startTime, endTime);
        } catch (StringIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs to know what event to add to the list!\n"
                    + "Please add a description of the event so Boo can help you!\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs both '/from' and '/to' times for the event task!\n"
                    + "Please provide both times, in the format of: /from (dd/MM/yyyy HHmm or dd/MM/yyyy ) "
                    + "/to (dd/MM/yyyy HHmm or dd/MM/yyyy )\n");
        }
    }

    /**
     * Validates that the description of a task is not empty.
     *
     * @param description The task description provided by the user.
     * @param taskType Type of task.
     * @throws BooException If the description is empty.
     */
    private static void validateDescription(String description, String taskType) throws BooException {
        if (description.isEmpty()) {
            throw new BooException("Oops! Boo needs to know what " + taskType + " task to add to the list!\n"
                    + "Please add a description of the " + taskType + " task so Boo can help you!\n");
        }
    }

    /**
     * Validates that the date provided for a task is not empty.
     *
     * @param date The date provided for the task.
     * @param taskType Type of task.
     * @throws BooException If the date is empty.
     */
    private static void validateDate(String date, String taskType) throws BooException {
        if (date.isEmpty()) {
            throw new BooException("Oops! Boo needs a '/by' time for the " + taskType + " task!\n"
                    + "Please provide a valid date in the format of: dd/MM/yyyy HHmm or dd/MM/yyyy\n");
        }
    }

    /**
     * Parses a date string into a LocalDateTime object.
     *
     * @param dateTime The date string to parse.
     * @return The parsed LocalDateTime object.
     * @throws BooException If the date format is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws BooException {
        try {
            return dateTime.contains(" ")
                    ? LocalDateTime.parse(dateTime, DATE_TIME_FORMAT)
                    : LocalDate.parse(dateTime, DATE_FORMAT).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new BooException("Oops! You have used the incorrect date format!\n"
                    + "Please try again with the format dd/MM/yyyy or dd/MM/yyyy HHmm!\n");
        }
    }
}
