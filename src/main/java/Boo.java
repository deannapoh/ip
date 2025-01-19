import java.util.Scanner;

public class Boo {
    public static void main(String[] args) {
        String greeting = "____________________________________________________________\n"
                + "Hello! I'm Boo \n"
                + "What can I do for you? \n"
                + "____________________________________________________________ \n";
        System.out.println(greeting);

        // Create scanner to read user inputs
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Read user input
            String input = scanner.nextLine();

            // Exit and show goodbye message if user types "bye"
            if (input.equalsIgnoreCase("bye")) {
                String goodbyeMessage = "____________________________________________________________ \n"
                        + "Bye. Hope to see you again soon! \n"
                        + "____________________________________________________________ \n";

                System.out.println(goodbyeMessage);
                break;
            }

            // Else, repeat whatever user types
            String echo = "____________________________________________________________ \n"
                    + input + "\n"
                    + "____________________________________________________________ \n";
            System.out.println(echo);
        }

        scanner.close();

    }


}
