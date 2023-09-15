FROM openjdk:17-jdk-alpine
ARG JAR-FILE=build/*.jar
COPY ./build/libs/eazy-test-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8800
#LABEL authors="Serethewind"

ENTRYPOINT ["java", "-jar","/app.jar"]