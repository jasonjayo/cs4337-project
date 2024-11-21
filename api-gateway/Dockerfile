# Use an official Java runtime as a parent image
FROM openjdk:23

# Set a working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
# Replace 'eureka-server-0.0.1-SNAPSHOT.jar' with the actual name of your JAR file
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Expose the port that the Eureka server listens on (8761)
EXPOSE 8080

# Default command to run the JAR file
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
