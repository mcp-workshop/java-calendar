package es.mcpworkshop.calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleCalendarApp {

  public static void main(String... args) throws IOException, GeneralSecurityException {

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
