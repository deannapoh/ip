import java.util.Scanner;

public class Boo {
    public static void main(String[] args) throws BooException {
        Greeting greeting = new Greeting();
        TaskList taskList = new TaskList();

        // Print greeting message
        greeting.printGreeting();

        // Create scanner to read user inputs
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            // Read user input
            String input = scanner.nextLine();

            try {
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
                        taskList.markAsDone(input);
                    } else if (input.toLowerCase().startsWith("unmark")) {
                        taskList.markAsNotDone(input);
                    } else {
                        // Else, add message and print the added message
                        taskList.addTask(input);
                    }
                }

            } catch (BooException e) {
                System.out.println(e.getMessage());
            }



        }

        scanner.close();

    }


}
