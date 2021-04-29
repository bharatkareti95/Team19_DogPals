FROM maven:3.6.3-jdk-8 AS build-env
WORKDIR /app

COPY pom.xml ./
COPY mvnw ./
RUN mvn dependency:go-offline
RUN mvn spring-javaformat:help

COPY . ./
RUN mvn spring-javaformat:apply
RUN mvnw -Pprod clean package  -DfinalName=dogpals_training

FROM openjdk:8-jre-alpine
EXPOSE 9001
WORKDIR /app

COPY --from=build-env /app/target/dogpals_training.jar ./dogpals_training.jar
CMD ["/usr/bin/java", "-jar", "/app/dogpals_training.jar"]


