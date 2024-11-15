# Club Management System

This project is a microservices-based application designed to manage clubs, including players, teams, events, and manager functionalities. It leverages Spring Boot, Eureka Service Discovery, and Docker for scalability and easy service management.

## Project Structure

The project is organized as follows:

- **eureka-service**: Manages service discovery, allowing different services to register and discover each other.
- **event-service**: Handles event creation, attendance tracking, and integrates with Google Calendar for syncing.
- **manager-service**: Manages event creation and other manager-related functionalities.
- **player-service**: Manages player information, such as names and emails.
- **team-service**: Handles team-related information, including team lineup and player assignments.
- **database**: Stores persistent data for all services.

## Prerequisites

- **Java 23**
- **Docker and Docker Compose** (for running the services)
- **Gradle** (for building the project)
- **MySQL** (if running the database locally)

## Getting Started

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/soccer-club-management.git
   cd soccer-club-management
   ```

2. **Setup the Database**:    
Ensure MySQL is installed and running, or use Docker Compose to set up the database:
    ```bash
   docker-compose up
   ```

3. **Configure Database**:    
Update the database configurations in each service’s application.properties file (located in src/main/resources) if needed.

4. **Run Eureka Service**:    
Start the Eureka Service to enable service discovery:
    ```bash
    cd eureka-service
    ./gradlew bootRun
    ```

5. **Run Other Services**:    
In separate terminals, navigate to each service folder and start them:
    ```bash
    cd event-service
    ./gradlew bootRun

    cd manager-service
    ./gradlew bootRun

    cd player-service
    ./gradlew bootRun

    cd team-service
    ./gradlew bootRun
