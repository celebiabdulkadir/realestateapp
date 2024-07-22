#Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install

# Add the application's jar to the image
COPY target/advert-0.0.1-SNAPSHOT.jar advert-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "advert-0.0.1-SNAPSHOT.jar"]