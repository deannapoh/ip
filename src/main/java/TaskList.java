import java.util.HashMap;
import java.util.Map;

public class TaskList {
    private HashMap<Integer, Task> taskMap;
    private int taskId;
    private Storage storage;

    public TaskList() throws BooException {
        this.taskMap = new HashMap<Integer, Task>();
        this.storage = new Storage("./data/Boo.txt");
        this.taskMap = storage.loadTasks();
        if (!taskMap.isEmpty()) {
            this.taskId = taskMap.size() + 1; // Set taskId based on loaded tasks
        } else {
            this.taskId = 1;
        }
    }

    private void save() throws BooException {
        storage.saveTask(taskMap);
    }

    // Add task to the list and print the added task
    public void addTask(String message) throws BooException{
        Task task;

        // Create new todo task
        if (message.toLowerCase().startsWith("todo")) {
            try {
                String description = message.substring(5).trim();
                if (description.isEmpty()) {
                    throw new BooException("Oops! Boo needs to know what todo task to add to the list!\nPlease add a description of the todo task so Boo can help you!\n");
                }
                task = new Todo(description);

            } catch (StringIndexOutOfBoundsException e) {
                throw new BooException("Oops! Boo needs to know what todo task to add to the list!\nPlease add a description of the todo so Boo can help you!\n");
            }

            // Create new deadline task
        } else if (message.toLowerCase().startsWith("deadline")) {
            try {
                String[] details = message.substring(9).split("/by");
                String description = details[0].trim();

                // Check if there is a description
                if (description.isEmpty()) {
                    throw new BooException("Oops! Boo needs to know what deadline task to add to the list!\nPlease add a description of the deadline task so Boo can help you!\n");
                }

                // Check if '/by' date is provided
                String by = details[1].trim();
                if (by.isEmpty()) {
                    throw new BooException("Oops! Boo needs a '/by' time for the deadline task!\nPlease provide a '/by' time, in the format of: /by (dd/MM/yyyy HHmm or dd/MM/yyyy)\n");
                }

                task = new Deadline(description, by);
            } catch (StringIndexOutOfBoundsException e) {
                throw new BooException("Oops! Boo needs to know what deadline task to add to the list!\nPlease add a description of the deadline task so Boo can help you!\n");
            } catch (IndexOutOfBoundsException e) {
                throw new BooException("Oops! Boo needs a '/by' time for the deadline task!\nPlease provide a '/by' time, in the format of: /by (dd/MM/yyyy HHmm or dd/MM/yyyy)\n");
            }

            // Create new event task
        } else if (message.toLowerCase().startsWith("event")) {
            try {
                String[] details = message.substring(6).split("/from|/to");
                String description = details[0].trim();
                // Check if there is a description
                if (description.isEmpty()) {
                    throw new BooException("Oops! Boo needs to know what event to add to the list!\nPlease add a description of the event so Boo can help you!\n");
                }

                // Check if '/from' date is provided
                String from = details[1].trim();
                if (from.isEmpty()) {
                    throw new BooException("Oops! Boo needs a '/from' time for the event task!\nPlease provide a '/from' time, in the format of: /from (dd/MM/yyyy HHmm or dd/MM/yyyy )\n");
                }

                String to = details[2].trim();
                if (to.isEmpty()) {
                    throw new BooException("Oops! Boo needs a '/to' time for the event task!\nPlease provide a '/to' time, in the format of: /to (dd/MM/yyyy HHmm or dd/MM/yyyy )\n");
                }

                if (description.isEmpty()) {
                    throw new BooException("Oops! Boo needs to know what event to add to the list!\nPlease add a description of the event so Boo can help you!\n");
                }
                if (from.isEmpty()) {
                    throw new BooException("Oops! Boo needs a '/from' time for the event task!\nPlease provide a '/from' time, in the format of: /from (dd/MM/yyyy HHmm or dd/MM/yyyy )\n");
                }
                if (to.isEmpty()) {
                    throw new BooException("Oops! Boo needs a '/to' time for the event task!\nPlease provide a '/to' time, in the format of: /to (dd/MM/yyyy HHmm or dd/MM/yyyy )\n");
                }

                task = new Event(description, from, to);
            } catch (StringIndexOutOfBoundsException e) {
                throw new BooException("Oops! Boo needs to know what event to add to the list!\nPlease add a description of the event so Boo can help you!\n");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new BooException("Oops! Boo needs both '/from' and '/to' times for the event task!\nPlease provide both times, in the format of: /from (dd/MM/yyyy HHmm or dd/MM/yyyy ) /to (dd/MM/yyyy HHmm or dd/MM/yyyy )\n");
            }

            // Else, throw exception
        } else {
            throw new BooException("Oops, Boo does not understand what you mean :(\n"
                                            + "Please use these keywords: \n"
                                            + "1. list: list our your task list\n"
                                            + "2. mark: mark a specific task as done (please specify which taskID)\n"
                                            + "3. unmark: unmark a specific task as done (please specify which taskID)\n"
                                            + "4. delete: delete a specific task from the list (please specify which taskID)\n"
                                            + "5. todo/event/deadline: add a todo/event/deadline task\n");
        }

        taskMap.put(taskId, task);
        String addedTask = "____________________________________________________________\n"
                + "Got it. Boo has added this task:\n" + "  " + task.toString() + "\n"
                + "Now you have " + this.taskId + " tasks in the list.\n"
                + "____________________________________________________________\n";
        System.out.println(addedTask);
        this.taskId++;
        save();
    }

    // Display the list of tasks
    public void printHistory() {
        if (this.taskMap.isEmpty()) {
            System.out.println("Yay! You currently have no tasks :) \n");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println("Here are the tasks in your list:");
            for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
                int taskId = entry.getKey();
                Task task = entry.getValue();
                System.out.println(taskId + ". " + task.toString());
            }
            System.out.println("____________________________________________________________\n");
        }
    }

    public void deleteTask(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\nMaybe you mixed up the task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your task list\n");
            }
            this.taskMap.remove(taskId);

            //shift the remaining tasks
            for (int i = taskId + 1; i <= this.taskId - 1; i++) {
                Task shiftedTask = taskMap.get(i);
                if (shiftedTask != null) {
                    taskMap.put(i - 1, shiftedTask);
                    taskMap.remove(i);
                }
            }
            // total taskID - 1 since one task was deleted
            this.taskId--;
            save();
            System.out.println("____________________________________________________________");
            System.out.println("Noted! Boo has removed this task:\n "
                    + task.toString() + "\n" + "Now you have " + (this.taskId-1) + " tasks in the list.\n"
                    +"____________________________________________________________\n");

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to delete it.\nPlease try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }
    }

    // Mark task as done and print out marked task
    public void markAsDone(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\nMaybe you mixed up the task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your task list\n");
            }
            task.markAsDone();
            save();
            System.out.println("____________________________________________________________");
            System.out.println("Nice! Boo has marked this task as done:\n "
                    + task.toString() + "\n"
                    + "____________________________________________________________\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to mark it as done.\nPlease try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }

    }

    // mark task as undone and print out unmarked task
    public void markAsNotDone(String input) throws BooException {
        try {
            int taskId = Integer.parseInt(input.split(" ")[1]);
            Task task = taskMap.get(taskId);
            if (task == null) {
                throw new BooException("Oh no! Boo could not find task with ID " + taskId + ".\nMaybe you mixed up the task IDS? Please try again!\nThere are currently " + taskMap.size() + " tasks in your task list\n");
            }
            task.markAsNotDone();
            save();
            System.out.println("____________________________________________________________");
            System.out.println("OK, Boo has marked this task as not done yet:\n "
                    + task.toString() + "\n"
                    + "____________________________________________________________\n");

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new BooException("Oops! Boo needs you to specify a task ID to mark it as done.\nPlease try again so that Boo can help :)\n");
        } catch (NumberFormatException e) {
            throw new BooException("Oops! Boo needs your Task ID to be an integer!\n");
        }

    }
}
