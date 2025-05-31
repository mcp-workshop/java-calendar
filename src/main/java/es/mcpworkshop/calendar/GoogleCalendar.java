package es.mcpworkshop.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class GoogleCalendar {

  private static final Logger logger = LoggerFactory.getLogger(GoogleCalendar.class);

  /** Application name. */
  private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  /** Directory to store authorization tokens for this application. */
  private static final String TOKENS_DIRECTORY_PATH = "/tmp/tokens";

  /**
   * Global instance of the scopes required by this quickstart. If modifying these scopes, delete
   * your previously saved tokens/ folder.
   */
  private static final List<String> SCOPES =
      Collections.singletonList(CalendarScopes.CALENDAR_READONLY);

  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
    // Load client secrets.
    GoogleClientSecrets clientSecrets = null;
    try (InputStream in = GoogleCalendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH)) {
      if (in == null) {
        throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
      }
      clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    }

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver)
            .authorize("user");
    // returns an authorized Credential object.
    return credential;
  }

  @Tool(
      description =
          """
          Retrieves personal information, birthdays, meetings, and all personal related events
          available in the user Google Calendar, where he does store all these information.
          Be free to retrieve all the events you need in order to search for your information.
          If 1 is not enough, maybe try more values. But 100 is the maximum allowed value,
          so if no information is available in those 100 events, just reply to the user you can't find the information.
          """)
  public List<Event> getGoogleEvents(
      @ToolParam(description = "Maximum number of events to retrieve") Integer maxEvents)
      throws GeneralSecurityException, IOException {
    logger.info("Retrieving {} events from Google Calendar...", maxEvents);
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Calendar service =
        new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

    // List the next 10 events from the primary calendar.
    DateTime now = new DateTime(System.currentTimeMillis());
    Events events =
        service
            .events()
            .list("primary")
            .setMaxResults(maxEvents)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
    return events.getItems();
  }
}
