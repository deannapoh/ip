import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, String by) throws BooException {
        super(description);
        try {
            // Check if time is present in the input
            if (by.contains(" ")) {
                // Time is provided, parse it as LocalDateTime
                this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
            } else {
                // No time, parse it as LocalDate
                this.by = LocalDate.parse(by, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            }
        } catch (DateTimeParseException e) {
            throw new BooException("Oops! You have used the incorrect date format!\nPlease try again with the format dd/MM/yyyy or dd/MM/yyyy HHmm!\n");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a")) + ")";
    }

    public String getBy() {
        return by.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a"));
    }
}
