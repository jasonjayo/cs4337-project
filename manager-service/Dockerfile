# Use an official OpenJDK image as a base
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the build artifact (jar file) into the container
COPY target/manager-service.jar /app/manager-service.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "manager-service.jar"]