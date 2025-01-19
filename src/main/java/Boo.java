import java.util.Scanner;

public class Boo {
    public static void main(String[] args) {
        Greeting greeting = new Greeting();
        InputHistory inputHistory = new InputHistory();

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
                    inputHistory.printHistory();
                } else {
                    // Else, add message and print the added message
                    inputHistory.addMessage(input);
                }
            }

        }

        scanner.close();

    }


}
