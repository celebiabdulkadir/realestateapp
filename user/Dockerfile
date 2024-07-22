#Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install

# Add the application's jar to the image
COPY target/user-0.0.1-SNAPSHOT.jar user-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "user-0.0.1-SNAPSHOT.jar"]