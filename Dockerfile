docker build --build-arg JAR_FILE=target/*.jar -t myorg/myapp .mvn
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
docker build -t myorg/myapp .
