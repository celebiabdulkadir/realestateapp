# First stage: Build the application with Eclipse Temurin and Maven
FROM eclipse-temurin:21 as build
RUN apt-get update && apt-get install -y maven
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Second stage: Run the application with JDK 21
FROM eclipse-temurin:21-jre
COPY --from=build /app/target/*.jar /usr/app/serviceregistry.jar
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "serviceregistry.jar"]
