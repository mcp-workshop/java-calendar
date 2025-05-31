package es.mcpworkshop.calendar;

import com.google.api.services.calendar.model.Event;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar")
public class GoogleCalendarController {

  GoogleCalendarService googleCalendarService;

  public GoogleCalendarController(GoogleCalendarService googleCalendarService) {
    this.googleCalendarService = googleCalendarService;
  }

  @GetMapping()
  List<Event> getGoogleEvents(@RequestParam Integer maxEvents)
      throws GeneralSecurityException, IOException {
    if (maxEvents == null || maxEvents < 1 || maxEvents > 10) {
      maxEvents = 5;
    }
    return googleCalendarService.getGoogleEvents(maxEvents);
  }
}
