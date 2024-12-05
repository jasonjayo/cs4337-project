# Club Management System

This project is a microservices-based application designed to manage clubs, including players, teams and events. It leverages Spring Boot, Eureka Service Discovery, and Docker for scalability and easy service management.

## Project Structure

The project is organized as follows:
- **api-gate**: Serves as the single entry point for all client requests.    
- **auth-service**: Integrates with Google OAuth 2.0 to allow users to log in with their Google accounts.   
- **eureka-service**: Manages service discovery, allowing different services to register and discover each other.
- **event-service**: Handles event creation, attendance tracking, and integrates with Google Calendar for syncing.
- **player-service**: Manages player information, such as names and emails.
- **team-service**: Handles team-related information, including team lineup and player assignments.
- **database**: Stores persistent data for all services.

## Prerequisites

- **Java 23**
- **Docker and Docker Compose** (for running the services)
- **Gradle** (for building the project)
- **MySQL** (if running the database locally)