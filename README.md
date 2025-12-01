# F1 Qualification System

A Spring Boot REST API application for tracking and managing Formula 1 racing driver lap times across various racing tracks.

## 📋 Project Scope

This application records track times set by racing drivers on racing tracks, enabling:
- **Driver Management**: Track F1 drivers with their numbers, names, and teams
- **Track Management**: Store racing circuit information including location and specifications
- **Lap Time Recording**: Record and query lap times achieved by drivers on specific tracks
- **Cascade Operations**: Automatically handle related data when deleting drivers or tracks

## 🏗️ Architecture

The project follows a **layered architecture** with proper separation of concerns:

```
┌─────────────────┐
│   Controllers   │  REST endpoints
├─────────────────┤
│    Services     │  Business logic
├─────────────────┤
│  Repositories   │  Data access
├─────────────────┤
│   H2 Database   │  In-memory storage
└─────────────────┘
```

## 🚀 Technologies

- **Java 21** (LTS)
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Lombok**
- **Jackson** (JSON processing)
- **Maven**

## 📦 Domain Models

### RacingDriver
- **ID**: `number` (Integer) - Driver's racing number
- `name` - Driver's full name
- `team` - Current team

### RacingTrack
- **ID**: `city` (String) - Location of the track
- `name` - Official track name
- `country` - Country where track is located
- `lengthInKm` - Track length in kilometers
- `elevationInMetersMeasuredFromSeaLevel` - Altitude above sea level

### TrackTime
- **ID**: `UUID` (auto-generated)
- `time` - Lap time in custom format (M:SS.mmm)
- `recordedAt` - Timestamp when recorded
- `driver` - Reference to RacingDriver
- `track` - Reference to RacingTrack

## 🔌 API Endpoints

### Racing Drivers
- `GET /api/racing-drivers` - Get all drivers
- `GET /api/racing-drivers/{number}` - Get driver by number
- `GET /api/racing-drivers?name={name}&team={team}` - Search drivers
- `POST /api/racing-drivers` - Create new driver
- `PUT /api/racing-drivers/{number}` - Update driver
- `DELETE /api/racing-drivers/{number}` - Delete driver (cascades to track times)

### Racing Tracks
- `GET /api/racing-tracks` - Get all tracks
- `GET /api/racing-tracks/{city}` - Get track by city
- `GET /api/racing-tracks?country={country}&name={name}` - Search tracks
- `POST /api/racing-tracks` - Create new track
- `PUT /api/racing-tracks/{city}` - Update track
- `DELETE /api/racing-tracks/{city}` - Delete track (cascades to track times)

### Track Times
- `GET /api/track-times` - Get all track times (sorted by time)
- `GET /api/track-times/{id}` - Get specific track time
- `GET /api/track-times/track/{city}` - Get all times for a specific track
- `GET /api/track-times?city={city}&driverName={name}` - Search track times
- `POST /api/track-times` - Record new track time
- `POST /api/track-times/fill` - Populate database from JSON
- `PUT /api/track-times/{id}` - Update track time
- `DELETE /api/track-times/{id}` - Delete track time

## 📊 Data Initialization

The application automatically loads initial data on startup:
1. **25 F1 Drivers** from `f1-drivers.json` (current grid + legends)
2. **28 Racing Tracks** from `f1-tracks.json` (current calendar + historic tracks)
3. **30 Track Times** available via `/fill` endpoint from `track-times.json`

## 🛠️ Running the Application

### Clone the Repository
```bash
git clone https://github.com/INBPM0522-TR/web-development-2025-RichardHUN.git
cd web-development-2025-RichardHUN/qualificationSystem
```

### Prerequisites
- **Java 21** (or compatible JDK like Java 17+)
- **Maven** (optional - Maven wrapper included)

### Method 1: Using Maven Wrapper (Recommended)

**Windows:**
```bash
mvnw.cmd clean spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw clean spring-boot:run
```

### Method 2: Using Maven Directly
```bash
mvn clean spring-boot:run
```

### Method 3: Using IDE (IntelliJ IDEA / Eclipse)
1. Open the project in your IDE
2. Wait for Maven dependencies to download
3. Right-click on `QualificationSystemApplication.java`
4. Select "Run"

### Method 4: Build JAR and Run
```bash
# Build the JAR
mvnw.cmd clean package

# Run the JAR
java -jar target/qualificationSystem-0.0.1-SNAPSHOT.jar
```


---

**Application URL:** `http://localhost:8084`  
**H2 Console:** `http://localhost:8084/db` (username: `asd`, password: `asd`)


## 📝 Example Requests

### Create a Driver
```json
POST /api/racing-drivers
{
  "number": 99,
  "name": "New Driver",
  "team": "New Team"
}
```

### Create a Track
```json
POST /api/racing-tracks
{
  "name": "Debreceni Gokart Pálya",
  "country": "Hungary",
  "city": "Józsa",
  "length": 8.9,
  "elevation": 121
}
```

### Create a Track Time
```json
POST /api/track-times
{
  "time": "1:23.456",
  "driver": {
    "number": 1
  },
  "track": {
    "city": "Monaco"
  }
}
```

## Security (Authentication & Authorization)
- External API access requires HTTP Basic authentication (default admin/admin123); the Angular frontend is allowed to use the API from the app origin.
- Static resources and same-origin requests are permitted; other requests must authenticate.

## Frontend integration
- The Angular app is built into `src/main/resources/static/` and served under the context path `/qualificationSystem/`.
- Rebuild the frontend in `qualificationSystem-ng` and run `mvnw.cmd clean package` to include the latest build.
