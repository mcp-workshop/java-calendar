# Google Calendar API Java Application

A simple Java application that connects to Google Calendar API to list upcoming events from your primary calendar.

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Google Cloud Console account with Calendar API enabled

## Setup Instructions

### 1. Enable Google Calendar API

1. Go to the [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the Google Calendar API:
   - Navigate to "APIs & Services" > "Library"
   - Search for "Google Calendar API"
   - Click on it and press "Enable"

### 2. Create OAuth 2.0 Credentials

1. In Google Cloud Console, go to "APIs & Services" > "Credentials"
2. Click "Create Credentials" > "OAuth client ID"
3. If prompted, configure the OAuth consent screen first:
   - Choose "External" user type
   - Fill in the required fields (App name, User support email, Developer contact)
   - Add your email to test users
4. For OAuth client ID:
   - Choose "Desktop application" as the application type
   - Give it a name (e.g., "Google Calendar Java App")
5. Download the credentials file and rename it to `credentials.json`

### 3. Setup Project

1. Clone or download this project
2. Place the `credentials.json` file in `src/main/resources/` directory
3. Install dependencies:
   ```bash
   mvn clean install
   ```

### 4. Run the Application

```bash
mvn exec:java -Dexec.mainClass="es.mcpworkshop.calendar.GoogleCalendar"
```

Or compile and run:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="es.mcpworkshop.calendar.GoogleCalendar"
```

## First Run Authorization

On the first run:
1. The application will open your default web browser
2. Sign in to your Google account
3. Grant permission to access your calendar
4. The authorization will be saved in the `tokens/` directory for future runs

## What the Application Does

- Connects to your Google Calendar using OAuth 2.0
- Retrieves the next 10 upcoming events from your primary calendar
- Displays event titles and start dates in the console

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── es/josetesan/mcp/
│   │       └── GoogleCalendar.java
│   └── resources/
│       └── credentials.json (you need to add this)
└── test/
tokens/ (created automatically after first authorization)
```

## Troubleshooting

- **FileNotFoundException for credentials.json**: Ensure the file is placed in `src/main/resources/`
- **Port 8888 already in use**: The OAuth flow uses port 8888. Make sure it's available or modify the port in the code
- **Access denied**: Make sure you've enabled the Calendar API and your OAuth consent screen is properly configured

## Permissions

This application requests read-only access to your Google Calendar (`CalendarScopes.CALENDAR_READONLY`). It cannot modify or delete your calendar events.