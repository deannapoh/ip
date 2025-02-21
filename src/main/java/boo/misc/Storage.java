package boo.misc;

import boo.task.Deadline;
import boo.task.Event;
import boo.task.Task;
import boo.task.Todo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a class that is in charge of storing and loading the task history into a hard disk.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object that allows the task history to be stored in a hard disk.
     *
     * @param filePath Path to the file that the task history will be stored in.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves task history into a file.
     * If a file has not been created, it creates a file for the task history to be saved into.
     *
     * @param taskMap Hashmap that contains a list of the task IDs and tasks.
     * @throws BooException If tasks were not saved successfully.
     */
    public void saveTasksToFile(HashMap<Integer, Task> taskMap) throws BooException {
        try {
            File file = new File(filePath);

            // Assert that file path is valid
            assert filePath != null && !filePath.trim().isEmpty() : "Invalid file path";

            // Create directories if they do not exist
            file.getParentFile().mkdirs();

            // Write into the file
            FileWriter writer = new FileWriter(file);
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                int taskId = entry.getKey();
                Task task = entry.getValue();
                assert task != null : "Task should not be null";

                String taskString = "taskID: " + taskId + " || " + task.getClass().getSimpleName() + " task || ";
                taskString += "isDone: " + task.isDone() + " || ";

                // Handle the task description based on its type
                String description = task.getDescription();
                assert description != null && !description.trim().isEmpty() : "Task description should not be empty";
                if (task instanceof Deadline) {
                    Deadline deadlineTask = (Deadline) task;
                    taskString += description + " (by: " + deadlineTask.getFormattedDeadline() + ")";
                } else if (task instanceof Event) {
                    Event eventTask = (Event) task;
                    taskString += description + " (from: " + eventTask.getFormattedStartTime() + " to: " + eventTask.getFormattedEndTime() + ")";
                } else {
                    taskString += description;
                }

                // Write the task string to the file
                writer.write(taskString + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new BooException("Oops! Something went wrong! Boo couldn't save your tasks :(\n");
        }
    }

    /**
     * Returns tasks that were saved in the file prior.
     * If file does not exist or is empty, an empty hashmap is returned.
     *
     * @return Hashmap of all the tasks that were present in the file.
     * @throws BooException If there was a problem loading the tasks in the file to the hashmap.
     */
    public HashMap<Integer, Task> loadTasksFromFile() throws BooException {
        HashMap<Integer, Task> taskMap = new HashMap<>();
        File file = new File(filePath);

        // Assert that file path is valid
        assert filePath != null && !filePath.trim().isEmpty() : "Invalid file path";

        // If file does not exist or is empty, return an empty map
        if (!file.exists() || file.length() == 0) {
            return taskMap;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String taskLine;

            // Check if file is empty
            while ((taskLine = reader.readLine()) != null) {
                if (taskLine.trim().isEmpty()) {
                    continue; // skip empty lines
                }
                // Split line by "||"
                String[] taskDetails = taskLine.split("\\|\\|");
                assert taskDetails.length >= 4 : "Invalid task format in file";

                if (taskDetails.length < 4) {
                    throw new BooException("Oh no! Invalid task format in file.\n"
                            + "Expected 'taskID || taskType || isDone || description'.\n");
                }
                try {
                    // Extract taskID, type, isDone, and description
                    int taskId = Integer.parseInt(taskDetails[0].split(":")[1].trim());
                    String taskType = taskDetails[1].split("task")[0].trim();
                    boolean isDone = Boolean.parseBoolean(taskDetails[2].split(":")[1].trim());
                    String description = taskDetails[3].trim();
                    assert description != null && !description.trim().isEmpty() : "Invalid task description";

                    // Create the task based on the type
                    Task task = null;
                    if (taskType.equals("Todo")) {
                        task = new Todo(description);
                    } else if (taskType.equals("Deadline")) {

                        // Parsing Deadline: split by " (by: "
                        String[] details = description.split(" \\(by: ");
                        if (details.length < 2) {
                            throw new BooException("Oops! Deadline task is missing 'by' date.\n");
                        }
                        String taskDescription = details[0].trim();
                        String deadlineDate = details[1].replace(")", "").trim();

                        // Convert "dd MMM yyy h:mm a" to "dd/MMM/yyy hhmm"
                        LocalDateTime parsedDeadline = LocalDateTime.parse(
                                deadlineDate, DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
                        String formattedDeadline = parsedDeadline.format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));

                        // Create new Deadline task
                        task = new Deadline(taskDescription, formattedDeadline);
                    } else if (taskType.equals("Event")) {
                        // Parsing Event: split by " (from: " and " to: "
                        String[] details = description.split(" \\(from: ");
                        if (details.length < 2) {
                            throw new BooException("Oops! Event task is missing 'from' time.\n");
                        }
                        String taskDescription = details[0].trim();
                        String[] timeDetails = details[1].split(" to: ");
                        String startTime = timeDetails[0].trim();
                        String endTime = timeDetails[1].split("\\)")[0].trim();

                        // Convert "dd MMM yyy h:mm a" to "dd/MMM/yyy hhmm"
                        LocalDateTime parsedStartTime = LocalDateTime.parse(
                                startTime, DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
                        String formattedStartTime = parsedStartTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
                        LocalDateTime parsedEndTime = LocalDateTime.parse(
                                endTime, DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
                        String formattedEndTime = parsedEndTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));

                        // Create new Event task
                        task = new Event(taskDescription, formattedStartTime, formattedEndTime);
                    } else {
                        throw new BooException("Oh no! Boo could not identify the task type found in file.\n");
                    }
                    // Assert that task is not null after parsing it
                    assert task != null : "Task is null after parsing";
                    // Mark the task as done if necessary
                    if (isDone) {
                        task.setAsDone();
                    }
                    taskMap.put(taskId, task);
                } catch (Exception e) {
                    throw new BooException("Error! Boo could not parse task: " + taskLine + "\n");
                }
            }
        } catch (IOException e) {
            throw new BooException("Oops! Something went wrong! Boo couldn't load your tasks :(\n");
        }
        return taskMap;
    }

}



