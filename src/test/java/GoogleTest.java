import es.mcpworkshop.calendar.GoogleCalendar;

public class GoogleTest {

    public static void main(String[] args) {

        GoogleCalendar googleCalendar = new GoogleCalendar();
        try {
            googleCalendar.getGoogleEvents(5)
                    .forEach(event -> System.out.printf("%s%n",event.getSummary()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
