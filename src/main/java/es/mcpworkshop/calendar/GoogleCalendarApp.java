package es.mcpworkshop.calendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleCalendarApp implements CommandLineRunner {

  public static void main(String... args) {
    SpringApplication.run(GoogleCalendarApp.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: java -jar GoogleCalendarApp <num_max_events_to_retrieve>");
    } else {
      Integer maxEvents = Integer.parseInt(args[0]);
      GoogleCalendar calendar = new GoogleCalendar();
      var events = calendar.getGoogleEvents(maxEvents);
      calendar.printEvents(events);
    }
  }
}
