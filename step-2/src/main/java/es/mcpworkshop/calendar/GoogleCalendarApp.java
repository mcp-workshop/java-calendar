package es.mcpworkshop.calendar;

import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GoogleCalendarApp {

  public static void main(String... args) {
    SpringApplication.run(GoogleCalendarApp.class, args);
  }

  @Bean
  MethodToolCallbackProvider methodToolCallbackProvider(
      GoogleCalendarService googleCalendarService) {
    return MethodToolCallbackProvider.builder().toolObjects(googleCalendarService).build();
  }
}
