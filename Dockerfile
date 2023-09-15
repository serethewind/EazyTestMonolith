FROM openjdk:17.0.1-jdk-slim
COPY --from=build /build/libs/eazy-test-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8800
ENTRYPOINT ["java", "-jar", "/app.jar"]

#FROM openjdk:17-jdk-alpine