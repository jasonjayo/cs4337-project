# Use an official Java runtime as a parent image
FROM openjdk:23

# Set the working directory
WORKDIR /app

# Copy the JAR file to the container
COPY build/libs/event-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (e.g., 8080)
EXPOSE 8083

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
