FROM openjdk:21-jdk
VOLUME /tmp
COPY build/libs/calendar-service-0.0.1-SNAPSHOT.jar calendar-service.jar
ENTRYPOINT ["java","-jar","/calendar-service.jar"]
