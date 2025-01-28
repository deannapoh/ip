import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Save task in file
    // Save task in file
    public void saveTask(HashMap<Integer, Task> taskMap) throws BooException {
        try {
            File file = new File(filePath);
            // Create directories if they do not exist
            file.getParentFile().mkdirs();
            // Write into the file
            FileWriter writer = new FileWriter(file);
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                int taskId = entry.getKey();
                Task task = entry.getValue();

                // Start building the task string
                String taskString = "taskID: " + taskId + " || " + task.getClass().getSimpleName() + " task || ";
                taskString += "isDone: " + task.isDone + " || "; // Assuming Task has isDone() method

                // Handle the task description based on its type
                String description = task.description;  // Assuming Task has a getDescription() method
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
            throw new BooException("Oops! Boo couldn't save your tasks :(");
        }
    }


    // Read tasks from the file
    // Returns HashMap of tasks in file
    public HashMap<Integer, Task> loadTasks() throws BooException {
        HashMap<Integer, Task> taskMap = new HashMap<>();
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            // If file does not exist or is empty, return an empty map
            return taskMap;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if file is empty
                if (line.trim().isEmpty()) {
                    // Skip empty lines
                    continue;
                }

                // Split line by "||"
                String[] parts = line.split("\\|\\|");

                if (parts.length < 4) {
                    throw new BooException("Invalid task format in file. Expected 'taskID || taskType || isDone || description'.");
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
                            throw new BooException("Deadline task is missing 'by' date.");
                        }
                        String taskDescription = details[0].trim();
                        String by = details[1].replace(")", "").trim();
                        task = new Deadline(taskDescription, by);
                    } else if (type.equals("Event")) {
                        // Parsing Event: split by " (from: " and " to: "
                        String[] details = description.split(" \\(from: ");
                        if (details.length < 2) {
                            throw new BooException("Event task is missing 'from' time.");
                        }
                        String taskDescription = details[0].trim();
                        String[] fromTo = details[1].split(" to: ");
                        String from = fromTo[0].trim();
                        String to = fromTo[1].split("\\)")[0].trim();
                        task = new Event(taskDescription, from, to);
                    } else {
                        throw new BooException("Unknown task type found in file.");
                    }

                    // Mark the task as done if necessary
                    if (isDone) {
                        task.markAsDone();
                    }

                    taskMap.put(taskId, task);

                } catch (Exception e) {
                    throw new BooException("Error parsing task: " + line);
                }
            }
        } catch (IOException e) {
            throw new BooException("Oops! Boo couldn't load your tasks :(");
        }

        return taskMap;
    }

}



