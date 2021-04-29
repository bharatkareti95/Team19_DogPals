FROM maven:3.6.3-jdk-8 AS build-env
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline
RUN mvn spring-javaformat:help

COPY . ./
RUN mvn spring-javaformat:apply
RUN mvn compile jib:buildTar -DfinalName=dogpals_training

FROM openjdk:8-jre-alpine
EXPOSE 9000
WORKDIR /app


