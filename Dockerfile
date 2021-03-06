FROM gradle:jdk15 as build

WORKDIR "/home/gradle/"

COPY --chown=gradle:gradle "." "/home/gradle/"

RUN /home/gradle/gradlew dependencies && \
    /home/gradle/gradlew build

FROM openjdk:15-jdk-alpine
COPY --from=build /home/gradle/build/libs/gradle-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]