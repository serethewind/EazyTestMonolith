FROM openjdk:17-jdk-alpine
ARG JAR-FILE=build/*.jar
COPY ./build/libs/eazy-test-0.0.1-SNAPSHOT-plain.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]