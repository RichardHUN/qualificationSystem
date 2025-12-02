# F1 Qualification System

A Spring Boot REST API application with an Angular frontend for tracking and managing Formula 1 racing driver lap times across various racing tracks.

## 📋 Project Scope

This application records track times set by racing drivers on racing tracks, enabling:
- **Driver Management**: Track F1 drivers with their numbers, names, and teams
- **Track Management**: Store racing circuit information including location and specifications
- **Lap Time Recording**: Record and query lap times achieved by drivers on specific tracks
- **Cascade Operations**: Automatically handle related data when deleting drivers or tracks

## 🏗️ Architecture

The project follows a **layered architecture** with proper separation of concerns. The system consists of an Angular Single Page Application (served as static assets) that communicates with the Spring Boot REST API. The REST API implements controllers -> services -> repositories and uses an H2 in-memory database for storage.

The frontend is implemented as two layers: a Presentation layer (Angular components / views) and a Client Services layer (Angular services that call the REST API).

Frontend and backend relationship (high-level):

```
+----------------------+          HTTP/REST           +---------------------------+
|      Frontend        | <--------------------------> |   Spring Boot Backend     |
|     (Angular SPA)    |                              |                           |
|                      |                              |  ┌─────────────────────┐  |
|  ┌Presentation────┐  |                              |  │   Controllers (API) │  |
|  │ Components / UI│  |                              |  ├─────────────────────┤  |
|  └────────────────┘  |                              |  │     Services        │  |
|  ┌Client Services─┐  |                              |  ├─────────────────────┤  |
|  │ HTTP/Api calls │  |                              |  │   Repositories      │  |
|  └────────────────┘  |                              |  ├─────────────────────┤  |
+----------------------+                              |  │     H2 Database     │  |
                                                      |  └─────────────────────┘  |
                                                      +---------------------------+

(Angular is built into static files and served by Spring Boot under the application's context path.)
```

## 🚀 Technologies

- **Java 21** (LTS)
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Lombok**
- **Jackson** (JSON processing)
- **Maven**
- **Angular 20.x** (SPA frontend)
- **Node.js & npm** (for building the Angular app)
- **Bootstrap** (styling)

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
- **Node.js** (recommended v18+)
- **npm** (bundled with Node.js) - required if you want to rebuild the Angular frontend

> Note: Node.js and npm are only necessary if you want to rebuild or modify the Angular frontend located in the `qualificationSystem-ng` folder. The backend JAR already includes a built frontend under `src/main/resources/static` when packaged.

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

**Application URL:** `http://localhost:8084/qualificationSystem/`  
**H2 Console:** `http://localhost:8084/qualificationSystem/db` (username: `asd`, password: `asd`)


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
- The Angular app (source in `qualificationSystem-ng`) is built into `src/main/resources/static/` and served under the application's context path `/qualificationSystem/` so visiting `http://localhost:8084/qualificationSystem/` loads the SPA.

How to rebuild and include the frontend (recommended workflow):
1. Open a terminal and go to the Angular project:

```bash
cd qualificationSystem-ng
```

2. Install dependencies (if needed):

```bash
npm install
```

3. Build the production assets:

```bash
npm run build
```

The compiled files will be written to `dist/qualificationSystem-ng/` (Angular default). Copy the production build into the backend static resources so Spring Boot serves them:

```bash
# from repository root
rm -rf qualificationSystem/src/main/resources/static/* ; mkdir -p qualificationSystem/src/main/resources/static
cp -r qualificationSystem-ng/dist/qualificationSystem-ng/* qualificationSystem/src/main/resources/static/
```

### Easier method: build & package (single step)

> Alternatively: running the Maven package phase from the backend (`mvnw.cmd clean package` or `mvn clean package`) will also run the frontend build (via the frontend-maven-plugin) and copy the generated `dist` output into `qualificationSystem/src/main/resources/static/` automatically as part of the build.

4. Rebuild the backend JAR to package the updated frontend:

```bash
mvnw.cmd clean package
```

5. Run the application and open:

```
http://localhost:8084/qualificationSystem/
```

Notes:
- The Angular app calls the backend REST API on the same origin (relative paths such as `/api/...`), so CORS is not required for the packaged app. During development (when running `ng serve`) a `proxy.conf.json` is included to forward API calls to the backend.
- The backend context-path (`/qualificationSystem`) is configured in `src/main/resources/application.yaml`. If you change it, update the frontend base href or the copy location accordingly.
