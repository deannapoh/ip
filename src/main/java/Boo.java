import java.util.Scanner;

public class Boo {
    public static void main(String[] args) {
        Greeting greeting = new Greeting();
        TaskList taskList = new TaskList();

        // Print greeting message
        greeting.printGreeting();

        // Create scanner to read user inputs
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Read user input
            String input = scanner.nextLine();

            // Exit and show goodbye message if user types "bye"
            if (input.equalsIgnoreCase("bye")) {
                // Print goodbye message
                greeting.printGoodbyeMessage();
                break;
            } else {
                // Print list if user types "list"
                if (input.equalsIgnoreCase("list")) {
                    taskList.printHistory();
                } else if (input.toLowerCase().startsWith("mark")) {
                    int taskId = Integer.parseInt(input.split(" ")[1]);
                    taskList.markAsDone(taskId);
                } else if (input.toLowerCase().startsWith("unmark")) {
                    int taskId = Integer.parseInt(input.split(" ")[1]);
                    taskList.markAsNotDone(taskId);
                } else {
                    // Else, add message and print the added message
                    taskList.addTask(input);
                }
            }

        }

        scanner.close();

    }


}
