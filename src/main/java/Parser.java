public class Parser {
    private TaskList taskList;
    private Ui ui;

    public Parser(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    public boolean parseCommand(String input) throws BooException {
        // Exit if user types "bye"
        if (input.equalsIgnoreCase("bye")) {
            ui.printGoodbyeMessage();
            return true;
        }

        if (input.equalsIgnoreCase("list")) {
            ui.printTaskHistory(taskList.getTaskMap());
        } else if (input.toLowerCase().startsWith("mark")) {
            taskList.markAsDone(input);
        } else if (input.toLowerCase().startsWith("unmark")) {
            taskList.markAsNotDone(input);
        } else if (input.toLowerCase().startsWith("delete")) {
            taskList.deleteTask(input);
        } else {
            taskList.addTask(parseTask(input));
        }
        return false;
    }

    private Task parseTask(String message) throws BooException {
        Task task;

        // Create new todo task
        if (message.toLowerCase().startsWith("todo")) {
            try {
                String description = message.substring(5).trim();
                if (description.isEmpty()) {
                    throw new BooException("Oops! Boo needs to know what todo task to add to the list!\nPlease add a description of the todo task so Boo can help you!\n");
                }
                return new Todo(description);
            } catch (StringIndexOutOfBoundsException e) {
                throw new BooException("Oops! Boo needs to know what todo task to add to the list!\nPlease add a description of the todo task so Boo can help you!\n");
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

                // Check if '/to' date is provided
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
        } else {
            throw new BooException("Oops, Boo does not understand what you mean :(\n"
                    + "Please use these keywords: \n"
                    + "1. list: list your task list\n"
                    + "2. mark: mark a specific task as done (please specify which taskID)\n"
                    + "3. unmark: unmark a specific task as done (please specify which taskID)\n"
                    + "4. delete: delete a specific task from the list (please specify which taskID)\n"
                    + "5. todo/event/deadline: add a todo/event/deadline task\n");
        }

        return task;
    }
}
