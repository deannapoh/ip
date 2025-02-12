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

            // Create directories if they do not exist
            file.getParentFile().mkdirs();

            // Write into the file
            FileWriter writer = new FileWriter(file);
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                int taskId = entry.getKey();
                Task task = entry.getValue();
                String taskString = "taskID: " + taskId + " || " + task.getClass().getSimpleName() + " task || ";
                taskString += "isDone: " + task.isDone() + " || ";

                // Handle the task description based on its type
                String description = task.getDescription();
                if (task instanceof Deadline) {
                    Deadline deadlineTask = (Deadline) task;
                    taskString += description + " (by: " + deadlineTask.getBy() + ")";
                } else if (task instanceof Event) {
                    Event eventTask = (Event) task;
                    taskString += description + " (from: " + eventTask.getFrom() + " to: " + eventTask.getTo() + ")";
                } else {
                    taskString += description;
                }

                // Write the task string to the file
                writer.write(taskString + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new BooException("Oops! Boo couldn't save your tasks :(\n");
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

        // If file does not exist or is empty, return an empty map
        if (!file.exists() || file.length() == 0) {
            return taskMap;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Check if file is empty
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }
                // Split line by "||"
                String[] parts = line.split("\\|\\|");
                if (parts.length < 4) {
                    throw new BooException("Invalid task format in file.\n"
                            + "Expected 'taskID || taskType || isDone || description'.");
                }
                try {
                    // Extract taskID, type, isDone, and description
                    int taskId = Integer.parseInt(parts[0].split(":")[1].trim());
                    String type = parts[1].split("task")[0].trim();
                    boolean isDone = Boolean.parseBoolean(parts[2].split(":")[1].trim());
                    String description = parts[3].trim();

                    // Create the task based on the type
                    Task task = null;
                    if (type.equals("Todo")) {
                        task = new Todo(description);
                    } else if (type.equals("Deadline")) {

                        // Parsing Deadline: split by " (by: "
                        String[] details = description.split(" \\(by: ");
                        if (details.length < 2) {
                            throw new BooException("Oops! Deadline task is missing 'by' date.");
                        }
                        String taskDescription = details[0].trim();
                        String by = details[1].replace(")", "").trim();

                        // Convert "dd MMM yyy h:mm a" to "dd/MMM/yyy hhmm"
                        LocalDateTime parsedBy = LocalDateTime.parse(
                                by, DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
                        String newBy = parsedBy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));

                        // Create new Deadline task
                        task = new Deadline(taskDescription, newBy);
                    } else if (type.equals("Event")) {
                        // Parsing Event: split by " (from: " and " to: "
                        String[] details = description.split(" \\(from: ");
                        if (details.length < 2) {
                            throw new BooException("Oops! Event task is missing 'from' time.");
                        }
                        String taskDescription = details[0].trim();
                        String[] fromTo = details[1].split(" to: ");
                        String from = fromTo[0].trim();
                        String to = fromTo[1].split("\\)")[0].trim();

                        // Convert "dd MMM yyy h:mm a" to "dd/MMM/yyy hhmm"
                        LocalDateTime parsedFrom = LocalDateTime.parse(
                                from, DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
                        String newFrom = parsedFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
                        LocalDateTime parsedTo = LocalDateTime.parse(
                                from, DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
                        String newTo = parsedTo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));

                        // Create new Event task
                        task = new Event(taskDescription, newFrom, newTo);
                    } else {
                        throw new BooException("Oh no! Boo could not identify the task type found in file.");
                    }
                    // Mark the task as done if necessary
                    if (isDone) {
                        task.setAsDone();
                    }
                    taskMap.put(taskId, task);
                } catch (Exception e) {
                    throw new BooException("Error! Boo could not parse task: " + line);
                }
            }
        } catch (IOException e) {
            throw new BooException("Oops! Boo couldn't load your tasks :(");
        }
        return taskMap;
    }

}



