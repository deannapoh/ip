import java.util.ArrayList;

public class InputHistory {
    private ArrayList<String> inputHistory;

    public InputHistory() {
        this.inputHistory = new ArrayList<String>();
    }

    // Add message to the list and print the added message
    public void addMessage(String message) {
        this.inputHistory.add(message);
        String addedMessage = "____________________________________________________________ \n"
                + "added: " + message + "\n"
                + "____________________________________________________________ \n";
        System.out.println(addedMessage);
    }

    // Display the list of messages
    public void printHistory() {
        if (this.inputHistory.isEmpty()) {
            System.out.println("List is empty");
        } else {
            System.out.println("____________________________________________________________");
            for (int i = 0; i < this.inputHistory.size(); i++) {
                String list = (i+1) + ". " + this.inputHistory.get(i);
                System.out.println(list);
            }
            System.out.println("____________________________________________________________ \n");
        }
    }
}
