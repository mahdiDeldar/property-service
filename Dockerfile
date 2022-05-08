FROM gradle:jdk11 AS builder
WORKDIR /src
COPY . ./
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM openjdk:slim-buster
RUN apt update
RUN apt install -y wget
RUN apt install -y net-tools

RUN adduser spring
USER spring:spring

ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.chubock.propertyservice.PropertyServiceApplication"]

