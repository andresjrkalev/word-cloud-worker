FROM gradle:latest AS build

WORKDIR /home/worker/src

COPY build.gradle settings.gradle gradlew ./

COPY gradle ./gradle

COPY src ./src

RUN ./gradlew clean build

FROM openjdk:19-alpine

WORKDIR /worker

COPY --from=build /home/worker/src/build/libs/*.jar worker.jar

ENTRYPOINT ["java", "-jar", "./worker.jar"]
