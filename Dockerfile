FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=build/libs/*.jar
COPY ./build/libs/gif-generator-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]