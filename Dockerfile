#Stage 1: Build the application
FROM maven:3.8.4-jdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

#Stage 2: Run the application
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/WinthiHomeApp-0.0.1-SNAPSHOT.jar  ./spring-winthihome-booking.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-winthihome-booking.jar"]

