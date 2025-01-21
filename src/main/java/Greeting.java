public class Greeting {
    private String greetingMessage;
    private String goodbyeMessage;

    public Greeting() {
        this.greetingMessage = "____________________________________________________________\n"
                + "Hello! I'm Boo\n"
                + "What can I do for you?\n"
                + "____________________________________________________________\n";

        this.goodbyeMessage = "____________________________________________________________\n"
                + "Bye. Hope to see you again soon!\n"
                + "____________________________________________________________\n";
    }

    public void printGreeting() {
        System.out.println(this.greetingMessage);
    }

    public void printGoodbyeMessage() {
        System.out.println(this.goodbyeMessage);
    }
}
