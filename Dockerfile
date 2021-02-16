FROM gradle:jdk15 as build

WORKDIR "/home/gradle/"

COPY --chown=gradle:gradle "./build.gradle.kts" "/home/gradle/"
RUN gradle dependencies

COPY --chown=gradle:gradle "./src/" "/home/gradle/src/"
RUN gradle build

FROM openjdk:15-jdk-alpine

COPY --from=build /home/gradle/build/libs/gradle-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]